package org.musetest.extensions;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class AssetInstallInstruction
    {
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

    public void setActionName(String action_name)
        {
        _action_name = action_name;
        }

    public Map<String, String> getParameters()
        {
        return _parameters;
        }

    public void setParameters(Map<String, String> parameters)
        {
        _parameters = parameters;
        }

    private String _action_name;
    private Map<String, String> _parameters;
    }


