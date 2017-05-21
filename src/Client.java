import CreatorModule.Creator;
import CreatorModule.CreatorHelper;
import UserModule.User;
import UserModule.UserHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import sun.net.spi.nameservice.NameService;

import java.util.Properties;
import java.util.Scanner;

/**
 * Created by yjfu on 2017/5/20.
 */
public class Client {
    static boolean exit = false;
    private User user;
    private Creator creator;
    private NamingContextExt nc;
    private Scanner scanner;

    public Client(){
        init();
    }
    private void init(){
        //设定reader
        this.scanner = new Scanner(System.in);
        //设定orb
        String[] args = {};
        Properties properties = new Properties();
        properties.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        properties.put("org.omg.CORBA.ORBInitialPort", "10001");
        ORB orb = ORB.init(args, properties);
        try{
            //获取名称管理器NameService的引用
            Object ncObj = orb.resolve_initial_references("NameService");
            this.nc = NamingContextExtHelper.narrow(ncObj);
            //获取creator的引用
            Object  creatorObj = this.nc.resolve_str("creator");
            this.creator = CreatorHelper.narrow(creatorObj);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean login(){
        String name,password;
        try{
            System.out.println("请输入用户名：");
            name = this.scanner.nextLine();
            System.out.println("请输入密码：");
            password = this.scanner.nextLine();

            //检测登录并在服务器POA中声明这个对象
            if(this.creator.login(name,password)){
                //获取user的引用
                Object userObj = this.nc.resolve_str("user."+name);
                this.user = UserHelper.narrow(userObj);
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        System.out.println("登陆失败：用户名或密码错误。");
        return false;
    }
    public void regist(){
        String name,password;
        System.out.println("请输入用户名：");
        name = this.scanner.nextLine();
        System.out.println("请输入密码：");
        password = this.scanner.nextLine();
        if(this.creator.register(name, password)){
            System.out.println("注册成功！");
        }
        else{
            System.out.println("注册失败：用户名已存在。");
        }
    }
    private void printMenu(){
        System.out.println("请输入如下指令序号：");
        System.out.println("1.增加条目");
        System.out.println("2.删除条目");
        System.out.println("3.查询条目");
        System.out.println("4.清空条目");
        System.out.println("5.显示所有条目");
        System.out.println("6.退出");
    }
    public boolean getInSystem(){
        System.out.println("请输入如下指令序号：");
        System.out.println("1.登录");
        System.out.println("2.注册");
        System.out.println("3.退出");
        int choice;
        if(!this.scanner.hasNextInt()){
            this.scanner.nextLine();
            choice = -1;
        }
        else{
            choice = this.scanner.nextInt();
            this.scanner.nextLine();
        }
        switch(choice){
            case 1:{
                if(this.login())
                    return true;
                break;
            }
            case 2:{
                this.regist();
                break;
            }
            case 3:{
                Client.exit = true;
                break;
            }
            default:{
                System.out.println("错误的输入！");
            }
        }
        return false;
    }
    private void show(){
        String res = this.user.show();
        if(res.length() == 0)
            System.out.println("目前还没有内容。");
        else
            System.out.println(res);
    }
    private void query(){
        System.out.println("输入欲查询的起始日期：（格式：yyyy-mm-dd,hh:mm）");
        String start = this.scanner.nextLine();
        System.out.println("输入欲查询的结束日期：（格式：yyyy-mm-dd,hh:mm）");
        String end = this.scanner.nextLine();
        String res = this.user.query(start, end);
        //res是"时间格式出错"
        if(res.startsWith("时")){
            System.out.println(res);
            return;
        }
        System.out.println("查询结果如下：");
        System.out.println(res);
    }
    private void deleteItem(){
        System.out.println("输入欲删除条目编号：");
        if(scanner.hasNextInt()){
            if(this.user.delete(scanner.next())){
                System.out.println("删除成功！");
                return ;
            }
        }
        System.out.println("删除失败：请查看输入是否合法。");
    }
    private void addItem(){
        System.out.println("输入条目起始日期：（格式：yyyy-mm-dd,hh:mm）");
        String start = this.scanner.nextLine();
        System.out.println("输入条目结束日期：（格式：yyyy-mm-dd,hh:mm）");
        String end = this.scanner.nextLine();
        System.out.println("输入条目描述标签：");
        String tag = this.scanner.nextLine();
        if(this.user.add(start, end, tag)){
            System.out.println("加入成功！");
        }
        else{
            System.out.println("加入失败：格式错误");
        }
    }
    public void runSystem(){
        this.printMenu();
        int choice;
        if(!this.scanner.hasNextInt()){
            this.scanner.nextLine();
            choice = -1;
        }
        else{
            choice = this.scanner.nextInt();
            this.scanner.nextLine();
        }
        switch(choice){
            case 1:{
                this.addItem();
                break;
            }
            case 2:{
                this.deleteItem();
                break;
            }
            case 3:{
                this.query();
                break;
            }
            case 4:{
                this.user.clear();
                break;
            }
            case 5:{
                this.show();
                break;
            }
            case 6:{
                Client.exit = true;
                break;
            }
            default:{
                System.out.println("错误的输入。");
            }
        }
    }
    public static void main(String[] args){
        Client client = new Client();
        while(!Client.exit&&!client.getInSystem());
        while(!Client.exit){
            client.runSystem();
        }
        System.out.println("bye~");

    }
}
