package com.fougas.decoder.Model;

/**
 * Created by Jean on 22/04/2017.
 * Class to build file.json to use API WhatsMate
 */

public class APIWhatsMateBuildJSON {

    /**
     * Build a file.json for the API Whatsmate
     * @param pFromLang Langage to translate
     * @param pToLang Langage of translation
     * @param pText text to translate
     * @return <String> the text translated
     */
    public static String buildJSON(Language pFromLang, Language pToLang, String pText){

         String jsonPayload = new StringBuilder()
                .append("{")
                .append("\"fromLang\":\"")
                .append(pFromLang)
                .append("\",")
                .append("\"toLang\":\"")
                .append(pToLang)
                .append("\",")
                .append("\"text\":\"")
                .append(pText)
                .append("\"")
                .append("}")
                .toString();

        return jsonPayload;
    }

}
