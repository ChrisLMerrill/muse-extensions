package org.museautomation.extensions.install.actions;

import org.museautomation.extensions.install.*;
import org.museautomation.extensions.api.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@SuppressWarnings("unused")  // instantiated via reflection
public class CreateFileAction extends PrepareFoldersAction
    {
    @Override
    public AssetInstallerAction.Type getType()
        {
        return AssetInstallerAction.Type.CreateFile;
        }

    @Override
    public boolean performAction(ExtensionProjectAsset asset, File folder, Map<String, String> parameters, ExtensionInstallLog log)
        {
        if (!super.performAction(asset, folder, parameters, log))
            return false;

        log.recordMessage("Create File: " + asset.getDefaultPath());
        try
            {
            File target = new File(folder, asset.getDefaultPath());
            FileOutputStream outstream = new FileOutputStream(target);
            outstream.write(asset.getContent());
            outstream.close();
            log.recordFileInstalled(target);
            return true;
            }
        catch (IOException e)
            {
            log.recordActionFailure(this);
            return false;
            }
        }
    }


