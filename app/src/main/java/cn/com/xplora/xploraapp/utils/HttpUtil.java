package cn.com.xplora.xploraapp.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yckj on 2016/4/14.
 */
public class HttpUtil  {

    private String TAG = "XPLORA";
    private String urlAddress;
    private URL url;
    private HttpURLConnection uRLConnection;

    public HttpUtil(String urlAddress){
        this.urlAddress = urlAddress;
    }

    //向服务器发送get请求
    public String doGet(String params){
        String getUrl = urlAddress;
        if(params!=null&&!"".equals(params.trim())){
            getUrl = getUrl+"?"+params;
        }
        try {
            Log.i(TAG,getUrl);
            url = new URL(getUrl);
            uRLConnection = (HttpURLConnection)url.openConnection();
            InputStream is = uRLConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String response = "";
            String readLine = null;
            while((readLine =br.readLine()) != null){
                //response = br.readLine();
                response = response + readLine;
            }
            is.close();
            br.close();
            uRLConnection.disconnect();
            return response;
        } catch (Exception e) {
            Log.i(TAG,e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //向服务器发送post请求
    public String doPost(String params){
        try {
            url = new URL(urlAddress);
            uRLConnection = (HttpURLConnection)url.openConnection();
            uRLConnection.setDoInput(true);
            uRLConnection.setDoOutput(true);
            uRLConnection.setRequestMethod("POST");
            uRLConnection.setUseCaches(false);
            uRLConnection.setInstanceFollowRedirects(false);
            uRLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            uRLConnection.connect();

            DataOutputStream out = new DataOutputStream(uRLConnection.getOutputStream());
            String content = params;
            out.writeBytes(content);
            out.flush();
            out.close();

            InputStream is = uRLConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String response = "";
            String readLine = null;
            while((readLine =br.readLine()) != null){
                //response = br.readLine();
                response = response + readLine;
            }
            is.close();
            br.close();
            uRLConnection.disconnect();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}