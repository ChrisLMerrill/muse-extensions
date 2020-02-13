package org.museautomation.extenions.registry;

import org.junit.*;
import org.museautomation.extensions.install.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionInstallLogTests
    {
    @Test
    public void recordFilePaths()
        {
        File folder = new File(System.getProperty("user.dir"));
        ExtensionInstallLog log = new ExtensionInstallLog(folder);
        log.recordFileInstalled(new File(folder, "file1"));
        log.recordFileInstalled(new File(folder, "file2"));
        log.recordFileInstalled(new File(folder, "folder1" + File.separator + "file3"));

        Assert.assertEquals(3, log.getNumberFilesInstalled());
        Assert.assertEquals("file1", log.getInstalledFilePath(0));
        Assert.assertEquals("file2", log.getInstalledFilePath(1));
        Assert.assertEquals("folder1" + File.separator + "file3", log.getInstalledFilePath(2));
        }
    }
