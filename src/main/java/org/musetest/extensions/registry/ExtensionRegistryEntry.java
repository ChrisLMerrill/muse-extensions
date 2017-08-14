package org.musetest.extensions.registry;

import org.musetest.extensions.*;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionRegistryEntry
    {
    public ExtensionInfo getInfo()
        {
        return _info;
        }

    public void setInfo(ExtensionInfo info)
        {
        _info = info;
        }

    @Override
    public boolean equals(Object obj)
        {
        if (!(obj instanceof ExtensionRegistryEntry))
            return false;
        ExtensionRegistryEntry other = (ExtensionRegistryEntry) obj;
        return Objects.equals(_info, other._info);
        }

    private ExtensionInfo _info;
    }


