package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class JsonReader {

    public static List<UserData> readUsers() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            File file = new File(
                System.getProperty("user.dir") + "/src/test/resources/users.json"
            );

            UserData[] users = mapper.readValue(file, UserData[].class);

            return Arrays.asList(users);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}