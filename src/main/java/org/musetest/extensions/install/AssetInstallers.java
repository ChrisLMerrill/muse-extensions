package org.musetest.extensions.install;

import org.musetest.extensions.*;

import java.io.*;
import java.net.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class AssetInstallers
    {
    public static AssetInstaller find(ExtensionProjectAsset asset)
        {
        return new AssetInstaller()
            {
            @Override
            public void install(ExtensionProjectAsset asset, File folder) throws IOException
                {
                if (asset.getContent() != null)
                    {
                    File destination = new File(folder, asset.getDefaultPath());
                    FileOutputStream outstream = new FileOutputStream(destination);
                    outstream.write(asset.getContent());
                    outstream.close();
                    return;
                    }
                if (asset.getUrl() != null)
                    {
                    HttpURLConnection connection = (HttpURLConnection) new URL(asset.getUrl()).openConnection();
                    InputStream instream = connection.getInputStream();
                    File destination = new File(folder, asset.getDefaultPath());
                    FileOutputStream outstream = new FileOutputStream(destination);
                    while (instream.available() > 0)
                        {
                        byte[] bytes = new byte[instream.available()];
                        outstream.write(instream.read(bytes));
                        }
                    outstream.close();
                    instream.close();
                    }
                }

            @Override
            public void remove(ExtensionProjectAsset asset, File folder) throws IOException
                {
                File target = new File(folder, asset.getDefaultPath());
                if (!target.delete())
                    throw new IOException("Unable to remove asset at: " + target.getAbsolutePath());
                }
            };
        }
    }


