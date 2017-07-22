package org.musetest.extensions;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionInfo
    {
    public ExtensionInfo(long ext_id, String ext_name, String ext_author, long ver_id, String ver_name)
        {
        _ext_id = ext_id;
        _ext_name = ext_name;
        _ext_author = ext_author;
        _ver_id = ver_id;
        _ver_name = ver_name;
        }

    private ExtensionInfo() {}

    public long getExtensionId()
        {
        return _ext_id;
        }

    public void setExtensionId(long ext_id)
        {
        _ext_id = ext_id;
        }

    public String getExtensionName()
        {
        return _ext_name;
        }

    public void setExtensionName(String ext_name)
        {
        _ext_name = ext_name;
        }

    public String getExtensionDesc()
        {
        return _ext_desc;
        }

    public void setExtensionDesc(String ext_desc)
        {
        _ext_desc = ext_desc;
        }

    public String getExtensionAuthor()
        {
        return _ext_author;
        }

    public void setExtensionAuthor(String ext_author)
        {
        _ext_author = ext_author;
        }

    public long getVersionId()
        {
        return _ver_id;
        }

    public void setVersionId(long ver_id)
        {
        _ver_id = ver_id;
        }

    public String getVersionName()
        {
        return _ver_name;
        }

    public void setVersionName(String ver_name)
        {
        _ver_name = ver_name;
        }

    public String getVersionDescription()
        {
        return _ver_desc;
        }

    public void setVersionDescription(String ver_desc)
        {
        _ver_desc = ver_desc;
        }

    public Date getVersionDate()
        {
        return _ver_date;
        }

    public void setVersionDate(Date ver_date)
        {
        _ver_date = ver_date;
        }

    public Date getInstallDate()
        {
        return _install_date;
        }

    public void setInstallDate(Date install_date)
        {
        _install_date = install_date;
        }

    public List<ExtensionProjectAsset> getAssets()
        {
        return _assets;
        }

    public void setAssets(List<ExtensionProjectAsset> assets)
        {
        _assets = assets;
        }

    private long _ext_id;
    private String _ext_name;
    private String _ext_desc;
    private String _ext_author;

    private long _ver_id;
    private String _ver_name;
    private String _ver_desc;
    private Date _ver_date;

    private Date _install_date;

    private List<ExtensionProjectAsset> _assets;
    }


