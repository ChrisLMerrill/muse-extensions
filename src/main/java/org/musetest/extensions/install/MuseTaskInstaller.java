package org.musetest.extensions.install;

import org.musetest.builtins.step.files.*;
import org.musetest.core.*;
import org.musetest.core.context.*;
import org.musetest.core.execution.*;
import org.musetest.core.project.*;
import org.musetest.core.resource.json.*;
import org.musetest.core.resource.origin.*;
import org.musetest.core.resource.storage.*;
import org.musetest.core.steptest.*;
import org.musetest.core.test.*;
import org.musetest.core.test.plugins.*;
import org.musetest.extensions.api.*;
import org.musetest.extensions.registry.*;
import org.slf4j.*;

import javax.annotation.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

/**
 * This installer is based on downloading and running a MuseTask to perform the installation logic.
 *
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class MuseTaskInstaller implements ExtensionInstaller
    {
    @Override
    public ExtensionRegistryEntry install(ExtensionInfo extension, File folder, ExtensionRegistry registry, @Nullable ExtensionInstallLog log)
        {
        log.recordMessage("Downloading installer...");
        LOG.error("Download and install from: " + extension.getInstallTaskUrl());
        // download the installer task
        String install_task_string;
        try
            {
            install_task_string = new Scanner(new URL(extension.getInstallTaskUrl()).openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();
            log.recordMessage("Installer download complete.");
            }
        catch (IOException e)
            {
            LOG.error("Download of installer failed", e);
            log.recordFailure("Download of installer failed.");
            return null;
            }

        // prepare the installer task
        MuseProject project = new SimpleProject(new FolderIntoMemoryResourceStorage(folder));
        SteppedTest install_task;
        log.recordMessage("Initializing installer...");
        List<MuseResource> resources = new FromJsonFileResourceFactory().createResources(new StreamResourceOrigin(new ByteArrayInputStream(install_task_string.getBytes())), project.getClassLocator());
        if (resources.size() == 0)
            {
            log.recordFailure("Unrecognized installer content.");
            return null;
            }

        if (resources.get(0) instanceof SteppedTest)
            install_task = (SteppedTest) resources.get(0);
        else
            {
            log.recordFailure("Installer is unexpected resource type: " + resources.get(0).getType().getName());
            return null;
            }
        log.recordMessage("Installer initialized.");

        // run the installer task
        log.recordMessage("Starting installer...");
        ProjectExecutionContext context = new ProjectExecutionContext(project);
        context.setVariable("install", Boolean.TRUE);
        try
            {
            project.getResourceStorage().addResource(new TestDefaultsInitializerConfiguration.TestDefaultsInitializerType().create());
            }
        catch (IOException e)
            {
            log.recordFailure("Unable to configure the installation environtment.");
            LOG.error("Unable to setup TestDefaultsInitializer", e);
            return null;
            }

        SimpleTestRunner runner = new SimpleTestRunner(context, new BasicTestConfiguration(install_task));
        runner.getExecutionContext().addEventListener(event ->
            {
            // filter events into the install log
            if (FileCreatedEventType.TYPE_ID.equals(event.getTypeId()))
                {
                log.recordFileInstalled(FileCreatedEventType.getFile(event));
                log.recordMessage(new FileCreatedEventType().getDescription(event));
                }
            else if (FileDeletedEventType.TYPE_ID.equals(event.getTypeId()))
                {
                log.recordFileInstalled(FileDeletedEventType.getFile(event));
                log.recordMessage(new FileDeletedEventType().getDescription(event));
                }
            });

        runner.runTest();
        ExtensionRegistryEntry entry = null;
        if (runner.completedNormally())
            {
            log.recordMessage("Installation complete.");
            // update registry
            try
                {
                entry = new ExtensionRegistryEntry(extension, log);
                registry.add(entry);
                log.setRegistryUpdated(true);
                }
            catch (ExtensionRegistryException e)
                {
                log.setRegistryUpdated(false);
                log.setRegistryUpdateMessage(e.getMessage());
                }
            }
        else
            log.recordFailure("Installation failed.");

        return entry;
        }

    private final static Logger LOG = LoggerFactory.getLogger(MuseTaskInstaller.class);
    }