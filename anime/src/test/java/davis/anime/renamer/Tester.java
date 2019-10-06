package davis.anime.renamer;

import com.uwetrottmann.thetvdb.TheTvdb;
import com.uwetrottmann.thetvdb.entities.Series;
import com.uwetrottmann.thetvdb.entities.SeriesResponse;
import davis.anime.data.Anime;
import davis.anime.data.Episode;
import davis.anime.data.Season;
import davis.anime.renamer.files.DirectoryScanner;
import davis.anime.renamer.tvdb.ApiKeyReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Copyright(c) 2019 All Rights Reserved. This software was created by Samuel Davis on 10/6/2019 */
@Slf4j
public class Tester {

  public int getSeasonNumberFromPath(String path) {
    // log.info("Seasons {}", path);
    int res = -1;
    try {
      res = Integer.valueOf(StringUtils.substringBetween(path, "Season", "Ep").trim());
    } catch (Exception e) {

    }
    return res;
  }

  public int getEpisodeNumber(File f) {
    // log.info("File {}", f.getAbsolutePath());
    int res = -1;
    try {
      res =
          Integer.valueOf(
              StringUtils.substringBetween(f.getName(), "Naruto Shippuden -", " -").trim());

    } catch (Exception e) {
      // log.error("1");
    }

    try {
      if (res == -1) {
        String t =
            StringUtils.substringBetween(f.getName(), "Naruto Shippuden Episode ", ".mp4").trim();
        if (t != null && !t.equalsIgnoreCase("")) {
          res = Integer.valueOf(t);
        }
      }

    } catch (Exception e) {
      //  log.error("2");
    }

    try {
      if (res == -1) {
        String t = StringUtils.substringBetween(f.getName(), "-", "-").trim();
        if (t != null) {
          res = Integer.valueOf(t);
        }
      }

    } catch (Exception e) {
      // log.error("3");
    }

    try {
      if (res == -1) {
        String t = StringUtils.substringBetween(f.getName(), "-", ".mkv").trim();
        if (t != null) {
          res = Integer.valueOf(t);
        }
      }

    } catch (Exception e) {
      // log.error("3");
    }
    try {
      if (res == -1) {
        res =
            Integer.valueOf(
                StringUtils.substringBetween(f.getName(), "Naruto Shippuuden -", "-").trim());
      }
    } catch (Exception e) {
      //    log.error("4");
    }
    if (res == -1) {
      log.error("No Episode Number {}", f.getName());
      System.exit(1);
    }

    return res;
  }

  public void moveFile(String old, String newFile) {
    try {
        log.info("Moving {} to {}", old, newFile);
      FileUtils.moveFile(FileUtils.getFile(old), FileUtils.getFile(newFile));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addToFiles(Map<String, List<File>> files, Path path) {

    if (path.toFile().getName().contains("S")) {
      if (files.get(path.getParent().toString()) == null) {
        List<File> some = new ArrayList<>();
        some.add(path.toFile());
        files.put(path.getParent().toString(), some);
      } else {
        files.get(path.getParent().toString()).add(path.toFile());
      }
    }
  }

  public Map<String, List<File>> getAllFiles(String pathS) throws IOException {
    Map<String, List<File>> files = new HashMap<>();
    Files.walk(Paths.get(pathS))
        .filter(Files::isRegularFile)
        .forEach(path -> addToFiles(files, path));
    return files;
  }

  public int getSeasonNumberNew(File file) {
    return Integer.valueOf(StringUtils.substringBetween(file.getName(), "S", "E").trim());
  }

  public int getEpisodeNumberNew(File file) {
    return Integer.valueOf(StringUtils.substringBetween(file.getName(), "E", ".").trim());
  }

  public void addToSeason(Map<Integer, Season.Builder> seasons, Episode episode) {
    if (seasons.get(episode.getSeasonNumber()) == null) {
      seasons.put(episode.getSeasonNumber(), Season.newBuilder());
    }
    seasons.get(episode.getSeasonNumber()).addEpisodes(episode);
  }

  public int getEpisodeCount(Season.Builder season) {
    return season.getEpisodesList().size();
  }

  public Integer getEpisodeNumberInSeason(Season.Builder season, Episode episode) {
    int startingEpisode = 15500;
    for (Episode e : season.getEpisodesList()) {
      if (e.getEpisodeNumber() < startingEpisode) {
        startingEpisode = e.getEpisodeNumber();
      }
    }
    int res = (episode.getEpisodeNumber() - startingEpisode) + 1;

    return res;
  }

  @Test
  public void test2() throws IOException {

    Map<Integer, Season.Builder> seasons = new HashMap<>();

    String naurtoLocation = "D:\\Multimedia\\TV Shows\\Naruto Shippuden";
    Map<String, List<File>> files = getAllFiles(naurtoLocation);

    for (List<File> fileList : files.values()) {

      for (File f : fileList) {
        Episode.Builder episodeBuilder = Episode.newBuilder();
        episodeBuilder.setSeasonNumber(getSeasonNumberNew(f));
        episodeBuilder.setEpisodeNumber(getEpisodeNumberNew(f));
        episodeBuilder.setCurrentPath(f.getAbsolutePath());
        episodeBuilder.setCurrentName(f.getName());
        addToSeason(seasons, episodeBuilder.build());
      }
    }

    for (Map.Entry<Integer, Season.Builder> entry : seasons.entrySet()) {
      for (Episode e : entry.getValue().getEpisodesList()) {
        log.info(
            "Episode original = {} , Episode New = {}",
            getEpisodeNumberNew(new File(e.getCurrentPath())),
            getEpisodeNumberInSeason(entry.getValue(), e));

        if(e.getSeasonNumber() > 1){
            moveFile(
                e.getCurrentPath(),
                Paths.get(
                    Paths.get(e.getCurrentPath()).getParent().toFile().getAbsolutePath(),
                    "S"
                        + e.getSeasonNumber()
                        + "E"
                        + getEpisodeNumberInSeason(entry.getValue(), e)
                        + "."
                        + StringUtils.substringAfterLast(e.getCurrentName(), "."))
                    .toFile()
                    .getAbsolutePath());
        }



      }
    }
  }

  @Test
  @Ignore
  public void test() throws IOException {
    String naurtoLocation = "D:\\Multimedia\\TV Shows\\Naruto Shippuden";

    boolean useTvDb = false;

    DirectoryScanner scanner = new DirectoryScanner(Paths.get(naurtoLocation));

    Anime.Builder animeBuilder = Anime.newBuilder();

    Map<String, List<File>> files = scanner.getAllFiles();
    for (Map.Entry<String, List<File>> entry : files.entrySet()) {
      Season.Builder seasonBuilder = Season.newBuilder();
      seasonBuilder.setCurrentName(Paths.get(entry.getKey()).toFile().getName());
      seasonBuilder.setCurrentPath(entry.getKey());
      int seasonsNumber = getSeasonNumberFromPath(seasonBuilder.getCurrentName());
      if (seasonsNumber == -1) {
        continue;
      }
      seasonBuilder.setSeasonNumber(seasonsNumber);
      // seasonBuilder.setSeasonNumber()

      Episode.Builder episodeBuilder = null;
      for (File f : entry.getValue()) {
        episodeBuilder = Episode.newBuilder();
        episodeBuilder.setCurrentName(f.getName());
        episodeBuilder.setCurrentPath(f.getAbsolutePath());
        episodeBuilder.setSeasonNumber(seasonsNumber);
        episodeBuilder.setEpisodeNumber(getEpisodeNumber(f));
        seasonBuilder.addEpisodes(episodeBuilder.build());
      }
      seasonBuilder.setNumberEpisodes(seasonBuilder.getEpisodesList().size());
      animeBuilder.addSeasons(seasonBuilder.build());
    }
    Anime anime = animeBuilder.build();

    for (Season season : anime.getSeasonsList()) {

      Path parentPath = Paths.get(season.getCurrentPath()).getParent();
      File newTargetDirectory =
          new File(
              Paths.get(
                      parentPath.toFile().getAbsolutePath(),
                      "Season" + String.valueOf(season.getSeasonNumber()))
                  .toFile()
                  .getAbsolutePath());
      log.info("test");
      newTargetDirectory.mkdirs();
      for (Episode episode : season.getEpisodesList()) {
        moveFile(
            episode.getCurrentPath(),
            Paths.get(
                    newTargetDirectory.getAbsolutePath(),
                    "S"
                        + episode.getSeasonNumber()
                        + "E"
                        + episode.getEpisodeNumber()
                        + "."
                        + StringUtils.substringAfterLast(episode.getCurrentName(), "."))
                .toFile()
                .getAbsolutePath());
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
