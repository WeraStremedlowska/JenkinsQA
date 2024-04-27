package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class APIWithRestAssuredTest extends JenkinsApi {

    private String username;
    private String token;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";
        String encodedAuthString = getEncodedAuthString();
        if (encodedAuthString == null) {
            System.err.println("Authentication string is missing or incorrect.");
            return;
        }
        String[] creds = new String(Base64.getDecoder().decode(encodedAuthString)).split(":", 2);
        if (creds.length < 2) {
            System.err.println("Authentication credentials are not properly formatted.");
            return;
        }
        RestAssured.authentication = RestAssured.preemptive().basic(creds[0], creds[1]);
        this.username = creds[0];
        this.token = creds[1];
    }

    @Test
    public void testCreateItem() {
        String xmlPayload = "<project>\n" +
                "    <description>This is a more detailed description for the Jenkins job.</description>\n" +
                "    <keepDependencies>false</keepDependencies>\n" +
                "    <canRoam>true</canRoam>\n" +
                "    <disabled>false</disabled>\n" +
                "    <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n" +
                "    <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" +
                "    <concurrentBuild>false</concurrentBuild>\n" +
                "</project>";

        given()
                .contentType(ContentType.XML)
                .body(xmlPayload)
                .when()
                .post("/createItem?name=NewJobRA")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteItem() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .preemptive()
                .basic(username, token)
                .when()
                .post("/job/NewJobRA/doDelete")
                .then()
                .statusCode(302);
    }
}



