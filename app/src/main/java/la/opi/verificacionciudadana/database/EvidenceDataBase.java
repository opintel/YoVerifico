package la.opi.verificacionciudadana.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import la.opi.verificacionciudadana.util.Config;

/**
 * Created by Jhordan on 02/03/15.
 */
public class EvidenceDataBase extends SQLiteOpenHelper {

    String sqlCreateTable = Config.TABLE;

    public EvidenceDataBase(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Config.ON_UPGRADE_DROP);
        db.execSQL(sqlCreateTable);

    }
}
