package gps.map.navigator.common.utils;

import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SerializationUtilsTest {

    @Test
    public void make_serialize_check_invalid_params() {
        SerializationUtils<Demo> utils = new SerializationUtils<>();

        assertNull(utils.serialize(null));
    }

    @Test
    public void make_deserialize_check_invalid_params() {
        SerializationUtils<Demo> utils = new SerializationUtils<>();

        assertNull(utils.deserialize(null));
    }

    @Test
    public void make_serialize_check_not_null() {
        SerializationUtils<Demo> utils = new SerializationUtils<>();

        assertNotNull(utils.serialize(new Demo("foo")));
    }

    @Test
    public void make_serialize_check_same() {
        Demo demo = new Demo("baa");
        SerializationUtils<Demo> utils = new SerializationUtils<>();

        byte[] serialized = utils.serialize(demo);

        Demo result = utils.deserialize(serialized);

        assertEquals("baa", result.getId());
    }

    private static class Demo implements Serializable {
        private String id;

        private Demo(String id) {
            this.id = id;
        }

        private String getId() {
            return id;
        }
    }
}