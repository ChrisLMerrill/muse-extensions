package org.museautomation.extensions.api;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class VersionInstallUrlUpdate
    {
    public String getAuthenticationToken()
        {
        return _auth_token;
        }

    public void setAuthenticationToken(String auth_token)
        {
        _auth_token = auth_token;
        }

    public String getUrl()
        {
        return _url;
        }

    public void setUrl(String url)
        {
        _url = url;
        }

    private String _auth_token;
    private String _url;
    }


