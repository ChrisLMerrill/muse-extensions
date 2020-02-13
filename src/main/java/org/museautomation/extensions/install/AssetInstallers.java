package org.museautomation.extensions.install;

import org.museautomation.extensions.api.*;

import java.io.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
class AssetInstallers
    {
    static AssetInstaller find(@SuppressWarnings("unused") ExtensionProjectAsset asset)  // expecting future use
        {
        return new AssetInstaller()
            {
            @Override
            public void install(ExtensionProjectAsset asset, File folder, ExtensionInstallLog log)
                {
                for (AssetInstallInstruction instruction : asset.getInstallInstructions())
                    {
                    AssetInstallerAction action = AssetInstallerActions.findImplementation(AssetInstallerAction.Type.valueOf(instruction.getActionName()));
                    if (action != null)
                        {
                        if (!action.performAction(asset, folder, instruction.getParameters(), log))
                            {
                            log.recordActionFailure(instruction);
                            break;
                            }
                        }
                    }
                }
            };
        }
    }


