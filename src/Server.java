import CreatorModule.Creator;
import CreatorModule.CreatorHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.util.Properties;

/**
 * Created by yjfu on 2017/5/19.
 */
public class Server {
    private POA rootPOA;
    private NamingContextExt nc;

    public static void main(String[] args){
        Server server = new Server();
    }
    public Server(){
        init();
    }
    private void init(){
        //创建ORB
        Properties properties = new Properties();
        properties.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        properties.put("org.omg.CORBA.ORBInitialPort", "10001");
        properties.setProperty("com.sun.CORBA.codeset.charsets", "0x05010001, 0x00010109"); // UTF-8, UTF-16
        properties.setProperty("com.sun.CORBA.codeset.wcharsets", "0x00010109, 0x05010001"); // UTF-16, UTF-8
        String []args = {};
        ORB orb = ORB.init(args, properties);
        try {
            //激活POAManager
            //在orb中获取RootPOA的引用
            Object rootPOARef = orb.resolve_initial_references("RootPOA");
            //将object类型的引用声明为POA类型
            this.rootPOA = POAHelper.narrow(rootPOARef);
            //激活
            this.rootPOA.the_POAManager().activate();

            //在orb中获得名称管理器NameingContextExt
            Object ncObj = orb.resolve_initial_references("NameService");
            this.nc = NamingContextExtHelper.narrow(ncObj);

            //注册对象creator并且绑定名称，相当于在POA中声明creator这个变量
            //声明对象实体
            CreatorImpl creatorImpl = new CreatorImpl(this);
            //将对象注册到POA中
            Object creatorImplObj = this.rootPOA.servant_to_reference(creatorImpl);
            //确定creator对象的名字
            NameComponent path[] = this.nc.to_name("creator");
            this.nc.rebind(path, creatorImplObj);

            //服务器运行
            System.out.println("服务器开始运行！");
            orb.run();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void createUser(String name){
        try{
            UserImpl user = new UserImpl(name);
            Object userObj = this.rootPOA.servant_to_reference(user);
            NameComponent path[] = this.nc.to_name("user."+name);
            this.nc.rebind(path, userObj);
            System.out.println("对象注册成功！");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
