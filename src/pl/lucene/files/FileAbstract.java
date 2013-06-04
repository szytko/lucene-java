package pl.lucene.files;

import java.io.File;

import org.apache.lucene.index.IndexWriter;

import pl.lucene.Logger;

abstract public class FileAbstract {

  /**
   *
   */
  protected IndexWriter indexWriter;
  /**
   *
   */
  protected File inputFile;

  /**
   *
   * @param indexWriter
   *            Creates and maintains an index
   * @param inputFile
   *            Indexing file
   */
  public FileAbstract(IndexWriter indexWriter, File inputFile) {
    this.indexWriter = indexWriter;
    this.inputFile = inputFile;

    Logger.log("Indexing file: " + inputFile.getName());
  }

  /**
   *
   * @throws Exception
   */
  abstract public void indexFile() throws Exception;
}
