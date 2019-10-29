package org.musetest.extensions.install.actions;

import org.apache.commons.compress.archivers.tar.*;
import org.apache.commons.compress.compressors.gzip.*;
import org.apache.commons.io.*;
import org.musetest.extensions.api.*;
import org.musetest.extensions.install.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class UntargzOneAsAction implements AssetInstallerAction
    {
    @Override
    public Type getType()
        {
        return Type.UntargzOneAs;
        }

    @Override
    public boolean performAction(ExtensionProjectAsset asset, File folder, Map<String, String> parameters, ExtensionInstallLog log)
        {
        log.recordMessage(String.format("UnTarGz %s from %s", parameters.get(FILENAME_PARAM), asset.getDefaultPath()));

        FileInputStream input_stream = null;
        GzipCompressorInputStream gzstream = null;
        TarArchiveInputStream tarstream = null;

        try
            {
            // get the input file
            File source_file = new File(folder, asset.getDefaultPath());
            if (!source_file.exists())
	            {
	            LOG.error("The file does not exist: " + source_file.getName());
	            log.recordActionFailure(this);
	            return false;
	            }

            // find the destination filename
            String filename = parameters.get(FILENAME_PARAM);
            if (filename == null)
                {
                LOG.error("Destination file name is required for UntargzOneAsAction");
                log.recordActionFailure(this);
                return false;
                }
            File target_file = new File(source_file.getParent(), filename);

            input_stream = new FileInputStream(source_file);
            gzstream = new GzipCompressorInputStream(input_stream);
            tarstream = new TarArchiveInputStream(gzstream);
            TarArchiveEntry entry;
            while ((entry = (TarArchiveEntry) tarstream.getNextEntry()) != null)
	            {
	            if (!entry.isDirectory() && entry.getSize() > 0)
		            {
		            try (OutputStream outstream = new FileOutputStream(target_file))
		                {
		                IOUtils.copy(tarstream, outstream);
		                log.recordFileInstalled(target_file);
		                break;
		                }
		            catch (IOException e)
			            {
			            LOG.error("Unable to extract archive entry due to: " + e.getMessage());
			            log.recordActionFailure(this);
			            return false;
			            }
		            }
	            }

            return true;
            }
        catch (Exception e)
            {
            LOG.error("Unable to execute action", e);
            log.recordActionFailure(this);
            return false;
            }
        finally
	        {
	        try
		        {
		        if (tarstream != null)
			        tarstream.close();
		        if (gzstream != null)
			        gzstream.close();
		        if (input_stream != null)
			        input_stream.close();
		        }
	        catch (IOException e)
		        {
		        // no-op
		        }
	        }
        }

    public final static String FILENAME_PARAM = "filename";

    private final static Logger LOG = LoggerFactory.getLogger(UntargzOneAsAction.class);
    }
