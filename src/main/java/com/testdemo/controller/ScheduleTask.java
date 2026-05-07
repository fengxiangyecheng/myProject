//package com.dbsun.controller;
//
//import com.dbsun.entity.system.PageData;
//import com.dbsun.mapper.CourseMapper;
//import com.dbsun.mapper.Sys_userMapper;
//import com.dbsun.mapper.Warn_mMapper;
//import com.dbsun.util.SendEamil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import javax.mail.MessagingException;
//import java.security.GeneralSecurityException;
//import java.text.SimpleDateFormat;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//import java.util.List;
//
//
//@Configuration      //主要用于标记配置类，兼备Component的效果。
//public class ScheduleTask {
//    //添加定时任务
//    @Scheduled(cron = "0 0/1 * * * ? ")
//    //或直接指定时间间隔，例如：5秒   周期从 0分钟开始，每 1分钟执行一次
//    // https://cron.ciding.cc/
//    //@Scheduled(fixedRate=5000)
//    private void configureTasks() {
//        System.out.println("定时检查发送任务已启动");
//        sendZY();
//
//    }
//
//
//    private void sendZY(String class_type_id) {
//        String str1 = "你有新的课程信息请注意查收";
//        PageData pageData = new PageData();
//        pageData.put("class_type_id", class_type_id);
//        List<PageData> list1 = _mapperSS.querySys_userKey(pageData);
//        for (int i = 0; i < list1.size(); i++) {
//            String username = list1.get(i).getString("USERNAME");
//            try {
//                new SendEamil().sendToken(username, "课程信息邮件", str1);
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            } catch (GeneralSecurityException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private Duration getTypeTime(String c_time) {
//		// 传入时间，判断时间是否达到发送范围时间内
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        String formattedDate = formatter.format(date);
//        //
//        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime time1 = LocalDateTime.parse(c_time, formatter1);
//        LocalDateTime time2 = LocalDateTime.parse(formattedDate, formatter1);
//        System.out.println(time1);
//        System.out.println(time2);
//        System.out.println("-------------------------------------");
//        return Duration.between(time1, time2);
//    }
//
//
//
//}
//
