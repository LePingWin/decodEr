package com.fougas.decoder.Model;


import android.content.Context;
import android.content.res.Resources;

import com.fougas.decoder.R;

import java.io.*;


/**
 * Created by Jean on 22/04/2017.
 * Read a file.txt
 */

public class ReadTxtFile {

    /**
     * Return a string of the content of the file to read
     * @param res Resources to find the path file
     * @return <String> which content text in file.txt
     */
    public static String Read(Resources res ) {
        String myData = "";
        InputStream iS;
        try {
            iS = res.getAssets().open("fileTranslate.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(iS));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            myData = out.toString();

            iS.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return myData;

    }
}

