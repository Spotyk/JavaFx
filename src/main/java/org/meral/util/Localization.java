package org.meral.util;

import java.util.Collections;
import java.util.ResourceBundle;

public class Localization {

    private ResourceBundle bundle;

    public Localization(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getStringByKey(String key) {
        return bundle.getString(key);
    }

    public String getCurrentLang() {
        return bundle.getLocale().toLanguageTag();
    }

    public String getKeyByString(String value) {
        String lang = null;
        for (String element : Collections.list(bundle.getKeys())) {
            if (bundle.getString(element).equals(value)) {
                lang = element;
            }

        }
        return lang;
    }
}
