package davis.anime.renamer.files;

import lombok.extern.slf4j.Slf4j;

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
public class DirectoryScanner {

  Path basePath = null;

  public DirectoryScanner(Path fileBaseLocation) {
    this.basePath = fileBaseLocation;
  }

    public DirectoryScanner(String fileBaseLocation) {
        this.basePath = Paths.get(fileBaseLocation);
    }

  private void addToFiles(Map<String, List<File>> files, Path path) {
    if (files.get(path.getParent().toString()) == null) {
      List<File> some = new ArrayList<>();
      some.add(path.toFile());
      files.put(path.getParent().toString(), some);
    } else {
      files.get(path.getParent().toString()).add(path.toFile());
    }
  }

  public Map<String, List<File>> getAllFiles() throws IOException {
    Map<String, List<File>> files = new HashMap<>();
    Files.walk(this.basePath).filter(Files::isRegularFile).forEach(path -> addToFiles(files, path));
    return files;
  }
}
