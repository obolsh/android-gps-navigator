package gps.map.navigator.common.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import gps.map.navigator.common.Constants;

public class DatabaseStorage extends SQLiteOpenHelper implements Storage {

    private static final String TABLE_CHUNKED = "table_chunked";
    private static final String TABLE_CACHE = "table_cache";
    private static final String COLUMN_CACHE_KEY = "cache_key";
    private static final String COLUMN_CACHE_VALUE = "cache_value";
    private static final String COLUMN_CHUNKED_VALUE = "chunked_value";
    private Integer databaseVersion;


    private static final String SQL_CREATE_CACHE_TABLE = "create table " + TABLE_CACHE +
            " (_id integer primary key autoincrement," +
            " " + COLUMN_CACHE_KEY + " varchar(255), " + COLUMN_CACHE_VALUE + " blob);";
    private static final String SQL_CREATE_CHUNKED_TABLE = "create table " + TABLE_CHUNKED +
            " (_id integer primary key autoincrement," +
            " " + COLUMN_CHUNKED_VALUE + " blob);";

    @Inject
    public DatabaseStorage(@Named(Constants.ApplicationContext) Context context,
                           @Named(Constants.DatabaseInfo) String databaseName,
                           @Named(Constants.DatabaseInfo) Integer databaseVersion) {
        super(context, databaseName, null, databaseVersion);
        this.databaseVersion = databaseVersion;
    }

    @Override
    public boolean hasData(String key, boolean defaultValue) {
        Cursor cursor = null;
        try {
            cursor = getCacheCursor(key);
            boolean result = cursor != null;
            result = result && cursor.moveToFirst() && cursor.getCount() > 0;
            return result;
        } catch (Throwable t) {
            return defaultValue;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private Cursor getCacheCursor(String key) {
        return makeQuery(TABLE_CACHE, new String[]{COLUMN_CACHE_KEY, COLUMN_CACHE_VALUE},
                String.format(Locale.US, "%s='%s'", COLUMN_CACHE_KEY, key));
    }

    private Cursor makeQuery(String table, String[] columns, String selection) {
        return getReadableDatabase().query(table, columns, selection, null, null, null, null);
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
        Cursor cursor = null;
        try {
            cursor = getCacheCursor(key);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getBlob(cursor.getColumnIndex(COLUMN_CACHE_VALUE));
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return defaultValue;

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
        List<byte[]> data = new ArrayList<>();
        try {
            Cursor cursor;
            cursor = makeQuery(TABLE_CHUNKED, new String[]{COLUMN_CHUNKED_VALUE}, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        while (cursor.moveToNext()) {
                            data.add(cursor.getBlob(cursor.getColumnIndex(COLUMN_CHUNKED_VALUE)));
                        }
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
        if (databaseVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHUNKED);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CACHE);
            onCreate(db);
        }
    }
}
