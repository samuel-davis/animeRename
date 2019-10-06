package davis.anime.renamer.tvdb;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

/** Copyright(c) 2019 All Rights Reserved. This software was created by Samuel Davis on 10/6/2019 */
@Slf4j
public class ApiKeyReader {
  private ApiKeyReader() {}

  public static TvdbApiKey parseFromFile(Path apiKeyPath) {
    TvdbApiKey apiKey = null;
    String res = null;
    try (FileInputStream fis = new FileInputStream(apiKeyPath.toFile())) {
      byte[] data = new byte[(int) apiKeyPath.toFile().length()];
      fis.read(data);
      fis.close();

      apiKey = new TvdbApiKey(new String(data, "UTF-8"));
    } catch (IOException e) {
      log.error("Unable to parse API Key File", e);
    }

    return apiKey;
  }
}
