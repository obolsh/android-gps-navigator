package gps.map.navigator.common.cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(RobolectricTestRunner.class)
@Config(shadows = {ShadowSQLiteOpenHelper.class}, manifest = Config.NONE)
public class DatabaseStorageTest {

    private DatabaseStorage storage;

    @Before
    public void setUp() throws Exception {
        storage = new DatabaseStorage(RuntimeEnvironment.application, "db_name", 1);
        storage.getWritableDatabase();
    }

    @After
    public void tearDown() throws Exception {
        storage.invalidate();
    }

    @Test
    public void make_getData_check_default_returned() {
        byte[] result = storage.getData("goo", "fifa".getBytes());
        boolean has_data = storage.hasData("goo", true);

        assertFalse(has_data);
        assertEquals("fifa", new String(result));
    }

    @Test
    public void make_saveData_check_saved() {
        boolean result = storage.saveData("foo", "baa".getBytes());

        assertTrue(result);
    }

    @Test
    public void make_saveData_check_match() {
        boolean save_result = storage.saveData("faa", "boo".getBytes());

        byte[] result = storage.getData("faa", new byte[0]);

        assertTrue(save_result);
        assertEquals("boo", new String(result));
    }

    @Test
    public void make_saveData_check_hasData() {
        boolean save_result = storage.saveData("fii", "buu".getBytes());

        boolean has_data = storage.hasData("fii", false);

        assertTrue(save_result && has_data);
    }

    @Test
    public void make_saveChunkedData_check_match() {
        byte[] first = "first".getBytes();
        byte[] secont = "second".getBytes();
        List<byte[]> list = new ArrayList<>();
        list.add(first);
        list.add(secont);

        boolean save_result = storage.saveChunkedData(list);

        List<byte[]> result = storage.getChunkedData();

        assertTrue(save_result);
        assertEquals("first", new String(result.get(0)));
        assertEquals("second", new String(result.get(1)));
    }
}