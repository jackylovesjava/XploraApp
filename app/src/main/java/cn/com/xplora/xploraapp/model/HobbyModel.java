package cn.com.xplora.xploraapp.model;

/**
 * Created by yckj on 2016/4/14.
 */
public class HobbyModel extends BaseModel {

    private String hobbyName;
    private String hobbyNameEn;
    private String imageUrl;
    private String imageName;

    private int selected;

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public String getHobbyNameEn() {
        return hobbyNameEn;
    }

    public void setHobbyNameEn(String hobbyNameEn) {
        this.hobbyNameEn = hobbyNameEn;
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

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

}
