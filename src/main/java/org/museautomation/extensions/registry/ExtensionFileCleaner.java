package org.museautomation.extensions.registry;

import com.fasterxml.jackson.databind.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionFileCleaner
    {
    ExtensionFileCleaner(File folder)
        {
        _folder = folder;
        File file = getCleanupFile();
        if (file.exists())
            {
            try
                {
                _list = new ObjectMapper().readValue(file, FileList.class);
                }
            catch (IOException e)
                {
                // ignore...we'll just ignore and overwrite if needed.
                }
            }
        }

    private File getCleanupFile()
        {
        return new File(_folder, "cleanup.json");
        }

    public void addFile(File file)
        {
        _list.getFilenames().add(file.getAbsolutePath());
        _changed = true;
        }

    public void saveChanges()
        {
        if (_changed)
            try
                {
                new ObjectMapper().writeValue(getCleanupFile(), _list);
                }
            catch (IOException e)
                {
                LOG.error("ExtensionFileCleaner is unable to save file", e);
                }
        }

    @SuppressWarnings("unused") // public API
    public void cleanup()
        {
        for (File file : getFiles())
            {
            if (file.delete())
                {
                _list.removeFilename(file.getAbsolutePath());
                _changed = true;
                }
            else
                LOG.error("ExtensionFileCleaner was unable to delete file: " + file.getAbsolutePath());
            }
        saveChanges();
        }

    private List<File> getFiles()
        {
        List<File> files = new ArrayList<>();
        for (String filename : _list.getFilenames())
            files.add(new File(filename));
        return files;
        }

    private static class FileList
        {
        @SuppressWarnings("WeakerAccess")  // needed for deserialization
        public List<String> getFilenames()
            {
            return _filenames;
            }

        @SuppressWarnings("WeakerAccess")  // needed for deserialization
        public void removeFilename(String filename)
            {
            _filenames.remove(filename);
            }

        @SuppressWarnings("unused")  // needed for deserialization
        public void setFilenames(List<String> filenames)
            {
            this._filenames = filenames;
            }

        private List<String> _filenames = new ArrayList<>();
        }

    private File _folder;
    private FileList _list = new FileList();
    private boolean _changed = false;

    private final static Logger LOG = LoggerFactory.getLogger(ExtensionFileCleaner.class);
    }