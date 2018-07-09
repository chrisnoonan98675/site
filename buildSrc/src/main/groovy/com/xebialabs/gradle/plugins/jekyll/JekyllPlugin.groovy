package com.xebialabs.gradle.plugins.jekyll

import com.xebialabs.gradle.plugins.utils.Files
import com.xebialabs.gradle.plugins.utils.Strings
import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.Exec
import org.ho.yaml.Yaml

class JekyllPlugin implements Plugin<Project> {

  public static final String JEKYLL_BUILD_TASK_NAME = "jekyllBuild"
  public static final String JEKYLL_FETCH_SOURCES_TASK_NAME = "jekyllFetchSources"
  public static final String JEKYLL_UPLOAD_DOC_TASK_NAME = "uploadJekyllDocumentation"

  @Override
  void apply(Project project) {

    // Apply the base plugin to get cleaning behaviour
    project.apply plugin: "base"

    checkMandatoryProps(project)

    project.tasks.create(JEKYLL_BUILD_TASK_NAME, Exec).configure {
      description = "Jekyll builds documentation from the provided markdowns and produces html output in _site folder"
      commandLine 'jekyll', 'build', '--config', '_config.yml,_jekyll.xebialabs.config.yml'
    }

    project.tasks.create(JEKYLL_UPLOAD_DOC_TASK_NAME, Exec).configure {
      description = "Uploads generated html documentation to the remote server"
      commandLine 'rsync', '-av', "${project.projectDir}/_site/",
          "${project.jekyllUserName}@${project.jekyllHost}:${project.jekyllPath}"
    }.dependsOn(JEKYLL_BUILD_TASK_NAME)

    def checkoutTasks = []

    for (def jekyllSource : SourceParser.processSources(new File("${project.projectDir}/sources.yml"))) {
      checkoutTasks.addAll(buildJekyllDocsForProject(jekyllSource, project))
    }

    project.tasks.create(JEKYLL_FETCH_SOURCES_TASK_NAME, DefaultTask).dependsOn(checkoutTasks)
  }

  static def getMarkdownExtensions(Project project) {
    def configYaml = Yaml.load(new File("${project.projectDir}/_config.yml"))
    def extensions = [] as Set

    def markdownConfig = configYaml.getAt("markdown_ext")
    if (markdownConfig) {
      extensions = markdownConfig.tokenize(',')
    } else {
      extensions = ["markdown"]
    }

    extensions
  }

  static def buildJekyllDocsForProject(def jekyllSource, Project project) {
    def taskNames = []

    def jekyllHome = "/var/lib/jenkins/jekyll/"
    def projectHome = "${jekyllHome}${jekyllSource.project}"
    def repoBase = jekyllSource.branches[0].repositoryBase

    def cloneTaskName = Strings.convertToCamel("jekyll-${jekyllSource.project}-clone-task")
    project.tasks.create(cloneTaskName, Exec).configure {
      if (!new File(projectHome).exists()) {
        commandLine 'git', 'clone', "${repoBase}/${jekyllSource.project}.git", "${projectHome}"
      } else {
        commandLine 'echo', "The repository ${projectHome} is already checked out."
      }
    }

    for (def branch : jekyllSource.branches) {

      def checkoutTaskName = Strings.convertToCamel("jekyll-${jekyllSource.project}-${branch.branch}-${branch.target}-checkout-task")
      project.tasks.create(checkoutTaskName, Exec).configure {
        workingDir = "${jekyllHome}/${jekyllSource.project}"
        commandLine 'git', 'checkout', branch.branch
      }.dependsOn(cloneTaskName)

      def pullTaskName = Strings.convertToCamel("jekyll-${jekyllSource.project}-${branch.branch}-${branch.target}-pull-task")
      project.tasks.create(pullTaskName, Exec).configure {
        workingDir = "${jekyllHome}/${jekyllSource.project}"
        commandLine 'git', 'pull', '--rebase'
      }.dependsOn(checkoutTaskName)

      def targetDirectory = "${jekyllSource.project}/${branch.target}"
      def deleteSourcesTask = Strings.convertToCamel("jekyll-${jekyllSource.project}-${branch.branch}-${branch.target}-delete-task")
      project.tasks.create(deleteSourcesTask, Delete).configure {
        delete targetDirectory
      }.dependsOn(pullTaskName)

      def copySourcesTask = Strings.convertToCamel("jekyll-${jekyllSource.project}-${branch.branch}-${branch.target}-copy-task")
      project.tasks.create(copySourcesTask, Copy).configure {
        from "${jekyllHome}/${jekyllSource.project}/${branch.path}"
        into targetDirectory
        exclude '**/*.yml'
      }.dependsOn(deleteSourcesTask)

      def addFrontMatterTask = Strings.convertToCamel("jekyll-${jekyllSource.project}-${branch.branch}-${branch.target}-add-front-matter-task")
      project.tasks.create(addFrontMatterTask, DefaultTask).doLast {
        new File("${jekyllSource.project}").eachFileRecurse(FileType.FILES) { file ->
          if (getMarkdownExtensions(project).contains(Files.getFileExtension(file.getName()))) {
            def prependContent = "---\n---\n"

            def ymlConfigFile = new File("${jekyllHome}/${jekyllSource.project}/${branch.path}", Files.getFileBaseName("${file.name}") + ".yml");
            if (ymlConfigFile.exists()) {
              prependContent = "${ymlConfigFile.text}\n"
            }

            def originalText = file.text
            file.withWriter { out ->
              out.println "${prependContent}${originalText}"
            }
          }
        }

      }.dependsOn(copySourcesTask)

      def jekyllBuildTaskName = Strings.convertToCamel("jekyll-${jekyllSource.project}-${branch.branch}-${branch.target}-jekyll-build-task")
      project.tasks.create(jekyllBuildTaskName, Exec).configure {
        commandLine 'jekyll', 'build', '--config', '_config.yml,_jekyll.xebialabs.config.yml'
      }.dependsOn(addFrontMatterTask)

      taskNames.add(jekyllBuildTaskName)
    }

    taskNames
  }

  static def checkMandatoryProps(Project project) {
    ['jekyllUserName', 'jekyllHost', 'jekyllPath'].each {
      if (!project.has("${it}")) {
        throw new GradleException("You didn't specify '${it}' property in your ~/.gradle/gradle.properties file")
      }
    }
  }

}