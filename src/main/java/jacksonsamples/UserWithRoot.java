package jacksonsamples;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "user")
public class UserWithRoot {
    public Integer id;
    public String name;

    public UserWithRoot(Integer id, String name){
        this.id = id;
        this.name = name;
    }
}
