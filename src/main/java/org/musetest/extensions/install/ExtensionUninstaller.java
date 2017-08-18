package org.musetest.extensions.install;

import org.musetest.extensions.registry.*;
import sun.misc.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public interface ExtensionUninstaller
    {
    void uninstall(ExtensionRegistryEntry extension, File folder) throws ExtensionInstallationException;
    }


