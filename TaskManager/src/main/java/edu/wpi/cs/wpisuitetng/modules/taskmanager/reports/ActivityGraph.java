/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Six-Appeal
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Map.Entry;

import org.jscience.mathematics.vector.Float64Matrix;


/**
 * G(V,E) and adjacency matrix representation of user activity over a specified time span
 * @author nhhughes
 *
 */
public class ActivityGraph {

	private Map<UserActivity, List<UserActivity>> graph;
	private Float64Matrix adjacencyMatrix;
	private Map<UserActivity, Integer> orders;
	private List<UserActivity> nodes;
	
	/**
	 * Makes an empty graph to use
	 */
	public ActivityGraph() {
		this.graph = new HashMap<UserActivity, List<UserActivity>>();
		this.nodes = new ArrayList<UserActivity>();
	}
	
	/**
	 * Adds a node to the empty graph
	 * @param nodeToAdd Node to add to the graph
	 * @return whether the add was successful or not
	 */
	public boolean addNode(UserActivity nodeToAdd) {
		if (this.graph.containsKey(nodeToAdd)) {
			return false;
		}
		this.nodes.add(nodeToAdd);
		this.graph.put(nodeToAdd, new LinkedList<UserActivity>());
		return true;
	}
	
	/**
	 * Adds an edge to the graph.  If the nodes in the edge specified don't exist, add the nodes as well
	 * @param source source node
	 * @param destination destination node
	 * @return whether the edge was added successfully or not
	 */
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
	
	/**
	 * Heuristic Function for determining edge weight in the adjacency matrix
	 * @param source source node
	 * @param destination destination node
	 * @return a double representing the edge weight between the source and destination
	 */
	private double someFunc(UserActivity source, UserActivity destination) {
		double to_return = ((double)(source.getImportance()  + destination.getImportance()))/((double)(source.getImportance() + destination.getImportance()));
		System.out.println(to_return);
		return to_return;
	}

	/**
	 * Getter for the order of the nodes in the graph (for use with the adjacency matrix)
	 * @return a map keyed by UserActivity with indices in the adjacency matrix as values
	 */
	public Map<UserActivity, Integer> getNodeOrders() {
		return orders;
	}
	
	/**
	 * Calculates the newest representation of the adjacency matrix based on recent updates to the graph
	 */
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
	
	/**
	 * Gets the user activity associated with the key of the UserActivity Node (the name of the user)
	 * @param key name of the user to find
	 * @return the UserActivity object associated with the user
	 */
	public UserActivity getNode(String key) {
		for (UserActivity user: this.nodes) {
			if (user.getName().equals(key)) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Updates the user activity in the graph based on the inputted user activity
	 * @param user user activity to update
	 * @return whether the update was successful or not
	 */
	public boolean updateNode(UserActivity user) {
		for (UserActivity oldUser : this.nodes) {
			if (oldUser.getName().equals(user.getName())) {
				oldUser.copyFrom(user);
			}
		}
		return false;
	}
	
	/**
	 * Gets the adjacency matrix representation of the graph
	 * calcAdjacencyMatrix must be called before this between updates of the graph for an accurate representation
	 * @return weighted adjacency matrix of the graph
	 */
	public Float64Matrix getAdjacencyMatrix() {
		return this.adjacencyMatrix;
	}
	
}
