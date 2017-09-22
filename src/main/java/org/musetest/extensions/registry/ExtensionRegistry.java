package org.musetest.extensions.registry;

import com.fasterxml.jackson.databind.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionRegistry
    {
    public ExtensionRegistry(File folder)
        {
        _folder = folder;
        if (!folder.exists() && !folder.mkdirs())
            LOG.error(String.format("Unable to create the extension registry folder (%s). Future extension registry updates will likely fail.", folder.getPath()));
        }

    public List<ExtensionRegistryEntry> listExtensions() throws ExtensionRegistryException
        {
        List<ExtensionRegistryEntry> entries = new ArrayList<>();

        File[] files = _folder.listFiles(new FileFilter()
            {
            @Override
            public boolean accept(File pathname)
                {
                Pattern pattern = Pattern.compile("\\d+-\\d+\\.json");
                return pattern.matcher(pathname.getName()).matches();
                }
            });
        if (files != null)
            {
            ObjectMapper mapper = new ObjectMapper();
            for (File file : files)
                {
                try (FileInputStream instream = new FileInputStream(file))
                    {
                    entries.add(mapper.readValue(instream, ExtensionRegistryEntry.class));
                    }
                catch (IOException e)
                    {
                    throw new ExtensionRegistryException("Unable to read extension registry entry from " + file.getPath(), e);
                    }
                }
            }

        return entries;
        }

    public void add(ExtensionRegistryEntry entry) throws ExtensionRegistryException
        {
        ObjectMapper mapper = new ObjectMapper();

        final String filename = getFilename(entry);
        final File file = new File(_folder, filename);
        if (file.exists())
            throw new ExtensionRegistryException(String.format("extension is already in the registry, filename=%s", filename));
        try (FileOutputStream outstream = new FileOutputStream(file))
            {
            mapper.writerWithDefaultPrettyPrinter().writeValue(outstream, entry);
            outstream.flush();
            }
        catch (IOException e)
            {
            throw new ExtensionRegistryException(String.format("Unable to save entry into the extension registry (filename=%s) due to: %s", filename, e.getMessage()), e);
            }
        }

    public void remove(ExtensionRegistryEntry entry) throws ExtensionRegistryException
        {
        String filename = getFilename(entry);
        File file = new File(_folder, filename);
        if (!file.exists())
            throw new ExtensionRegistryException(String.format("the entry was not found in the extension registry, filename=%s", filename));
        if (!file.delete())
            throw new ExtensionRegistryException(String.format("unable to delete the entry from the extension registry, filename=%s", filename));
        }

    private String getFilename(ExtensionRegistryEntry entry)
        {
        return String.format("%d-%d.json", entry.getInfo().getExtensionId(), entry.getInfo().getVersionId());
        }

    private File _folder;

    public final static String DEFAULT_FOLDER = ".extreg";

    private final static Logger LOG = LoggerFactory.getLogger(ExtensionRegistry.class);
    }


