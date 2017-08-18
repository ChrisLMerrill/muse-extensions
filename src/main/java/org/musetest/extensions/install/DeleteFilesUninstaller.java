package org.musetest.extensions.install;

import org.musetest.extensions.registry.*;
import sun.misc.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class DeleteFilesUninstaller implements ExtensionUninstaller
    {
    @Override
    public void uninstall(ExtensionRegistryEntry extension, File folder) throws ExtensionInstallationException
        {
        List<File> files_to_remove = new ArrayList<>();
        for (String path : extension.getLog().getInstalledFilePaths())
            {
            File file = new File(folder, path);
            if (!file.exists())
                throw new ExtensionInstallationException("Unable to attempt uninstall of the extension - file is missing: " + file.getPath());
            files_to_remove.add(file);
            }

        for (File file : files_to_remove)
            if (!file.delete())
                throw new ExtensionInstallationException("Unable to delete file. Uninstall is incomplete. File is " + file.getPath());
        }
    }


