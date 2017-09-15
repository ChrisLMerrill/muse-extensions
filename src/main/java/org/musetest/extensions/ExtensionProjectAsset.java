package org.musetest.extensions;

import java.util.*;

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

    @SuppressWarnings("unused")  // needed for JSON deserialization
    public String getStringContent()
        {
        return new String(_content);
        }

    @SuppressWarnings("unused")  // needed for JSON deserialization
    public void setStringContent(String content)
        {
        _content = content.getBytes();
        }

    public List<AssetInstallInstruction> getInstallInstructions()
        {
        return _instructions;
        }

    public void setInstallInstructions(List<AssetInstallInstruction> instructions)
        {
        _instructions = instructions;
        }

    @Override
    public boolean equals(Object obj)
        {
        if (obj instanceof ExtensionProjectAsset)
            {
            ExtensionProjectAsset other = (ExtensionProjectAsset) obj;
            return Objects.equals(_default_path, other._default_path)
                && Objects.equals(_url, other._url)
                && Arrays.equals(_content, other._content);

            }
        return false;
        }

    private String _default_path;
    private String _url;
    private byte[] _content;
    private List<AssetInstallInstruction> _instructions;
    }


