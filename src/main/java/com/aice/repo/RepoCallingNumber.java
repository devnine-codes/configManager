package com.aice.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.aice.dao.bizphone.DaoBizPhone;

@Deprecated
@Mapper
@Repository
public interface RepoCallingNumber {

  List<DaoBizPhone> selectAll();

  List<DaoBizPhone> selectAllByCompanyId(Long companyId);

  void updateCallingNumber(DaoBizPhone req);

  void deleteCompanyCallingNumber(Long callingId);

  void insertCallingNumber(DaoBizPhone req);
}
