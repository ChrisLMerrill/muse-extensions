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
    public ExtensionUninstallResult uninstall(ExtensionRegistryEntry extension, File folder)
        {
        ExtensionUninstallResult result = new ExtensionUninstallResult();
        List<File> files_to_remove = new ArrayList<>();
        for (String path : extension.getLog().getInstalledFilePaths())
            {
            File file = new File(folder, path);
            if (!file.exists())
                result.addWarning("File not found: " + file.getPath());
            files_to_remove.add(file);
            }

        for (File file : files_to_remove)
            if (!file.delete())
                result.addError("Uninstall is incomplet. Unable to delete file: " + file.getPath());
        return result;
        }
    }


