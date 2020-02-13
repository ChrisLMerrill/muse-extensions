package org.museautomation.extensions.api;

import com.fasterxml.jackson.annotation.*;

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

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    public String getAuthenticationToken()
        {
        return _auth_token;
        }

    public void setAuthenticationToken(String auth_token)
        {
        _auth_token = auth_token;
        }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getInstallTaskUrl()
        {
        return _install_task_url;
        }

    public void setInstallTaskUrl(String install_task_url)
        {
        _install_task_url = install_task_url;
        }

    private long _extension_id;
    private String _version_name;
    private String _description;
    private String _auth_token;
    private String _install_task_url;
    private List<ExtensionProjectAsset> _assets;

    @Override
    public boolean equals(Object obj)
        {
        if (!(obj instanceof ExtensionVersionCreateRequest))
            return false;
        ExtensionVersionCreateRequest other = (ExtensionVersionCreateRequest) obj;

        return _extension_id == other._extension_id
            && Objects.equals(_version_name, other._version_name)
            && Objects.equals(_description, other._description)
            && Objects.equals(_assets, other._assets);
        }
    }


