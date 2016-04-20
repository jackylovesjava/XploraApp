package cn.com.xplora.xploraapp.model;

/**
 * Created by yckj on 2016/4/14.
 */
public class BaseModel {
    private int uuid;
    private int uuidInBack;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseModel baseModel = (BaseModel) o;

        return uuidInBack == baseModel.uuidInBack;

    }

    @Override
    public int hashCode() {
        return uuidInBack;
    }
}
