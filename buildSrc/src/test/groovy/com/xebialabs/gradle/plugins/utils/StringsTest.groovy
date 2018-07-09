package com.xebialabs.gradle.plugins.utils

import org.junit.Test


class StringsTest {

  @Test
  def void convertToCamelTest() {
    def camelized = Strings.convertToCamel("jekyll-xl-deploy-clone-task")
    def expectedValue = "JekyllXlDeployCloneTask"

    assert camelized == expectedValue

    def camelized2 = Strings.convertToCamel("jekyll-xl-deploy-3.9.x-maintenance-jekyll-build-task")
    def expectedValue2 = "JekyllXlDeploy3.9.XMaintenanceJekyllBuildTask"

    assert camelized2 == expectedValue2
  }

}
