package sd.data.json;

import com.google.common.base.MoreObjects;

/**
 * 统一返回数据
 * author 陈李 on 2017/6/17.
 */
public class JsonResult<T> {

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean haveError;

    public String getCode() {
        return code;
    }

    public JsonResult setCode(String code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public JsonResult setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isHaveError() {
        return haveError;
    }

    public boolean getHaveError() {
        return haveError;
    }

    public JsonResult setHaveError(boolean haveError) {
        this.haveError = haveError;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("data", data)
                .add("haveError", haveError)
                .toString();
    }
}
