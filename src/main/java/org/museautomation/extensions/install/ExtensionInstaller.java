package org.museautomation.extensions.install;

import org.museautomation.extensions.registry.*;
import org.museautomation.extensions.api.*;

import javax.annotation.*;
import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public interface ExtensionInstaller
    {
    ExtensionRegistryEntry install(ExtensionInfo extension, File folder, ExtensionRegistry registry, @Nullable ExtensionInstallLog log);
    }

