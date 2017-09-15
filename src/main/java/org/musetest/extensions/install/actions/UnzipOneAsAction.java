package org.musetest.extensions.install.actions;

import com.google.common.io.*;
import org.musetest.extensions.*;
import org.musetest.extensions.install.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class UnzipOneAsAction implements AssetInstallerAction
    {
    @Override
    public Type getType()
        {
        return Type.UnzipOneAs;
        }

    @Override
    public boolean performAction(ExtensionProjectAsset asset, File folder, Map<String, String> parameters, ExtensionInstallLog log)
        {
        log.recordMessage(String.format("Unzip %s from %s", parameters.get(FILENAME_PARAM), asset.getDefaultPath()));

        try
            {
            File source_file = new File(folder, asset.getDefaultPath());
            if (!source_file.exists())
                return false;
            ZipFile zipfile = new ZipFile(source_file);
            ZipEntry entry = zipfile.entries().nextElement();
            String filename = parameters.get(FILENAME_PARAM);
            if (filename == null)
                {
                log.recordActionFailure(this);
                return false;
                }
            File target_file = new File(source_file.getParent(), filename);
            try (OutputStream outstream = new FileOutputStream(target_file); InputStream instream = zipfile.getInputStream(entry))
                {
                ByteStreams.copy(instream, outstream);
                log.recordFileInstalled(target_file);
                }
            zipfile.close();
            return true;
            }
        catch (Exception e)
            {
            log.recordActionFailure(this);
            return false;
            }
        }

    public final static String FILENAME_PARAM = "filename";
    }


