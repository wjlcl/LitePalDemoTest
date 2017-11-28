package exmaple.com.litepaldemo;


import org.litepal.crud.DataSupport;

/**
 * Created by lcl on 2017/11/13.
 */

public class Comment extends DataSupport {
    private int id;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
