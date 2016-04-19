package cn.com.xplora.xploraapp.model;

/**
 * Created by jackylovesjava on 16/4/19.
 */
public class TrendsetterModel extends BaseModel{
    private String imageUrl;
    private String imageName;
    private String userName;
    private String intro;
    private String introEn;
    private String userNameEn;
    private int followings;
    private int followers;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIntroEn() {
        return introEn;
    }

    public void setIntroEn(String introEn) {
        this.introEn = introEn;
    }

    public String getUserNameEn() {
        return userNameEn;
    }

    public void setUserNameEn(String userNameEn) {
        this.userNameEn = userNameEn;
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
}
