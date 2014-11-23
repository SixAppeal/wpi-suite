package edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;


/**
 * Search functionality to find tasks within cache
 * 
 * @author akshoop
 * @author nhhughes
 */
public class Search {
	private boolean isInit;
	private boolean create = true;
	private String indexPath = "index";
	
	/**
	 * General constructor for Search class
	 */
	public Search() {
		isInit = false;
	}
	
	/**
	 * Initialize function
	 */
	public void initialize() {
		isInit = true;
	}
	
	/**
	 * Main search method
	 * 
	 * @param input The string that user wants to search for
	 * @throws SearchException 
	 * @throws IOException
	 * @throws ParseException 
	 */
	public List<Integer> searchFor(String input) throws SearchException, IOException, ParseException {
		if (!isInit)
			throw new SearchException("Search is not initialized.");
		
		String titleResult = "";
		String descResult = "";
		String assignedToResult = "";
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_10_0);
		
		QueryParser parserTitle = new QueryParser(Version.LUCENE_4_10_0, "title", analyzer);
		QueryParser parserDesc = new QueryParser(Version.LUCENE_4_10_0, "description", analyzer);
		QueryParser parserAssignedTo = new QueryParser(Version.LUCENE_4_10_0, "assignedTo", analyzer);
		
		Query queryTitle = parserTitle.parse(input);
		Query queryDesc = parserDesc.parse(input);
		Query queryAssignedTo = parserAssignedTo.parse(input);
		
//		System.out.println("Searching for title: " + queryTitle.toString("title"));
//		System.out.println("Searching for desc: " + queryDesc.toString("description"));
		
		TopDocs resultsTitle = searcher.search(queryTitle, 5);
		TopDocs resultsDesc = searcher.search(queryDesc, 5);
		TopDocs resultsAssignedTo = searcher.search(queryAssignedTo, 5);
		
		ScoreDoc[] hitsTitle = resultsTitle.scoreDocs;
		ScoreDoc[] hitsDesc = resultsDesc.scoreDocs;
		ScoreDoc[] hitsAssignedTo = resultsAssignedTo.scoreDocs;
//		System.out.println("length of hits title is :" + hitsTitle.length);
//		System.out.println("length of hits desc is :" + hitsDesc.length);
		
		for (ScoreDoc sd: hitsTitle) {
			System.out.println("sd title doc and score is: " + sd.doc + " " + sd.score);
		}
		for (ScoreDoc sd: hitsDesc) {
			System.out.println("sd desc doc and score is: " + sd.doc + " " + sd.score);
		}
		for (ScoreDoc sd: hitsAssignedTo) {
			System.out.println("sd assignedTo doc and score is: " + sd.doc + " " + sd.score);
		}

		for (ScoreDoc sd : hitsTitle) {
			String title = searcher.doc(sd.doc).getField("title").stringValue();
			String descrip = searcher.doc(sd.doc).getField("description").stringValue();
			String members = searcher.doc(sd.doc).getField("assignedTo").stringValue();
			System.out.println(title + " :: " + descrip + " :: " + members);
		}
		
		for (ScoreDoc sd : hitsDesc) {
			String title = searcher.doc(sd.doc).getField("title").stringValue();
			String descrip = searcher.doc(sd.doc).getField("description").stringValue();
			String members = searcher.doc(sd.doc).getField("assignedTo").stringValue();
			System.out.println(title + " :: " + descrip + " :: " + members);
		}
		
		
		Map<Integer, Float> totalScores = new HashMap<Integer, Float>();
		for (int i = 0; i < hitsTitle.length; i++) {
			Integer id = Integer.valueOf(searcher.doc(hitsTitle[i].doc).getField("id").stringValue());
			System.out.println(id);
			totalScores.put(id, hitsTitle[i].score);
		}
		for (ScoreDoc sd : hitsDesc) {
			Integer id = Integer.valueOf(searcher.doc(sd.doc).getField("id").stringValue());
			System.out.println(id);
			if (totalScores.containsKey(id)) {
				totalScores.put(id,  sd.score + totalScores.get(id));
			}
			else {
				totalScores.put(id, sd.score);
			}
		}
		for (ScoreDoc sd : hitsAssignedTo) {
			
			Integer id = Integer.valueOf(searcher.doc(sd.doc).getField("id").stringValue());
			System.out.println(id);
			if (totalScores.containsKey(id)) {
				totalScores.put(id,  sd.score + totalScores.get(id));
			}
			else {
				totalScores.put(id, sd.score);
			}
		}
		System.out.println(totalScores.size());
		List<Integer> toReturn = new LinkedList<Integer>();
		Integer[] TaskIdsToReturn = totalScores.keySet().toArray(new Integer[0]);
		Boolean flag = false;
		for (int i = 0; i < TaskIdsToReturn.length; i ++){
			flag = false;
			for (int j = 0; j < toReturn.size(); j ++) {
				if (totalScores.get(TaskIdsToReturn[i]) < totalScores.get(toReturn.get(j))) {
					toReturn.add(j, TaskIdsToReturn[i]);
					flag = true;
					break;
				}
			}
			if (!flag) {
				toReturn.add(TaskIdsToReturn[i]);
			}
			
		}
		
		
		if (hitsTitle.length > 0) {
			
			Document docTitle = searcher.doc(hitsTitle[0].doc);
			titleResult = docTitle.getField("title").stringValue();
		}
		if (hitsDesc.length > 0) {
			Document docDesc = searcher.doc(hitsDesc[0].doc);
			descResult = docDesc.getField("description").stringValue();
		}
		if (hitsAssignedTo.length > 0) {
			Document docAssignedTo = searcher.doc(hitsAssignedTo[0].doc);
			assignedToResult = docAssignedTo.getField("assignedTo").stringValue();
		}
		
		reader.close();
		
//		return titleResult + " " + descResult + " " + assignedToResult;
		return toReturn;
	}
	
	/**
	 * Creates index for each task which is necessary for searching
	 * @param taskList The list of tasks through which we will make an index for each one
	 * @throws IOException 
	 */
	public void createIndex(List<Task> taskList) throws IOException {
		Directory dir = FSDirectory.open(new File(indexPath));
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_10_0);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_0, analyzer);
		
		if (create) {
			// Create a new index in directory, removing any previously indexed documents
			iwc.setOpenMode(OpenMode.CREATE);
		}
		else {
			// Add new documents to an existing index
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}
		
		IndexWriter writer = new IndexWriter(dir, iwc);
		
		indexTasks(writer, taskList);
		
		writer.close();
	}
	
	/**
	 * Indexes the given file using the given writer, or if a directory is given,
	 * recurses over files and directories found under the given directory.
	 * 
	 * @param writer Writer to the index where the given file/dir info will be stored
	 * @param taskList The list of tasks to go through to make indexes
	 * @throws IOException 
	 */
	private void indexTasks(IndexWriter writer, List<Task> taskList) throws IOException {
		// make a new empty document
		Document doc = new Document();
		
		for (Task t: taskList) {
			String members = "";
			for (String s: t.getAssignedTo()) {
				members += s + " ";
			}
			// Make the primary search field the title of the task t
			doc.add(new TextField("title", t.getTitle(), Field.Store.YES));
			
			// Add contents of description from task t
			doc.add(new TextField("description", t.getDescription(), Field.Store.YES));
			
			// Add contents of assigned to member list from task t
			doc.add(new TextField("assignedTo", members, Field.Store.YES));
			
			// Used to store task ID
			doc.add(new StringField("id", Integer.toString(t.getId()), Field.Store.YES));
			System.out.println(t.getId());
			writer.addDocument(doc);
		}
		
		
	}
}
