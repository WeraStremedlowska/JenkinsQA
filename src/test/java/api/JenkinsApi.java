package api;

import java.util.Base64;
import java.util.Properties;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class JenkinsApi {
    protected String username;
    protected String token;
    private static String encodedAuthString;

    public JenkinsApi() {
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream(Paths.get("src", "test", "resources", "local.properties").toString())) {
            prop.load(input);
            this.username = "wera_stremedlowska";
            this.token = "117dbdb27edbc7104998798d8a2dd6be0d";

            if (username != null) {
                String authString = username + ":" + token;
                this.encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());
            }
        } catch (IOException e) {
            System.err.println("Failed to load properties file: " + e.getMessage());
        }
    }

    protected static String getEncodedAuthString() {
        return encodedAuthString;
    }
}

