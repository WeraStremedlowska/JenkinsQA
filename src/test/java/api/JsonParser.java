package api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonParser {

    public static void main(String[] args) {
        try {
            printJobDetailsFromJson(".idea/httpRequests/2024-04-27T124408.200.json");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Reads a JSON file and prints details of each job, including their names and the status of their last completed builds.
     *
     * @param filePath The path to the JSON file.
     * @throws IOException If there is an error reading the file.
     * @throws JSONException If there is an error parsing the JSON.
     */
    public static void printJobDetailsFromJson(String filePath) throws IOException, JSONException {
        String jsonText = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject json = new JSONObject(jsonText);
        JSONArray jobs = json.getJSONArray("jobs");

        for (int i = 0; i < jobs.length(); i++) {
            JSONObject job = jobs.getJSONObject(i);
            String name = job.getString("name");
            String description = job.getString("description");
            JSONObject lastCompletedBuild = job.getJSONObject("lastCompletedBuild");
            String result = lastCompletedBuild.getString("result");
            System.out.println("Job Name: " + name + " ,Description: " + description + ", Last Completed Build Status: " + result);
        }
    }
}


