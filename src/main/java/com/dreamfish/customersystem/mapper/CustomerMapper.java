package com.dreamfish.customersystem.mapper;

import com.dreamfish.customersystem.entity.Customer;
import com.dreamfish.customersystem.entity.CustomerIndustry;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMapper {

    @Select("SELECT t_customers.*,t_industry.name as industryName FROM t_customers,t_industry WHERE t_customers.id = #{id} AND t_industry.id = t_customers.industry")
    Customer getCustomerById(@Param("id")Integer id);

    @Select("SELECT id FROM t_customers WHERE user_id = #{id}")
    List<Customer> getCustomersByUserId(@Param("id")Integer id);

    @Select("SELECT * FROM t_industry WHERE 1 LIMIT 32")
    List<CustomerIndustry> getCustomerIndustry();

    @Select("SELECT * FROM t_industry WHERE name = #{name}")
    CustomerIndustry getCustomerIndustryByName(@Param("name") String name);

    @Insert("INSERT INTO t_industry(name) VALUES(#{name})")
    void addCustomerIndustry(@Param("name") String name);

    @Delete("Delete FROM t_customers WHERE id = #{id}")
    void deleteCustomerById(@Param("id")Integer id);
}
