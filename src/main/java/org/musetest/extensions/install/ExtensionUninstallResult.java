package org.musetest.extensions.install;

import java.util.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
class ExtensionUninstallResult
    {
    void addWarning(String warning)
        {
        _warnings.add(warning);
        }

    void addError(String error)
        {
        _errors.add(error);
        }

    boolean isSuccess()
        {
        return _warnings.size() == 0 && _errors.size() == 0;
        }

    private List<String> _warnings = new ArrayList<>();
    private List<String> _errors = new ArrayList<>();
    }


