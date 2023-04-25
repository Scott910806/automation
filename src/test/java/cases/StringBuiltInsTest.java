package cases;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import mytools.HEstrategy;
import mytools.MyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class StringBuiltInsTest {
    private HEstrategy hestrategy;

    @BeforeEach
    public void setUp(){
        this.hestrategy = new HEstrategy();
    }

    @Test
    public void built_ins_for_string_save() throws Exception{
        String payload = MyUtils.readJsonFile("src/test/java/data/save/built_ins_for_string.json");
        Response response = hestrategy.save(payload);
        assertEquals(200, response.statusCode());
    }

    @Test
    public void built_ins_for_string_run() throws Exception{
        String payload = MyUtils.readJsonFile("src/test/java/data/run/built_ins_for_string.json");
        Response response = hestrategy.runTest(payload);
        assertEquals(200, response.statusCode());
        //解析响应
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.asString());
        JsonNode output = jsonNode.get(0).get("executeTraceRespList").get(0).get("output");
        assertEquals("el", output.get("subStringValue").textValue());
        log.info(output.toString());
        assertEquals(11, output.get("strLength").asInt());
        assertEquals("HELLO WORLD", output.get("strUpper").textValue());
        assertEquals("hello world", output.get("strLower").textValue());
        assertTrue(output.get("isStrStartsWith").asBoolean());
        assertFalse(output.get("isStrEndsWith").asBoolean());
        assertTrue(output.get("isStrContains").asBoolean());
        assertEquals("shuhe    test", output.get("strTrim").textValue());
        assertTrue(output.get("strFormat").textValue().contains("welcome, scott. The time is"));
        assertTrue(output.get("isStrMatch").asBoolean());
        assertEquals("HEngine is the best ", output.get("strReplace").textValue());
        assertEquals("we$$are$$the$$best", output.get("strJion").textValue());
//        log.info(output.get("strSplit").toString());
        assertEquals("[\"we\",\"are\",\"second\",\"to\",\"none\"]", output.get("strSplit").toString());
    }
}
