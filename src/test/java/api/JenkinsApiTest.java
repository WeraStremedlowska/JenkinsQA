package api;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class JenkinsApiTest extends JenkinsApi {
    private static final HttpClient client = HttpClient.newHttpClient();// or password
    private static final String BASE_URL = "http://localhost:8080";

    private static String encodeCredentials() {
        String auth = getEncodedAuthString();
        return "Basic " + auth;
    }

    private static String[] getHeader() {
        return new String[]{"Authorization", encodeCredentials(), "Content-Type", "application/xml"};
    }

    private static HttpResponse<String> getHttp(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + url))
                    .headers("Authorization", encodeCredentials(), "Accept", "application/json")
                    .GET()
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpResponse<String> postHttp(String url, String body) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + url))
                    .headers(getHeader())
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Ignore
    @Test(priority=1)
    public void testCreateItem() {
        String xmlPayload = TestData.XML_PAYLOAD;
        HttpResponse<String> response = postHttp("/createItem?name=Project", xmlPayload);
        assertEquals(200, response.statusCode());
    }


    @Ignore
    @Test(priority=2)
    public void testGetItem() {
        HttpResponse<String> response = getHttp("/api/json?tree=jobs[name,description,lastBuild[lastBuildNumber,duration],lastCompletedBuild[result],nextBuildNumber]");
        assertEquals(200, response.statusCode());
        System.out.println(response.body());
        assertTrue(response.body().contains("Project"));
        assertTrue(response.body().contains("This is a more detailed description for the Jenkins job."));
    }

    @Ignore
    @Test(priority=3)
    public void testRenameJob() {
        HttpResponse<String> response = postHttp("/job/Project/doRename?newName=NewProject", "");
        assertEquals(302, response.statusCode());

        HttpResponse<String> confirmResponse = getHttp("/api/json?tree=jobs[name]");
        assertTrue(confirmResponse.body().contains("NewProject"));
    }


    @Ignore
    @Test(priority=4)
    public void testDeleteItem() {
        HttpResponse<String> response = postHttp("/job/NewProject/doDelete", "");
        assertEquals(302, response.statusCode());
    }
}

