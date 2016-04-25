package commrhardman23.httpsgithub.employeedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by scott-hardman on 16-04-14.
 */
public class EmployeeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "employee";
    private static final int DB_VERSION = 1;

    public EmployeeDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("CREATE TABLE EMPLOYEE (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "POSITION TEXT, " +
                "EMPLOYEE_NUM INTEGER, " +
                "WAGE REAL);");
    }

    public void insertElement(SQLiteDatabase db, ContentValues newContent){

        db.insert("EMPLOYEE", null, newContent);

    }

    public int alterElement(SQLiteDatabase db, ContentValues alteredContent, String where,
                            String[] whereArgs){

        return db.update("EMPLOYEE", alteredContent, where, whereArgs);

    }

    public int deleteElement(SQLiteDatabase db, String where, String[] whereArgs){

        return db.delete("EMPLOYEE", where, whereArgs);

    }
}
