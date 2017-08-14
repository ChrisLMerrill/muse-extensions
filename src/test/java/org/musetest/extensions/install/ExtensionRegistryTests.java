package org.musetest.extensions.install;

import org.apache.commons.io.*;
import org.junit.*;
import org.musetest.extensions.*;
import org.musetest.extensions.registry.*;

import java.io.*;
import java.nio.file.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionRegistryTests
    {
    @Test
    public void addAndRemoveExtensions() throws ExtensionRegistryException
        {
        // list extensions - should be zero
        ExtensionRegistry registry = new ExtensionRegistry(_folder);
        checkRegistryAndNewRegistry(registry, 0);

        // add an extension
        final ExtensionRegistryEntry entry1 = new ExtensionRegistryEntry();
        ExtensionInfo info1 = new ExtensionInfo(1L, "ext1", "author1", 1L, "v1");
        entry1.setInfo(info1);
        registry.add(entry1);
        checkRegistryAndNewRegistry(registry, 1, entry1);

        // add an extension
        final ExtensionRegistryEntry entry2 = new ExtensionRegistryEntry();
        ExtensionInfo info2 = new ExtensionInfo(2L, "ext2", "author2", 2L, "v2");
        entry2.setInfo(info2);
        registry.add(entry2);
        checkRegistryAndNewRegistry(registry, 2, entry2);

        // remove an extension
        registry.remove(entry1);
        checkRegistryAndNewRegistry(registry, 1, entry2);

        // remove an extension
        registry.remove(entry2);
        checkRegistryAndNewRegistry(registry, 0);
        }

    private void checkRegistryAndNewRegistry(ExtensionRegistry registry, int num_entries, ExtensionRegistryEntry... entries) throws ExtensionRegistryException
        {
        checkRegistry(registry, num_entries, entries);
        checkRegistry(new ExtensionRegistry(_folder), num_entries, entries);
        }

    private void checkRegistry(ExtensionRegistry registry, int num_entries, ExtensionRegistryEntry[] entries) throws ExtensionRegistryException
        {
        Assert.assertEquals(num_entries, registry.listExtensions().size());
        if (entries != null)
            for (ExtensionRegistryEntry entry : entries)
                Assert.assertTrue(registry.listExtensions().contains(entry));
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


