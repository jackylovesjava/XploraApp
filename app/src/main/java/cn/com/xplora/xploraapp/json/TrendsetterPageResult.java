package cn.com.xplora.xploraapp.json;

import java.util.List;

import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.TrendsetterModel;

/**
 * Created by yckj on 2016/4/14.
 */
public class TrendsetterPageResult extends BaseResult {

    private List<TrendsetterModel> trendsetterList;

    private int currentPage;
    private int pageSize;
    private int totalPage;
    private int totalCount;
    private int step;
    public List<TrendsetterModel> getTrendsetterList() {
        return trendsetterList;
    }

    public void setTrendsetterList(List<TrendsetterModel> trendsetterList) {
        this.trendsetterList = trendsetterList;
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
}
