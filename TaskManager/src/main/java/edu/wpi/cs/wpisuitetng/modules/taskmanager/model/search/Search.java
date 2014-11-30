package edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

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
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
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
	private Directory index;
	private Directory temp_index = null;
	
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
	 * @return toReturn The list of tasks that have the search results
	 * @throws SearchException 
	 * @throws IOException
	 * @throws ParseException 
	 */
	public List<Integer> searchFor(String input) throws SearchException, IOException, ParseException {
		if (!isInit)
			throw new SearchException("Search is not initialized.");
		
		if (this.temp_index != null) {
			this.index.close();
			this.index = temp_index;
			this.temp_index = null;
		}
		
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		
		Query queryTitle = new QueryParser("title", analyzer).parse(input);
		Query queryDescription = new QueryParser("description", analyzer).parse(input);
		Query queryMembers = new QueryParser("assignedTo", analyzer).parse(input);
		
		TopScoreDocCollector collectorTitle = TopScoreDocCollector.create(10, true);
		searcher.search(queryTitle, collectorTitle);
		ScoreDoc[] hits = collectorTitle.topDocs().scoreDocs;
		
		
		//System.out.println("Found " + hits.length + " hit(s) for title.");
		
		List<Integer> toReturn = new ArrayList<Integer>();
		Map<Integer, Integer> rankings = new HashMap<Integer, Integer>();
		
		
		for(int i=0;i<hits.length;++i) {
		    int docId = hits[i].doc;
		    Document d = searcher.doc(docId);
		    //System.out.println((i + 1) + ". " + d.get("id"));
		    rankings.put(Integer.parseInt(d.get("id")), 3*(hits.length-i));
		}
		
		TopScoreDocCollector collectorDescription = TopScoreDocCollector.create(10, true);
		searcher.search(queryDescription, collectorDescription);
		hits = collectorDescription.topDocs().scoreDocs;
		
		//System.out.println("Found " + hits.length + " hit(s) for description.");
		
		for(int i=0;i<hits.length;++i) {
		    int docId = hits[i].doc;
		    Document d = searcher.doc(docId);
		    //System.out.println((i + 1) + ". " + d.get("id"));
		    Integer id = Integer.parseInt(d.get("id"));
		    if (rankings.containsKey(id)) {
		    	rankings.put(id, 2*(hits.length -i) + rankings.get(id));
		    }
		    else {
		    	rankings.put(id, 2*i);
		    }
		}
		
		TopScoreDocCollector collectorMembers = TopScoreDocCollector.create(10, true);
		searcher.search(queryMembers, collectorMembers);
		hits = collectorMembers.topDocs().scoreDocs;
		
		//System.out.println("Found " + hits.length + " hit(s) for members.");
		
		for(int i=0;i<hits.length;++i) {
		    int docId = hits[i].doc;
		    Document d = searcher.doc(docId);
		    //System.out.println((i + 1) + ". " + d.get("id"));
		    Integer id = Integer.parseInt(d.get("id"));
		    if (rankings.containsKey(id)) {
		    	rankings.put(id, (hits.length - i) + rankings.get(id));
		    }
		    else {
		    	rankings.put(id, 2*i);
		    }
		}
		
		PriorityQueue<IdRanking> idList = new PriorityQueue<IdRanking>();
		
		for (Map.Entry<Integer, Integer> i: rankings.entrySet()) {
			idList.add(new IdRanking(i.getKey(), i.getValue()));
//			System.out.println("i get key is: " + i.getKey());
//			System.out.println("i get value is: " + i.getValue());
		}
		
		while (!idList.isEmpty()) {
			toReturn.add(idList.poll().getId());
		}
		
		return toReturn;
	
	}
	
	/**
	 * Creates index for each task, which is necessary for searching
	 * @param taskList The list of tasks through which we will make an index for each one
	 * @throws IOException 
	 */
	public void createIndex(Task[] taskList) throws IOException {
		this.index = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LATEST, analyzer);
		
		if (create) {
			// Create a new index in directory, removing any previously indexed documents
			iwc.setOpenMode(OpenMode.CREATE);
		}
		else {
			// Add new documents to an existing index
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}
		
		IndexWriter writer = new IndexWriter(index, iwc);
		
		indexTasks(writer, taskList);
		
		writer.close();
	}
	
	public void updateIndex(Task[] taskList) throws IOException {
		this.temp_index = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LATEST, analyzer);
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter writer = new IndexWriter(temp_index, iwc);
		indexTasks(writer, taskList);
		writer.close();
	}
	
	public boolean isInitialized() {
		return this.isInit;
	}
	
	/**
	 * Indexes the given file using the given writer, or if a directory is given,
	 * recurses over files and directories found under the given directory.
	 * 
	 * @param writer Writer to the index where the given file/dir info will be stored
	 * @param taskList The list of tasks to go through to make indexes
	 * @throws IOException 
	 */
	private void indexTasks(IndexWriter writer, Task[] taskList) throws IOException {		
		for (Task t: taskList) {
			// make a new empty document
			Document doc = new Document();
			
			String members = "";
			for (String s: t.getAssignedTo()) {
				members += s + " ";
			}
			// Make the primary search field the title of the task t
			doc.add(new TextField("title", t.getTitle(), Field.Store.NO));
			
			// Add contents of description from task t
			doc.add(new TextField("description", t.getDescription(), Field.Store.NO));
			
			// Add contents of assigned to member list from task t
			doc.add(new TextField("assignedTo", members, Field.Store.NO));
			
			// Used to store task ID
			doc.add(new StringField("id", Integer.toString(t.getId()), Field.Store.YES));
			
			writer.addDocument(doc);
		}
		
		
	}
}


class IdRanking implements Comparable<IdRanking> {
	private int id;
	private Integer ranking;
	
	IdRanking(int id, Integer ranking) {
		this.id = id;
		this.ranking = ranking;
	}

	@Override
	public int compareTo(IdRanking o) {
		// The return is negative because PriorityQueue is low to high,
		// but we want high to low because we want highest results first.
		return -(this.ranking.compareTo(o.ranking));
	}
	
	public int getId() {
		return id;
	}
}