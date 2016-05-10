package cn.com.xplora.xploraapp.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.xplora.xploraapp.model.EventModel;
import cn.com.xplora.xploraapp.model.TrendsetterModel;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.AbDateUtil;

/**
 * 解析返回的JSON，得到ActiveCitiesResult对象
 * Created by yckj on 2016/4/14.
 */
public class FetchEventListResultJsonResolver extends BaseJsonResolver {

    public static FetchEventListResult parse(String response){
        FetchEventListResult result = new FetchEventListResult();
        try {
            JSONObject root = new JSONObject(response);
            boolean flag = root.getBoolean("result");
            String errorMsg = root.getString("errorMsg");
            if(!flag){
                result.setResult(false);
                result.setErrorMsg(errorMsg);
                return result;

            }else{
                result.setResult(true);
                List<EventModel> resultList = new ArrayList<EventModel>();
                int currentPage = root.getInt("currentPage");
                int pageSize = root.getInt("pageSize");
                int totalPage = root.getInt("totalPage");
                int totalCount = root.getInt("totalCount");
                int step = root.getInt("step");
                result.setCurrentPage(currentPage);
                result.setPageSize(pageSize);
                result.setTotalPage(totalPage);
                result.setTotalCount(totalCount);
                result.setStep(step);

                JSONArray array = root.getJSONArray("eventList");
                if(array!=null&&array.length()>0){

                    for(int i = 0;i<array.length();i++){
                        EventModel model = new EventModel();
                        JSONObject json = (JSONObject)array.opt(i);
                        String title = ignoreNullValue(json.getString("title"));
                        String titleEn = ignoreNullValue(json.getString("titleEn"));
                        String address = ignoreNullValue(json.getString("address"));
                        String addressEn = ignoreNullValue(json.getString("addressEn"));
                        String shortAddress = ignoreNullValue(json.getString("shortAddress"));
                        String shortAddressEn = ignoreNullValue(json.getString("shortAddressEn"));
                        String longitude = ignoreNullValue(json.getString("longitude"));
                        String latitude = ignoreNullValue(json.getString("latitude"));
                        String content = ignoreNullValue(json.getString("content"));
                        String contentEn = ignoreNullValue(json.getString("contentEn"));
                        String tips = ignoreNullValue(json.getString("tips"));
                        String tipsEn = ignoreNullValue(json.getString("tipsEn"));
                        double price = json.getDouble("price");
                        boolean featured = json.getBoolean("featured");
                        boolean likedByCurrentUser = json.getBoolean("likedByCurrentUser");
                        boolean attendeeByCurrentUser = json.getBoolean("attendeeByCurrentUser");
                        int attendeeCount = json.getInt("attendeeCount");
                        int likeCount = json.getInt("likeCount");

                        model.setTitle(title);
                        model.setTitleEn(titleEn);
                        model.setAttendeeCount(attendeeCount);
                        model.setShortAddress(shortAddress);
                        model.setShortAddressEn(shortAddressEn);
                        model.setPrice(price);
                        model.setLikeCount(likeCount);
                        model.setLikedByCurrentUser(likedByCurrentUser);
                        model.setFeatured(featured);

                        long endDateTime = json.getLong("endDateTime");
                        long startDateTime = json.getLong("startDateTime");
                        Date endDate = new Date(endDateTime);
                        Date startDate = new Date(startDateTime);
                        Date now = new Date();
                        long nowTime = now.getTime();
                        if(startDate.after(now)){
                        //未开始
                            int offsetDays = AbDateUtil.getOffectDay(startDateTime,nowTime);
                            if(offsetDays==0){//当天，算几个时后开始
                                int offsetHours = AbDateUtil.getOffectHour(startDateTime,nowTime);
                                if(offsetHours==0){//一小时内
                                    int offsetMinute = AbDateUtil.getOffectMinutes(startDateTime,nowTime);
                                    String daysOffset = offsetMinute + "分后开始";
                                    String daysOffsetEn = "Starts "+offsetMinute+" later";
                                    model.setDayOffset(daysOffset);
                                    model.setDayOffsetEn(daysOffsetEn);
                                }else{
                                    String daysOffset = offsetHours + "小时后开始";
                                    String daysOffsetEn = "Starts "+offsetHours+" hours later";
                                    model.setDayOffset(daysOffset);
                                    model.setDayOffsetEn(daysOffsetEn);
                                }
                            }
                            if(offsetDays<30){ //一个月内显示 多少天后开始
                                String daysOffset = offsetDays + "天后开始";
                                String daysOffsetEn = "Starts "+offsetDays+" days later";
                                model.setDayOffset(daysOffset);
                                model.setDayOffsetEn(daysOffsetEn);
                            }else {
                                int startYear = AbDateUtil.getYear(startDate);
                                int nowYear = AbDateUtil.getYear(now);
                                if(startYear>nowYear){//显示年份
                                    String startDateStr = AbDateUtil.getStringByFormat(startDate,"yyyy-MM-dd");
                                    String daysOffset = startDateStr + "开始";
                                    String daysOffsetEn = "Starts on "+startDateStr;
                                    model.setDayOffset(daysOffset);
                                    model.setDayOffsetEn(daysOffsetEn);
                                }else{//不用显示年份
                                    String startDateStr = AbDateUtil.getStringByFormat(startDate,"MM-dd");
                                    String daysOffset = startDateStr + "开始";
                                    String daysOffsetEn = "Starts on "+startDateStr;
                                    model.setDayOffset(daysOffset);
                                    model.setDayOffsetEn(daysOffsetEn);
                                }
                            }
                        }else{
                            if (endDate.after(now)){
                            //未结束

                                String statusLabel = "正在进行中";
                                String statusLabelEn = "Happening";

                                model.setStatusLabel(statusLabel);
                                model.setStatusLabelEn(statusLabelEn);

                                int offsetDays = AbDateUtil.getOffectDay(endDateTime,nowTime);
                                if(offsetDays==0){//当天，算几个时后结束
                                    int offsetHours = AbDateUtil.getOffectHour(endDateTime,nowTime);
                                    if(offsetHours==0){//一小时内
                                        int offsetMinute = AbDateUtil.getOffectMinutes(endDateTime,nowTime);
                                        String daysOffset = offsetMinute + "分后结束";
                                        String daysOffsetEn = "Ends "+offsetMinute+" later";
                                        model.setDayOffset(daysOffset);
                                        model.setDayOffsetEn(daysOffsetEn);
                                    }else{
                                        String daysOffset = offsetHours + "小时后结束";
                                        String daysOffsetEn = "Ends "+offsetHours+" hours later";
                                        model.setDayOffset(daysOffset);
                                        model.setDayOffsetEn(daysOffsetEn);
                                    }
                                }
                                if(offsetDays<30){ //一个月内显示 多少天后开始
                                    String daysOffset = offsetDays + "天后开始";
                                    String daysOffsetEn = "Ends "+offsetDays+" days later";
                                    model.setDayOffset(daysOffset);
                                    model.setDayOffsetEn(daysOffsetEn);
                                }else {
                                    int endYear = AbDateUtil.getYear(endDate);
                                    int nowYear = AbDateUtil.getYear(now);
                                    if(endYear>nowYear){//显示年份
                                        String endDateStr = AbDateUtil.getStringByFormat(endDate,"yyyy-MM-dd");
                                        String daysOffset = endDateStr + "结束";
                                        String daysOffsetEn = "Ends on "+endDateStr;
                                        model.setDayOffset(daysOffset);
                                        model.setDayOffsetEn(daysOffsetEn);
                                    }else{//不用显示年份
                                        String endDateStr = AbDateUtil.getStringByFormat(endDate,"MM-dd");
                                        String daysOffset = endDateStr + "结束";
                                        String daysOffsetEn = "Ends on "+endDateStr;
                                        model.setDayOffset(daysOffset);
                                        model.setDayOffsetEn(daysOffsetEn);
                                    }
                                }
                            }else{
                                //已结束
                                String daysOffset = "活动已结束";
                                String daysOffsetEn = "Finished";
                                model.setDayOffsetEn(daysOffsetEn);
                                model.setDayOffset(daysOffset);
                            }
                        }
                        int uuidInBack = json.getInt("uuid");
                        model.setUuidInBack(uuidInBack);
                        JSONArray eventImageArray = json.getJSONArray("eventImageList");
                        if(eventImageArray!=null&&eventImageArray.length()>0){
                            JSONObject coverImageJson  = (JSONObject)eventImageArray.opt(0);
                            String coverImageUrl = coverImageJson.getString("imageUrl");
                            model.setCoverImageUrl(coverImageUrl);
                        }
                        JSONArray attendeeArray = json.getJSONArray("eventAttendeeList");
                        if(attendeeArray!=null&&attendeeArray.length()>0){

                            List<UserModel> attendeeList = new ArrayList<UserModel>();
                            if(attendeeArray.length()>5){//只显示5个
                                for(int j = 0;j<5;j++){
                                    JSONObject attendeeJson = attendeeArray.optJSONObject(j);
                                    JSONObject attendeeUserJson = attendeeJson.getJSONObject("userModel");
                                    String attendeeUserImageUrl = attendeeUserJson.getString("imageUrl");
                                    UserModel attendee = new UserModel();
                                    attendee.setImageUrl(attendeeUserImageUrl);
                                    attendeeList.add(attendee);
                                }
                            }else{
                                for(int j = 0;j<attendeeArray.length();j++){
                                    JSONObject attendeeJson = attendeeArray.optJSONObject(j);
                                    JSONObject attendeeUserJson = attendeeJson.getJSONObject("userModel");
                                    String attendeeUserImageUrl = attendeeUserJson.getString("imageUrl");
                                    UserModel attendee = new UserModel();
                                    attendee.setImageUrl(attendeeUserImageUrl);
                                    attendeeList.add(attendee);
                                }
                            }

                            model.setAttendeeList(attendeeList);

                        }

//
                        resultList.add(model);
                    }

                }
                result.setEventList(resultList);
                return result;


            }
        }catch (Exception ex){
            result.setErrorMsg("SYSTEM ERROR");
            result.setResult(false);
            ex.printStackTrace();
            return result;
        }
    }
}
