package org.musetest.extensions.webservice;

import org.musetest.extensions.*;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@SuppressWarnings("unused")  // used by web services and UI
public class ExtensionVersionCreateRequest
    {
    private ExtensionVersionCreateRequest() { }  // required for de/serialization

    public ExtensionVersionCreateRequest(long extension_id, String version_name)
        {
        _extension_id = extension_id;
        _version_name = version_name;
        }

    public long getExtensionId()
        {
        return _extension_id;
        }

    public void setExtensionId(long extension_id)
        {
        _extension_id = extension_id;
        }

    public String getVersionName()
        {
        return _version_name;
        }

    public void setVersionName(String version_name)
        {
        _version_name = version_name;
        }

    public List<ExtensionProjectAsset> getAssets()
        {
        return _assets;
        }

    public void setAssets(List<ExtensionProjectAsset> assets)
        {
        _assets = assets;
        }

    public String getDescription()
        {
        return _description;
        }

    public void setDescription(String description)
        {
        _description = description;
        }

    private long _extension_id;
    private String _version_name;
    private String _description;
    private List<ExtensionProjectAsset> _assets;
    }


