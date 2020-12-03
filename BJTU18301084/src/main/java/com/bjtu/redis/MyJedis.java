package com.bjtu.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.bjtu.redis.JsonStrTool.JsontoString;

public class MyJedis {
    String key="totalCnt";//设置围观人数的Key，value是进入时间
    private Jedis jedis;
    private static HashMap<String,HashMap> actions=new HashMap<>();
    private static HashMap<String,HashMap> counters=new HashMap<>();

    public MyJedis(){
        jedis=JedisInstance.getInstance().getResource();
        initActionsMap();
        initCountersMap();
    }
    public HashMap<String,HashMap> getactions(){
        return actions;
    }
    public HashMap<String,HashMap> getcounters(){
        return counters;
    }
    //展示当前人数
    public void showUserCount(){
        if(jedis.get(key)==null){
            System.out.println("主播不存在，无用户");
        }
        else{
            System.out.println("现在观众人数为："+jedis.get(key));
        }
    }
    //改变围观人数
    public void delUser(String value){
        //获取当前时间
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmm");
        String leaveTime =dateFormat.format(date);//value内容
        SimpleDateFormat argformat=new SimpleDateFormat("HHmmss");
        String arg=argformat.format(date);
        int leavearg=Integer.parseInt(arg);//zset第二个参数

        System.out.println("减少用户");
        int num=Integer.parseInt(value);//del的数量
        if(jedis.get(key)==null){
            System.out.println("主播不存在");
        }
        else if(Integer.parseInt(jedis.get(key))==0){
            System.out.println("现在围观用户为0，不能减少");
        }
        else if(Integer.parseInt(jedis.get(key))<num){
            System.out.println("现有围观人数"+jedis.get(key)+"人，减少人数不能大于现有人数");
        }
        else if(Integer.parseInt(jedis.get(key))>=num){
            for(int i=0;i<num;i++){
                jedis.decr(key);
                //存储离开的时间
                //list
                jedis.rpush("leaveList", leaveTime);
                //set
                jedis.sadd("leaveSet", leaveTime);
                //zset
                jedis.zadd("leaveZset",leavearg,leaveTime);
            }
        }
    }
    public void addUser(String value){
        //获取当前时间
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmm");
        String enterTime =dateFormat.format(date);//value内容
        SimpleDateFormat argformat=new SimpleDateFormat("HHmmss");
        String arg=argformat.format(date);
        int enterarg=Integer.parseInt(arg);//zset第二个参数

        System.out.println("增加用户");
        int num=Integer.parseInt(value);//add的数量
        if(jedis.get(key)==null) {
            jedis.set(key,value);
            for(int i=0;i<num;i++){
                //存储进入的时间
                //list
                jedis.rpush("enterList", enterTime);
                //set
                jedis.sadd("enterSet", enterTime);
                //zset
                jedis.zadd("enterZset",enterarg,enterTime);
            }
        }
        else {
            for(int i=0;i<num;i++) {
                jedis.incr(key);
                //存储进入的时间
                //list
                jedis.rpush("enterList", enterTime);
                //set
                jedis.sadd("enterSet", enterTime);
                //zset
                jedis.zadd("enterZset",enterarg,enterTime);
            }
        }
    }

    //获得某一时段内进来多少人，走了多少人
    public void showUserFreq(String stime){
        String[] re = stime.split(" ");
        String start=re[0];
        String end=re[1];

        List<String> enterlist = jedis.lrange("enterList",0,-1);//进入列表
        if(enterlist.size()==0){
            System.out.println("目前为止还没有进入过观众");
        }
        else{
            long enter1 =Long.valueOf(start), enter2 =Long.valueOf(end);
            int entertotal=0;
            if(enter2<enter1){
                System.out.println("输入格式不合法，不能显示enterList");
            }
            else {
                long elem;
                for(int i=0;i<enterlist.size();i++){
                    elem=Long.valueOf(enterlist.get(i));
                    if(elem>=enter1&&elem<=enter2){
                        entertotal++;
                    }
                }
                System.out.println(stime+"期间进入人数："+entertotal+"人");
            }
        }

        List<String> leavelist = jedis.lrange("leaveList",0,-1);//离开列表
        if(leavelist.size()==0){
            System.out.println("目前为止还没有离开过观众");
        }
        else{
            long leave1 =Long.valueOf(start), leave2 =Long.valueOf(end);
            int leavetotal =0;
            if(leave2 < leave1){
                System.out.println("输入格式不合法，不能显示leaveList");
            }
            else {
                long elem;
                for(int i=0;i<leavelist.size();i++){
                    elem=Long.valueOf(leavelist.get(i));
                    if(elem>= leave1 &&elem<= leave2){
                        leavetotal++;
                    }
                }
                System.out.println(stime+"期间离开人数："+ leavetotal +"人");
            }
        }
    }

    //获得并展示对应key的list中的内容
    public void ShowListAll(String listkey){
        List<String> list = jedis.lrange(listkey,0,-1);
        if(list.size()==0){
            System.out.println(listkey+"中暂无内容");
        }
        else{
            System.out.println(listkey+"中的内容为：");
            for(int i=0; i<list.size(); i++) {
                System.out.println(list.get(i));
            }
        }
    }
    //获得并展示对应key的set中的内容
    public void ShowSetAll(String setkey){
        Set<String> set = jedis.smembers(setkey);
        if(set.size()==0){
            System.out.println(setkey+"中暂无内容");
        }
        else{
            System.out.println(setkey+"中的内容为：");
            System.out.println(set);
        }
    }
    //获得并展示对应key的zset中的内容
    public void ShowZsetAll(String zsetkey){
        Set<String> zset = jedis.zrange(zsetkey,0,-1);
        if(zset.size()==0){
            System.out.println(zsetkey+"中暂无内容");
        }
        else{
            System.out.println(zsetkey+"中的内容为：");
            System.out.println(zset);
        }
    }
    /*public void delete(){
        jedis.zremrangeByRank("enterZset",0,-1);
        jedis.zremrangeByRank("leaveZset",0,-1);

        List<String> enterlist = jedis.lrange("enterList",0,-1);//进入列表
        for(int i=0;i<enterlist.size();i++){
            jedis.rpop("enterList");
        }
        List<String> leavelist = jedis.lrange("leaveList",0,-1);//进入列表
        for(int i=0;i<leavelist.size();i++){
            jedis.rpop("leaveList");
        }

        Set<String> set = jedis.smembers("enterSet");
        for(int i=0;i<set.size();i++){
            jedis.spop("enterSet");
        }
        Set<String> set2 = jedis.smembers("leaveSet");
        for(int i=0;i<set2.size();i++){
            jedis.spop("leaveSet");
        }
    }
*/
    public void initActionsMap(){
        if(actions!=null){
            actions.clear();
        }
        String s = JsontoString("src/main/resources/actions.json");
        JSONObject jsonobj = JSON.parseObject(s);//字符串转为json对象
        JSONArray array = jsonobj.getJSONArray("actions");//通过json对象构建JSONArray数组
        for (int i = 0; i < array.size(); i++) {//获取大数组中每一个json元素
            JSONObject elem = (JSONObject) array.get(i);
            HashMap<String, String> action = new HashMap<>();//每一个小action
            String name = (String) elem.get("name");
            action.put("name", name);
            String describe = (String) elem.get("describe");
            action.put("describe", describe);
            JSONArray a = elem.getJSONArray("show");//数组
            for (int m = 0; m < a.size(); m++) {
                JSONObject o = (JSONObject) a.get(m);
                String show = (String) o.get("counterName");
                action.put("show", show);
            }
            JSONArray b = elem.getJSONArray("operation");//数组
            if (b != null) {//有的没有operation，只是显示内容
                for (int n = 0; n < b.size(); n++) {
                    JSONObject o = (JSONObject) b.get(n);
                    String operation = (String) o.get("counterName");
                    action.put("operation", operation);
                }
            }
            actions.put(name, action);
        }
    }
    public void initCountersMap(){
        if(counters!=null){
            counters.clear();
        }
        String s = JsontoString("src/main/resources/counters.json");
        JSONObject jsonobj = JSON.parseObject(s);//字符串转为json对象
        JSONArray array = jsonobj.getJSONArray("counters");//通过json对象构建JSONArray数组
        for (int i = 0; i < array.size(); i++) {//获取大数组中每一个json元素
            JSONObject elem = (JSONObject) array.get(i);
            HashMap<String, String> counter = new HashMap<>();

            String counterName = (String) elem.get("counterName");
            counter.put("counterName", counterName);

            String counterIndex= (String) elem.get("counterIndex");
            counter.put("counterIndex", counterIndex);

            String type = (String) elem.get("type");
            counter.put("type", type);

            String keyFields = (String) elem.get("keyFields");
            counter.put("keyFields", keyFields);

            String valueFields = (String) elem.get("valueFields");
            if(valueFields!=null){
                counter.put("valueFields", valueFields);
            }

            String fields = (String) elem.get("fields");
            if(fields!=null){
                counter.put("fields", fields);
            }
            counters.put(counterName, counter);
        }
    }
}
