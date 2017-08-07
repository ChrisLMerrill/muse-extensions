package org.musetest.extensions.install;

import org.musetest.extensions.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class AssetInstallers
    {
    public static AssetInstaller find(ExtensionProjectAsset asset)
        {
        return new AssetInstaller()
            {
            @Override
            public void install(ExtensionProjectAsset asset, File folder, ExtensionInstallLog log) throws IOException
                {
                for (AssetInstallInstruction instruction : asset.getInstallInstructions())
                    {
                    AssetInstallerAction action = AssetInstallerActions.findImplementation(AssetInstallerAction.Type.valueOf(instruction.getActionName()));
                    if (action != null && !action.performAction(asset, folder, instruction.getParameters(), log))
                        log.recordActionFailure(instruction);
                    }
                }

            @Override
            public void remove(ExtensionProjectAsset asset, File folder) throws IOException
                {
                File target = new File(folder, asset.getDefaultPath());
                if (!target.delete())
                    throw new IOException("Unable to remove asset at: " + target.getAbsolutePath());
                }
            }

            ;
        }
    }


