package com.shuffleblab.charityquiz;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	@SuppressLint("SdCardPath")
	private static String DB_PATH = "/data/data/com.shuffleblab.charityquiz/databases/";

	private static String DB_NAME = "CharityQuizDb";
	private static final String DATABASE_TABLE = "QuestionsNL";

	private SQLiteDatabase myDataBase;
	private final Context myContext;
	private static final int DATABASE_VERSION = 1;

	// Assign column names to variables
	public static final String KEY_ROWID = "_id";
	public static final String KEY_QUESTION = "question";
	public static final String KEY_ANSWER1 = "a1";
	public static final String KEY_ANSWER2 = "a2";
	public static final String KEY_ANSWER3 = "a3";
	public static final String KEY_ANSWER4 = "a4";
	public static final String KEY_PHOTOCHARITY = "photocharity";
	public static final String KEY_QUESTIONIMAGE = "photoquestion";
	public static final String KEY_DESCRIPTION = "description";

	public static int selectionNumber;
	public static ArrayList<String> result = new ArrayList<String>();
	public static String questionImage;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DatabaseHelper(Context context) {

		super(context, DB_NAME, null, DATABASE_VERSION);
		this.myContext = context;

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		try {
			createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Get random questions and everything else like images from the database.
	public ArrayList<String> getData() {
		// Make sure "result" is empty every time method is run. Else the user
		// will keep getting the same questions.
		result.clear();

		// Make sure application picks a random row by assigning a random value.
		Random r = new Random();
		selectionNumber = r.nextInt(14) + 1;

		String[] columns = new String[] { KEY_ROWID, KEY_QUESTION, KEY_ANSWER1,
				KEY_ANSWER2, KEY_ANSWER3, KEY_ANSWER4, KEY_PHOTOCHARITY,
				KEY_QUESTIONIMAGE, KEY_DESCRIPTION };

		// Assign random number to the 'Where' clause
		String selection = KEY_ROWID + "= '" + selectionNumber + "'";

		Cursor c = myDataBase.query(DATABASE_TABLE, columns, selection, null,
				null, null, null);

		// Assign indexes to columns.
		@SuppressWarnings("unused")
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iQuestion = c.getColumnIndex(KEY_QUESTION);
		int iA1 = c.getColumnIndex(KEY_ANSWER1);
		int iA2 = c.getColumnIndex(KEY_ANSWER2);
		int iA3 = c.getColumnIndex(KEY_ANSWER3);
		int iA4 = c.getColumnIndex(KEY_ANSWER4);
		int iPhotoC = c.getColumnIndex(KEY_PHOTOCHARITY);
		int iPhotoQ = c.getColumnIndex(KEY_QUESTIONIMAGE);
		int iDescription = c.getColumnIndex(KEY_DESCRIPTION);

		// Get data from database and put them in variables.
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result.add(c.getString(iQuestion));
			result.add(c.getString(iPhotoQ));
			result.add(c.getString(iA1));
			result.add(c.getString(iA2));
			result.add(c.getString(iA3));
			result.add(c.getString(iA4));
			result.add(c.getString(iPhotoC));
			result.add(c.getString(iDescription));
		}

		return result;
	}

}