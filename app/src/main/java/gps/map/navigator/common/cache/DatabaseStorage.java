package gps.map.navigator.common.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseStorage extends SQLiteOpenHelper implements IStorage {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database_storage.db";
    private static final String TABLE_CHUNKED = "table_chunked";
    private static final String TABLE_CACHE = "table_cache";
    private static final String COLUMN_CACHE_KEY = "cache_key";
    private static final String COLUMN_CACHE_VALUE = "cache_value";
    private static final String COLUMN_CHUNKED_VALUE = "chunked_value";


    private static final String SQL_CREATE_CACHE_TABLE = "create table " + TABLE_CACHE +
        " (_id integer primary key autoincrement," +
        " " + COLUMN_CACHE_KEY + " varchar(255), " + COLUMN_CACHE_VALUE + " blob);";
    private static final String SQL_CREATE_CHUNKED_TABLE = "create table " + TABLE_CHUNKED +
        " (_id integer primary key autoincrement," +
        " " + COLUMN_CHUNKED_VALUE + " blob);";

    public DatabaseStorage(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public boolean hasData(String key, boolean defaultValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_CACHE, new String[]{COLUMN_CACHE_KEY, COLUMN_CACHE_VALUE},
                String.format(Locale.US, "%s='%s'", COLUMN_CACHE_KEY, key), null, null, null, null);
            boolean result = cursor != null;
            result = result && cursor.moveToFirst() && cursor.getCount() > 0;
            return result;
        } catch (Throwable t) {
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public boolean saveData(String key, byte[] data) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CACHE_KEY, key);
            cv.put(COLUMN_CACHE_VALUE, data);
            getWritableDatabase().insert(TABLE_CACHE, null, cv);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public byte[] getData(String key, byte[] defaultValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CACHE, new String[]{COLUMN_CACHE_KEY, COLUMN_CACHE_VALUE},
            String.format(Locale.US, "%s='%s'", COLUMN_CACHE_KEY, key), null, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.getCount() <= 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getBlob(cursor.getColumnIndex(COLUMN_CACHE_VALUE));
        } catch (Exception e) {
            return null;
        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean saveChunkedData(List<byte[]> data) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv;
            for (int i = 0; i < data.size(); i++) {
                cv = new ContentValues();
                cv.put(COLUMN_CHUNKED_VALUE, data.get(i));
                db.insert(TABLE_CHUNKED, null, cv);
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public List<byte[]> getChunkedData() {
        SQLiteDatabase db = getReadableDatabase();
        List<byte[]> data = new ArrayList<>();
        try {
            Cursor cursor = db.query(TABLE_CHUNKED, new String[]{COLUMN_CHUNKED_VALUE},
                null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    cursor.moveToFirst();
                    while (cursor.moveToNext()) {
                        data.add(cursor.getBlob(cursor.getColumnIndex(COLUMN_CHUNKED_VALUE)));
                    }
                } finally {
                    cursor.close();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return data;
    }

    @Override
    public void invalidate() {
        close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CACHE_TABLE);
        db.execSQL(SQL_CREATE_CHUNKED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DB_VERSION != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHUNKED);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CACHE);
            onCreate(db);
        }
    }
}
