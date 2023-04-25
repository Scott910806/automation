package mytools;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;


public class HEstrategy {
    private String p_n = "黄宏贵";
    private String p_u = "fa3d363c-426f-4575-ab34-d3bf45755df5";
    private RequestSpecification requestSpecification;
    private String baseUrl = "http://hengine.apps01.ali-bj-sit03.shuheo.net/hengine";

    public HEstrategy(){setRequest();}
    public HEstrategy(String p_n, String p_u){
        this.p_n = p_n;
        this.p_u = p_u;
        setRequest();
    }

    private void setRequest(){
        this.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType("application/json")
                .build();
    }

    public Response save(String payload){
        String url = "/decision/config/save" + "?p_u=" + p_u + "&p_n=" + p_n;
        return given().spec(requestSpecification).body(payload).when().post(url);
    }

    public Response runTest(String payload){
        String url = "/test/document/run";
        return given().spec(requestSpecification).body(payload).when().put(url);
    }
}
