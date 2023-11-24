package jacksonsamples;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class UnwrappedUser {
    public int id;

    @JsonUnwrapped
    public Name name;

    public static class Name{
        public String firstName;
        public String lastName;
        public Name(){}
        public Name(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    public UnwrappedUser(){}
    public UnwrappedUser(int id, Name name){
        this.id = id;
        this.name = name;
    }
}
