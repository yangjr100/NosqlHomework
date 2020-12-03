package com.bjtu.redis;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] arg) throws Exception {
        MyJedis myjedis=new MyJedis();
        //设置文件监听
        FileMonitor fm = new FileMonitor(500);//设置监听周期
        fm.monitor("src/main/resources", new FileListener(myjedis)); //对目标文件夹添加监听
        fm.start();
        //获得hashmap
        HashMap<String,HashMap> actionsMap=myjedis.getactions();
        HashMap<String,HashMap> countersMap=myjedis.getcounters();
        //输出hashmap
        System.out.println(actionsMap);
        System.out.println(countersMap);
        HashMap<String,String> action,counter;
        showMenu();//显示选择菜单
        Scanner input=new Scanner(System.in);
        String choice=input.nextLine();//获取输入选项
        //清空原有内容
        //myjedis.delete();
        while (!choice.equals("0")){
            if(choice.equals("1"))
            {
                System.out.println("1.增加围观用户");
                action=actionsMap.get("ADD_USER");
                String oprname=(String)action.get("operation");
                counter=countersMap.get(oprname);
                String temp= (String) counter.get("valueFields");
                myjedis.addUser(temp);
                //展示增加后围观人数
                myjedis.showUserCount();
            }
            else if(choice.equals("2"))
            {
                System.out.println("2.减少围观用户");
                action=actionsMap.get("DEL_USER");
                String oprname=(String)action.get("operation");
                counter=countersMap.get(oprname);
                String temp= (String) counter.get("valueFields");
                myjedis.delUser(temp);
                //展示减少后围观人数
                myjedis.showUserCount();
            }
            else if(choice.equals("3")){
                System.out.println("3.显示当前总人数");
                myjedis.showUserCount();
            }
            else if(choice.equals("4"))
            {
                System.out.println("4.显示指定时段内的围观用户变动");
                action=actionsMap.get("SHOW_USER_FREQ");
                String oprname=(String)action.get("show");
                counter=countersMap.get(oprname);
                String temp= (String) counter.get("fields");
                myjedis.showUserFreq(temp);
            }
            else if(choice.equals("5"))
            {
                System.out.println("5.查看所有List");
                myjedis.ShowListAll("enterList");
                myjedis.ShowListAll("leaveList");
            }
            else if(choice.equals("6"))
            {
                System.out.println("6.查看所有Set");
                myjedis.ShowSetAll("enterSet");
                myjedis.ShowSetAll("leaveSet");
            }
            else if(choice.equals("7"))
            {
                System.out.println("7.查看所有ZSet");
                myjedis.ShowZsetAll("enterZset");
                myjedis.ShowZsetAll("leaveZset");
            }
            showMenu();
            choice=input.nextLine();//再次获取
        }
    }
    public static void showMenu(){
        //redis-server.exe redis.windows.conf
        System.out.println("==========MENU==========");
        System.out.println("1.新增围观用户");
        System.out.println("2.减少围观用户");
        System.out.println("3.显示当前总人数");
        System.out.println("4.显示指定时段内的围观用户变动");
        System.out.println("5.查看所有List");
        System.out.println("6.查看所有Set");
        System.out.println("7.查看所有ZSet");
        System.out.println("0.退出程序");
        System.out.println("请输入对应的操作序号：");
    }
}
