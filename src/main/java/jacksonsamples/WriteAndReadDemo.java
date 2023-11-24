package jacksonsamples;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WriteAndReadDemo {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = new Car("yellow", "renault");
        // 写
        objectMapper.writeValue(new File("target/car.json"), car);

        String carAsString = objectMapper.writeValueAsString(car);
        System.out.println(carAsString);
        // 读
        Car carFromFile = objectMapper.readValue(new File("target/car.json"), Car.class);
        System.out.println(carFromFile.getColor());
        System.out.println(carFromFile.getType());

        Car carFromString = objectMapper.readValue(carAsString, Car.class);
        System.out.println(carFromString.getColor());
        System.out.println(carFromString.getType());

        //JSON to Jackson JsonNode
        JsonNode jsonNode = objectMapper.readTree(carAsString);
        String color = jsonNode.get("color").asText();
        System.out.println(color);

        //Creating a Java List From a JSON Array String
        String jsonCarArray = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});
        listCar.stream().forEach(n -> System.out.println(n.getColor() + n.getType()));


        //Creating Java Map From JSON String
        Map<String, Object> map = objectMapper.readValue(carAsString, new TypeReference<Map<String, Object>>(){});
        System.out.println(map.get("color"));
        System.out.println(map.get("type"));
    }
}
