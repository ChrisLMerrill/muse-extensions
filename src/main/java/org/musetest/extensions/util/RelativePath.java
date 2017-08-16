package org.musetest.extensions.util;

import java.io.*;
import java.nio.file.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class RelativePath
    {
    public static Path get(File from, File to)
        {
        Path from_path = Paths.get(from.getAbsolutePath());
        Path to_path = Paths.get(to.getAbsolutePath());
        return from_path.relativize(to_path);
        }
    }


