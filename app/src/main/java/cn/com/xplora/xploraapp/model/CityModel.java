package cn.com.xplora.xploraapp.model;

import java.util.Date;

/**
 * Created by yckj on 2016/4/14.
 */
public class CityModel extends BaseModel {

    private String cityName;
    private String cityNameEn;
    private String imageUrl;
    private String imageName;


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
}
