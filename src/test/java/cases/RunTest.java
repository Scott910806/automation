package cases;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import mytools.HEstrategy;
import mytools.MyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Slf4j
@Feature("测试运行")
@Tag("HEngine")
@Execution(ExecutionMode.CONCURRENT)
public class RunTest {
    private HEstrategy hestrategy;

    @BeforeEach
    public void setUp(){
        this.hestrategy = new HEstrategy();
    }

    @Story("字符串相关的内置函数")
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

    @Story("数值相关的内置函数")
    @Test
    public void built_ins_for_number_run() throws Exception{
        String payload = MyUtils.readJsonFile("src/test/java/data/run/built_ins_for_number.json");
        Response response = hestrategy.runTest(payload);
        assertEquals(200, response.statusCode());
//        解析response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.asString());
        JsonNode output = jsonNode.get(0).get("executeTraceRespList").get(0).get("output");
        assertEquals("9", output.get("absInteger").toString());
        assertEquals("3.14", output.get("absFloat").toString());
        assertEquals("5.34", output.get("absDouble").toString());
        assertEquals("5.0", output.get("ceilDouble").toString());
        assertEquals("4.0", output.get("floorDouble").toString());
        assertEquals("1.6094379124341003", output.get("logDouble").toString());
        assertEquals("3.0", output.get("log10Double").toString());
        assertEquals("2.0", output.get("logBaseDouble").toString());
        assertEquals("45", output.get("maxInteger").toString());
        assertEquals("8.88", output.get("maxDouble").toString());
        assertEquals("20", output.get("minInteger").toString());
        assertEquals("3.14", output.get("minDouble").toString());
        assertEquals("1000.0", output.get("powDouble").toString());
        assertEquals("0.34", output.get("maxFloat").toString());
        assertEquals("-1.65", output.get("minFloat").toString());
        assertEquals("9", output.get("roundInteger").toString());
        assertEquals("2.23606797749979", output.get("sqrtDouble").toString());
        assertTrue(output.get("nextRandomInteger").isInt());
        JsonNode integer1 = output.get("nextRandomInteger1");
        assertTrue(integer1.isInt() && integer1.asInt()>0 && integer1.asInt()<=100);
        JsonNode integer2 = output.get("nextRandomInteger2");
        assertTrue(integer2.isInt() && integer2.asInt()>10 && integer2.asInt()<=20);
        JsonNode double1 = output.get("nextRandomDouble");
        assertTrue(double1.isDouble() && double1.asDouble()>0 && double1.asDouble()<1);
    }

    @Story("集合相关的内置函数")
    @Test
    public void built_ins_for_collections_run() throws Exception{
        String payload = MyUtils.readJsonFile("src/test/java/data/run/built_ins_for_collections.json");
        Response response = hestrategy.runTest(payload);
        assertEquals(200, response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.asString());
        JsonNode output = jsonNode.get(0).get("executeTraceRespList").get(0).get("output");
        assertTrue(output.get("anyCalculation").asBoolean());
        assertEquals(5, output.get("countCalculation").asInt());
        assertEquals(3, output.get("minCalculation").asInt());
        assertEquals(45, output.get("maxCalculation").asInt());
        assertEquals(90, output.get("sumCalculation").asInt());
        assertEquals(18.0, output.get("averageCalculation").asDouble());
        assertTrue(output.get("containsCalculation").asBoolean());
        assertEquals("beijing", output.get("firstCalculation").textValue());
        assertEquals("chengdu", output.get("lastCalculation").textValue());
        log.info(output.toString());
        assertEquals("[\"beijing\",\"shanghai\",\"shenzhen\",\"chengdu\"]", output.get("distinctCalculation").toString());
        assertEquals("[\"chengdu\",\"shenzhen\",\"shanghai\",\"shanghai\",\"beijing\"]", output.get("reverseCalculation").toString());
        assertEquals("[\"shanghai\",\"shanghai\",\"beijing\"]", output.get("skipCalculation").toString());
        assertEquals("[\"chengdu\",\"shenzhen\"]", output.get("takeCalculation").toString());
        assertTrue(output.get("crudCalculation").isEmpty());
        assertFalse(output.get("anyCalculation1").asBoolean());
    }

    @Story("类型检查与转换相关的内置函数")
    @Test
    public void built_ins_for_type_check_and_conversion_run() throws Exception{
        String payload = MyUtils.readJsonFile("src/test/java/data/run/built_ins_for_type_check_and_conversion.json");
        Response response = hestrategy.runTest(payload);
        assertEquals(200, response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.asString());
        JsonNode output = jsonNode.get(0).get("executeTraceRespList").get(0).get("output");
        log.info(output.toString());
        assertFalse(output.get("isInteger").asBoolean());
        assertTrue(output.get("isDouble").asBoolean());
        assertEquals("66", output.get("integerToString").textValue());
        assertTrue(output.get("toInteger").isInt());
        assertEquals(5, output.get("toInteger").asInt());
        assertTrue(output.get("toDouble").isDouble());
        assertEquals(3.14, output.get("toDouble").asDouble());
        assertEquals(new BigDecimal("3.1415926"), output.get("toDecimal").decimalValue());
        assertEquals("decimalVar1 is greater than decimalVar2", output.get("decimalCompare").textValue());
        assertEquals(new BigDecimal("10.21111"), output.get("decimalAdd").decimalValue());
        assertEquals(new BigDecimal("7.741976"), output.get("decimalSub").decimalValue());
        assertEquals(new BigDecimal("11.082143761881"), output.get("decimalMul").decimalValue());
        assertEquals(new BigDecimal("7.27100513783375"), output.get("decimalDiv").decimalValue());
//        log.info(output.get("decimalDiv").numberType().toString());
    }

    @Story("LINQ")
    @Test
    public void heel_for_LINQ_run() throws Exception{
        String payload = MyUtils.readJsonFile("src/test/java/data/run/heel_for_LINQ.json");
        Response response = hestrategy.runTest(payload);
        assertEquals(200, response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.asString());
        JsonNode output = jsonNode.get(0).get("executeTraceRespList").get(0).get("output");
        log.info(output.toString());
        assertEquals("At last one vehicle is newer", output.get("decisionAny").textValue());
        assertEquals("There are at least 3 vehicles in the policy", output.get("decisionCount").textValue());
        assertEquals("There are more than 2 tickets in the policy", output.get("decisionSum").textValue());
        assertEquals("The average year of the vehicles is less than 2018", output.get("decisionAverage").textValue());
        assertEquals("The oldest vehicle is before 2015", output.get("decisionMin").textValue());
        assertEquals("The newest vehicle is after 2015", output.get("decisionMax").textValue());
        assertEquals("The last driver is tianxi", output.get("decisionLast").textValue());
        assertEquals("The first driver is soctt", output.get("decisionFirst").textValue());
        assertEquals("[\"yuliangliang\",\"tianxi\",\"tianxiPlus\",\"scott\"]", output.get("decisionDistinct").toString());
        assertEquals("[\"scott\",\"yuliangliang\",\"tianxiPlus\",\"tianxi\"]", output.get("decisionToArray").toString());
        assertEquals("tianxi", output.get("decisionOrderby1").textValue());
        assertEquals("[\"yuliangliang\",\"scott\",\"tianxiPlus\",\"tianxi\"]", output.get("decisionOrderby2").toString());
        assertEquals("[\"tianxi\",\"tianxiPlus\",\"scott\",\"yuliangliang\"]", output.get("decisionOrderby3").toString());
        assertEquals("Average age is over 20, and it is 26.25", output.get("decisionLet1").textValue());
        assertEquals("[\"scott\",\"yuliangliang\",\"tianxiPlus\",\"tianxi\"]", output.get("decisionLet2").toString());
        assertEquals("[\"scott drives BYD 2022\",\"yuliangliang drives BMW 2010\",\"tianxiPlus drives AUDI 2019\",\"tianxi drives HONDA 1999\"]", output.get("decisionJoin1").toString());
        assertEquals("Average is 26.25", output.get("decisionSelect").textValue());
        assertEquals("scott", output.get("decisionFirst1").textValue());
        assertEquals("The last driver is tianxi", output.get("decisionLast1").textValue());
    }

    @Story("HEEL基础用法")
    @Test
    public void heel_samples_run() throws Exception{
        String payload = MyUtils.readJsonFile("src/test/java/data/run/heel_samples.json");
        Response response = hestrategy.runTest(payload);
        assertEquals(200, response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.asString());
        JsonNode output = jsonNode.get(0).get("executeTraceRespList").get(0).get("output");
        log.info(output.toString());
        assertEquals("Hello, scott", output.get("simpleExamples").get("simpleFormat").textValue());
        assertEquals("Hello, scott. How are you, scott", output.get("simpleExamples").get("betterFormat").textValue());
        assertEquals("Eligible", output.get("simpleExamples").get("eligible").textValue());
        assertTrue(output.get("simpleExamples").get("isEmergency").booleanValue());
        assertEquals("[\"SO\",\"yxma@shuhe.c\"]", output.get("simpleExamples").get("invaildFields").toString());
        assertEquals("yxma@shuhe.c", output.get("simpleExamples").get("lastInvaildField").textValue());
        assertEquals("SO", output.get("simpleExamples").get("firstInvaildField").textValue());
        assertEquals("The cost is $128.76", output.get("numericalExamples").get("description").textValue());
        assertEquals("The cost is 128.76", output.get("numericalExamples").get("betterDescription").textValue());
        assertTrue(output.get("numericalExamples").get("isRoundPrice").booleanValue());
        assertEquals(150, output.get("numericalExamples").get("assignVariable").intValue());
        assertTrue(output.get("numericalExamples").get("age45").booleanValue());
        assertTrue(output.get("numericalExamples").get("age44Plus").booleanValue());
        assertTrue(output.get("numericalExamples").get("age46Mins").booleanValue());
        assertTrue(output.get("numericalExamples").get("ageCheck").booleanValue());
        assertEquals("Young forever", output.get("numericalExamples").get("ageCategory").textValue());
        assertTrue(output.get("numericalExamples").get("ageBetween40And50").booleanValue());
        assertFalse(output.get("numericalExamples").get("age18MinsOr60Plus").booleanValue());
    }

    @Test
    public void built_ins_for_Date_run() throws Exception{
        String payload = MyUtils.readJsonFile("src/test/java/data/run/built_ins_for_Date.json");
        Response response = hestrategy.runTest(payload);
        assertEquals(200, response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.asString());
        JsonNode output = jsonNode.get(0).get("executeTraceRespList").get(0).get("output");
        assertAll("Fields of Date",
                ()->{
                    JsonNode scott = output.get("scott");
                    assertAll("assert",
                            ()->assertEquals(26, scott.get("day").intValue()),
                            ()->assertEquals(6,scott.get("month").intValue()),
                            ()->assertEquals(1991, scott.get("year").intValue()),
                            ()->assertEquals(6, scott.get("hour").intValue()),
                            ()->assertEquals(30, scott.get("minute").intValue()),
                            ()->assertEquals(55, scott.get("second").intValue())
                            );
                }
        );
        assertAll("Function of Date",
                ()->{
                    ZonedDateTime anotherDate = LocalDateTime.of(2023,02,01,06,55,44).atZone(ZoneId.systemDefault());
                    ZonedDateTime now = ZonedDateTime.now();
                    ZonedDateTime now1 = Instant.ofEpochMilli(output.get("now").longValue()).atZone(ZoneId.systemDefault());
                    ZonedDateTime utc = Instant.ofEpochMilli(output.get("universalTime").longValue()).atZone(ZoneId.systemDefault());
                    String utcf = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss")).format(utc);
                    Long addTimeSpan = anotherDate.plusDays(2).plusHours(12).plusMinutes(30).plusSeconds(40).toInstant().toEpochMilli();
                    Long minusTimeSpan = anotherDate.minusDays(2).minusHours(12).minusMinutes(30).minusSeconds(40).toInstant().toEpochMilli();
                    assertAll("assert",
                            ()->assertEquals("2023-01-31 22:55:44", utcf),
                            ()->assertTrue(now1.isBefore(now) && now1.isAfter(now.minusSeconds(2))),
                            ()->assertEquals(output.get("addTimeSpan").longValue(), addTimeSpan),
                            ()->assertEquals(output.get("addTimeSpan1").longValue(), minusTimeSpan),
                            ()->assertEquals(anotherDate.isAfter(now.minusMonths(6)), output.get("withIn6Months").booleanValue()),
                            ()->assertEquals(anotherDate.isAfter(now.minusYears(1)), output.get("withIn1Year").booleanValue()),
                            ()->assertEquals(anotherDate.isBefore(now.plusDays(15)), output.get("in15Days").booleanValue()),
                            ()->assertEquals(anotherDate.isBefore(now.plusSeconds(30)),output.get("in30Seconds").booleanValue())
                    );
                }
                );
        assertAll("Function of Date",
                ()->{
                    ZonedDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0)).atZone(ZoneId.systemDefault());
                    assertAll("assert",
                            ()->assertEquals(today.toInstant().toEpochMilli(), output.get("today").longValue()),
                            ()->assertEquals(today.plusMonths(4).toInstant().toEpochMilli(), output.get("addMonths").longValue()),
                            ()->assertEquals(today.plusYears(2).toInstant().toEpochMilli(), output.get("addYears").longValue()),
                            ()->assertEquals(today.minusDays(10).toInstant().toEpochMilli(), output.get("addDays").longValue()),
                            ()->assertEquals(today.minusSeconds(20).toInstant().toEpochMilli(), output.get("addSeconds").longValue())
                            );
                }
                );
    }

}
