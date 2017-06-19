package com.enerchu.ConnectWeb;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by samsung on 2017-06-04.
 */

public class ConnectWebforUpdate extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;
    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
            URL url = new URL("http://172.21.33.253:8080/test2Enerchu/UpdateServerFromAnd.jsp");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            sendMsg = setSendInfo(strings);
            osw.write(sendMsg);
            osw.flush();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "EUC-KR");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                parsingMsg();
            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }

    public String[] parsingMsg(){
        String data[] = new String[8];
        try {
            if (receiveMsg.length() == 0) {
                Log.i("통신 결과", receiveMsg + "Null Exception");
            } else {
                Log.i("통신 결과", receiveMsg);
                JSONObject jsonObject = new JSONObject(receiveMsg);
                JSONArray jsonArray = jsonObject.getJSONArray("DATA");

                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.i("parsing", jsonArray.getJSONObject(i).toString());
                    jsonObject = jsonArray.getJSONObject(i);
                    data[0] = jsonObject.getString("type");
                    data[1] = jsonObject.getString("userid");
                    data[2] = jsonObject.getString("tabid");
                    data[3] = jsonObject.getString("oninfos");
                    data[4] = jsonObject.getString("subtab1");
                    data[5] = jsonObject.getString("subtab2");
                    data[6] = jsonObject.getString("subtab3");
                    data[7] = jsonObject.getString("subtab4");

                    updateMultiTabInfoToAppDB(data);
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public String setSendInfo(String... strings){
        return "type="+strings[0]+"&userid="+strings[1]+"&tabid="+strings[2]+"&oninfos="+strings[3];

    }

    public void updateMultiTabInfoToAppDB(String[] data){

    }
}
