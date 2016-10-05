package com.xebialabs.gradle.plugins.jekyll

import org.ho.yaml.Yaml

class SourceParser {

  def static readSources(File file) {
    processSources(file);
  }

  def static readSources(InputStream inputStream) {
    processSources(inputStream);
  }

  def static processSources(def sources) {
    def loadedSources = Yaml.load(sources)

    def defaults = loadedSources.getAt("defaults");
    def yamlSources = loadedSources.getAt("sources");

    for (def yamlSource : yamlSources) {
      for (def defaultValue : defaults) {

        def branches = yamlSource.getAt("branches");
        for (def branch: branches) {
          if (!branch.containsKey(defaultValue.key)) {
            branch.put(defaultValue.key, defaultValue.value);
          }
        }
      }
    }

    yamlSources
  }

}
