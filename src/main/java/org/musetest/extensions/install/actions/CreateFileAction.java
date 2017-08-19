package org.musetest.extensions.install.actions;

import org.musetest.extensions.*;
import org.musetest.extensions.install.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@SuppressWarnings("unused")  // instantiated via reflection
public class CreateFileAction extends PrepareFoldersAction
    {
    @Override
    public Type getType()
        {
        return Type.CreateFile;
        }

    @Override
    public boolean performAction(ExtensionProjectAsset asset, File folder, Map<String, String> parameters, ExtensionInstallLog log)
        {
        if (!super.performAction(asset, folder, parameters, log))
            return false;

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


