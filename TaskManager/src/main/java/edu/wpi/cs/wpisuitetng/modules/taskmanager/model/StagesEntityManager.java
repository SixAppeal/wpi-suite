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

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

public class StagesEntityManager implements EntityManager<StageList> {

	Data db;

	public StagesEntityManager(Data data) {
		db = data;
	}

	@Override
	public StageList makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		
		final StageList newStageList = StageList.fromJson(content);
		if(!db.save(newStageList, s.getProject())) {
			throw new WPISuiteException();
		}
		return newStageList;
		
	}

	@Override
	public StageList[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		StageList[] stageList = null;
		try {
			stageList = db.retrieve(StageList.class, "id", 1, s.getProject()).toArray(new StageList[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(stageList.length < 1 || stageList[0] == null) {
			throw new NotFoundException();
		}
		return stageList;
	}

	@Override
	public StageList[] getAll(Session s) throws WPISuiteException {
		return db.retrieveAll(new StageList()).toArray(new StageList[0]);
	}

	@Override
	public StageList update(Session s, String content) throws WPISuiteException {
		StageList updatedSL = StageList.fromJson(content);

		//Gets old task, modifies it, and saves it again
		List<Model> oldSLList = db.retrieve(StageList.class, "id", 1, s.getProject());
		
		if(oldSLList.size() < 1 || oldSLList.get(0) == null) {
			throw new BadRequestException("No StageList exists.");
		}

		StageList existingSL = (StageList)(oldSLList.get(0));	

		// update the new task
		existingSL.clear();
		existingSL.addAll(updatedSL);

		if(!db.save(existingSL, s.getProject())) {
			throw new WPISuiteException();
		}

		return existingSL;
	}

	@Override
	public void save(Session s, StageList model) throws WPISuiteException {
		db.save(model);
	}

	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		throw new NotImplementedException();
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		throw new NotImplementedException();
	}

	@Override
	public int Count() throws WPISuiteException {
		return 1;
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

}
