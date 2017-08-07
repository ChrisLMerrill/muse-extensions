package org.musetest.extensions.install;

import org.musetest.extensions.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionInstallLog
    {
    public int getNumberFilesInstalled()
        {
        return _files_installed;
        }

    public void recordFileInstalled(File destination)
        {
        _files_installed++;
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

    private int _files_installed = 0;
    private int _failed_actions = 0;
    }


