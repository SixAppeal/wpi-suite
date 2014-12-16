/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: SixAppeal
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.taskmanager.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Activity;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

/**
 * Class to generate the report on member contributions 
 * @author nhhughes
 *
 */
public class ReportGenerator {
	
	public ReportGenerator(Date startDate, Date endDate, Cache cache) {
		ActivityGraph userImportanceGraph = new ActivityGraph();
		PriorityQueue<HistoryElement> allHistory = new PriorityQueue<HistoryElement>();
		Task[] allTasks = (Task []) cache.retrieve("task");
		generateHistory(allHistory, allTasks);
		
	}
	
	public void generateHistory(PriorityQueue<HistoryElement> history, Task[] tasks) {
		for (Task toAnalyze : tasks) {
			List<Activity> taskHistory = toAnalyze.getActivities();
			for (Activity activityToAnalyze : taskHistory) {
				//do shit involving comments and history (really ugly merge algorithm but should work pretty well
			}
		}
	}
}
