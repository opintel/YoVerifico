package la.opi.verificacionciudadana.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Jhordan on 11/02/15.
 */
public class StorageFiles {

    final static private String mobilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    final static private String directoryPath = mobilePath + Config.DIRECTORY_EVIDENCE;

    public static String getDirectoryPath() {
        return directoryPath;
    }

    public static void createDirectory(Context context) {

        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        } catch (Exception e) {

        }


    }

    private static void writeOpiSomething(String path) {

        try {

            File myFile = new File(path, Config.TXT_YO_VERIFICO);

            if (!myFile.exists()) {

                OutputStreamWriter opiWriteTxt = new OutputStreamWriter(new FileOutputStream(myFile));
                opiWriteTxt.write(Config.CONDITIONS_AND_TERMS);
                opiWriteTxt.close();

            }


        } catch (Exception e) {


        }

    }

    public static void deleteFilesFromDirectory() {
        File[] listFile;


        try {
            File directory = new File(directoryPath);
            if (directory.isDirectory()) {
                listFile = directory.listFiles();


                for (int i = 0; i < listFile.length; i++) {


                    if (listFile[i].exists()) {
                        listFile[i].delete();
                        System.out.println("se ha borrado" + listFile[i]);
                    }


                }


            }


        } catch (Exception e) {


        }
    }


    public static Boolean empityDirectory() {

        try {
            File directory = new File(directoryPath);
            if (directory.exists()) {
                File[] filesList = directory.listFiles();
                if (filesList == null) {
                    return true;
                }
            }
        } catch (Exception e) {

        }
        return false;

    }


}
