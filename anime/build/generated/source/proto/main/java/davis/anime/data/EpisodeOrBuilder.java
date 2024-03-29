// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: davis/anime/data/Anime.proto

package davis.anime.data;

public interface EpisodeOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Episode)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bool episode_number = 1;</code>
   */
  boolean getEpisodeNumber();

  /**
   * <code>.Season ref_season = 2;</code>
   */
  boolean hasRefSeason();
  /**
   * <code>.Season ref_season = 2;</code>
   */
  davis.anime.data.Season getRefSeason();
  /**
   * <code>.Season ref_season = 2;</code>
   */
  davis.anime.data.SeasonOrBuilder getRefSeasonOrBuilder();

  /**
   * <code>string current_path = 3;</code>
   */
  java.lang.String getCurrentPath();
  /**
   * <code>string current_path = 3;</code>
   */
  com.google.protobuf.ByteString
      getCurrentPathBytes();

  /**
   * <code>string target_path = 4;</code>
   */
  java.lang.String getTargetPath();
  /**
   * <code>string target_path = 4;</code>
   */
  com.google.protobuf.ByteString
      getTargetPathBytes();

  /**
   * <code>string current_name = 5;</code>
   */
  java.lang.String getCurrentName();
  /**
   * <code>string current_name = 5;</code>
   */
  com.google.protobuf.ByteString
      getCurrentNameBytes();

  /**
   * <code>string target_name = 6;</code>
   */
  java.lang.String getTargetName();
  /**
   * <code>string target_name = 6;</code>
   */
  com.google.protobuf.ByteString
      getTargetNameBytes();

  /**
   * <code>string tvdb_ref = 7;</code>
   */
  java.lang.String getTvdbRef();
  /**
   * <code>string tvdb_ref = 7;</code>
   */
  com.google.protobuf.ByteString
      getTvdbRefBytes();

  /**
   * <code>string subtitle_ref = 8;</code>
   */
  java.lang.String getSubtitleRef();
  /**
   * <code>string subtitle_ref = 8;</code>
   */
  com.google.protobuf.ByteString
      getSubtitleRefBytes();

  /**
   * <code>bool has_subtitles = 9;</code>
   */
  boolean getHasSubtitles();

  /**
   * <code>float file_size = 10;</code>
   */
  float getFileSize();
}
