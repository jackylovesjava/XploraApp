package cn.com.xplora.xploraapp.model;

import java.util.List;

/**
 * Created by yckj on 2016/4/14.
 */
public class EventModel extends BaseModel {

    private String title;
    private String titleEn;
    private String coverImageUrl;//封面
    private String dayOffset;//多少天后结束,多少天后开始
    private String dayOffsetEn;
    private double price;
    private String statusLabel;//显示状态：正在进行中，置顶
    private String statusLabelEn;
    private List<UserModel> attendeeList;
    private boolean likedByCurrentUser;
    private boolean featured;
    private int attendeeCount;
    private int likeCount;
    private String shortAddress;
    private String shortAddressEn;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getDayOffset() {
        return dayOffset;
    }

    public void setDayOffset(String dayOffset) {
        this.dayOffset = dayOffset;
    }

    public String getDayOffsetEn() {
        return dayOffsetEn;
    }

    public void setDayOffsetEn(String dayOffsetEn) {
        this.dayOffsetEn = dayOffsetEn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public String getStatusLabelEn() {
        return statusLabelEn;
    }

    public void setStatusLabelEn(String statusLabelEn) {
        this.statusLabelEn = statusLabelEn;
    }

    public List<UserModel> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(List<UserModel> attendeeList) {
        this.attendeeList = attendeeList;
    }

    public boolean isLikedByCurrentUser() {
        return likedByCurrentUser;
    }

    public void setLikedByCurrentUser(boolean likedByCurrentUser) {
        this.likedByCurrentUser = likedByCurrentUser;
    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public void setShortAddress(String shortAddress) {
        this.shortAddress = shortAddress;
    }

    public String getShortAddressEn() {
        return shortAddressEn;
    }

    public void setShortAddressEn(String shortAddressEn) {
        this.shortAddressEn = shortAddressEn;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
}
