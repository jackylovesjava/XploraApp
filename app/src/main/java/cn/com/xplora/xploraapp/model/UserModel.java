package cn.com.xplora.xploraapp.model;

import java.util.Date;

/**
 * Created by yckj on 2016/4/14.
 */
public class UserModel extends BaseModel {

    private String userName;
    private String mobile;
    private String imageUrl;
    private String imageName;
    private String hobbyIds;
    private String hobby;
    private String hobbyEn;
    private int cityId;
    private String cityName;
    private String cityNameEn;
    private int followings;
    private int followers;
    private boolean newUser;
    private boolean logined;
    private Date lastLoginDate;
    private boolean autoPush;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getHobbyEn() {
        return hobbyEn;
    }

    public void setHobbyEn(String hobbyEn) {
        this.hobbyEn = hobbyEn;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public boolean isLogined() {
        return logined;
    }

    public void setLogined(boolean logined) {
        this.logined = logined;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getHobbyIds() {
        return hobbyIds;
    }

    public void setHobbyIds(String hobbyIds) {
        this.hobbyIds = hobbyIds;
    }

    public boolean isAutoPush() {
        return autoPush;
    }

    public void setAutoPush(boolean autoPush) {
        this.autoPush = autoPush;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }
}
