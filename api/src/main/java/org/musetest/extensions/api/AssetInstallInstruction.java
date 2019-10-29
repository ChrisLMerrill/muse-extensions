package org.musetest.extensions.api;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class AssetInstallInstruction
    {
    @SuppressWarnings("unused") // needed for JSON deserialization
    public AssetInstallInstruction()
        {
        }

    public AssetInstallInstruction(String action_name)
        {
        _action_name = action_name;
        }

    public String getActionName()
        {
        return _action_name;
        }

    @SuppressWarnings("unused")  // needed for JSON deserialization
    public void setActionName(String action_name)
        {
        _action_name = action_name;
        }

    public Map<String, String> getParameters()
        {
        return _parameters;
        }

    @SuppressWarnings("unused")  // needed for JSON deserialization
    public void setParameters(Map<String, String> parameters)
        {
        _parameters = parameters;
        }

    public String renderDescription()
        {
        return _action_name;
        }

    private String _action_name;
    private Map<String, String> _parameters;
    }


