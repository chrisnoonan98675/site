package com.xebialabs.gradle.plugins.utils


class Files {

  public static final String FILE_NAME_SPLIT_PATTERN = "\\.(?=[^\\.]+\$)"

  static def getFileBaseName(String fileName) {
    fileName.split(FILE_NAME_SPLIT_PATTERN)[0]
  }

  static def getFileExtension(String fileName) {
    fileName.split(FILE_NAME_SPLIT_PATTERN)[-1]
  }

}
