package org.musetest.extensions.install;

import org.musetest.extensions.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public interface ExtensionInstaller
    {
    void install(ExtensionInfo extension, File folder) throws IOException;
    void remove(ExtensionInfo extension, File folder) throws IOException;
    }

