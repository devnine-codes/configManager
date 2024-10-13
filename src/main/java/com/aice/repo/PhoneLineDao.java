package com.aice.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.aice.dao.bizphone.DaoBizPhone;

@Deprecated
@Mapper
@Repository
public interface PhoneLineDao {
    List<DaoBizPhone> selectAll();

    DaoBizPhone selectPhoneLine(DaoBizPhone vo);

    List<DaoBizPhone> selectPhoneLineList();

    void insertPhoneLine(DaoBizPhone vo);

    void updateNumbers(DaoBizPhone vo);

    void deleteNumbers(DaoBizPhone vo);
}
