package com.aice.repo;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.aice.dao.DaoCompanyStaff;

@Deprecated
@Mapper
@Repository
public interface RepoStaff {

    /**
     * 특정 직원 조회
     *
     * @param pkCompanyStaff
     * @return
     */
    Optional<DaoCompanyStaff> findByStaffId(long pkCompanyStaff);
}
