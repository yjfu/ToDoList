import java.io.Serializable;

/**
 * Created by yjfu on 2017/5/19.
 */
public class UserInfo implements Serializable{
    private String name;
    private String password;

    public UserInfo(String name, String password){
        this.name = name;
        this.password = password;
    }
    public String getName(){
        return this.name;
    }
    public String getPassword(){
        return this.password;
    }
}
