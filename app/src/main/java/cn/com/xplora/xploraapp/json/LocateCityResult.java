package cn.com.xplora.xploraapp.json;

import java.util.List;

import cn.com.xplora.xploraapp.model.CityModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class LocateCityResult extends BaseResult {

    private CityModel cityModel;

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }
}
