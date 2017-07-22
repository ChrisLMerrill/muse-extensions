package org.musetest.extensions.webservice;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
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

    private long _extension_id;
    private String _version_name;
    }


