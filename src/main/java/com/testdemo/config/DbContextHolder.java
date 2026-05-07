package com.testdemo.config;

import java.util.ArrayList;
import java.util.List;

/**
  * 作用：
  * 1、保存一个线程安全的DatabaseType容器
  */
public class DbContextHolder {
	
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();
    /*
     * 管理所有的数据源id;
     * 主要是为了判断数据源是否存在;
     */
    public static List<String> dataSourceIds = new ArrayList<String>();
             public static void setDatabaseType(DatabaseType type){
            	 contextHolder.set(type);
             }

            public static void clearDatabaseType(){
            	contextHolder.remove();
            }

             public static DatabaseType getDatabaseType(){
                return contextHolder.get();
            }
    
             /**
              * 判断指定DataSrouce当前是否存在
              *
              * @param dataSourceId
              * @return
              * @author SHANHY
              * @create  2016年1月24日
              */
             public static boolean containsDataSource(String dataSourceId){
                 return dataSourceIds.contains(dataSourceId);
             } 
             
}
