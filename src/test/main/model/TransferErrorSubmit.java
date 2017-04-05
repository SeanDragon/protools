package model;

import org.bson.types.ObjectId;
import pro.mojo.mongo.annotation.Collection;

import java.io.Serializable;
import java.util.Date;

/**
 * 转账过程中 request状态码不是200或者返回文本长度为0或者转账过程途中抛出异常,都会添加一条记录
 */
@Collection(name = "pay_submit_transfer_error")
public class TransferErrorSubmit implements Serializable {

    private ObjectId _id;
    private String LoanJsonList;//转装列表
    private String PlatformMoneymoremore;//平台乾多多标识
    private String TransferAction;//转账类型
    private String Action;//操作类型
    private String TransferType;//转账方式
    private String NeedAudit;//通过是否需要审核
    private String DelayTransfer;//是否半自动批处理
    private String RandomTimeStamp;//随机时间戳
    private String Remark1;//自定义备注
    private String Remark2;//自定义备注
    private String Remark3;//自定义备注
    private String ReturnURL;//页面返回网址
    private String NotifyURL;//后台通知网址
    private String SignInfo;//签名信息
    private Date date;

    private String errMsg;//错误信息

    private String OrderNo;
    private String BatchNo;

    private Integer type; // 1 == loan  2 == financial

    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getNeedAudit() {
        return NeedAudit;
    }

    public void setNeedAudit(String needAudit) {
        NeedAudit = needAudit;
    }

    public String getLoanJsonList() {
        return LoanJsonList;
    }

    public void setLoanJsonList(String loanJsonList) {
        LoanJsonList = loanJsonList;
    }

    public String getPlatformMoneymoremore() {
        return PlatformMoneymoremore;
    }

    public void setPlatformMoneymoremore(String platformMoneymoremore) {
        PlatformMoneymoremore = platformMoneymoremore;
    }

    public String getTransferAction() {
        return TransferAction;
    }

    public void setTransferAction(String transferAction) {
        TransferAction = transferAction;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getTransferType() {
        return TransferType;
    }

    public void setTransferType(String transferType) {
        TransferType = transferType;
    }

    public String getDelayTransfer() {
        return DelayTransfer;
    }

    public void setDelayTransfer(String delayTransfer) {
        DelayTransfer = delayTransfer;
    }

    public String getRandomTimeStamp() {
        return RandomTimeStamp;
    }

    public void setRandomTimeStamp(String randomTimeStamp) {
        RandomTimeStamp = randomTimeStamp;
    }

    public String getRemark1() {
        return Remark1;
    }

    public void setRemark1(String remark1) {
        Remark1 = remark1;
    }

    public String getRemark2() {
        return Remark2;
    }

    public void setRemark2(String remark2) {
        Remark2 = remark2;
    }

    public String getRemark3() {
        return Remark3;
    }

    public void setRemark3(String remark3) {
        Remark3 = remark3;
    }

    public String getReturnURL() {
        return ReturnURL;
    }

    public void setReturnURL(String returnURL) {
        ReturnURL = returnURL;
    }

    public String getNotifyURL() {
        return NotifyURL;
    }

    public void setNotifyURL(String notifyURL) {
        NotifyURL = notifyURL;
    }

    public String getSignInfo() {
        return SignInfo;
    }

    public void setSignInfo(String signInfo) {
        SignInfo = signInfo;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    @Override
    public String toString() {
        return pro.cg.convert.ModelToJson(this);
    }
}
