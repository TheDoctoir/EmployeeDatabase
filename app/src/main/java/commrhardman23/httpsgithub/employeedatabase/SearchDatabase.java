package commrhardman23.httpsgithub.employeedatabase;

import android.app.DownloadManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchDatabase extends AppCompatActivity {

    EditText edtxtName;
    EditText edtxtPosition;
    EditText edtxtEmployeeNum;
    EditText edtxtWage;
    TextView txtvwResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_database);

        edtxtName = (EditText) findViewById(R.id.edtxtName);
        edtxtPosition = (EditText) findViewById(R.id.edtxtPosition);
        edtxtEmployeeNum = (EditText) findViewById(R.id.edtxtEmployeeNum);
        edtxtWage = (EditText) findViewById(R.id.edtxtWage);
        txtvwResult = (TextView) findViewById(R.id.txtvwResult);

    }

    /**
     * searchDatabase gets all the information the user wants to search for, searches the database
     * for it, and displays the result in a TextView.
     * @param vw is the button this method is associated with
     */
    public void searchDatabase(View vw) {

        int numOfArguments = 0;
        int indexOfSearchArray = 0;
        String name = "";
        String position = "";
        String employeeNum = "";
        String wage = "";
        String whereToSearch = "";
        String[] elementsToSearch;
        EmployeeDatabaseHelper employeeDatabaseHelper = new EmployeeDatabaseHelper(this, null, null, 0);
        SQLiteDatabase db;
        Cursor searchCursor;


        if (edtxtName.getText().length() != 0) {

            numOfArguments++;
            name = edtxtName.getText().toString();

            whereToSearch += "NAME = ?";
        }

        if (edtxtPosition.getText().length() != 0) {

            numOfArguments++;
            position = edtxtPosition.getText().toString();

            if (whereToSearch.equals("")) {
                whereToSearch += "POSITION = ?";
            } else {
                whereToSearch += " OR POSITION = ?";
            }


        }

        if (edtxtEmployeeNum.getText().length() != 0) {

            numOfArguments++;
            employeeNum = edtxtEmployeeNum.getText().toString();

            if (whereToSearch.equals("")) {
                whereToSearch += "EMPLOYEE_NUM = ?";
            } else {
                whereToSearch += " OR EMPLOYEE_NUM = ?";
            }


        }

        if (edtxtWage.getText().length() != 0) {

            numOfArguments++;
            wage = edtxtWage.getText().toString();

            if (whereToSearch.equals("")) {
                whereToSearch += "WAGE = ?";
            } else {
                whereToSearch += " OR WAGE = ?";
            }


        }

        if (numOfArguments == 0) {

            elementsToSearch = null;
            whereToSearch = null;

        } else {

            elementsToSearch = new String[numOfArguments];

            if (edtxtName.getText().length() != 0) {

                elementsToSearch[indexOfSearchArray] = name;
                indexOfSearchArray++;

            }

            if (edtxtPosition.getText().length() != 0) {

                elementsToSearch[indexOfSearchArray] = position;
                indexOfSearchArray++;

            }

            if (edtxtEmployeeNum.getText().length() != 0) {

                elementsToSearch[indexOfSearchArray] = employeeNum;
                indexOfSearchArray++;

            }

            if (edtxtWage.getText().length() != 0) {

                elementsToSearch[indexOfSearchArray] = wage;
                indexOfSearchArray++;

            }

        }


          /*1. Get a Readable reference to the database using the db variable (Remember your
             try-catch block. The if-else that follows should also go in your try block).
         2. Use searchCursor to query the database with the predetermined search values and
             return all columns of the database for the searched values.  Use the whereToSearch and
             elementsToSearch variables for the String where and String[] whereArgs parameters,
             respectively. The query should go inside the try-catch block before the if statement
             that follows.
             */

        try {

            employeeDatabaseHelper = new EmployeeDatabaseHelper(this, null, null, 0);
            db = employeeDatabaseHelper.getReadableDatabase();
            searchCursor = db.query("EMPLOYEE", new String[]{"NAME, POSITION, WAGE, EMPLOYEE_NUM"}, whereToSearch, elementsToSearch, null, null, null);

            if (searchCursor.getCount() == 0) {

                txtvwResult.setText("There are no entries with this info...");

            } else {

                if (searchCursor.moveToFirst()) {

                    for (int i = 0; i < searchCursor.getCount(); i++) {

                        txtvwResult.setText(txtvwResult.getText().toString() +
                                String.format("Name: %-35s Position: %s\nEmployee Number: %-16d" +
                                                " Wage: %.2f\n", searchCursor.getString(0),
                                        searchCursor.getString(1), searchCursor.getInt(2),
                                        searchCursor.getDouble(3)));

                        //We need to move to the next row... How do we do this?
                        searchCursor.moveToNext();

                    }

                }
            }

            searchCursor.close();

        } catch (SQLiteException e) {

            txtvwResult.setText("No readable entries.");
        }
    }

    /**
     * deleteEntry gets all the deletion criteria from the user and deletes all entries in the
     * database that contain the information provided
     * @param vw is the button the method is associated with
     */

    public void deleteEntry(View vw){

        int numOfArguments = 0;
        int indexOfSearchArray = 0;
        String name = "";
        String position = "";
        String employeeNum = "";
        String wage = "";
        String whereToDelete = "";
        String[] elementsToDelete;
        EmployeeDatabaseHelper employeeDatabaseHelper = new EmployeeDatabaseHelper(this, null, null, 0);
        SQLiteDatabase db;
        int numRowsDeleted;

        if(edtxtName.getText().length() != 0){

            numOfArguments++;
            name = edtxtName.getText().toString();
            whereToDelete += "NAME = ?";

        }

        if(edtxtPosition.getText().length() != 0){

            numOfArguments++;
            position = edtxtPosition.getText().toString();

            if(whereToDelete.equals("")) {
                whereToDelete += "POSITION = ?";
            } else {
                whereToDelete += " AND POSITION = ?";
            }

        }

        if(edtxtEmployeeNum.getText().length() != 0){

            numOfArguments++;
            employeeNum = edtxtEmployeeNum.getText().toString();

            if(whereToDelete.equals("")) {
                whereToDelete += "EMPLOYEE_NUM = ?";
            } else {
                whereToDelete += " AND EMPLOYEE_NUM = ?";
            }

        }

        if(edtxtWage.getText().length() != 0){

            numOfArguments++;
            wage = edtxtWage.getText().toString();

            if(whereToDelete.equals("")) {
                whereToDelete += "WAGE = ?";
            } else {
                whereToDelete += " AND WAGE = ?";
            }

        }

        if(numOfArguments == 0){

            elementsToDelete = null;
            whereToDelete = null;

        } else {

            elementsToDelete = new String[numOfArguments];

            if (edtxtName.getText().length() != 0) {

                elementsToDelete[indexOfSearchArray] = name;
                indexOfSearchArray++;

            }

            if (edtxtPosition.getText().length() != 0) {

                elementsToDelete[indexOfSearchArray] = position;
                indexOfSearchArray++;

            }

            if (edtxtEmployeeNum.getText().length() != 0) {

                elementsToDelete[indexOfSearchArray] = employeeNum;
                indexOfSearchArray++;

            }

            if (edtxtWage.getText().length() != 0) {

                elementsToDelete[indexOfSearchArray] = wage;
                indexOfSearchArray++;

            }

        }

        /**
         * 1. Get a Writable reference to the database using the db variable (Remember the
         *    try-catch block. The next two steps should go in your try block)
         * 2. Use the EmployeeDatabase's deleteElement method to delete entries and set the method
         *    call to equal numRowsDeleted. Use the whereToDelete and elementsToDelete variables for
         *    the String where and String[] whereArgs parameters, respectively
         * 3. Display the number of rows deleted
         */

        try {
            db = employeeDatabaseHelper.getWritableDatabase();

            numRowsDeleted = employeeDatabaseHelper.deleteElement(db, whereToDelete, elementsToDelete);
            txtvwResult.setText("Number of rows deleted is..." + Integer.toString(numRowsDeleted));

            db.close();

        } catch (SQLiteException e){
            txtvwResult.setText("The database cannot be found.");
        }

    }


}