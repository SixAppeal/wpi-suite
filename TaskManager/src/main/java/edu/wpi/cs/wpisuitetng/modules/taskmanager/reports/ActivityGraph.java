/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Map.Entry;

import org.jscience.mathematics.vector.Float64Matrix;


public class ActivityGraph {

	private Map<UserActivity, List<UserActivity>> graph;
	private Float64Matrix adjacencyMatrix;
	private Map<UserActivity, Integer> orders;
	private List<UserActivity> nodes;
	
	public ActivityGraph() {
		this.graph = new HashMap<UserActivity, List<UserActivity>>();
		this.nodes = new ArrayList<UserActivity>();
	}
	
	public boolean addNode(UserActivity nodeToAdd) {
		if (this.graph.containsKey(nodeToAdd)) {
			return false;
		}
		this.nodes.add(nodeToAdd);
		this.graph.put(nodeToAdd, new LinkedList<UserActivity>());
		return true;
	}
	
	public boolean addEdge(UserActivity source, UserActivity destination) {
		if (this.graph.containsKey(source)) {
			if (!this.graph.get(source).contains(destination)) {
				this.graph.get(source).add(destination);
				if (!this.graph.containsKey(destination)) {
					this.addNode(destination);
				}
				return true;
			}
		}
		else {
			this.addNode(source);
			this.graph.get(source).add(destination);
			if (!this.graph.containsKey(destination)) {
				this.addNode(destination);
			}
			return true;
		}
		return false;
	}
	
	private double someFunc(UserActivity source, UserActivity destination) {
		double to_return = ((double)(source.getImportance()  + destination.getImportance()))/((double)(source.getImportance() + destination.getImportance()));
		System.out.println(to_return);
		return to_return;
	}

	public Map<UserActivity, Integer> getNodeOrders() {
		return orders;
	}
	
	public void calcAdjacencyMatrix() {
		int dimension = this.graph.size();
		double[][] matrixArray = new double[dimension][dimension];
		Map<UserActivity, Integer> columns = new HashMap<UserActivity, Integer>();
		int count = 0;
		for (Entry<UserActivity, List<UserActivity>> e : this.graph.entrySet()) {
			columns.put(e.getKey(), count);
			count++;
		}
		for (Entry<UserActivity, List<UserActivity>> e : this.graph.entrySet()) {
			for (UserActivity toAdd : e.getValue()) {
				matrixArray[columns.get(e.getKey())][columns.get(toAdd)] = someFunc(e.getKey(), toAdd);
			}
		}
		
		adjacencyMatrix = Float64Matrix.valueOf(matrixArray);
		orders = columns;
	}
	
	public UserActivity getNode(String key) {
		for (UserActivity user: this.nodes) {
			if (user.getName().equals(key)) {
				return user;
			}
		}
		return null;
	}
	
	public boolean updateNode(UserActivity user) {
		for (UserActivity oldUser : this.nodes) {
			if (oldUser.getName().equals(user.getName())) {
				oldUser.copyFrom(user);
			}
		}
		return false;
	}
	
	public Float64Matrix getAdjacencyMatrix() {
		return this.adjacencyMatrix;
	}
	
}
