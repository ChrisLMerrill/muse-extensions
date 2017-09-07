package org.musetest.extensions.install;

import com.fasterxml.jackson.annotation.*;
import org.musetest.extensions.*;
import org.musetest.extensions.registry.*;
import org.musetest.extensions.util.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionInstallLog
    {
    @SuppressWarnings("unused")
    private ExtensionInstallLog() // needed for deserialization
        {
        }

    public ExtensionInstallLog(File folder)
        {
        _folder = folder;
        }

    public int getNumberFilesInstalled()
        {
        return _files_installed;
        }

    // only for JSON deserialization
    void setNumberFilesInstalled(int files_installed)
        {
        _files_installed = files_installed;
        }

    public void recordFileInstalled(File destination)
        {
        _files_installed++;
        _installed_file_paths.add(RelativePath.get(_folder, destination).toString());
        }

    public void recordActionFailure(AssetInstallInstruction instruction)
        {
        _failed_actions++;
        }

    public void recordActionFailure(AssetInstallerAction action)
        {
        _failed_actions++;
        }

    public int getNumberActionFailures()
        {
        return _failed_actions;
        }

    // only for JSON deserialization
    void setNumberActionFailures(int failed_actions)
        {
        _failed_actions = failed_actions;
        }

    public String getInstalledFilePath(int index)
        {
        return _installed_file_paths.get(index);
        }

    public List<String> getInstalledFilePaths()
        {
        return _installed_file_paths;
        }

    public void setInstalledFilePaths(List<String> installed_file_paths)
        {
        _installed_file_paths = installed_file_paths;
        }

    public void recordFileDeleted(File target)
        {
        String path = RelativePath.get(_folder, target).toString();
        if (_installed_file_paths.remove(path))
            _files_installed--;
        }

    public boolean isRegistryUpdated()
        {
        return _registry_updated;
        }

    public void setRegistryUpdated(boolean registry_updated)
        {
        _registry_updated = registry_updated;
        }

    /**
     * The exception message if unable to update the registry.
     */
    public String getRegistryUpdateMessage()
        {
        return _registry_update_message;
        }

    public void setRegistryUpdateMessage(String registry_update_message)
        {
        _registry_update_message = registry_update_message;
        }

    public ExtensionRegistryEntry getEntry()
        {
        return _entry;
        }

    public void setEntry(ExtensionRegistryEntry entry)
        {
        _entry = entry;
        }

    private transient File _folder;
    private int _files_installed = 0;
    private int _failed_actions = 0;
    private boolean _registry_updated = false;
    private String _registry_update_message = null;
    private ExtensionRegistryEntry _entry = null;
    private List<String> _installed_file_paths = new ArrayList<>();
    }


