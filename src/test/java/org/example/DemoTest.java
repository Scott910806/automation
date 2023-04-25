package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import mytools.MyUtils;
import org.junit.jupiter.api.*;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;


@Slf4j
@Feature("demo用例")
public class DemoTest {
    static String requestPayload;
    private RequestSpecification requestSpecification;

    @BeforeAll
    public static void setUp(){log.info("开始执行测试用例");}

    @AfterAll
    public static void tearDown(){
        log.info("运行结束");
    }

    @BeforeEach
    public void setRequest(){
        requestSpecification = new RequestSpecBuilder().
                setBaseUri("http://hengine.apps01.ali-bj-sit03.shuheo.net/hengine").
                setContentType("application/json").build();
    }

    @Test
    @Disabled
    @Story("/decision/config/save")
    @Description("保存策略配置")
    public void saveStrategyConfig() throws Exception{
        String requestPayload = MyUtils.readJsonFile("src/test/java/data/demo.json");
        given().
                spec(requestSpecification).
                body(requestPayload).
        when().
                post("/decision/config/save?p_u=fa3d363c-426f-4575-ab34-d3bf45755df5&p_n=黄宏贵").
        then().
                statusCode(200);
    }

    @Test
    @Story("/test/document/run")
    @Description("运行测试")
    public void executeStrategy() throws Exception{
        String requestPayload = MyUtils.readJsonFile("src/test/java/data/document.json");
        String respBody =
                given().
                        spec(requestSpecification).
                        body(requestPayload).
                when().
                        put("/test/document/run").asString();
        // 解析response
        ObjectMapper mapper = new ObjectMapper();
        String targetValue = null;
        JsonNode jsonNode = mapper.readTree(respBody);
        JsonNode arrayNode = jsonNode.get(0).get("testDocumentVariableSummaryVo").get("output");
        if (arrayNode instanceof ArrayNode){
            for (JsonNode node : arrayNode) {
                if (node.get("varKey").textValue().equals("score2")) {
//                    log.info(node.get("testValue").toString());
                    targetValue = node.get("testValue").textValue();
                }
            }
        }
        assertEquals("china", targetValue);
    }
}
