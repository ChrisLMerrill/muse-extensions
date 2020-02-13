package org.museautomation.extensions.install;

import org.museautomation.extensions.api.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionInstallers
    {
    public static ExtensionInstaller find(@SuppressWarnings("unused") ExtensionInfo extension)  // expecting future use
        {
        if (extension.getInstallTaskUrl() == null)
            return new LegacyAssetInstaller();
        else
            return new MuseTaskInstaller();
        }
    }


