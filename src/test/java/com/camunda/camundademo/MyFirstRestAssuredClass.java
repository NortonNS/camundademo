package com.camunda.camundademo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.Matchers.hasSize;

class MyFirstRestAssuredClass {
    //final static String url="http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1";
    final static String uri="http://demo.guru99.com/V4/sinkministatement.php";
    final static long responseTimeThreshold = 1200; // milliseconds
    final static int successfulStatusCode = 200;
    private static RequestSpecification spec;

    // Initialize a Specification to be used in other JUNIT Testing
    @BeforeAll
    static void initSpec() {
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://demo.guru99.com/")
                .addFilter(new ResponseLoggingFilter()) //log request and response for better debugging. You can also only log if a requests fails.
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    // Use pathParam - show how you can pass parameters to the URL path.
    @Test
    void getTesting() {
    	given()
                .pathParam("raceSeason","2017")
                .when()
                .get("http://ergast.com/api/f1/{raceSeason}/circuits.json")
                .then()
                .assertThat()
                    .statusCode(200)
                .and()
                    .contentType(ContentType.JSON).
                and()
                    .body("MRData.CircuitTable.Circuits.circuitId",hasSize(20));
    }

    // use spec above to set the initial url, content type, etc.
    @Test
    void getResponseStatus(){
        given()
                .spec(spec)
                .queryParam("CUSTOMER_ID","68195")
                .queryParam("PASSWORD","1234!")
                .queryParam("Account_No","1")
                .when()
                .get("V4/sinkministatement.php")
                .then()
                .statusCode(successfulStatusCode);
                //.and().contentType(ContentType.BINARY);
    }

    // Check to ensure response time is within acceptable limits.
    @Test
    void getResponseTime()  {
        long responseTime = get(uri).timeIn(MILLISECONDS);
        System.out.println("The time taken to fetch the response "+ responseTime + " milliseconds");

        Assert.isTrue(responseTime < responseTimeThreshold, "Response Time Exceeded");
    }
}
