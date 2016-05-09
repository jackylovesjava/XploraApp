package cn.com.xplora.xploraapp.json;

import java.util.List;

import cn.com.xplora.xploraapp.model.EventModel;
import cn.com.xplora.xploraapp.model.HobbyModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class FetchEventListResult extends BaseResult {

    private int currentPage;
    private int pageSize;
    private int totalPage;
    private int totalCount;
    private int step;

    private int cityId;
    private int districtId;
    private int hobbyId;
    private String dateline;

    private List<EventModel> eventList;


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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public List<EventModel> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventModel> eventList) {
        this.eventList = eventList;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(int hobbyId) {
        this.hobbyId = hobbyId;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }
}
