package com.fougas.decoder.Model;

/**
 * Created by Jean on 22/04/2017.
 */

/**
 * Language - an enum of language codes supported by the APIs Yandex and WhatsMate
 */
public enum Language {

    ENGLISH("en"),
    FRENCH("fr"),
    RUSSIAN("ru");

    /**
     * String representation of this language.
     */
    private final String language;

    /**
     * Enum constructor.
     * @param pLanguage The language identifier.
     */
    private Language(final String pLanguage) {
        language = pLanguage;
    }

    /*
    public static Language fromString(final String pLanguage) {
        for (Language l : values()) {
            if (l.toString().equals(pLanguage)) {
                return l;
            }
        }
        return null;
    }
*/
    /**
     * Returns the String representation of this language.
     * @return <String> representation of this language.
     */
    @Override
    public String toString() {
        return language;
    }

}


