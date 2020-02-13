package org.museautomation.extensions.install;

import org.museautomation.extensions.registry.*;
import org.museautomation.extensions.api.*;

import java.io.*;

/**
 * This installer is based on the AssetInstaller design. The design is legacy/deprecated.
 *
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@Deprecated
public class LegacyAssetInstaller implements ExtensionInstaller
    {
    @Override
    public ExtensionRegistryEntry install(ExtensionInfo extension, File folder, ExtensionRegistry registry, ExtensionInstallLog log)
        {
        if (log == null)
            log = new ExtensionInstallLog(folder);
        log.setFolder(folder);
        log.recordMessage("Starting installation of extension: " + extension.getDisplayNameVersion());
        for (ExtensionProjectAsset asset : extension.getAssets())
            {
            AssetInstallers.find(asset).install(asset, folder, log);
            if (log.getNumberActionFailures() > 0)
                break;
            }

        ExtensionRegistryEntry entry = null;
        if (log.getNumberActionFailures() == 0)
            {
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
        if (log.getNumberActionFailures() == 0 || log.isRegistryUpdated())
            log.recordMessage("Installation successful.");
        else
            log.recordMessage("Installation FAILED.");
        return entry;
        }

    }


