package com.test.html;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class JD_Address
{  
	/** 
     * @param args 
     */  
    public static void main(String[] args) {  
        // 读取原始json文件并进行操作和输出  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(  
                    "src/json/city.json"));// 读取原始json文件  
            BufferedWriter bw = new BufferedWriter(new FileWriter(  
                    "src/json/citynew.json"));// 输出新的json文件  
            String s = null, ws = null;  
//            while ((s = br.readLine()) != null) {  
//                 System.out.println(s);  
                try {  
                	JSONArray dataJson = new JSONArray(readJsonFile("src/json/city.json"));// 创建一个包含原始json串的json对象  
                    for (int i = 0; i < dataJson.length(); i++) {  
                        JSONObject info = dataJson.getJSONObject(i);// 获取features数组的第i个json对象  
                        if(info.has("children")) {
                        	JSONArray jsonItem = info.getJSONArray("children");
                        	 for(int j=0;j<jsonItem.length();j++) {
                        		 JSONObject infoItemOne = jsonItem.getJSONObject(j);
                        		 infoItemOne.put("value", infoItemOne.getString("label"));
                        		 if(jsonItem.getJSONObject(j).has("children")) {
                        			 JSONArray infoItem = infoItemOne.getJSONArray("children");
                            		 for(int x=0;x<infoItem.length();x++) {
                            			 JSONObject infoItems = infoItem.getJSONObject(x);
                            			 infoItems.put("value", infoItems.getString("label"));
                                     } 
                        		 } 
                             }
                        }
                        info.put("value", info.getString("label"));
                    }  
                    ws = dataJson.toString();  
                } catch (JSONException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            bw.write(ws);  
            bw.flush();  
            br.close();  
            bw.close();  
  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
    }  
    
    public static String readJsonFile(String filename) throws IOException {
        try (InputStream is = new FileInputStream(filename)) {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        }    
    }

}