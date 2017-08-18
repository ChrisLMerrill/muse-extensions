package org.musetest.extensions.install;

import org.musetest.extensions.*;

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
            public ExtensionInstallLog install(ExtensionInfo extension, File folder) throws IOException
                {
                final ExtensionInstallLog log = new ExtensionInstallLog(folder);
                for (ExtensionProjectAsset asset : extension.getAssets())
                    {
                    AssetInstallers.find(asset).install(asset, folder, log);
                    }
                return log;
                }
            };
        }
    }


