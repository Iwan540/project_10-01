package com.springboot.springbootweb04.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.springbootweb04.entity.Notice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

public interface NoticeMapper extends BaseMapper<Notice> {

    @Select("select * from notice order by date desc")
    IPage<Notice> selectNewest(Page<Notice> page);

    @Select("select * from notice where date LIKE CONCAT('%', #{date}, '%')")
    IPage<Notice> selectByDate(Page<Notice> page, @Param("date") String date);

    // 如果你需要查询与特定日期匹配的记录总数
    @Select("SELECT COUNT(*) FROM notice WHERE date LIKE CONCAT('%', #{date}, '%')")
    int getTotalCountByDate(@Param("date") String date);

    @Delete("DELETE FROM notice WHERE nid = #{nid}")
    int deleteNoticeById(@Param("nid") Integer nid);

}
