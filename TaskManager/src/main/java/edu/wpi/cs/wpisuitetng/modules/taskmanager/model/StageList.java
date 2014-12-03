/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: William Temple
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * @author wmtemple
 *
 * This is a wrapper class for the ordered storage of Stages within our module.
 * 
 * This class allows us to store Stages in a list, and manipulate it as a collection just as in a LinkedList, but at the
 * same time it is a model for the database.
 *
 */
public class StageList extends AbstractModel implements List<Stage>, Queue<Stage> {

	@SuppressWarnings("unused")
	private int id = 1;
	private LinkedList<Stage> stageList;
	
	/**
	 * Creates a new, empty StageList
	 */
	public StageList() {
		this.stageList = new LinkedList<Stage>();
	}
	
	/**
	 * Creates a new StageList, pre-filling it with certain items
	 * @param c A collection to include in the stagelist by default
	 */
	public StageList(Collection<Stage> c) {
		super();
		this.stageList = new LinkedList<Stage>(c);
	}
	
	/**
	 * Creates a new StageList, pre-filling it with the contents of the array
	 * @param arr The array to fill the list
	 */
	public StageList(Stage[] st) {
		super();
		this.stageList = (LinkedList<Stage>) Arrays.asList(st);
		
	}
	
	@Override
	public String toString() {
		String output = new String("[");
		for( Stage s : this ) {
			output += "{" + s.toString() + "}, ";
		}
		return output + "]";
	}
	
	public static StageList fromJson(String json) {
		return new Gson().fromJson(json, StageList.class);
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		throw new RuntimeException("Called save() on StageList");
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		throw new RuntimeException("Called delete() on StageList");
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJson()
	 */
	@Override
	public String toJson() {
		return new Gson().toJson(this, StageList.class);
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {
		throw new RuntimeException("Called identify() on StageList");
	}

	@Override
	public boolean offer(Stage e) {
		return stageList.offer(e);
	}

	@Override
	public Stage remove() {
		return stageList.remove();
	}

	@Override
	public Stage poll() {
		return stageList.poll();
	}

	@Override
	public Stage element() {
		return stageList.element();
	}

	@Override
	public Stage peek() {
		return stageList.peek();
	}

	@Override
	public int size() {
		return stageList.size();
	}

	@Override
	public boolean isEmpty() {
		return stageList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return stageList.contains(o);
	}

	@Override
	public Iterator<Stage> iterator() {
		return stageList.iterator();
	}

	@Override
	public Object[] toArray() {
		return stageList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return stageList.toArray(a);
	}

	@Override
	public boolean add(Stage e) {
		return stageList.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return stageList.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return stageList.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Stage> c) {
		return stageList.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Stage> c) {
		return stageList.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return stageList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return stageList.retainAll(c);
	}

	@Override
	public void clear() {
		stageList.clear();
	}

	@Override
	public Stage get(int index) {
		return stageList.get(index);
	}

	@Override
	public Stage set(int index, Stage element) {
		return stageList.set(index, element);
	}

	@Override
	public void add(int index, Stage element) {
		stageList.add(index, element);
	}

	@Override
	public Stage remove(int index) {
		return stageList.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return stageList.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return stageList.lastIndexOf(o);
	}

	@Override
	public ListIterator<Stage> listIterator() {
		return stageList.listIterator();
	}

	@Override
	public ListIterator<Stage> listIterator(int index) {
		return stageList.listIterator(index);
	}

	@Override
	public List<Stage> subList(int fromIndex, int toIndex) {
		return stageList.subList(fromIndex, toIndex);
	}

}
