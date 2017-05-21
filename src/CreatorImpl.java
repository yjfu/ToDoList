import CreatorModule.CreatorPOA;

import java.io.*;
import java.util.HashMap;

/**
 * Created by yjfu on 2017/5/19.
 */
public class CreatorImpl extends CreatorPOA {
    private HashMap<String, UserInfo> users;
    private Server myServer;

    public CreatorImpl(Server server){
        this.myServer = server;
        readUsers();
    }
    private void save(){
        try{
            FileOutputStream fout = new FileOutputStream("users.data");
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(this.users);
            oout.close();
            fout.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void readUsers(){
        File file = new File("users.data");
        if(file.exists()){
            try {
                FileInputStream fin = new FileInputStream("users.data");
                ObjectInputStream oin = new ObjectInputStream(fin);
                this.users = (HashMap<String, UserInfo>)oin.readObject();
                oin.close();
                fin.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            this.users = new HashMap<String, UserInfo>();
        }
    }
    @Override
    public boolean register(String name, String password){
        if(this.users.containsKey(name))
            return false;
        UserInfo ui = new UserInfo(name, password);
        this.users.put(name, ui);
        this.save();
        return true;
    }
    @Override
    public boolean login(String name, String password){
        if(this.users.containsKey(name)&&this.users.get(name).getPassword().equals(password)) {
            //在POA中声明一个user对象
            this.myServer.createUser(name);
            return true;
        }
        return false;
    }
}
