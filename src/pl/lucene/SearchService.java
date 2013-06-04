package pl.lucene;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

public class SearchService extends LuceneSearchService { 

	/**
	 * 
	 * @param indexDirectory
	 * @throws IOException
	 */
	public SearchService(String indexDirectory) throws IOException {
		super(indexDirectory);
	}

	/**
	 * Searches term in specified indexes directory
	 * @param searchTerm
	 */
	public void search(String searchTerm) {
        try {
    		long startTime = System.currentTimeMillis();
    		
    		IndexReader ireader = IndexReader.open(indexDirectory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            QueryParser parser = new QueryParser(Version.LUCENE_36, "content", analyzer);
            Query query = parser.parse(searchTerm);
            TopDocs docs = isearcher.search(query, 20);           
            ScoreDoc[] hits = docs.scoreDocs;
            
            long endTime = System.currentTimeMillis();
            
            Logger.log("\nFound " + docs.totalHits + " results in time " + (endTime - startTime) + " miliseconds. \n\nList of documents:\n");

            for (ScoreDoc hit : hits) {
                Document doc = isearcher.doc(hit.doc);
                Logger.log(doc.get("filename"));
            }
            
            isearcher.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
