package org.museautomation.extensions.install;

import org.museautomation.extensions.api.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public interface AssetInstaller
    {
    void install(ExtensionProjectAsset asset, File folder, ExtensionInstallLog log);
    }

