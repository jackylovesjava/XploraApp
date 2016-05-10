package cn.com.xplora.xploraapp.json;

import java.util.List;

import cn.com.xplora.xploraapp.model.HobbyModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class HobbyFilterResult extends BaseResult {

        private List<HobbyModel> hobbyList;

        public List<HobbyModel> getHobbyList() {
            return hobbyList;
        }

        public void setHobbyList(List<HobbyModel> hobbyList) {
            this.hobbyList = hobbyList;
        }

}
