package com.springboot.springbootweb04.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.springbootweb04.entity.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from sys_user where sys_user.email = #{email} and sys_user.passwd = #{passwd} limit 1")
    SysUser login(String email, String passwd);

    @Select(("select * from sys_user where email LIKE CONCAT('%',#{email},'%')"))
    List<SysUser> selectAllByEmail(String email);

    @Select(("select * from sys_user where sname LIKE CONCAT('%',#{sname},'%')"))
    List<SysUser> selectAllBySname(String sname);

    // 添加这个方法来插入新用户到数据库
    @Insert("INSERT INTO sys_user(upi, sname, email, passwd, priority) VALUES(#{upi}, #{sname}, #{email}, #{passwd}, #{priority})")
    int register(SysUser newUser);

    @Select("select * from sys_user where sys_user.upi = #{upi} limit 1")
    SysUser selectByUpi(String upi);








}
