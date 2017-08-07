package org.musetest.extensions.install.actions;

import org.musetest.extensions.*;
import org.musetest.extensions.install.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class DeleteAssetAction implements AssetInstallerAction
    {
    @Override
    public Type getType()
        {
        return Type.DeleteFile;
        }

    @Override
    public boolean performAction(ExtensionProjectAsset asset, File folder, Map<String, String> parameters, ExtensionInstallLog log)
        {
        File target = new File(folder, asset.getDefaultPath());
        if (!target.exists())
            return false;
        return target.delete();
        }
    }


