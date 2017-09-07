package org.musetest.extensions.commandline;

import io.airlift.airline.*;
import org.musetest.core.commandline.*;
import org.musetest.extensions.install.*;
import org.musetest.extensions.registry.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@SuppressWarnings("unused")  // is called by reflection
@Command(name = "remove-extension", description = "Remove an extension from the project")
public class RemoveCommand extends MuseCommand
    {
    @SuppressWarnings("WeakerAccess")  // needs to be public
    @Arguments(description = "id of the extension version to remove", required = true)
    public String extension_id;

    @Override
    public void run()
        {
        Long extension_version_id;
        try
            {
            extension_version_id = Long.parseLong(extension_id);
            }
        catch (NumberFormatException e)
            {
            System.out.println("extension_id must be an integer");
            return;
            }

        try
            {
            final File project_folder = new File(System.getProperty("user.dir"));
            final File extreg_folder = new File(project_folder, ExtensionRegistry.DEFAULT_FOLDER);
            ExtensionRegistry registry = new ExtensionRegistry(extreg_folder);
            ExtensionRegistryEntry entry_to_remove = null;
            for (ExtensionRegistryEntry entry : registry.listExtensions())
                if (entry.getInfo().getVersionId() == extension_version_id)
                    entry_to_remove = entry;
            if (entry_to_remove == null)
                {
                System.out.println("Extension " + extension_id + " was not found in the registry of extensions installed in this project.");
                return;
                }


            // install the extension
            System.out.println(String.format("Removing extension: %s...", entry_to_remove.getInfo().getDisplayNameVersion()));
            ExtensionUninstaller uninstaller = ExtensionUninstallers.findUninstaller(entry_to_remove, project_folder);
            final ExtensionUninstallResult result = uninstaller.uninstall(entry_to_remove, project_folder, registry);

            if (result.isSuccess())
                {
                for (String warning : result.getWarnings())
                    System.out.println("WARNING: " + warning);
                System.out.println("successfully uninstalled extension " + entry_to_remove.getInfo().getDisplayNameVersion());
                }
            else
                {
                for (String error : result.getErrors())
                    System.out.println("ERROR: " + error);
                System.out.println(String.format("uninstall of %s failed.", entry_to_remove.getInfo().getDisplayNameVersion()));
                }
            }
        catch (Exception e)
            {
            System.out.println("Failed to install extension: " + e.getLocalizedMessage());
            //e.printStackTrace();
            }

        }
    }


