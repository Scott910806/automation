package jacksonsamples;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Event {
    public String name;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public Date eventDate;

    public Event(String name, Date eventDate){
        this.name = name;
        this.eventDate = eventDate;
    }
}
