package Utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

public class DataUtils {
    private static final String TEST_DATA_PATH = "src/test/resources/TestData/";

    public static String getJsonData(String fileName, String key) {
        try {
            FileReader reader = new FileReader(TEST_DATA_PATH + fileName + ".json");
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(key).getAsString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getPropertyValue(String fileName, String key) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(TEST_DATA_PATH + fileName + ".properties"));
            return properties.getProperty(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static JsonObject getUserByType(String fileName, String userType) {
        try {
            FileReader reader = new FileReader(TEST_DATA_PATH + fileName + ".json");
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonArray usersArray = jsonElement.getAsJsonObject().getAsJsonArray("users");
            for (JsonElement userElement : usersArray) {
                JsonObject userObject = userElement.getAsJsonObject();
                if (userObject.get("type").getAsString().equalsIgnoreCase(userType)) {
                    return userObject;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}