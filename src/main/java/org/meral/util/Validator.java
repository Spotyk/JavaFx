package org.meral.util;

public class Validator {
    public boolean isValidString(String string) {
        return string != null && !string.isEmpty() && string.length() > 3 && string.length() < 50;
    }
}
