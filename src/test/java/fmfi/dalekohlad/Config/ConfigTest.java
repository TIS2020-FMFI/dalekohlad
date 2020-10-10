package fmfi.dalekohlad.Config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest {
    private static String example1;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        //Method annotated with `@BeforeClass` will execute once before any of the test methods in this class.
        example1 = "{ \"name\":\"John\", \"age\":30, \"car\":null }";
    }

    @Test
    public void get_type_test() {
        Config cnf = new Config(example1);
        assertTrue(cnf.getJsonElement("car").isJsonNull());
        JsonElement name_je = cnf.getJsonElement("name");
        assertTrue(name_je.isJsonPrimitive());
        JsonPrimitive name_jp = name_je.getAsJsonPrimitive();
        assertTrue(name_jp.isString());
        assertEquals("John", name_jp.getAsString());
    }
}