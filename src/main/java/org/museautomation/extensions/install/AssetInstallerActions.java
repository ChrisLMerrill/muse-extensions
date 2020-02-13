package org.museautomation.extensions.install;

import org.reflections.*;
import org.slf4j.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class AssetInstallerActions
    {
    public static AssetInstallerAction findImplementation(AssetInstallerAction.Type type)
        {
        if (ACTIONS == null)
            {
            Reflections reflections = new Reflections("org.museautomation.extensions.install.actions");
            Set<Class<? extends AssetInstallerAction>> types = reflections.getSubTypesOf(AssetInstallerAction.class);
            ACTIONS = new HashSet<>();
            for (Class<? extends AssetInstallerAction> action_class : types)
                {
                if (Modifier.isAbstract(action_class.getModifiers()))
                    continue;
                try
                    {
                    AssetInstallerAction action = action_class.newInstance();
                    ACTIONS.add(action);
                    }
                catch (Exception e)
                    {
                    LOG.error(String.format("Unable to instantiate an instance of %s due to %s", action_class.getName(), e.getMessage()));
                    }
                }
            }

        for (AssetInstallerAction action : ACTIONS)
            if (action.getType().equals(type))
                return action;
        return null;
        }

    private static Set<AssetInstallerAction> ACTIONS = null;

    private final static Logger LOG = LoggerFactory.getLogger(AssetInstallerActions.class);
    }


