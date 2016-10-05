package com.xebialabs.gradle.plugins.utils

class Strings {

  static def convertToCamel(String s) {
    s.replaceAll(/\b-?([a-z0-9])/, { it[1][0].toUpperCase() });
  }

  static def initialCap(String s) {
    return s.empty ? s : s.substring(0, 1).toUpperCase() + s.substring(1)
  }
}
