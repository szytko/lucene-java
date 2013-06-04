package pl.lucene.files;

import java.io.File;
import java.io.FileInputStream;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class WordDoc extends FileAbstract {

	/**
	 * 
	 * @param indexWriter
	 * @param inputFile
	 */
	public WordDoc(IndexWriter indexWriter, File inputFile) {
		super(indexWriter, inputFile);
	}

	/**
	 * Indexes Word document using POI library
	 */
	@Override
	public void indexFile() throws Exception {
		POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(inputFile));		
		WordExtractor extractor = new WordExtractor(fileSystem); 
		
		Document doc = new Document();
		doc.add(new Field("filename", inputFile.getName(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("filepath", inputFile.getAbsolutePath(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("content", extractor.getText(), Field.Store.YES, Field.Index.ANALYZED));
        indexWriter.addDocument(doc);
	}
}
