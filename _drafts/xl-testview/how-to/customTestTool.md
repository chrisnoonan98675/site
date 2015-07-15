---
layout: beta
title: Write custom test tools
categories:
- xl-testview
subject:
- System administration
tags:
- system administration
- installation
- ldap
- authentication
---

XL TestView supports a number of test tools out of the box, but there are many other tools available. Some are commercial, self build or proprietary. To support all these tools, it is possible to write custom test tools.

A test tool is essentially a program that parses several test files, and produces events that XL TestView can store in its database. XL TestView offers several interfaces, and users can write implementations in Python or Java that will provide the logic that is specific for that test tool. The following interfaces are available:

1. FunctionalTestToolApi
2. PerformanceTestToolApi
3. GreatPowerTestToolApi

## Writing a new Test Tool
The following steps are required for a new test tool:

1. Select the interface you require to implement.
2. Write the implementation
3. Add an entry to a synthetic.xml file.

## Writing a functional test tool
This is the interface that needs to be implemented:

    public interface TestToolApi<T> extends UberTestTool {
    
        /**
         * Filters blob list of files in list which contains fails who are *all* interpretable for the test tool
         */
        List<OverthereFile> filterReadable(List<OverthereFile> blob);
    
        /**
         * Splits a file into 'Subjects', which can be thought of as 'test suites' or 'scenarios'
         *
         * Returns a list of subjects, which are of type T
         */
        List<T> splitIntoSubjects(OverthereFile file);
    
        List<T> splitIntoCases(final T subject);
    
        /**
         * Returns the time that the last test was executed, or the most recent modification of the file if no such information is available.
         *
         * @param files The test result
         * @return the number of milliseconds since 1 January 1970
         */
        long getLastModified(OverthereFile files);
    
        /**
         * Returns the test result. One of the following:
         * PASSED
         * FAILED
         * SKIPPED
         * ERROR
         *
         * @param testCase
         * @param eventMap An unmodifiable map of all collected values
         * @return
         */
        String getResult(T testCase, Map<String, Object> eventMap);
    
        /**
         * Gets the duration of a testCase in milliseconds
         * @param testCase
         * @param eventMap An unmodifiable map of all collected values
         * @return
         */
        int getDuration(final T testCase, final Map<String, Object> eventMap);
    
        /**
         * Gets the reason of failure of a test case
         * @param testCase
         * @param eventMap An unmodifiable map of all collected values
         * @return
         */
        String failureReason(T testCase, final Map<String, Object> eventMap);
    
        /**
         * Returns the hierarchy of this test. This is a list of Strings that, combined with the test name, uniquely identifies this test case.
         * This map will be used to calculate the drill down features of events.
         *
         * For example:
         *
         * ['com','xebialabs','xltestview','MyTestClass']
         *
         * @param testCase The test case to parse.
         * @param subject
         * @param file
         * @param eventMap An unmodifiable map of all collected values
         * @return
         */
        List<String> getSuite(T testCase, final T subject, OverthereFile file, Map<String, Object> eventMap);
    
        /**
         * Add additional properties to events
         * @param testCase
         * @param subject
         * @param file
         * @param eventMap An unmodifiable map of all collected values
         * @return
         */
        Map<String, Object> getOtherProperties(T testCase, final T subject, OverthereFile file, Map<String, Object> eventMap);
    }

## Configure unsecure LDAP

To configure LDAP, update the following properties in the `xl-testview.conf` file:

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| `xlt.authentication.method` | Set to `ldap` |
| `xlt.authentication.ldap.url` | Set to the complete URL of your LDAP server including the port number; for example, `ldap://server.domain:389` |
| `xlt.authentication.ldap.user-dn` | Set to a distinguished name template that identifies users; for example, `cn={0},ou=people,dc=xebialabs,dc=com`, where `{0}` will be replaced by the user name |

**Note:** Identifying users by properties other than the user name is not currently supported.

After saving the `xl-testview.conf` file, [restart XL TestView](/xl-testview/how-to/start.html) and log in.

## Configure secure LDAP

If your LDAP configuration uses a certificate signed by a certificate authority or a certificate that is trusted in the global Java truststore, you should be able to connect by setting the secure URL (for example, `ldaps://server.domain:636`) in `xlt.authentication.ldap.url`.

If you use a self-signed certificate and you cannot add it to the global truststore, you need to configure a local keystore. You can do so using the [`keytool`](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/keytool.html) utility (part of the Java JDK distribution).

**Note:** Ensure you complete the registration process before you configure LDAP. Registration will not work afterward.

To configure a local keystore:

1. Export the certificate of your LDAP server. Please consult the documentation of your LDAP server for instructions.
2. Go to the `conf` directory of your installation. Another location is also allowed.
3. Create a new truststore using the following command:

        keytool -import  -alias ldap.example.com -file ldapCertificate.crt -keystore truststore.jks

4. Type a keystore password twice.
5. Confirm you trust this account.
6. In the configuration file, set `xlt.truststore.location` to the absolute file location of the truststore you just created.
7. Set `xlt.truststore.password` to the password.
8. After saving the file, [restart XL TestView](/xl-testview/how-to/start.html) and log in.
