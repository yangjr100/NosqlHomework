package com.bjtu.redis;

import java.io.*;

public class JsonStrTool {
    //读json,json转化为字符串
    public static String JsontoString(String path){
        String jsonStr = "";
        try{
            File file = new File(path);//打开并读取JSON文件，转化为字符串
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file), "Utf-8");
            int temp = 0;
            StringBuffer buffer = new StringBuffer();
            while ((temp = reader.read()) != -1) {
                buffer.append((char) temp);
            }
            fileReader.close();
            reader.close();
            jsonStr = buffer.toString();
            return jsonStr;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    //写json，字符串转化为json
    public void StringtoJson(String path,String str){
        File f=new File(path);
        try {
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            pw.write(str);
            fw.close();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
