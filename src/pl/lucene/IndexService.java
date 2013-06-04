package pl.lucene;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.util.Version;

import pl.lucene.files.Pdf;
import pl.lucene.files.Txt;
import pl.lucene.files.WordDoc;

public class IndexService extends LuceneSearchService {

	/**
	 * 
	 * @param indexDirectoryName
	 * @throws IOException
	 */
    public IndexService(String indexDirectoryName) throws IOException {
    	super(indexDirectoryName);
    }
    
    /**
     * 
     * @throws IOException
     */
    private void clearIndexDirectory() throws IOException {
    	Logger.log("Cleanning index directory");
    	String files[] = indexDirectory.listAll();
    	for (String fileName : files) {
    		File file = new File(indexDirectoryName + "/" + fileName);
    		file.delete();
    	}
    }
    
    /**
     * 
     */
    private int indexedFilesCount = 0;
    
    /**
     * 
     */
    public void buildIndexes(String inputDirectoryName) {
    	try {
    		long startTime = System.currentTimeMillis();
    		
    		clearIndexDirectory();
    		
    		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, analyzer);
    		IndexWriter indexWriter = new IndexWriter(indexDirectory, config);
   
    		File input = new File(inputDirectoryName);
    		if (!input.exists()) {
    			System.err.println("Input directory does not exist");
    			System.exit(2);
    		}
    		File inputFiles[] = input.listFiles();
    		
    		for (File file : inputFiles) {
    			//file validation
    			if (!file.exists()) continue;
    			if (file.isDirectory()) continue;
    			if (!file.canRead()) continue;
    			if (file.isHidden()) continue;
    			
    			String fileExtension = file.getName().substring(file.getName().lastIndexOf('.')+1);

    			switch (fileExtension) {
	    			case "pdf" :
	    				new Pdf(indexWriter, file).indexFile();
	    				break;
	    			case "txt" :
	    				new Txt(indexWriter, file).indexFile();
	    				break;
	    			case "doc" :
	    				new WordDoc(indexWriter, file).indexFile();
	    				break;
    			}
    			indexedFilesCount++;
    		}
    		
    		indexWriter.close();
    		
    		long endTime = System.currentTimeMillis();
    		    	
    		Logger.log("Indexed " + indexedFilesCount + " files in time " 
    				+ (String.format("%d sec", TimeUnit.MILLISECONDS.toSeconds(endTime - startTime)))
    				);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }    
}
