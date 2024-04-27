package api;

import java.util.Base64;
import java.util.Properties;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.IOException;

public class JenkinsApi {
    private String encodedAuthString;

    public JenkinsApi() {
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream(Paths.get("src", "test", "resources", "local.properties").toString())) {
            prop.load(input);
            String username = prop.getProperty("local.admin.username");
            String token = prop.getProperty("token");

            if (username != null && token != null) {
                String authString = username + ":" + token;
                encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());
            }
        } catch (IOException e) {
            System.err.println("Failed to load properties file: " + e.getMessage());
        }
    }

    public String getEncodedAuthString() {
        return encodedAuthString;
    }
}

