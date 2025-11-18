package Utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Optional;
import java.util.Properties;

public class DataUtils {

    private static final Logger log = LoggerFactory.getLogger(DataUtils.class);
    private static final String TEST_DATA_PATH = "src/test/resources/TestData/";

    private DataUtils() {
    }

    public static Optional<String> getJsonData(String fileName, String key) {
        try (FileReader reader = new FileReader(TEST_DATA_PATH + fileName + ".json")) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonElement value = jsonElement.getAsJsonObject().get(key);
            if (value != null) {
                return Optional.of(value.getAsString());
            } else {
                log.warn("Key '{}' not found in {}.json", key, fileName);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Failed to read JSON '{}.json'. Exception: {}", fileName, e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<String> getPropertyValue(String fileName, String key) {
        try (FileInputStream file = new FileInputStream(TEST_DATA_PATH + fileName + ".properties")) {
            Properties properties = new Properties();
            properties.load(file);
            String value = properties.getProperty(key);
            if (value != null) {
                return Optional.of(value);
            } else {
                log.warn("Property '{}' not found in {}.properties", key, fileName);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Failed to read properties '{}.properties'. Exception: {}", fileName, e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<JsonObject> getUserByType(String fileName, String userType) {
        try (FileReader reader = new FileReader(TEST_DATA_PATH + fileName + ".json")) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonArray usersArray = jsonElement.getAsJsonObject().getAsJsonArray("users");
            for (JsonElement userElement : usersArray) {
                JsonObject userObject = userElement.getAsJsonObject();
                if (userObject.get("type").getAsString().equalsIgnoreCase(userType)) {
                    return Optional.of(userObject);
                }
            }
            log.warn("User type '{}' not found in {}.json", userType, fileName);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Failed to read JSON '{}.json'. Exception: {}", fileName, e.getMessage());
            return Optional.empty();
        }
    }
}