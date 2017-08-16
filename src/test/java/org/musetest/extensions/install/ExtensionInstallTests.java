package org.musetest.extensions.install;

import org.apache.commons.io.*;
import org.junit.*;
import org.musetest.extensions.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionInstallTests
    {
    @Test
    public void installAndRemoveAsset() throws IOException
        {
        ExtensionProjectAsset asset = createAsset("asset.txt", "abc123");
        final AssetInstaller installer = AssetInstallers.find(asset);
        installer.install(asset, _folder, new ExtensionInstallLog(_folder));
        verifyAssetInstalled(asset, true);

        // remove it
        installer.remove(asset, _folder);
        verifyAssetInstalled(asset, false);
        }

    @Test
    public void installAndRemove() throws IOException
        {
        ExtensionProjectAsset asset1 = createAsset("asset1.txt", "content1");
        ExtensionProjectAsset asset2 = createAsset("asset2.txt", "content2");
        ExtensionInfo extension = new ExtensionInfo(1L, "ext1", "me", 1L, "1.0");
        List<ExtensionProjectAsset> assets = new ArrayList<>();
        assets.add(asset1);
        assets.add(asset2);
        extension.setAssets(assets);

        final ExtensionInstaller installer = ExtensionInstallers.find(extension);
        installer.install(extension, _folder);

        verifyAssetInstalled(asset1, true);
        verifyAssetInstalled(asset2, true);
        // TODO verify the extension registry was updated

        installer.remove(extension, _folder);
        verifyAssetInstalled(asset1, false);
        verifyAssetInstalled(asset2, false);
        // TODO verify the extension registry was restored
        }

    /*
    @Test
    public void verifyExtensionGood()
        {
        // verify an installed extension has all assets present
        Assert.fail("this test is not yet finished"); // TODO
        }

    @Test
    public void verifyExtensionCorrupted()
        {
        // verify an installed extension has a corrupted asset (verify fails)
        Assert.fail("this test is not yet finished"); // TODO
        }

    @Test
    public void installDuplicate()
        {
        // install a duplicate (should fail)
        Assert.fail("this test is not yet finished"); // TODO
        }

    @Test
    public void upgrade()
        {
        Assert.fail("this test is not yet finished"); // TODO
        }
*/

    private ExtensionProjectAsset createAsset(String asset_filename, String asset_content)
        {
        ExtensionProjectAsset asset = new ExtensionProjectAsset();
        asset.setDefaultPath(asset_filename);
        asset.setContent(asset_content.getBytes());
        List<AssetInstallInstruction> instructions = new ArrayList<>();
        instructions.add(new AssetInstallInstruction(AssetInstallerAction.Type.CreateFile.name()));
        asset.setInstallInstructions(instructions);
        return asset;
        }

    private void verifyAssetInstalled(ExtensionProjectAsset asset, boolean installed) throws IOException
        {
        final File installed_asset = new File(_folder, asset.getDefaultPath());
        boolean exists = installed_asset.exists();
        if (!installed)
            exists = !exists;
        Assert.assertTrue("asset was not created in folder", exists);

        if (installed)
            Assert.assertEquals("asset does not contain the correct content", new String(asset.getContent()), new String(Files.readAllBytes(installed_asset.toPath())));
        }

    @Before
    public void setup() throws IOException
        {
        _folder = Files.createTempDirectory("musetest-").toFile();
        }

    @After
    public void teardown() throws IOException
        {
        FileUtils.deleteDirectory(_folder);
        }

    private File _folder;
    }


