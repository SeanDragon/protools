package redis;

import java.lang.management.ManagementFactory;

/**
 * Created by steven on 2016/11/25.
 */
public class TaskObject implements TaskInterface {
    private static final long serialVersionUID = 713743259L;

    private long id;
    private String content;

    public TaskObject(long _id, String _content){
        id = _id;
        content = _content;
    }

    @Override
    public int execute(Object... objs) {
        System.out.println("pid: "+ ManagementFactory.getRuntimeMXBean().getName()+", thread id:" + Thread.currentThread().getId() +", "+"TaskObject: " + id + " , " + content);
        return 0;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
