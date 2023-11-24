package jacksonsamples;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class ExtendableBean {
    public String name;
    private Map<String, String> properties;

    public ExtendableBean(){this.properties = new HashMap<>();}

    public ExtendableBean(String name) {
        this.name = name;
        this.properties = new HashMap<>();
    }

    @JsonAnyGetter
    public Map<String ,String> getProperties(){
        return properties;
    }

    @JsonAnySetter
    public void add(String attr, String value){
        properties.put(attr, value);
    }
}
