package api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JenkinsJsonParser {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a file path as an argument.");
            return;
        }

        String filePath = args[0];

        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jobsArray = jsonObject.getJSONArray("jobs");
            JSONArray viewsArray = jsonObject.getJSONArray("views");

            System.out.println("Jobs:");
            for (int i = 0; i < jobsArray.length(); i++) {
                JSONObject job = jobsArray.getJSONObject(i);
                System.out.println(job.getString("_class"));
            }

            System.out.println("Views:");
            for (int i = 0; i < viewsArray.length(); i++) {
                JSONObject view = viewsArray.getJSONObject(i);
                System.out.println(view.getString("_class"));

                JSONArray viewJobsArray = view.getJSONArray("jobs");
                System.out.println("Jobs in this view:");
                for (int j = 0; j < viewJobsArray.length(); j++) {
                    JSONObject viewJob = viewJobsArray.getJSONObject(j);
                    System.out.println(viewJob.getString("_class"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
