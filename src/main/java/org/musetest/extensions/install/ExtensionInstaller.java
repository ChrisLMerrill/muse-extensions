package org.musetest.extensions.install;

import org.musetest.extensions.api.*;
import org.musetest.extensions.registry.*;

import javax.annotation.*;
import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public interface ExtensionInstaller
    {
    ExtensionRegistryEntry install( ExtensionInfo extension, File folder, ExtensionRegistry registry, @Nullable ExtensionInstallLog log);
    }

