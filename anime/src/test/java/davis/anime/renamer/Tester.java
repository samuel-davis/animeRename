package davis.anime.renamer;

import com.uwetrottmann.thetvdb.TheTvdb;
import com.uwetrottmann.thetvdb.entities.Series;
import com.uwetrottmann.thetvdb.entities.SeriesResponse;
import davis.anime.renamer.files.DirectoryScanner;
import davis.anime.renamer.tvdb.ApiKeyReader;
import org.junit.Test;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Copyright(c) 2019 All Rights Reserved. This software was created by Samuel Davis on 10/6/2019 */
public class Tester {

  @Test
  public void test() throws IOException {
    String naurtoLocation =
        "D:\\Multimedia\\TV Shows\\[HcLs] Naruto Shippuden Season 1-18 English Dubbed [Sehjada]";

    boolean useTvDb = false;

    Pattern p =
        Pattern.compile(
            "\\(\\?i\\)\\[\\\\/\\]\\(\\?:S\\(\\?:eason\\)\\?\\s*\\(\\?=\\d\\)\\)\\?\\(Specials\\|\\d\\{1,3\\}\\)\\[\\\\/\\]\\(\\?:\\[\\^\\\\/\\]\\+\\[\\\\/\\]\\)\\*\\[\\^\\\\/\\]\\+\\(\\?:\\b\\|_\\)\\(\\?:\\[ _\\.\\-\\]\\*\\(\\?:ep\\?\\[ \\.\\]\\?\\)\\?\\(\\d\\{1,3\\}\\)\\(\\?:\\[_ \\]\\?v\\d\\+\\)\\?\\)\\+\\(\\?\\=\\b\\|_\\)\\[\\^\\]\\)\\}\\]\\*\\?\\(\\?:\\[\\[\\(\\{\\]\\[\\^\\]\\)\\}\\]\\+\\[\\]\\)\\}\\]\\[ _\\.\\-\\]\\*\\)\\*\\?\\(\\?:\\[\\[\\(\\{\\]\\[\\da\\-f\\]\\{8\\}\\[\\]\\)\\}\\]\\)");

    DirectoryScanner scanner = new DirectoryScanner(Paths.get(naurtoLocation));

    Map<String, List<File>> files = scanner.getAllFiles();
    for (Map.Entry<String, List<File>> entry : files.entrySet()) {
      for (File f : entry.getValue()) {
        Matcher matcher = p.matcher(f.getAbsolutePath());
        boolean matches = matcher.matches();
      }
    }

    if (useTvDb) {
      TheTvdb theTvdb =
          new TheTvdb(
              ApiKeyReader.parseFromFile(Paths.get("K:\\PROJECTS\\animeRename\\API_KEY.txt"))
                  .getKey());
      try {
        Response<SeriesResponse> response = theTvdb.series().series(83462, "en").execute();
        if (response.isSuccessful()) {
          Series series = response.body().data;
          System.out.println(series.seriesName + " is awesome!");
        }
      } catch (Exception e) {
        // see execute() javadoc
      }
    }
  }
}
