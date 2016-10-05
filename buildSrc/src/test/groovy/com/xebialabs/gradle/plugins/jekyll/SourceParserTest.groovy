package com.xebialabs.gradle.plugins.jekyll

import org.junit.Test

class SourceParserTest {

  @Test
  public void shouldParseYamlConfigTest() {
    InputStream input = SourceParserTest.getClass().getResourceAsStream("/jekyll-sources.yml")

    def repositoryBase = "git@github.com:xebialabs"
    def defaultPath = "documentation/src/main/markdown"

    def yamlSources = SourceParser.readSources(input)

    assert yamlSources.size() == 2

    assert yamlSources.get(0).get("project") == "xl-deploy"
    assert yamlSources.get(0).get("branches").size() == 2

    assert yamlSources.get(0).get("branches").get(0).size() == 4
    assert yamlSources.get(0).get("branches").get(0).get("branch") == "master"
    assert yamlSources.get(0).get("branches").get(0).get("path") == defaultPath
    assert yamlSources.get(0).get("branches").get(0).get("repositoryBase") == repositoryBase
    assert yamlSources.get(0).get("branches").get(0).get("target") == "xl-deploy/4.5.x"

    assert yamlSources.get(0).get("branches").get(1).size() == 4
    assert yamlSources.get(0).get("branches").get(1).get("branch") == "3.9.x-maintenance"
    assert yamlSources.get(0).get("branches").get(1).get("path") == defaultPath
    assert yamlSources.get(0).get("branches").get(1).get("repositoryBase") == repositoryBase
    assert yamlSources.get(0).get("branches").get(1).get("target") == "xl-deploy/3.9.x"

    assert yamlSources.get(1).get("project") == "cloud-pack"
    assert yamlSources.get(1).get("branches").size() == 2

    assert yamlSources.get(1).get("branches").get(0).size() == 4
    assert yamlSources.get(1).get("branches").get(0).get("branch") == "3.9.x-maintenance"
    assert yamlSources.get(1).get("branches").get(0).get("path") == "ec2-plugin/src/main/markdown"
    assert yamlSources.get(1).get("branches").get(0).get("repositoryBase") == repositoryBase
    assert yamlSources.get(1).get("branches").get(0).get("target") == "xl-scale/ec2-plugin/3.9.x"

    assert yamlSources.get(1).get("branches").get(1).size() == 4
    assert yamlSources.get(1).get("branches").get(1).get("branch") == "3.9.x-maintenance"
    assert yamlSources.get(1).get("branches").get(1).get("path") == "cloud-plugin/src/main/markdown"
    assert yamlSources.get(1).get("branches").get(1).get("repositoryBase") == repositoryBase
    assert yamlSources.get(1).get("branches").get(1).get("target") == "xl-scale/cloud-plugin/3.9.x"

  }
}
