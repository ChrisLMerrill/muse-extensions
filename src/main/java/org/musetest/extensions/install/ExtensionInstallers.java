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
            public ExtensionInstallLog install(ExtensionInfo extension, File folder, ExtensionRegistry registry)
                {
                final ExtensionInstallLog log = new ExtensionInstallLog(folder);
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
                return log;
                }
            };
        }
    }


