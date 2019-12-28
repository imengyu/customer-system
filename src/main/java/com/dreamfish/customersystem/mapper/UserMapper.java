package com.dreamfish.customersystem.mapper;

import com.dreamfish.customersystem.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT id,name,password,name,state,code FROM t_users WHERE code = #{code}")
    List<User> findByUserCode(@Param("code") String code);
    @Update("UPDATE t_users SET state = #{state} FROM t_users WHERE code = #{code}")
    boolean updateUserStateByUserCode(@Param("state") boolean state, @Param("code") String code);
    @Select("SELECT id,name,state,code,head_img FROM t_users WHERE id = #{id}")
    User getUserById(@Param("id") int id);
}
