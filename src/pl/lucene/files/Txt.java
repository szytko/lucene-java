package pl.lucene.files;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

public class Txt extends FileAbstract {

	/**
	 * 
	 * @param indexWriter
	 * @param inputFile
	 */
	public Txt(IndexWriter indexWriter, File inputFile) {
		super(indexWriter, inputFile);	
	}
	
	/**
	 * 
	 * @param aFileName
	 * @return
	 * @throws IOException
	 */
	private FileReader readFileContent(String aFileName) throws IOException {
		return new FileReader(aFileName);
	}
	
	/**
	 * 
	 */
	@Override
	public void indexFile() throws Exception {
		
        Document doc = new Document();
		doc.add(new Field("filename", inputFile.getName(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("filepath", inputFile.getAbsolutePath(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("content", readFileContent(inputFile.getAbsolutePath())));
        indexWriter.addDocument(doc);
	}
}
