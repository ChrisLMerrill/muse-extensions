package org.musetest.extensions.install;

import org.apache.commons.io.*;
import org.junit.*;
import org.musetest.extensions.*;
import org.musetest.extensions.install.actions.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class AssetInstallerActionTests
    {
/*
    @Test
    public void downloadAction()
        {
        ExtensionProjectAsset asset = new ExtensionProjectAsset();
        asset.setUrl("http://ide4selenium.com/favicon.ico");
        asset.setDefaultPath("favicon.ico");
        ExtensionInstallLog log = new ExtensionInstallLog(_folder);

        boolean result = new DownloadAction().performAction(asset, _folder, null, log);

        Assert.assertTrue(result);
        final File installed_asset = new File(_folder, "favicon.ico");
        Assert.assertTrue(installed_asset.exists());
        Assert.assertEquals(1150, installed_asset.length());
        Assert.assertEquals(1, log.getNumberFilesInstalled());
        }
*/

    @Test
    public void downloadFailed()
        {
        ExtensionProjectAsset asset = new ExtensionProjectAsset();
        asset.setUrl("http://ide4selenium.com/blah_missing_url");
        asset.setDefaultPath("emptyfile.txt");

        boolean result = new DownloadAction().performAction(asset, _folder, null, new ExtensionInstallLog(_folder));

        Assert.assertFalse(result);
        }

    @Test
    public void unzipAction() throws URISyntaxException, IOException
        {
        ExtensionProjectAsset asset = new ExtensionProjectAsset();
        final String archive_filename = "testarchive.zip";
        asset.setDefaultPath(archive_filename);

        // copy the test file into the project folder
        Files.copy(getClass().getClassLoader().getResourceAsStream(archive_filename), new File(_folder, archive_filename).toPath(), StandardCopyOption.REPLACE_EXISTING);

        boolean result = new UnzipAction().performAction(asset, _folder, null, new ExtensionInstallLog(_folder));

        Assert.assertTrue(result);
        File target = new File(_folder, "testfile.txt");
        Assert.assertTrue(target.exists());
        final Scanner scanner = new Scanner(target);
        String content = scanner.useDelimiter("\\Z").next();
        scanner.close();
        Assert.assertEquals("This is a test file.", content);
        }

    @Test
    public void unzipOneAsAction() throws URISyntaxException, IOException
        {
        ExtensionProjectAsset asset = new ExtensionProjectAsset();
        final String archive_filename = "testarchive.zip";
        asset.setDefaultPath(archive_filename);

        // copy the test file into the project folder
        Files.copy(getClass().getClassLoader().getResourceAsStream(archive_filename), new File(_folder, archive_filename).toPath(), StandardCopyOption.REPLACE_EXISTING);

        Map<String, String> parameters = new HashMap<>();
        parameters.put(UnzipOneAsAction.FILENAME_PARAM, "file.ext");
        boolean result = new UnzipOneAsAction().performAction(asset, _folder, parameters, new ExtensionInstallLog(_folder));

        Assert.assertTrue(result);
        File original = new File(_folder, "testfile.txt");
        Assert.assertFalse(original.exists());
        File target = new File(_folder, "file.ext");
        Assert.assertTrue(target.exists());
        final Scanner scanner = new Scanner(target);
        String content = scanner.useDelimiter("\\Z").next();
        scanner.close();
        Assert.assertEquals("This is a test file.", content);
        }

    @Test
    public void unzipActionNestedFile() throws IOException
        {
        ExtensionProjectAsset asset = new ExtensionProjectAsset();
        final String archive_filename = "testarchive.zip";
        final String asset_path = "subfolder/" + archive_filename;
        asset.setDefaultPath(asset_path);

        // copy the test file into the project folder
        File subfolder = new File(_folder, "subfolder");
        Assert.assertTrue("something wrong with test environment", subfolder.mkdir());
        Files.copy(getClass().getClassLoader().getResourceAsStream(archive_filename), new File(subfolder, archive_filename).toPath(), StandardCopyOption.REPLACE_EXISTING);
        boolean result = new UnzipAction().performAction(asset, _folder, null, new ExtensionInstallLog(_folder));

        Assert.assertTrue(result);
        File target = new File(subfolder, "testfile.txt");
        Assert.assertTrue(target.exists());
        }

    @Test
    public void deleteAction() throws IOException
        {
        ExtensionProjectAsset asset = new ExtensionProjectAsset();
        final String archive_filename = "testarchive.zip";
        asset.setDefaultPath(archive_filename);

        // copy the test file into the project folder
        Files.copy(getClass().getClassLoader().getResourceAsStream(archive_filename), new File(_folder, archive_filename).toPath(), StandardCopyOption.REPLACE_EXISTING);

        boolean result = new DeleteAssetAction().performAction(asset, _folder, null, new ExtensionInstallLog(_folder));

        Assert.assertTrue(result);
        Assert.assertFalse(new File(_folder, archive_filename).exists());
        }

    @Before
    public void setup() throws IOException
        {
        _folder = Files.createTempDirectory("musetest-").toFile();
        //System.out.println("_folder: " + _folder.getAbsolutePath());
        }

    @After
    public void teardown() throws IOException
        {
        FileUtils.deleteDirectory(_folder);
        }

    private File _folder;
    }


