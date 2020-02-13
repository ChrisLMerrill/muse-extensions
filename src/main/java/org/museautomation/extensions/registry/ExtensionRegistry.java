package org.museautomation.extensions.registry;

import com.fasterxml.jackson.databind.*;
import org.museautomation.core.*;
import org.museautomation.core.resource.*;
import org.museautomation.core.resource.storage.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionRegistry
    {
    @SuppressWarnings("unused")  // this is the public API for expected use
    public ExtensionRegistry(MuseProject project)
        {
        ResourceStorage storage = project.getResourceStorage();
        if (!(storage instanceof FolderIntoMemoryResourceStorage))
            {
            String message = "Unable to use extensions with project resource storage type: " + storage.getClass().getSimpleName();
            LOG.error(message);
            throw new IllegalArgumentException(message);
            }

        _folder = new File(((FolderIntoMemoryResourceStorage) storage).getBaseLocation(), DEFAULT_FOLDER);
        if (!_folder.exists() && !_folder.mkdirs())
            LOG.error(String.format("Unable to create the extension registry folder (%s). Future extension registry updates will likely fail.", _folder.getPath()));
        }

    public ExtensionRegistry(File folder)
        {
        _folder = folder;
        if (!folder.exists() && !folder.mkdirs())
            LOG.error(String.format("Unable to create the extension registry folder (%s). Future extension registry updates will likely fail.", folder.getPath()));
        }

    public List<ExtensionRegistryEntry> listExtensions() throws ExtensionRegistryException
        {
        List<ExtensionRegistryEntry> entries = new ArrayList<>();

        File[] files = _folder.listFiles(pathname ->
            {
            Pattern pattern = Pattern.compile("\\d+-\\d+\\.json");
            return pattern.matcher(pathname.getName()).matches();
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

    public ExtensionFileCleaner getCleaner()
        {
        return new ExtensionFileCleaner(_folder);
        }

    private File _folder;

    public final static String DEFAULT_FOLDER = ".extreg";

    private final static Logger LOG = LoggerFactory.getLogger(ExtensionRegistry.class);
    }


