package org.museautomation.extensions.install.actions;

import com.google.common.io.*;
import org.museautomation.extensions.install.*;
import org.museautomation.extensions.api.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class UnzipAction implements AssetInstallerAction
    {
    @Override
    public Type getType()
        {
        return Type.UnzipAll;
        }

    @Override
    public boolean performAction(ExtensionProjectAsset asset, File folder, Map<String, String> parameters, ExtensionInstallLog log)
        {
        log.recordMessage(String.format("Unzip: %s", asset.getDefaultPath()));
        try
            {
            File source_file = new File(folder, asset.getDefaultPath());
            if (!source_file.exists())
                return false;
            ZipFile zipfile = new ZipFile(source_file);
            final Enumeration<? extends ZipEntry> entries = zipfile.entries();
            while (entries.hasMoreElements())
                {
                ZipEntry entry = entries.nextElement();
                File target_file = new File(source_file.getParent(), entry.getName());
                try (OutputStream outstream = new FileOutputStream(target_file); InputStream instream = zipfile.getInputStream(entry))
                    {
                    ByteStreams.copy(instream, outstream);
                    log.recordFileInstalled(target_file);
                    }
                }
            zipfile.close();
            return true;
            }
        catch (Exception e)
            {
            // log the error
            return false;
            }
        }
    }


