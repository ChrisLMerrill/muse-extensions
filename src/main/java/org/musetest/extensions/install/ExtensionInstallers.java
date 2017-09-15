package org.musetest.extensions.install;

import org.musetest.extensions.*;
import org.musetest.extensions.registry.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionInstallers
    {
    public static ExtensionInstaller find(@SuppressWarnings("unused") ExtensionInfo extension)  // expecting future use
        {
        return new ExtensionInstaller()
            {
            @Override
            public void install(ExtensionInfo extension, File folder, ExtensionRegistry registry, ExtensionInstallLog log)
                {
                if (log == null)
                    log = new ExtensionInstallLog(folder);
                log.setFolder(folder);
                log.recordMessage("Starting installation of extension: " + extension.getDisplayNameVersion());
                for (ExtensionProjectAsset asset : extension.getAssets())
                    AssetInstallers.find(asset).install(asset, folder, log);

                if (log.getNumberActionFailures() == 0)
                    {
                    try
                        {
                        final ExtensionRegistryEntry entry = new ExtensionRegistryEntry(extension, log);
                        registry.add(entry);
                        log.setEntry(entry);
                        log.setRegistryUpdated(true);
                        }
                    catch (ExtensionRegistryException e)
                        {
                        log.setRegistryUpdated(false);
                        log.setRegistryUpdateMessage(e.getMessage());
                        }
                    }
                if (log.getNumberActionFailures() == 0 || log.isRegistryUpdated())
                    log.recordMessage("Installation successful.");
                else
                    log.recordMessage("Installation FAILED.");
                }
            };
        }
    }


