package cn.com.xplora.xploraapp.model;

/**
 * Created by yckj on 2016/4/14.
 */
public class BaseModel {
    private int uuid;
    private int uuidInBack;
    private boolean result;
    private String errorMsg;

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public int getUuidInBack() {
        return uuidInBack;
    }

    public void setUuidInBack(int uuidInBack) {
        this.uuidInBack = uuidInBack;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
