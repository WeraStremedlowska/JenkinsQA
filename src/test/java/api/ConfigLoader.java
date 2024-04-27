package api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    public static void main(String[] args) {
        Properties prop = new Properties();
        try {
            // Load the properties file
            prop.load(new FileInputStream("local.properties"));

            // Retrieve properties
            String username = prop.getProperty("local.admin.username");
            String password = prop.getProperty("local.admin.password");  // Though you might not need the password for the API
            String token = prop.getProperty("token");

            // Print to verify that properties are loaded (You should remove or secure this line in production!)
            System.out.println("Username: " + username + ", Token: " + token);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

