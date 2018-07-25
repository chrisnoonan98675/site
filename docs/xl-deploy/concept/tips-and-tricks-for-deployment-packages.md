---
title: Tips and tricks for deployment packages
categories:
xl-deploy
subject:
Packaging
tags:
package
application
encoding
artifacts
weight: 217
---

## Overriding default artifact comparison

When XL Deploy imports a package, it creates a _checksum_ for each artifact in the package. The checksum property on an artifact is a string property and can contain any string value. It is used during an upgrade to determine whether the content of an artifact CI in the new package differs from the artifact CI in the previous package. If you include information in an artifact that changes on every build, such as a build number or build timestamp, the checksum will be different even though the contents of the artifact has not changed.

In this scenario, it can be useful to override the XL Deploy-generated checksum and provide your own inside your package. Here is an example of an artifact CI with its own checksum:

{% highlight xml %}
<jee.Ear name="AnimalZooBE" file="AnimalZooBE-1.0.ear">
  <checksum>1.0</checksum>
</jee.Ear>
{% endhighlight %}

Using the above artifact definition, even if the EAR file itself is different, XL Deploy will consider the EAR file unchanged as long as it has value _1.0_ for the _checksum_ property.

## Specifying encoding for files in artifacts

By default XL Deploy will try to detect the encoding for files if a Byte Order Mark (BOM) is present. If it is not, XL Deploy will fallback to the platform encoding of Java. To ensure that these files are kept in their correct encoding while running them through the placeholder replacement, you can specify the encoding in the `fileEncodings` artifact property. This property maps regular expressions matching the full path of the file in the artifact to a target encoding.

For example, suppose you have the following (internationalized) files in a `file.Folder` artifact:

* `web-content/en-US/index.html`
* `web-content/nl-NL/index.html`
* `web-content/zh-CN/index.html`
* `web-content/ja-JP/index.html`

You want the Chinese and Japanese index pages to be treated as _UTF-16BE_, while the others can be treated as UTF-8. You can specify this in the manifest as follows:

{% highlight xml %}
<file.Folder name="webContent" file="web-content">
  <fileEncodings>
    <entry key=".+(en-US|nl-NL).+">UTF-8</entry>
    <entry key=".+(zh-CN|ja-JP).+">UTF-16BE</entry>
  </fileEncodings>
</file.Folder>
{% endhighlight %}

XL Deploy will use those encodings when replacing placeholders in these files.
