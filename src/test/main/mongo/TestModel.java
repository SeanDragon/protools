package mongo;

import java.util.Date;

/**
 * Created by steven on 2016/12/21.
 */
public class TestModel {
    //主键
    private Long _id;
    private Integer id;
    //此条审核的类型
    private String checkType;
    //审核结果/状态
    private String checkResult;
    //审核日期
    private Date date;
    //审核人名称
    private String userName;
    //审核备注
    private String checkNote;

//    @ManyToOne(cascade = CascadeType.ALL, optional = false)
//    @JoinColumn(name = "loanId")
//    @NoExpose
//    private SubjectLoanModel loanModel;


    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCheckNote() {
        return checkNote;
    }

    public void setCheckNote(String checkNote) {
        this.checkNote = checkNote;
    }

    @Override
    public String toString() {
        return pro.tools.convert.ModelToJson(this);
    }
}