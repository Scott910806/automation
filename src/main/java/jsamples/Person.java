package jsamples;

import java.time.LocalDate;

public class Person {
    private String firstname;
    private String lastname;

    private Gender gender;

    private LocalDate birthday;

    public Person(){}
    public Person(String firstname, String lastname){
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Person(String firstname, String lastname, Gender gender, LocalDate birthday){
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getFirstname(){return this.firstname;}

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public String getLastname(){return this.lastname;}

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public Gender getGender() {
        return this.gender;
    }

    public LocalDate getBirthday(){
        return this.birthday;
    }

    enum Gender{
        F,M
    }
}
