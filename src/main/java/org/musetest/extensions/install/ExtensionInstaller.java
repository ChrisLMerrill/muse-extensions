package org.musetest.extensions.install;

import org.musetest.extensions.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public interface ExtensionInstaller
    {
    ExtensionInstallLog install(ExtensionInfo extension, File folder) throws IOException;
    }

