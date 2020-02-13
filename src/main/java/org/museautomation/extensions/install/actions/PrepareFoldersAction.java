package org.museautomation.extensions.install.actions;

import org.museautomation.extensions.install.*;
import org.museautomation.extensions.api.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public abstract class PrepareFoldersAction implements AssetInstallerAction
    {
    @Override
    public boolean performAction(ExtensionProjectAsset asset, File folder, Map<String, String> parameters, ExtensionInstallLog log)
        {
        String path = asset.getDefaultPath();
        StringTokenizer tokenizer = new StringTokenizer(path, "/\\");
        File current = folder;
        while (tokenizer.hasMoreTokens())
            {
            String new_path = tokenizer.nextToken();
            if (tokenizer.hasMoreTokens())  // checking for another...because the final token will be the name, which we won't created a folder for
                {
                current = new File(current, new_path);
                if (!current.exists())
                    {
                    if (current.mkdir())
                        log.recordFileInstalled(current);
                    else
                        return false;
                    }
                }
            }

        return true;
        }
    }


