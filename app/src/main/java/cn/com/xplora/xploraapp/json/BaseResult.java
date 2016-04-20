package cn.com.xplora.xploraapp.json;

/**
 * Created by yckj on 2016/4/20.
 */
public class BaseResult {
    private boolean result;
    private String errorMsg;

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
