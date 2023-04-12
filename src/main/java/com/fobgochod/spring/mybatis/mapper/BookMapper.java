package com.fobgochod.spring.mybatis.mapper;

import com.fobgochod.spring.mybatis.domain.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMapper {

    Book findById(@Param("id") int id);
}
