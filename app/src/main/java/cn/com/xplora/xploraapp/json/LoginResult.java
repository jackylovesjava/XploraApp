package cn.com.xplora.xploraapp.json;

import java.util.List;

import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.UserModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class LoginResult extends BaseResult {

    private UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
