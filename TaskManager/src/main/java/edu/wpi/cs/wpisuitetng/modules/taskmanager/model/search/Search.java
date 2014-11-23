package edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
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
	public String searchFor(String input) throws SearchException, IOException, ParseException {
		if (!isInit)
			throw new SearchException("Search is not initialized.");
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_10_0);
		QueryParser parser = new QueryParser(Version.LUCENE_4_10_0, "title", analyzer);
		
		Query query = parser.parse(input);
		System.out.println("Searching for: " + query.toString("title"));
		
		TopDocs results = searcher.search(query, 5);
		ScoreDoc[] hits = results.scoreDocs;
		
		Document doc = searcher.doc(hits[0].doc);
		
		reader.close();
		
		return doc.getField("title").stringValue();
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
			// Make the primary search field the title of the task t
			Field titleField = new StringField("title", t.getTitle(), Field.Store.YES);
			doc.add(titleField);
			
			// Add contents of description from task t
			//TODO NEED TO DO DESCRIPTION
			//doc.add(new TextField());
			
			// Add contents of member list from task t
			//TODO NEED TO DO MEMBER LIST
			//doc.add(new TextField());
			
			writer.addDocument(doc);
		}
		
		
	}
}
