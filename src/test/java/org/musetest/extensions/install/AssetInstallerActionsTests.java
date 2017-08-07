package org.musetest.extensions.install;

import org.junit.*;
import org.musetest.extensions.install.actions.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class AssetInstallerActionsTests
    {
    @Test
    public void locateZipAction()
        {
        AssetInstallerAction zip = AssetInstallerActions.findImplementation(AssetInstallerAction.Type.UnzipAll);
        Assert.assertNotNull(zip);
        Assert.assertTrue(zip instanceof UnzipAction);
        }

    @Test
    public void locateDownloadAction()
        {
        AssetInstallerAction download = AssetInstallerActions.findImplementation(AssetInstallerAction.Type.Download);
        Assert.assertNotNull(download);

        Assert.assertTrue(download instanceof DownloadAction);
        }
    }


