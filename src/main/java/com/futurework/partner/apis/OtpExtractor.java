package com.futurework.partner.apis;

import com.fasterxml.jackson.databind.JsonNode;
import com.futurework.partner.utilities.JsonUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static com.futurework.partner.apis.BaseApi.setUpRequestSpecification;

public class OtpExtractor {
    static JsonNode data = JsonUtils.getTestData().get("checkAccountCreation");
    static String userName = data.get("email").asText();  // Example dynamic username
    static String mobile = data.get("password").asText();  // The password that will be encoded automatically
    static String url = "https://maas-uat.futurework.com.sa/maas/login";


    public static Response login() {
        setUpRequestSpecification();

        return RestAssured.given()
                .queryParam("userName", userName)
                .queryParam("mobile", mobile)
                .queryParam("userType", "SME")
                .contentType(ContentType.JSON)
                .when()
                .post(url)
                .then().log().all()
                .statusCode(200)  // Ensure the response status is 200 (OK)
                .extract()
                .response();
    }

    public static String getOtp(Response response) {
        JsonPath jsonPath = response.jsonPath();
        String code = jsonPath.getString("code");
        System.out.println("The Code is " + code);
        String otpTrimmed = code.length() > 4 ? code.substring(0, 4) : code;
        return otpTrimmed;
    }

}
