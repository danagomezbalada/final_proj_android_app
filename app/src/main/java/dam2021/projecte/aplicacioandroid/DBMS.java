package dam2021.projecte.aplicacioandroid;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Singleton class which provides methods for interacting with the database.
 * Constructs a database from a script contained in the assets folder.
 * These methods are mostly high-level abstractions of the queries which are used
 * throughout the application.
 *
 * @author João Xavier <joao.xavier@novabase.pt>
 *
 */
public class DBMS extends SQLiteOpenHelper {

    private static final String TAG =           "DBMS";

    private static final String DATABASE_FILE = "script_mobil.sql";
    private static final String DATABASE_NAME = "agenda_activitats";
    private static final int DATABASE_VERSION = 1;
    private static final int BUFFER_SIZE =      2048;

    private static SQLiteDatabase db;
    private static DBMS instance;
    private static Context context;

    /**
     * Constructor instantiating every member.
     *
     * @param context the context to set.
     * @param name the name to set.
     * @param factory the factory to set.
     * @param version the version to set.
     */
    private DBMS(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        DBMS.context = context;
    }

    /**
     * Creates the database from a script within a transaction.
     * Rollbacks the transaction if an error occurs.
     *
     * @param db the {@link SQLiteDatabase} object.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();

        try {
            readDatabaseScript(db);
            db.setTransactionSuccessful();
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage());
        } catch (SQLException sqle) {
            Log.e(TAG, sqle.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Reads the database script from a path determined internally and executes every SQL
     * statement in the script.
     *
     * @param db the {@link SQLiteDatabase} object.
     * @throws IOException if the database script wasn't found.
     * @throws SQLException in case a SQL statement was malformed.
     */
    private void readDatabaseScript(SQLiteDatabase db) throws IOException, SQLException {
        InputStream script = context.getAssets().open(DATABASE_FILE);
        byte[] buffer = new byte[BUFFER_SIZE];

        for (int byteRead = script.read(); byteRead != -1; byteRead = script.read()) {
            // resets the buffer
            Arrays.fill(buffer, (byte) 0);

            // reads a SQL statement to the buffer
            for (int i = 0; byteRead != -1 && i != BUFFER_SIZE; byteRead = script.read(), i++) {
                buffer[i] = (byte) byteRead;

                if (byteRead == ';')
                    break;
            }

            // execute the SQL statement from the buffer
            if (byteRead != -1)
                db.execSQL(new String(buffer));
        }
    }

    /**
     * Runs the onCreate method, since the database script will drop the tables upon beginning.
     *
     * @param db the {@link SQLiteDatabase} object.
     * @param oldVersion the old version id.
     * @param newVersion the new version id.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /*
     * (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#close()
     */
    @Override
    public synchronized void close() {
        if (instance != null)
            db.close();
    }

    /**
     * Retrieves a thread-safe instance of the singleton object {@link DBMS} and opens the database
     * with writing permissions.
     *
     * @param context the context to set.
     * @return the singleton instance.
     */
    public static synchronized DBMS getInstance(Context context) {
        if (instance == null) {
            instance = new DBMS(context, DATABASE_NAME, null, DATABASE_VERSION);
            db = instance.getWritableDatabase();
        }

        return instance;
    }

    /**
     * Counts the rows of a given table, using a method from {@link DatabaseUtils}.
     *
     * @param table the table from where to count the rows.
     * @return the number of entries of the given table.
     */
    public int getNumberRows(String table) {
        return (int) DatabaseUtils.queryNumEntries(db, table);
    }
}
