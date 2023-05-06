package com.onlinestorewepr.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtil {
  public static String getImgDir() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("/yyyy/MM/dd/HH/mm");
    return dateTimeFormatter.format(LocalDateTime.now());
  }

  public static void deleteDir(String path) throws IOException {
    Path existedImagePath = Paths.get(path);
    Files.deleteIfExists(existedImagePath);

    // Files.deleteIfExists(Paths.get(product.getImage()))
  }
}
