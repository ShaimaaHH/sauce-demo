package Utilities;

import Models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DataUtils {

    private static final Logger log = LoggerFactory.getLogger(DataUtils.class);
    private static final String TEST_DATA_PATH = "src/test/resources/TestData/";

    // Private constructor to prevent instantiation
    private DataUtils() {
    }

    public static User getUserByType(String type) {
        try (FileReader reader = new FileReader(TEST_DATA_PATH + "LoginCredentials.json")) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            Gson gson = new Gson();
            Type userListType = new TypeToken<List<User>>() {
            }.getType();
            List<User> users = gson.fromJson(jsonObject.get("users"), userListType);
            return users.stream()
                    .filter(user -> user.getType().equalsIgnoreCase(type))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User type '" + type + "' not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON file: " + e.getMessage(), e);
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
}