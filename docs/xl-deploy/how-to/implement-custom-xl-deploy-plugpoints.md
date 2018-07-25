---
pre_rules: true
title: Implement custom XL Deploy plugpoints
categories:
xl-deploy
subject:
Plugins
tags:
java
plugin
plugpoint
ci
orchestrator
importer
event listener
weight: 222
---

Functionality in the XL Deploy server can be customized by using _plugpoints_. Plugpoints are specified and implemented in Java. On startup, XL Deploy scans its classpath for implementations of its plugpoints in the `com.xebialabs` or `ext.deployit` packages and prepares them for use. There is no additional configuration required.

The XL Deploy Server supports the following plugpoints:

* _protocol_: specify a new method for connecting to remote hosts
* _deployment package importer_: allow XL Deploy to import deployment packages in a custom format
* _orchestrator_: control the way XL Deploy combines plans to generate the overall deployment workflow
* _event listener_: specify a listener for XL Deploy notifications and commands

Refer to the [Javadoc](https://docs.xebialabs.com/xl-deploy/latest/javadoc/udm-plugin-api/) for detailed information about the Java API.

## Defining Protocols

A protocol in XL Deploy is a method for making a connection to a host. Overthere, XL Deploy's remote execution framework, uses protocols to build a connection with a target machine. Protocol implementations are read by Overthere when XL Deploy starts.

Classes implementing a protocol must adhere to two requirements:

* The class must implement the `OverthereConnectionBuilder` interface
* The class must have the `@Protocol` annotation
* Define a custom host CI type that overrides the default value for property `protocol`

Example of a custom host CI type:

    <type type="custom.MyHost" extends="overthere.Host">
      <property name="protocol" default="myProtocol" hidden="true"/>
    </type>


The `OverthereConnectionBuilder` interface specifies only one method, `connect`. This method creates and returns a subclass of `OverthereConnection` representing a connection to the remote host. The connection must provide access to files (`OverthereFile` instances) that XL Deploy uses to execute deployments.

For more information, see the [Overthere project](http://github.com/xebialabs/overthere).

## Defining Importers and ImportSources

An `importer` is a class that turns a source into a collection of XL Deploy entities. Both the import source as well as the importer can be customized. XL Deploy comes with a default importer that understands the DAR package format.

Import sources are classes implementing the `ImportSource` interface and can be used to obtain a handle to the deployment package file to import. Import sources can also implement the `ListableImporter` interface, which indicates they can produce a list of possible files that can be imported. The user can make a selection out of these options to start the import process.

When the import source has been selected, all configured importers in XL Deploy are invoked in turn to see if any importer is capable of handling the selected import source (the `canHandle` method). The first importer that indicates it can handle the package is used to perform the import. XL Deploy's default importer is used as a fallback.

First, the `preparePackage` method is invoked. This instructs the importer to produce a `PackageInfo` instance describing the package metadata. This data is used by XL Deploy to determine whether the user requesting the import has sufficient rights to perform it. If so, the importer's `importEntities` method is invoked, allowing the importer to read the import source, create deployables from the package and return a complete `ImportedPackage` instance. XL Deploy will handle storing of the package and contents.

## Defining Orchestrators

An _orchestrator_ is a class that performs the orchestration stage. The orchestrator is invoked after the delta-analysis phase and before the planning stage and implements the `Orchestrator` interface containing a single method:

    Orchestration orchestrate(DeltaSpecification specification);

For example, this is the (Scala) implementation of the default orchestrator:

    @Orchestrator.Metadata (name = "default", description = "The default orchestrator")
    class DefaultOrchestrator extends Orchestrator {
      def orchestrate(specification: DeltaSpecification) = interleaved(getDescriptionForSpec(specification), specification.getDeltas)
    }

It takes all delta specifications and puts them together in a single, interleaved plan. This results in a deployment plan that is ordered solely on the basis of the step's `order` property.

In addition to the default orchestrator, XL Deploy also contains the following orchestrators:

* `sequential-by-container` and `parallel-by-container` orchestrator. These orchestrators groups steps that deal with the same container together, enabling deployments across a farm of middleware.
* `sequential-by-composite-package` and `parallel-by-composite-package` orchestrators. These orchestrators groups steps per contained package together. The order of the member packages in the composite package is preserved.
* `sequential-by-deployment-group` and `parallel-by-deployment-group` orchestrators. These orchestrators use the `deployment group` synthetic property on a container to group steps for all containers with the same deployment group. These orchestrators are provided by a separate plugin that comes bundled with XL Deploy inside the `plugins/` directory.

## Defining Event Listeners

The XL Deploy Core sends [events](/xl-deploy/latest/javadoc/engine-spi/index.html) that listeners can act upon. There are two types of events in XL Deploy system:

* Notifications: Events that indicate that XL Deploy has executed a particular action
* Commands: Events that indicate XL Deploy is about to to execute a particular action

The difference is that commands are fired *before* an action takes place, while notifications are fired *after* an action takes place.

### Listening for notifications

Notifications indicate a particular action has occurred in XL Deploy. Some examples of notifications in XL Deploy are:

* The system is started or stopped
* A user logs into or out of the system
* A CI is created, updated, moved or deleted
* A security role is created, updated or deleted
* A task (deployment, undeployment, control task or discovery) is started, cancelled or aborted

Notification event listeners are Java classes that have the `@DeployitEventListener` annotation and have one or more methods annotated with the T2 event bus `@Subscribe` annotation.

For example, this is the implementation of a class that logs all notifications it receives:

    import nl.javadude.t2bus.Subscribe;

    import com.xebialabs.deployit.engine.spi.event.AuditableDeployitEvent;
    import com.xebialabs.deployit.engine.spi.event.DeployitEventListener;
    import com.xebialabs.deployit.plugin.api.udm.ConfigurationItem;

    /**
     * This event listener logs auditable events using our standard logging facilities.
     */
    @DeployitEventListener
    public class TextLoggingAuditableEventListener {

        @Subscribe
        public void log(AuditableDeployitEvent event) {
            logger.info("[{}] {} {}", new Object[] { event.component, event.username, event.message });
        }

        private static Logger logger = LoggerFactory.getLogger("audit");
    }

### Listening for commands

Commands indicate that XL Deploy has been asked to perform a particular action. Some examples of commands in XL Deploy are:

* A request to create a CI or CIs has been received
* A request to update a CI has been received
* A request to delete a CI or CIs has been received

Command event listeners are Java classes that have the `@DeployitEventListener` annotation and have one or more methods annotated with the T2 event bus `@Subscribe` annotation. Command event listeners have the option of vetoing a particular command which causes it to not be executed. Vetoing event listeners indicate that they have the ability to veto the command in the Subscribe annotation and veto the command by throwing a `VetoException`  from the event handler method.

As an example, this listener class listens for update CI commands and optionally vetoes them:

    @DeployitEventListener
    public class RepositoryCommandListener {

        public static final String ADMIN = "admin";

        @Subscribe(canVeto = true)
        public void checkWhetherUpdateIsAllowed(UpdateCiCommand command) throws VetoException {
            checkUpdate(command.getUpdate(), newHashSet(command.getRoles()), command.getUsername());
        }

        private void checkUpdate(final Update update, final Set<String> roles, final String username) {
            if(...) {
                throw new VetoException("UpdateCiCommand vetoed");
            }
        }
    }
