package jacksonsamples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class SerializationTest {
    @Test
    void whenSerializingUsingJsonAnyGetter_thenCorrect() throws JsonProcessingException{
        ExtendableBean bean = new ExtendableBean("my bean");
        bean.add("attr1", "value1");
        bean.add("attr2", "value2");
        String result = new ObjectMapper().writeValueAsString(bean);
        log.info(result);
        assertThat(result, containsString("attr1"));
        assertThat(result, containsString("value1"));
    }

    @Test
    void whenSerializingUsingJsonGetter_thenCorrect() throws JsonProcessingException{
        MyBean bean = new MyBean(1, "My bean");
        String result = new ObjectMapper().writeValueAsString(bean);
        log.info(result);
        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));
    }

    @Test
    void whenSerializingUsingJsonRawValue_thenCorrect() throws JsonProcessingException{
        RawBean bean = new RawBean("My bean", "{\"attr\":false}");
        String result = new ObjectMapper().writeValueAsString(bean);
        log.info(result);
        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("{\"attr\":false}"));
    }

    @Test
    void whenSerializingUsingJsonValue_thenCorrect() throws JsonProcessingException, IOException {
        String enumAsString = new ObjectMapper().writeValueAsString(TypeEnumWithValue.TYPE1);
        log.info(enumAsString);
        assertThat(enumAsString, is("\"Type A\""));
    }

    @Test
    void whenSerializingUsingJsonRootName_thenCorrect() throws JsonProcessingException{
        UserWithRoot user = new UserWithRoot(1, "John");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String result = mapper.writeValueAsString(user);
        log.info(result);
        assertThat(result, containsString("user"));
        assertThat(result, containsString("John"));
    }

    @Test
    void whenSerializingUsingJsonSerialize_thenCorrect() throws JsonProcessingException, ParseException{
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String toParse = "2023-09-14 18:30:00";
        LocalDateTime date = LocalDateTime.parse(toParse, dtf);
        EventWithSerializer event = new EventWithSerializer("party", date);
        String result = new ObjectMapper().writeValueAsString(event);
        log.info(result);
        assertThat(result, containsString(toParse));
    }

    @Test
    void whenDeserializingUsingJsonCreator_thenCorrect() throws IOException{
        String json = "{\"id\": 1, \"theName\": \"My bean\"}";
        BeanWithCreator bean = new ObjectMapper().readerFor(BeanWithCreator.class).readValue(json);
        assertEquals("My bean", bean.name);
    }

    @Test
    void whenDeserializingUsingJsonInject_thenCorrect() throws IOException{
        String json = "{\"name\": \"My bean\"}";
        InjectableValues inject = new InjectableValues.Std().addValue(int.class, 1);
        BeanWithInject bean = new ObjectMapper().reader(inject).forType(BeanWithInject.class).readValue(json);
        assertEquals("My bean", bean.name);
        assertEquals(1, bean.id);
    }

    @Test
    void whenDeserializingUsingJsonAnySetter_thenCorrect() throws IOException{
        String json = "{\"name\": \"My bean\", \"attr2\": \"value2\", \"attr1\": \"value1\"}";
        ExtendableBean bean = new ObjectMapper().readerFor(ExtendableBean.class).readValue(json);
        assertEquals("My bean", bean.name);
        assertEquals("value2", bean.getProperties().get("attr2"));
    }

    @Test
    void whenDeserializingUsingJsonSetter_thenCorrect() throws IOException{
        String json = "{\"id\": 1, \"name\": \"My bean\"}";
        MyBean bean = new ObjectMapper().readerFor(MyBean.class).readValue(json);
        assertEquals("My bean", bean.getTheName());
    }

    @Test
    void whenDeserializingUsingJsonDeserializer_thenCorrect() throws IOException{
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String json = "{\"name\": \"party\", \"eventDate\": \"2023-09-15 14:30:40\"}";
        EventWithSerializer event = new ObjectMapper().readerFor(EventWithSerializer.class).readValue(json);
        assertEquals("2023-09-15 14:30:40", dtf.format(event.eventDate));
    }

    @Test
    void whenDeserializingUsingJsonAlias_thenCorrect() throws IOException{
        String json = "{\"fName\": \"John\", \"lastName\": \"Green\"}";
        AliasBean bean = new ObjectMapper().readerFor(AliasBean.class).readValue(json);
        assertEquals("John", bean.getFirstName());
    }

    @Test
    void whenSerializingUsingJsonFormat_thenCorrect() throws JsonProcessingException, ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String toParse = "20-09-2023 10:20:40";
        Date date = sdf.parse(toParse);
        Event event = new Event("party", date);
        String result = new ObjectMapper().writeValueAsString(event);
        log.info(result);
        assertThat(result, containsString("2023-09-20 10:20:40"));
    }

    @Test
    void whenSerializingUsingJsonUnwrapped_thenCorrect() throws JsonProcessingException {
        UnwrappedUser.Name name = new UnwrappedUser.Name("John", "Doe");
        UnwrappedUser user = new UnwrappedUser(1, name);
        String result = new ObjectMapper().writeValueAsString(user);
        log.info(result);
        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("name")));
    }

    @Test
    void whenSerializingUsingJsonViews_thenCorrect() throws JsonProcessingException {
        Item item = new Item(2, "book", "John");
        String result = new ObjectMapper().writerWithView(Views.Public.class).writeValueAsString(item);
        log.info(result);
        assertThat(result, containsString("2"));
        assertThat(result, containsString("book"));
        assertThat(result, not(containsString("John")));
    }

    @Test
    void whenSerializingUsingJacksonReferenceAnnotation_thenCorrect() throws JsonProcessingException{
        UserWithRef user = new UserWithRef(1, "John");
        ItemWithRef item = new ItemWithRef(2, "book", user);
        user.addItem(item);
        String result = new ObjectMapper().writeValueAsString(item);
        log.info(result);
        assertThat(result, containsString("2"));
        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("userItems")));
    }

    @Test
    void whenSerializingUsingJsonIdentity_thenCorrect() throws JsonProcessingException{
        UserWithIdentity user = new UserWithIdentity(1, "John");
        ItemWithIdentity item = new ItemWithIdentity(2, "book", user);
        user.addItem(item);
        String result = new ObjectMapper().writeValueAsString(item);
        log.info(result);
        assertThat(result, containsString("2"));
        assertThat(result, containsString("John"));
        assertThat(result, containsString("userItems"));
    }

    @Test
    void whenSerializingUsingJsonFilter_thenCorrect() throws JsonProcessingException {
        FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("name"));
        BeanWithFilter bean = new BeanWithFilter(1, "my bean");
        String result = new ObjectMapper().writer(filters).writeValueAsString(bean);
        log.info(result);
        assertThat(result, containsString("my bean"));
        assertThat(result, not(containsString("id")));
    }

    @Test
    void whenSerializingUsingCustomAnnotation_thenCorrect() throws JsonProcessingException{
        BeanWithCustomAnnotation bean = new BeanWithCustomAnnotation(1, "My bean", null);
        String result = new ObjectMapper().writeValueAsString(bean);
        log.info(result);
        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));
    }

    @Test
    void whenSerializingUsingMinInAnnotation_thenCorrect() throws JsonProcessingException{
        ItemWithRef item = new ItemWithRef(1, "book", null);
        String result = new ObjectMapper().writeValueAsString(item);
        log.info(result);
        assertThat(result, containsString("owner"));

        ObjectMapper mapper = new ObjectMapper().addMixIn(UserWithRef.class, MyMinxInForIgnoreType.class);
        result = mapper.writeValueAsString(item);
        log.info(result);
        assertThat(result, not(containsString("owner")));
    }

    @Test
    void whenDisablingAllAnnotation_thenAllDisabled() throws IOException{
        MyBean bean = new MyBean(1, null);
        ObjectMapper mapper = new ObjectMapper();
//        mapper.disable(MapperFeature.USE_ANNOTATIONS);
        String result = mapper.writeValueAsString(bean);
        log.info(result);
    }

    @Test
    void whenDeserializingIgnoreTheNewField() throws JsonProcessingException {
        String jsonString = "{\"color\": \"BLACK\", \"type\": \"FIAT\", \"year\": \"1970\"}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Car car = mapper.readValue(jsonString, Car.class);
        log.info(car.getColor());
        log.info(car.getType());
        JsonNode jsonNode = mapper.readTree(jsonString);
        String year = jsonNode.get("year").asText();
        log.info(year);
    }

    @Test
    void customSerializer() throws JsonProcessingException{
        SimpleModule module = new SimpleModule("CustomCarSerializer", new Version(1, 0, 0, null, null,null));
        module.addSerializer(Car.class, new CustomCarSerializer());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        Car car = new Car("yellow", "renault");
        String carJson = mapper.writeValueAsString(car);
        log.info(carJson);
        assertThat(carJson, containsString("renault"));
    }

    @Test
    void customDeserializer() throws JsonProcessingException{
        String json = "{\"color\": \"black\", \"type\": \"BMW\"}";
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Car.class, new CustomCarDeserializer());
        mapper.registerModule(module);
        Car car = mapper.readValue(json, Car.class);
        log.info(car.getColor());
        log.info(car.getType());
    }

    @Test
    void handleCollection() throws JsonProcessingException{
        String jsonArray = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        Car[] cars = mapper.readValue(jsonArray, Car[].class);
        log.info(cars[0].getType());
        log.info(cars[0].getColor());
    }

    @Test
    void givenJsonHasUnknownValuesButJacksonIsIgnoringUnknowns_whenDeserializing_thenCorrect() throws JsonProcessingException, JsonMappingException, IOException{
        String json = "{\"color\": \"black\", \"type\": \"BMW\", \"stringValue\": \"ab\"}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Car car = mapper.readValue(json, Car.class);
        assertNotNull(car);
        assertThat(car.getColor(), equalTo("black"));
        assertThat(car.getType(), equalTo("BMW"));
    }

    @Test
    void givenJsonHasUnknownValuesButIgnoredOnClass_whenDeserializing_thenCorrect() throws JsonProcessingException, IOException{
        String json = "{\"color\": \"black\", \"type\": \"BMW\", \"stringValue\": \"ab\"}";
        ObjectMapper mapper = new ObjectMapper();
        Car car = mapper.readValue(json, Car.class);
        assertNotNull(car);
        assertThat(car.getColor(), equalTo("black"));
        assertThat(car.getType(), equalTo("BMW"));
    }

    @Test
    void givenNotAllFieldsHaveValuesInJson_whenDeserializingAJsonToAClass_thenCorrect() throws JsonProcessingException, IOException{
        String json = "{\"color\": \"black\", \"stringValue\": \"ab\"}";
        ObjectMapper mapper = new ObjectMapper();
        Car car = mapper.readValue(json, Car.class);
        assertNotNull(car);
        assertThat(car.getColor(), equalTo("black"));
    }
}
