package com.shuffleblab.charityquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_SCORE = "score";
	public static final String KEY_GOED = "goed";
	public static final String KEY_FOUT = "fout";

	static final String DATABASE_NAME = "CharityQuizDb";
	static final String DATABASE_TABLE = "HighScore";
	static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	SQLiteDatabase ourDatabase;
	String[] columns = new String[] { KEY_ROWID, KEY_SCORE, KEY_GOED, KEY_FOUT };

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SCORE
					+ " INTEGER NOT NULL, " + KEY_GOED + " INTEGER NOT NULL, "
					+ KEY_FOUT + " INTEGER NOT NULL);");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public Database(Context c) {
		ourContext = c;
	}

	public Database open() {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(int score, int goed, int fout) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_SCORE, score);
		cv.put(KEY_GOED, goed);
		cv.put(KEY_FOUT, fout);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public int getScore(Cursor c2) {
		int iScore = c2.getColumnIndex(KEY_SCORE);
		int score = c2.getInt(iScore);
		return score;
	}

	public String getGoed(Cursor c) {
		int iGoed = c.getColumnIndex(KEY_GOED);
		String Goed = Integer.toString(c.getInt(iGoed));
		return Goed;
	}

	public String getFout(Cursor c3) {
		int iFout = c3.getColumnIndex(KEY_FOUT);
		String Fout = Integer.toString(c3.getInt(iFout));
		return Fout;
	}
}
