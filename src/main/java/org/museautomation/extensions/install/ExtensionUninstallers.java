package org.museautomation.extensions.install;

import org.museautomation.extensions.registry.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionUninstallers
    {
    public static ExtensionUninstaller findUninstaller(ExtensionRegistryEntry entry, File folder)
        {
        return new DeleteFilesUninstaller();
        }
    }


