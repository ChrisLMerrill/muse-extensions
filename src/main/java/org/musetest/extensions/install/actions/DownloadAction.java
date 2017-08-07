package org.musetest.extensions.install.actions;

import org.musetest.extensions.*;
import org.musetest.extensions.install.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class DownloadAction implements AssetInstallerAction
    {
    @Override
    public Type getType()
        {
        return AssetInstallerAction.Type.Download;
        }

    @Override
    public boolean performAction(ExtensionProjectAsset asset, File folder, Map<String, String> parameters, ExtensionInstallLog log)
        {
        File destination = new File(folder, asset.getDefaultPath());
        if (!destination.getParentFile().exists())
            {
            if (!destination.getParentFile().mkdirs())
                return false; // TODO record the error
            }

        try (FileOutputStream outstream = new FileOutputStream(destination);
             InputStream instream = new URL(asset.getUrl()).openStream())
            {
            byte[] chunk = new byte[4096];
            int bytesRead;
            while ((bytesRead = instream.read(chunk)) > 0)
                outstream.write(chunk, 0, bytesRead);

            outstream.close();
            instream.close();

            if (destination.length() > 0)
                {
                log.recordFileInstalled(destination);
                return true;
                }
            else
                return false;
            }
        catch (IOException e)
            {
            // TODO log the failure

            return false;
            }
        }
    }


