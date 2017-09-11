package org.musetest.extensions.install;

import org.musetest.extensions.*;

import java.io.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public interface AssetInstallerAction
    {
    Type getType();
    boolean performAction(ExtensionProjectAsset asset, File folder, Map<String, String> parameters, ExtensionInstallLog log);

    enum Type
        {
        CreateFile,
        DeleteFile,
        Download,
        UnzipAll,
        UnzipOneAs,
        }
    }

