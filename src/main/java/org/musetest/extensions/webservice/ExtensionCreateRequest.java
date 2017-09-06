package org.musetest.extensions.webservice;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@SuppressWarnings("unused")  // used by web services and UI
public class ExtensionCreateRequest
    {
    public ExtensionCreateRequest(String name, String author_email, String auth_token)
        {
        _name = name;
        _author_email = author_email;
        _auth_token = auth_token;
        }

    @SuppressWarnings("unused") // needed for de/serialization
    private ExtensionCreateRequest() {}

    public String getName()
        {
        return _name;
        }

    public void setName(String name)
        {
        _name = name;
        }

    public String getAuthorEmail()
        {
        return _author_email;
        }

    public void setAuthorEmail(String author_email)
        {
        _author_email = author_email;
        }

    public String getAuthenticationToken()
        {
        return _auth_token;
        }

    public void setAuthenticationToken(String auth_token)
        {
        _auth_token = auth_token;
        }

    public String getDescription()
        {
        return _description;
        }

    public void setDescription(String description)
        {
        _description = description;
        }

    private String _name;
    private String _author_email;
    private String _auth_token;
    private String _description;
    }


