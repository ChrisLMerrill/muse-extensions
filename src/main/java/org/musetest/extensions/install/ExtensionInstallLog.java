package org.musetest.extensions.install;

import com.fasterxml.jackson.annotation.*;
import org.musetest.extensions.api.*;
import org.musetest.extensions.registry.*;
import org.musetest.extensions.util.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@SuppressWarnings("unused") // public API used outside this projet
public class ExtensionInstallLog
    {
    @SuppressWarnings("unused") // required for deserialization
    private ExtensionInstallLog()
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

    void recordFailure(String message)
        {
        recordMessage(message);
        _failed_actions++;
        }

    void recordActionFailure(AssetInstallInstruction instruction)
        {
        recordMessage("Asset installation failed: " + instruction.renderDescription());
        _failed_actions++;
        }

    public void recordActionFailure(AssetInstallerAction action)
        {
        _failed_actions++;
        recordMessage("Action failed: " + action.getType().name());
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
        recordMessage("delete file: " + target);
        }

    public boolean isRegistryUpdated()
        {
        return _registry_updated;
        }

    void setRegistryUpdated(boolean registry_updated)
        {
        _registry_updated = registry_updated;
        recordMessage("Extension Registry updated: " + registry_updated);
        }

    /**
     * The exception message if unable to update the registry.
     * @return The message
     */
    public String getRegistryUpdateMessage()
        {
        return _registry_update_message;
        }

    void setRegistryUpdateMessage(String registry_update_message)
        {
        _registry_update_message = registry_update_message;
        recordMessage(registry_update_message);
        }

    // property removed, stub methods retained for serialization compatibility. Remove in the future.
    @JsonIgnore
    @Deprecated
    public ExtensionRegistryEntry getEntry() { return null; }

    // property removed, stub methods retained for serialization compatibility. Remove in the future.
    @JsonIgnore
    @Deprecated
    public void setEntry(ExtensionRegistryEntry entry) { }

    public void recordMessage(String text)
        {
        final Message message = new Message(System.currentTimeMillis(), text);
        _messages.add(message);
        for (MessageListener listener : _listeners)
            listener.messageAdded(message);
        }

    public void addMessageListener(MessageListener listener)
        {
        _listeners.add(listener);
        }

    public void removeMessageListener(MessageListener listener)
        {
        _listeners.remove(listener);
        }

    public void setFolder(File folder)
        {
        if (_folder != null)
            LOG.warn("");
        _folder = folder;
        }

    private transient File _folder;
    private int _files_installed = 0;
    private int _failed_actions = 0;
    private boolean _registry_updated = false;
    private String _registry_update_message = null;
    private List<String> _installed_file_paths = new ArrayList<>();
    private List<Message> _messages = new ArrayList<>();
    private transient List<MessageListener> _listeners = new ArrayList<>();

    public class Message
        {
        Message(long time, String message)
            {
            _time = time;
            _message = message;
            }

        public String getText()
            {
            return _message;
            }

        long _time;
        String _message;
        }

    public interface MessageListener
        {
        void messageAdded(Message message);
        }

    private final static Logger LOG = LoggerFactory.getLogger(ExtensionInstallLog.class);
    }


