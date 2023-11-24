package jacksonsamples;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AliasBean {
    @JsonAlias({"f_name", "fName"})
    private String firstName;

    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
