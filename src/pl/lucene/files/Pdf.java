package pl.lucene.files;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.pdfbox.lucene.LucenePDFDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class Pdf extends FileAbstract {

  /**
   *
   * @param indexWriter
   * @param inputFile
   */
  public Pdf(IndexWriter indexWriter, File inputFile) {
    super(indexWriter, inputFile);
  }

  /**
   * Indexes PDF document using PDFBox library
   */
  @Override
  public void indexFile() throws Exception {
    PDDocument doc = PDDocument.load(inputFile);
    String content = new PDFTextStripper().getText(doc);
    Document luceneDocument = LucenePDFDocument.getDocument(inputFile);
    luceneDocument.add(new Field("filename", inputFile.getName(),
        Field.Store.YES, Field.Index.ANALYZED));
    luceneDocument.add(new Field("filepath", inputFile.getAbsolutePath(),
        Field.Store.YES, Field.Index.ANALYZED));
    luceneDocument.add(new Field("content", content, Field.Store.YES,
        Field.Index.ANALYZED));

    indexWriter.addDocument(luceneDocument);
    doc.close();
  }
}
