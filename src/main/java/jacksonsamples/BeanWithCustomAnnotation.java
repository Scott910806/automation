package jacksonsamples;

import java.util.Date;

@CustomAnnotation
public class BeanWithCustomAnnotation {
    public int id;
    public String name;
    public Date dateCreated;

    public BeanWithCustomAnnotation(){}
    public BeanWithCustomAnnotation(int id, String name, Date dateCreated){
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }
}
