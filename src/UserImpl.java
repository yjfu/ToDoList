import UserModule.User;
import UserModule.UserPOA;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yjfu on 2017/5/19.
 */
public class UserImpl extends UserPOA {
    private String name;
    private List<ItemInfo> items;

    public UserImpl(String name){
        this.name = name;
        readItems();
    }
    private void readItems(){
        File file = new File(this.name + ".items");
        if(file.exists()) {
            try {
                FileInputStream fin = new FileInputStream(this.name + ".items");
                ObjectInputStream oin = new ObjectInputStream(fin);
                this.items = (List<ItemInfo>) oin.readObject();
                oin.close();
                fin.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            this.items = new ArrayList<ItemInfo>();
        }
    }
    private void save(){
        try {
            FileOutputStream fout = new FileOutputStream(this.name + ".items");
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(items);
            oout.close();
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean add(String startTime, String endTime, String tag){
        //验证合法性
        if(startTime.matches(ItemInfo.dateRex)&&endTime.matches(ItemInfo.dateRex)){
            //格式合法
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd,hh:mm");
                Date st = simpleDateFormat.parse(startTime);
                Date et = simpleDateFormat.parse(endTime);
                if(st.after(et)){
                    //开始日期与结束日期颠倒
                    return false;
                }
                this.items.add(new ItemInfo(st, et, tag));
                this.save();
            }
            catch(Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean delete(String id){
        int num = Integer.parseInt(id);
        if(num<this.items.size()&&num>0)
            this.items.remove(num);
        else
            return false;

        this.save();
        return true;
    }
    @Override
    public  boolean clear(){
        this.items = new ArrayList<ItemInfo>();
        this.save();
        return true;
    }
    @Override
    public String query(String startTime, String endTime){
        //验证合法性
        if(startTime.matches(ItemInfo.dateRex)&&endTime.matches(ItemInfo.dateRex)){
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd,hh:mm");
                Date st = simpleDateFormat.parse(startTime);
                Date et = simpleDateFormat.parse(endTime);

                String res = "";
                for(int i = 0;i<this.items.size();i++){
                    ItemInfo ii = this.items.get(i);
                    if(ii.getStart().after(st)&&ii.getEnd().before(et)){
                        res += "编号：" + i +"\n"
                                +"开始时间：" + simpleDateFormat.format(ii.getStart()) + "\n"
                                +"结束时间：" + simpleDateFormat.format(ii.getEnd()) + "\n"
                                +"标签：" + ii.getTag() + "\n\n";
                    }
                }
                return res;
            }
            catch(Exception e){
                e.printStackTrace();
                return "时间格式出错！\n\n";
            }
        }
        else{
            return "时间格式出错！\n\n";
        }
    }
    @Override
    public String show(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd,hh:mm");
        String res = "";
        for(int i = 0;i<this.items.size();i++){
            ItemInfo ii = this.items.get(i);
            res += "编号：" + i +"\n"
                    +"开始时间：" + simpleDateFormat.format(ii.getStart()) + "\n"
                    +"结束时间：" + simpleDateFormat.format(ii.getEnd()) + "\n"
                    +"标签：" + ii.getTag() + "\n\n";
        }
        return res;
    }
}
