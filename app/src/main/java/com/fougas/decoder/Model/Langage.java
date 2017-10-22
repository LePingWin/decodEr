package com.fougas.decoder.Model;

import java.util.*;

/**
  * Language
  */
public class Langage {

    private static HashMap<String,String> langages= new HashMap<String,String>();

    /**
     * get Enum.
     * @param plangage The language identifier.
     */
     public static String getELanguage(String plangage) {
         /*Adding elements to HashMap*/
         langages.put("English", "en");
         langages.put("French", "fr");
         langages.put("Chinese", "zh");
         if(langages.get(plangage) == null)
             return langages.get("English");
         else
             return langages.get(plangage);

     }

}
