package jacksonsamples;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class EventWithSerializer {
    public String name;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public LocalDateTime eventDate;

    public EventWithSerializer(){}

    public EventWithSerializer(String name, LocalDateTime eventDate){
        this.name = name;
        this.eventDate = eventDate;
    }
}
