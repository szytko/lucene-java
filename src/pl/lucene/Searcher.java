package pl.lucene;

import pl.lucene.vendors.CmdLineParser;
import pl.lucene.vendors.CmdLineParser.Option;

public class Searcher {

  /**
   *
   */
  private static void printUsage() {
    System.err
        .println("Usage: java -jar Lucene.jar [{-s, --search} phrase] [{-i, --index} input_data_directory] indexes_directory_path");
  }

  /**
   *
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    CmdLineParser cmd = new CmdLineParser();

    Option<String> search = cmd.addStringOption('s', "string");
    Option<String> index = cmd.addStringOption('i', "index");

    try {
      cmd.parse(args);
    } catch (CmdLineParser.OptionException ex) {
      System.err.println(ex.getMessage());
      printUsage();
      System.exit(2);
    }

    String[] otherArgs = cmd.getRemainingArgs();
    try {
      if (otherArgs.length == 0)
        throw new Exception("Enter path to index directory");
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
      printUsage();
      System.exit(2);
    }

    String searchTerm = cmd.getOptionValue(search);
    String indexDirectory = cmd.getOptionValue(index);

    if (searchTerm != null && indexDirectory != null) {
      throw new Exception("Run application in one mode");
    }

    if (searchTerm != null && searchTerm.length() > 0) {
      SearchService searcher = new SearchService(otherArgs[0]);
      Logger.log("Searching : " + searchTerm);
      searcher.search(searchTerm);
    } else {
      if (indexDirectory != null && indexDirectory.length() == 0)
        throw new Exception(
            "Enter path to directory with files for indexing");
      IndexService indexer = new IndexService(otherArgs[0]);
      indexer.buildIndexes(indexDirectory);
    }
  }
}
