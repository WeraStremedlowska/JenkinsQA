package api;

import java.util.Base64;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class JenkinsApi {
    public static void main(String[] args) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/test/resources/local.properties"));
            String username = prop.getProperty("local.admin.username");
            String token = prop.getProperty("token");

            if (username == null || token == null) {
                System.out.println("Username or token is missing in the properties file.");
                return;
            }

            String authString = username + ":" + token;
            String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());
            System.out.println("Authorization: Basic " + encodedAuthString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
