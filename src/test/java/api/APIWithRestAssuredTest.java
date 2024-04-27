package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APIWithRestAssuredTest extends JenkinsApi {

    @Order(1)
    @Test
    public void testCreateItem() {
        RestAssured.authentication = RestAssured.preemptive().basic(username, token);

        given()
                .contentType(ContentType.XML)
                .body(TestData.XML_PAYLOAD)
                .when()
                .post("/createItem?name=NewJobRA")
                .then()
                .statusCode(200);
    }

    @Order(2)
    @Test
    public void testGetItem() {
        RestAssured.authentication = RestAssured.preemptive().basic(username, token);

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/api/json?tree=jobs[name,description,lastBuild[lastBuildNumber,duration],lastCompletedBuild[result],nextBuildNumber]")
                .then()
                .statusCode(200)
                .body("jobs.find { it.name == 'NewJobRA' }.description", equalTo("This is a more detailed description for the Jenkins job."))
                .body("jobs.find { it.name == 'NewJobRA' }.nextBuildNumber", equalTo(1))
                .body("jobs.find { it.name == 'NewJobRA' }.lastBuild", nullValue())
                .body("jobs.find { it.name == 'NewJobRA' }.lastCompletedBuild", nullValue());
    }

    @Order(3)
    @Test
    public void testDeleteItem() {
        RestAssured.authentication = RestAssured.preemptive().basic(username, token);

        given()
                .contentType(ContentType.XML)
                .when()
                .post("/job/NewJobRA/doDelete")
                .peek() // print the response body to the console
                .then()
                .statusCode(302)
                .header("Location", equalTo("http://localhost:8080/"));
    }
}




