package cases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import mytools.HEstrategy;
import mytools.MyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Slf4j
@Feature("检查、编译、翻译、保存")
public class TranslateTest {
    private HEstrategy hEstrategy;

    @BeforeEach
    public void setUp(){
        this.hEstrategy = new HEstrategy();
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/java/data/save/simpleHeel.json",
            "src/test/java/data/save/built_ins_for_string.json",
            "src/test/java/data/save/built_ins_for_number.json",
            "src/test/java/data/save/built_ins_for_collections.json",
            "src/test/java/data/save/built_ins_for_type_check_and_conversion.json",
            "src/test/java/data/save/heel_for_LINQ.json",
            "src/test/java/data/save/heel_samples.json"})
    public void translate(String path) throws Exception{
        String payload = MyUtils.readJsonFile(path);
        Response response = hEstrategy.save(payload);
        assertEquals(200, response.statusCode());
    }
}
