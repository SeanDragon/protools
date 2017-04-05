package model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class UserModel implements Pojo {
    private Integer id;
    private Long lvar;
    private String username;
    private List<String> list;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLvar() {
        return lvar;
    }

    public void setLvar(Long lvar) {
        this.lvar = lvar;
    }
}
