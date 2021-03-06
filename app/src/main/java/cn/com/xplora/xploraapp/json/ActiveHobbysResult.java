package cn.com.xplora.xploraapp.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.HobbyModel;
import cn.com.xplora.xploraapp.model.UserModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class ActiveHobbysResult extends BaseResult {

        private int currentPage;
        private int pageSize;

        private List<HobbyModel> hobbyList;

        public List<HobbyModel> getHobbyList() {
            return hobbyList;
        }

        public void setHobbyList(List<HobbyModel> hobbyList) {
            this.hobbyList = hobbyList;
        }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
