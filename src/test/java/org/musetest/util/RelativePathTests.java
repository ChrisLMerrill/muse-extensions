package org.musetest.util;

import org.junit.*;
import org.musetest.extensions.util.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class RelativePathTests
    {
    @Test
    public void inFolder()
        {
        String base = System.getProperty("user.dir");
        final String filename = "the_file";
        String path = RelativePath.get(new File(base), new File(base + File.separator + filename)).toString();
        Assert.assertEquals(filename, path);
        }

    @Test
    public void inSubfolder()
        {
        String base = System.getProperty("user.dir");
        final String filename = "subfolder" + File.separator + "the_file";
        String path = RelativePath.get(new File(base), new File(base + File.separator + filename)).toString();
        Assert.assertEquals(filename, path);
        }

    }


