package com.dreamfish.customersystem.mapper;

import com.dreamfish.customersystem.entity.Customer;
import com.dreamfish.customersystem.entity.CustomerIndustry;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CustomerMapper {

    @Select("SELECT t_customers.*,t_industry.name as industryName FROM t_customers,t_industry WHERE t_customers.id = #{id} AND t_industry.id = t_customers.industry")
    Customer getCustomerById(@Param("id")Integer id);

    @Select("SELECT * FROM t_industry WHERE 1 LIMIT 32")
    List<CustomerIndustry> getCustomerIndustry();

    @Delete("Delete FROM t_customers WHERE id = #{id}")
    void deleteCustomerById(@Param("id")Integer id);
}
