package org.musetest.extensions.install;

import org.musetest.extensions.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public interface AssetInstaller
    {
    void install(ExtensionProjectAsset asset, File folder) throws IOException;
    void remove(ExtensionProjectAsset asset, File folder) throws IOException;
    }

