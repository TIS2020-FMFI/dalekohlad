package fmfi.dalekohlad.Config;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Config {
    private static final Logger lgr = LogManager.getLogger(Config.class);
    private JsonObject json_object;

    public Config(String json) {
        json_object = JsonParser.parseString(json).getAsJsonObject();
    }

    public JsonElement getJsonElement(String element_name) {
        lgr.debug("Initial test");
        return json_object.get(element_name);
    }
}
