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
import java.util.List;
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

		generateHistory(allHistory, allTasks);
		generateCompletedTasks(titles, allTasks, startDate, endDate);
		generateReport(titles);

	}

	/**
	 * Generate the html and png files need
	 * @param completedTasks tasks completed in time span
	 */
	public void generateReport(List<String> completedTasks) {
		try {
			PrintStream out = new PrintStream(file);
			out.print("<html><body><h1>Task Manager Report:</h1><br>");
			out.print("<h3>Start Date: </h3>" + start);
			out.print("<br><h3>End Date: </h3>" + end);
			out.print("<br><br>");
			out.print("<h3>Members Included</h3>");
			printMembers(out);
			out.println("<h3>Tasks Completed During Time Period</h3>");
			printTasks(out, completedTasks);
			out.print("<div>");
			out.print("<img src=\"actualeffort.png\" alt=\"Actual Effort\" style=\"width:400px;height:400px\">");
			out.print("</div><div>");
			out.print("<img src=\"estimatedimportance.png\" alt=\"Estimated Importance of Each Team Member\" style=\"width:400px;height:400px\">");
			out.print("</div><div>");
			out.print("<img src=\"importancegraph.png\" alt=\"Graph Visualization of Importance\" style=\"width:400px;height:400px\">");
			out.print("</div></body>");
			out.print("</html>");
			out.close();

			String path = file.getAbsolutePath();
			String name = file.getName();
			int location = path.lastIndexOf(name);
			path = path.substring(0, location);

			double values[] = {1.0};
			CategoryDataset dataSet = createData(values, members, "Actual Effort");
			createChart(dataSet, path + "actualeffort.png", "Actual Effort");
			createChart(dataSet, path + "estimatedimportance.png", "Importance");
			createChart(dataSet, path + "importancegraph.png", "Importance");

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
				//do stuff involving history
			}
			List<Comment> taskComments = toAnalyze.getComments();
			for (Comment commentToAnalyze : taskComments) {
				//do stuff involving comments
			}
		}
		List<HistoryElement> elements = new ArrayList<HistoryElement>();
		while (!history.isEmpty()) {
			elements.add(history.remove());
		}
		for (int i = 1; i < elements.size(); i++) {
			//update contributors with 
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
		return true;
	}

	/**
	 * Make a list of all completed tasks during a time span
	 * @param titles
	 * @param tasks
	 * @param start
	 * @param end
	 */
	public void generateCompletedTasks(List<String> titles, Task[] tasks, Date start, Date end) {
		for (Task toAnalyze : tasks ) {
			if (isCompleted(start, end, toAnalyze)) {
				titles.add(toAnalyze.getTitle());
			}
		}
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
	private CategoryDataset createData(double[] data, List<String> members, String category) {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for (int k = 0; k < members.size(); k++) {
			dataSet.setValue(data[k], members.get(k), category);// populate
		}
		return dataSet;
	}
}
