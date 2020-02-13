package org.museautomation.extensions.install;

import org.museautomation.extensions.registry.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public interface ExtensionUninstaller
    {
    ExtensionUninstallResult uninstall(ExtensionRegistryEntry extension, File folder, ExtensionRegistry registry);
    }


