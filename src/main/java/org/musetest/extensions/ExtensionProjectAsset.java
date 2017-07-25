package org.musetest.extensions;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionProjectAsset
    {
    public String getDefaultPath()
        {
        return _default_path;
        }

    public void setDefaultPath(String default_path)
        {
        _default_path = default_path;
        }

    public String getUrl()
        {
        return _url;
        }

    public void setUrl(String url)
        {
        _url = url;
        }

    
    public byte[] getContent()
        {
        return _content;
        }

    public void setContent(byte[] content)
        {
        _content = content;
        }

    private String _default_path;
    private String _url;
    private byte[] _content;
    }


