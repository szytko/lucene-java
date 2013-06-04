package pl.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

abstract public class LuceneSearchService {

	/**
	 * 
	 */
	protected Directory indexDirectory;
	/**
	 * 
	 */
	protected String indexDirectoryName;
	/**
	 * 
	 */
	protected final Analyzer analyzer;

	/**
	 * 
	 * @param indexDirectoryName
	 * @throws IOException
	 */
	public LuceneSearchService(String indexDirectoryName) throws IOException {
		this.indexDirectoryName = indexDirectoryName;
		indexDirectory = FSDirectory.open(new File(indexDirectoryName));
		analyzer = new StandardAnalyzer(Version.LUCENE_36);
	}
}
