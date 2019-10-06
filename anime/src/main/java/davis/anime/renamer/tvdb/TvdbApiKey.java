package davis.anime.renamer.tvdb;

import lombok.Data;

/** Copyright(c) 2019 All Rights Reserved. This software was created by Samuel Davis on 10/6/2019 */
@Data
public class TvdbApiKey {
  private String key;

  public TvdbApiKey(String key) {
    this.key = key;
  }
}
