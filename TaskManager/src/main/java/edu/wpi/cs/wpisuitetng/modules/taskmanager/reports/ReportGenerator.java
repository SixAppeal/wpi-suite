/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Six-Appeal
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.taskmanager.reports;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ThreadSafeLocalCache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Activity;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Comment;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

/**
 * Class to generate the report on member contributions 
 * @author nhhughes
 *
 */
public class ReportGenerator {

	private File file;
	private Date start;
	private Date end;
	private List<String> members;
	private List<Double> actualEffortValues;
	private List<String> actualEffortMembers;
	
	/**
	 * Creates a report based on the parameters passed in
	 * @param file the file path for the index.html file in the report
	 * @param startDate start date for analysis
	 * @param endDate end date for analysis
	 * @param cache copy of cache
	 * @param members members to analyze
	 */
	public ReportGenerator(File file, Date startDate, Date endDate, ThreadSafeLocalCache cache, List<String> members) {
		this.file = file;
		this.start = startDate;
		this.end = endDate;
		this.members = members;
		ActivityGraph userImportanceGraph = new ActivityGraph();
		PriorityQueue<HistoryElement> allHistory = new PriorityQueue<HistoryElement>();
		Task[] allTasks = (Task []) cache.retrieve("task");
		List<String> titles = new ArrayList<String>();
		Map<String, Double> actualEffort;
		generateHistory(allHistory, allTasks);
		actualEffort = generateCompletedTasks(titles, allTasks, startDate, endDate);
		List<Double> actualMemberEfforts = new ArrayList<Double>();
		List<String> actualMembers = new ArrayList<String>();
		for (Entry<String, Double> actualMemberTuple : actualEffort.entrySet()) {
			actualMemberEfforts.add(actualMemberTuple.getValue());
			actualMembers.add(actualMemberTuple.getKey());
		}
		this.actualEffortValues = actualMemberEfforts;
		this.actualEffortMembers = actualMembers;
		generateReport(titles);

	}

	/**
	 * Generate the html and png files need
	 * @param completedTasks tasks completed in time span
	 */
	public void generateReport(List<String> completedTasks) {
		try {
			PrintStream out = new PrintStream(file);
			out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"report.css\"/>");
			out.print("<html><body><h1>Task Manager Report:</h1><hr><br>");
			out.print("<h3>Start Date: </h3>" + start);
			out.print("<br><h3>End Date: </h3>" + end);
			out.print("<hr><br><br>");
			out.print("<h3>Members Included</h3>");
			printMembers(out);
			out.println("<hr><h3>Tasks Completed During Time Period</h3>");
			printTasks(out, completedTasks);
			out.print("<hr><div>");
			out.print("<img src=\"actualeffort.png\" alt=\"Actual Effort\" style=\"width:400px;height:400px\">");
			out.print("</div><hr><div>");
			out.print("<img src=\"estimatedimportance.png\" alt=\"Estimated Importance of Each Team Member\" style=\"width:400px;height:400px\">");
			out.print("</div><hr><div>");
			out.print("<img src=\"importancegraph.png\" alt=\"Graph Visualization of Importance\" style=\"width:400px;height:400px\">");
			out.print("</div></body>");
			out.print("</html>");
			out.close();

			String path = file.getAbsolutePath();
			String name = file.getName();
			int location = path.lastIndexOf(name);
			path = path.substring(0, location);

			File cssFile = new File(path + "report.css");
			PrintStream outCss = new PrintStream(cssFile);
			outCss.println("@import url(http://fonts.googleapis.com/css?family=Ubuntu:300,400|Open+Sans:400italic,700italic,400,700);");
			outCss.println("html {");
			outCss.println("\tmargin: 0;");
			outCss.println("\tpadding: 0;");
			outCss.println("}\n");
			outCss.println("body {");
			outCss.println("\tfont-family: 'Open Sans', sans-serf, arial;");
			outCss.println("}\n");
			outCss.println("h1,");
			outCss.println("h2,");
			outCss.println("h3 {");
			outCss.println("\tfont-family: 'Ubuntu', 'Helvetica Neue', Helvetica, arial;");
			outCss.println("}\n");
			outCss.println("a {");
			outCss.println("\tcolor: #337ab7;");
			outCss.println("\ttextdectoration: none;");
			outCss.println("}\n");
			outCss.println("a:hover {");
			outCss.println("\tborder-bottom: 1px dotted;");
			outCss.println("}\n");
			outCss.println("p {");
			outCss.println("\tmargin-bottom: 10px;");
			outCss.println("}\n");
			
			outCss.close();
			
			
			CategoryDataset dataSet = createData(this.actualEffortValues, this.actualEffortMembers, "Actual Effort");
			createChart(dataSet, path + "actualeffort.png", "Actual Effort");
			createChart(dataSet, path + "estimatedimportance.png", "Importance");
//			createChart(dataSet, path + "importancegraph.png", "Importance");

		} catch (FileNotFoundException e) {
			System.out.println("Cannot Create File!");
			e.printStackTrace();
		}
	}

	/**
	 * Generate a list of history events
	 * @param history empty priority queue to use
	 * @param tasks all the tasks current in the database
	 */
	public void generateHistory(PriorityQueue<HistoryElement> history, Task[] tasks) {
		for (Task toAnalyze : tasks) {
			List<Activity> taskHistory = toAnalyze.getActivities();
			for (Activity activityToAnalyze : taskHistory) {
				history.add(new HistoryElement(toAnalyze, activityToAnalyze.getUser(), true, activityToAnalyze.getDate()));
			}
			List<Comment> taskComments = toAnalyze.getComments();
			for (Comment commentToAnalyze : taskComments) {
				history.add(new HistoryElement(toAnalyze, commentToAnalyze.getUser(), true, commentToAnalyze.getDate()));	 
			}
		}
		List<HistoryElement> elements = new ArrayList<HistoryElement>();
		while (!history.isEmpty()) {
			elements.add(history.remove());
		}
		for (int i = 1; i < elements.size(); i++) {
			for (int j = i-1;j>0;j--){
				if(elements.get(i).getAssociatedTask() == elements.get(j).getAssociatedTask()){
					List<String> contributingToAdd = new ArrayList<String>(elements.get(j).getOldContributingMembers());
					contributingToAdd.add(elements.get(j).getContributingMember());
					elements.get(i).setOldContributingMembers(contributingToAdd);
					break;
				}
			}
		}

	}

	/**
	 * Output members to html file
	 * @param out printstream for file
	 */
	public void printMembers(PrintStream out) {
		out.println("<ul>");
		for (String toPrint: this.members) {
			out.print("<li>" + toPrint + "</li>");
		}
		out.println("</ul>");
	}

	/**
	 * Output tasks completed to html file
	 * @param out printstream for file
	 * @param allTasks tasks to output
	 */
	public void printTasks(PrintStream out, List<String> allTasks) {
		out.println("<ol>");
		for (String toPrint: allTasks) {
			out.print("<li>" + toPrint + "</li>");
		}
		out.println("</ol>");
	}

	/**
	 * Check if a task was completed inside a specific time frame
	 * @param start
	 * @param end
	 * @param toCheck
	 * @return
	 */
	public boolean isCompleted(Date start, Date end, Task toCheck) {
		List<Activity> log = new ArrayList<Activity>();
		System.out.println(log);
		for (int i = log.size() - 1; i >= 0; i--) {
			Activity activityToCheck = log.get(i);
			System.out.println(start + " : " + end + " : " + activityToCheck.getDate());
			if (activityToCheck.getActivity().contains("Completed")) {
				if (start.compareTo(activityToCheck.getDate()) == -1 && end.compareTo(activityToCheck.getDate()) == 1) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Make a list of all completed tasks during a time span
	 * @param titles
	 * @param tasks
	 * @param start
	 * @param end
	 */
	public Map<String, Double> generateCompletedTasks(List<String> titles, Task[] tasks, Date start, Date end) {
		Map<String, Double> toReturn = new HashMap<String, Double>();
		for (Task t : tasks) {
			System.out.println(t);
		}
		for (Task toAnalyze : tasks ) {
			if (isCompleted(start, end, toAnalyze)) {
				titles.add(toAnalyze.getTitle());
				for (String assignedMember : toAnalyze.getAssignedTo()) {
					if (toReturn.containsKey(assignedMember)) {
						toReturn.put(assignedMember, toReturn.get(assignedMember + toAnalyze.getActualEffort()));
					}
					else {
						toReturn.put(assignedMember, (double)toAnalyze.getActualEffort());
					}
				}
			}
		}
		return toReturn;
	}

	/**
	 * Create a chart based data passed in. Adapted from Requirements Manager
	 * @param dataset dataset to plot
	 * @param path file path to plot to
	 * @param title title of plot
	 */
	private void createChart(CategoryDataset dataset, String path, String title) {
		JFreeChart chart = ChartFactory.createBarChart(
				title,         // chart title
				"Name",               // domain axis label
				"Temp",                  // range axis label
				dataset,                  // data
				PlotOrientation.VERTICAL, // orientation
				true,                     // include legend
				true,
				false
				);

		chart.setBackgroundPaint(Color.white);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.white);

		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());


		File chartFile = new File(path);
		try {
			ChartUtilities.saveChartAsPNG(chartFile, chart, 400, 400);
		} catch (IOException e) {
			System.out.println("Couldn't open file: " + chartFile.getAbsolutePath());
		}
	}

	/**
	 * Create a dataset from some analysis data
	 * @param data numbers associated with each member
	 * @param members members analyzed
	 * @param category type of analysis performed
	 * @return
	 */
	private CategoryDataset createData(List<Double> data, List<String> members, String category) {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for (int k = 0; k < members.size(); k++) {
			dataSet.setValue(data.get(k), members.get(k), category);
		}
		return dataSet;
	}
}
