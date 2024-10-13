package com.aice.repo;

import java.util.Optional;

import com.aice.dao.DaoCompanyStaff;
import com.aice.dao.dnis.DaoCompanyDnis;
import org.apache.ibatis.annotations.Select;

import com.aice.dao.DaoCompany;
import org.springframework.stereotype.Repository;

/**
 * RepoCompany : tbl_company
 */
@Repository
public interface RepoCompany {

    @Select({"""
<script>
/*RepoCompany.selectCompanyById*/
select *
from tbl_company
where pk_company = #{pkCompany}
</script>
            """})
    Optional<DaoCompany> selectCompanyById(Long pkCompany);

    @Select({"""
<script>
/*RepoCompany.findMemberByCompanySeq*/
SELECT
    pk_company_staff
FROM tbl_company_staff
WHERE 1=1
AND fd_staff_level_code='A1001'
AND fk_company=#{companySeq}
</script>        
    """})
    DaoCompanyStaff findMemberByCompanySeq(Long companySeq);
}