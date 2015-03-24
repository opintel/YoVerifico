package la.opi.verificacionciudadana.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;

import la.opi.verificacionciudadana.util.Config;

/**
 * Created by Jhordan on 02/03/15.
 */
public class ActionsDataBase {


    private static ArrayList<String> titleEvidence;
    private static ArrayList<String> photoEvidence;

    public static ArrayList<String> getPhotoEvidence() {
        return photoEvidence;
    }

    public static void setPhotoEvidence(ArrayList<String> photoEvidence) {
        ActionsDataBase.photoEvidence = photoEvidence;
    }

    public static ArrayList<String> getTitleEvidence() {
        return titleEvidence;
    }

    public static void setTitleEvidence(ArrayList<String> titleEvidence) {
        ActionsDataBase.titleEvidence = titleEvidence;
    }

    public static void insertDataBase(Context context, String evidenceTitle, String evidencePicture) {
        SQLiteDatabase db;
        EvidenceDataBase evidenciasDB = new EvidenceDataBase(context, Config.DATA_BASE_NAME, null, 1);
        db = evidenciasDB.getWritableDatabase();
        ContentValues verificacion = new ContentValues();
        verificacion.put(Config.FIELD_EVIDENCE_NAME, evidenceTitle);
        verificacion.put(Config.FIELD_EVIDENCE_PICTURE, evidencePicture);
        db.insert(Config.TABLE_NAME, null, verificacion);

    }


    public static void queryDataBase(Context context) {

        SQLiteDatabase db;
        ArrayList<String> titlePath = new ArrayList<>();
        ArrayList<String> photosPath = new ArrayList<>();

        EvidenceDataBase evidenciasDB = new EvidenceDataBase(context, Config.DATA_BASE_NAME, null, 1);
        db = evidenciasDB.getWritableDatabase();


        try {

            if (db != null) {
                String query = Config.QUERY_PATH;
                Cursor c = db.rawQuery(query, null);


                if (c.moveToFirst()) {
                    do {

                        titlePath.add(c.getString(0));
                        photosPath.add(c.getString(1));


                    } while (c.moveToNext());


                }
                db.close();
                c.close();
            }
            setTitleEvidence(titlePath);
            setPhotoEvidence(photosPath);

        } catch (Exception e) {

        } finally {

            db.close();
        }


    }

}



