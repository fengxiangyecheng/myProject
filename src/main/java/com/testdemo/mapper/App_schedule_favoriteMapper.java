package com.testdemo.mapper;

import com.testdemo.entity.system.PageData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface App_schedule_favoriteMapper {

    int countFavorite(@Param("user_id") String userId, @Param("task_id") String taskId);

    int insertFavorite(PageData pd);

    int deleteFavorite(@Param("user_id") String userId, @Param("task_id") String taskId);
}
