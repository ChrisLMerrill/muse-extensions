package org.musetest.extensions.install;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
public class ExtensionUninstallResult
    {
    void addWarning(String warning)
        {
        _warnings.add(warning);
        }
    void addError(String error)
        {
        _errors.add(error);
        }

    public boolean isSuccess()
        {
        return _warnings.size() == 0 && _errors.size() == 0;
        }

    public List<String> getWarnings()
        {
        return _warnings;
        }

    public List<String> getErrors()
        {
        return _errors;
        }

    private List<String> _warnings = new ArrayList<>();
    private List<String> _errors = new ArrayList<>();
    }


