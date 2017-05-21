import java.io.Serializable;
import java.util.Date;

/**
 * Created by yjfu on 2017/5/19.
 */
public class ItemInfo implements Serializable {
    private Date start;
    private Date end;
    private String tag;
    static String dateRex = "\\d{4}-\\d{2}-\\d{2},\\d{2}:\\d{2}";

    public ItemInfo(Date start, Date end, String tag){
        this.start = start;
        this.end = end;
        this.tag = tag;
    }

    public Date getStart(){
        return this.start;
    }
    public Date getEnd(){
        return this.end;
    }
    public String getTag(){
        return this.tag;
    }

}
