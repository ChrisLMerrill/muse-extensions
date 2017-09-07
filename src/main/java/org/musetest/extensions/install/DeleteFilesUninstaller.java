package org.musetest.extensions.install;

import org.musetest.extensions.registry.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class DeleteFilesUninstaller implements ExtensionUninstaller
    {
    @Override
    public ExtensionUninstallResult uninstall(ExtensionRegistryEntry extension, File folder, ExtensionRegistry registry)
        {
        ExtensionUninstallResult result = new ExtensionUninstallResult();
        List<File> files_to_remove = new ArrayList<>();
        for (String path : extension.getLog().getInstalledFilePaths())
            {
            File file = new File(folder, path);
            if (!file.exists())
                result.addWarning("File to be deleted is already gone: " + file.getPath());
            files_to_remove.add(file);
            }

        Collections.reverse(files_to_remove);
        for (File file : files_to_remove)
            {
            final File[] subfiles = file.listFiles();
            if (subfiles != null && subfiles.length > 0)
                continue;  // don't attempt (and fail) to remove folders that are not empty. This is, presumably, due to other extensions installing in this folder.
            if (!file.delete())
                result.addError("Unable to delete file: " + file.getPath());
            }

        try
            {
            registry.remove(extension);
            }
        catch (ExtensionRegistryException e)
            {
            result.addError("Unable to update the extension registry: " + e.getMessage());
            }

        return result;
        }
    }


