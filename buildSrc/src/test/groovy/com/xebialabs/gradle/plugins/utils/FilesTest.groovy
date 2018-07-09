package com.xebialabs.gradle.plugins.utils

import org.junit.Test


class FilesTest {

  @Test
  def void getFileBaseNameTest() {

      def baseName = Files.getFileBaseName("climanual.yml");
      def expectedBaseName = "climanual";

      assert expectedBaseName == baseName;

      def baseName2 = Files.getFileBaseName("climanual.some.markdown");
      def expectedBaseName2 = "climanual.some";

      assert baseName2 == expectedBaseName2;
  }

  @Test
  def void getFileExtensionTest() {

    def extension = Files.getFileExtension("climanual.yml");
    def expectedExtension = "yml";

    assert expectedExtension == extension;

    def extension2 = Files.getFileExtension("climanual.some.markdown");
    def expectedExtension2 = "markdown";

    assert extension2 == expectedExtension2;
  }


}
