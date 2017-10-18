package com.fougas.decoder.Service;

import android.content.Context;
import android.content.res.Resources;

import java.io.*;


    /**
     * Created by Jean on 22/04/2017.
     * Read a file.txt
     */

    public class ServiceTxtFile {

        /**
         * Return a string of the content of the file to read
         * @param res Resources to find the path file
         * @param nameFile String contents the name of file which will be read
         * @return <String> which content text in file.txt
         */
        public static String read(Resources res, String nameFile ) {
            String myData = "";
            InputStream iS;
            try {
                iS = res.getAssets().open(nameFile);
                if (iS != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(iS));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                    }
                    myData = out.toString();
                }

                iS.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return myData;

        }

        /**
         * Write the content of the file. This file is in the internal storage
         * @param fileContext Context to create the  file
         * @param nameFile String contents the name of file which will be write
         * @param content String content of the file
         * @return <String> which content text in file.txt
         */
        public static void write(Context fileContext, String nameFile, String content ) {
            String fileNameStr="MyFileName";
            String fileContentStr=content;
            try {

                // To open you can choose the mode MODE_PRIVATE, MODE_APPEND,
                // MODE_WORLD_READABLE, MODE_WORLD_WRITEABLE
                // This is the creation mode (Private, World Readable et World Writable),
                // Append is used to open the file and write at its end
                FileOutputStream fos= fileContext.openFileOutput(fileNameStr, Context.MODE_PRIVATE);
                // Open the writer
                OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fos);
                // Write
                outputStreamWriter.write(fileContentStr);
                // Close streams
                outputStreamWriter.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }