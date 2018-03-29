---
title: Configure file upload properties
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- security
- file upload
- settings
weight: 498
---

In XL Release version 7.5.3 you can configure the file upload properties.
File upload properties are used to set a limit for the size of uploaded files and to restrict content accepted by XL Release.

## Configure the file upload size

To configure the upload size, add the `xl`(if it does not already exist), `server`, `upload`, and `max-size` properties to the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` configuration file.

### Sample code for setting the limit of upload size

    xl {
        server {
            upload {
                max-size = 100
            }
        }
    }

## Restrict the accepted file extensions

To additionally restrict file upload to accept only certain patterns, you can use the following configuration:

    xl {
        server {
            upload {
                max-size = 100
                allowed-content-types = {
                    "*": ["*.txt", "*.pdf", "Jenkinsfile"]
                }
            }
        }
    }
    
## Restrict accepted file content

To additionally restrict the file upload content, you can use the following configuration:

    xl {
        server {
            upload {
                max-size = 100
                analyze-content = true
                allowed-content-types = {
                    "text/*": ["*"]
                    "application/pdf": ["*.pdf"]
                    "image/*": ["*.jpg", "*.png"]
                }
            }
        }
    }
    
If you set `analyze-content` to `true`, XL Release tries to deduce the MIME type of the uploaded content.
The `allowed-content-types` map consists of MIME type pattern and accepted filename patterns. For possible combinations of MIME types and accepted filename patterns, refer to [tika-mimetypes.xml](https://github.com/apache/tika/blob/master/tika-core/src/main/resources/org/apache/tika/mime/tika-mimetypes.xml).
