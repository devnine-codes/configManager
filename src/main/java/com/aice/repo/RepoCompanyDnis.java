package com.aice.repo;

import java.util.List;
import java.util.Optional;

import com.aice.dao.DaoCompanyStaff;
import com.aice.dao.dnis.DaoCompanyDnisCamelCase;
import org.springframework.stereotype.Repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.aice.dao.dnis.DaoCompanyDnis;

/**
 * RepoCompanyDnis : tbl_company_dnis
 */

@Repository
public interface RepoCompanyDnis {
    @Select({"""
<script>
/*RepoCompanyDnis.findById*/
select
    *
from tbl_company_dnis
where pk_company_dnis=#{pkCompanyDnis}
</script>
    """})
    Optional<DaoCompanyDnis> findById(Long pkCompanyDnis);

    List<DaoCompanyDnis> findByFkCompany(Long fkCompany);
    List<DaoCompanyDnis> findByFkCompanyOrderByPkCompanyDnisDesc(Long fkCompany);


    @Select({"""
<script>
/*RepoCompanyDnis.findDnis*/
select
    *
from tbl_company_dnis
where fd_dnis=#{fdDnis}
</script>
    """})
    Boolean checkForDnis(DaoCompanyDnis req);

    //boolean existsByPkCompanyDnisAndFkCompanyStaffAiAndFdDnis(Long pkCompanyDnis, Long fkCompanyStaffAi, String fdDnis);

    //@Transactional
    //Long deleteByFkCompanyAndFkCompanyStaffAiAndFdDnis(Long fkCompany, Long fkCompanyStaffAi, String fdDnis);

    /*
    @Query(
        nativeQuery=true,
        value="""
            delete from tbl_company_dnis
            where 1=1
            and fk_company = :fkCompany
            and fk_company_staff_ai = :fkCompanyStaffAi
            and fd_dnis = :fdDnis
        """)
    */
    
    @Insert({"""
<script>
/*RepoCompanyDnis.createDnis*/
insert into tbl_company_dnis (
    solution_type
    ,user_type
    ,fk_company
    ,fk_company_staff
    ,fk_company_staff_ai
    ,dnis_type
    ,use_category
    ,fwd_num
    ,fd_dnis
    ,full_dnis
    ,vgw_id
    ,default_yn
    ,interj_yn
    ,inbound_yn
    ,outbound_yn
    ,vgw_auth_pw
    ,fd_bound_io
    ,fd_use_yn
    ,fd_regdate
) values (
    ifnull(#{solutionType},'B11')
    ,ifnull(#{userType},'B2001')
    ,#{fkCompany}
    ,#{fkCompanyStaff}
    ,#{fkCompanyStaffAi}
    ,#{dnisType}
    ,ifnull(#{useCategory},'workcenter')
    ,#{fwdNum}
    ,#{fdDnis}
    ,#{fullDnis}
    ,#{vgwId}
    ,ifnull(#{defaultYn},'Y')
    ,ifnull(#{interjYn}, 'N')
    ,ifnull(#{inboundYn},'Y')
    ,ifnull(#{outboundYn},'Y')
    ,#{vgwAuthPw}
    ,ifnull(#{fdBoundIo}, 'I')
    ,ifnull(#{fdUseYn}, 'Y')
    ,now()
)
</script>
    """})
    @Options(useGeneratedKeys = true, keyProperty = "pkCompanyDnis")
    void createDnis(DaoCompanyDnis req);

    @Select({"""
<script>
/*RepoCompanyDnis.findAllDnis*/
select
    pk_company_dnis
    ,solution_type
    ,case
        when solution_type='B11' then '워크센터'
        when solution_type='B12' then '메타휴먼'
        when solution_type='B13' then '스튜디오'
        when solution_type='B14' then '손비서'
        when solution_type='B15' then '통합브랜드'
    end solution_type_name
    ,user_type
    ,case
        when user_type='B2001' then '회사'
        when user_type='B2002' then '개인'
    end user_type_name
    ,fk_company
    ,fk_company_staff
    ,fk_company_staff_ai
    ,dnis_type AS numberType
    ,fwd_num
    ,fd_dnis
    ,full_dnis
    ,vgw_id
    ,default_yn
    ,interj_yn
    ,inbound_yn
    ,outbound_yn
    ,vgw_auth_pw
    ,fd_bound_io
    ,fd_use_yn
    ,date_format(fd_regdate,'%Y-%m-%d %T') fd_regdate
    ,date_format(fd_moddate,'%Y-%m-%d %T') fd_moddate
from
    tbl_company_dnis
where 1=1
    <if test='fkCompany != null and \"\".equals(fkCompany) == false'>
    and fk_company=#{fkCompany}
    </if>
    <if test='fdDnis != null and \"\".equals(fdDnis) == false'>
    and fd_dnis=#{fdDnis}
    </if>
    <if test='fullDnis != null and \"\".equals(fullDnis) == false'>
    and full_dnis=#{fullDnis}
    </if>
    <if test='solutionType != null and \"\".equals(solutionType) == false'>
    and solution_type=#{solutionType}
    </if>
    <if test='userType != null and \"\".equals(userType) == false'>
    and user_type=#{userType}
    </if>
    <if test='vgwId != null and \"\".equals(vgwId) == false'>
    and vgw_id=#{vgwId}
    </if>
</script>
    """})
    List<DaoCompanyDnis> findAll(DaoCompanyDnis req);

    @Update({"""
<script>
/*RepoCompanyDnis.updateDnis*/
update tbl_company_dnis set
    <if test='aiStaffTo != null and \"\".equals(aiStaffTo) == false'>
    fk_company_staff_ai=#{aiStaffTo},
    </if>
    fd_moddate=now()
where 1=1
    and fk_company=#{fkCompany}
    <if test='aiStaffFrom != null and \"\".equals(aiStaffFrom) == false'>
    and fk_company_staff_ai=#{aiStaffFrom}
    </if>
    and fd_use_yn='Y'
</script>
    """})
    void updateDnis2(DaoCompanyDnis req);

    @Update({"""
<script>
/*RepoCompanyDnis.updateDnis*/
update tbl_company_dnis set
    <if test='aiStaffTo != null and \"\".equals(aiStaffTo) == false'>
    fk_company_staff_ai=#{aiStaffTo},
    </if>
    fd_moddate=now()
where 1=1
    and fk_company=#{fkCompany}
    <if test='aiStaffFrom != null and \"\".equals(aiStaffFrom) == false'>
    and fk_company_staff_ai=#{aiStaffFrom}
    </if>
    and fd_use_yn='Y'
</script>
    """})
    void updateDnis(DaoCompanyStaff req);

    @Delete({"""
<script>
/*RepoCompanyDnis.deleteDnis*/
delete from tbl_company_dnis
where 1=1
    and fk_company=#{fkCompany}
    and fk_company_staff_ai=#{fkCompanyStaffAi}
    and fd_dnis=#{fdDnis}
    and full_dnis=#{fullDnis}
</script>
    """})
    void deleteDnis(Long fkCompany, Long fkCompanyStaffAi, String fdDnis, String fullDnis);

    @Select({"""
<script>
/*RepoCompanyDnis.findByStaff*/
SELECT
    *
FROM tbl_company_dnis
WHERE 1=1
    AND fk_company_staff_ai = #{fkCompanyStaffAi}
</script>        
    """})
    Optional<DaoCompanyDnis> findByStaff(DaoCompanyDnis req);

    @Select({"""
<script>
/*RepoCompanyDnis.findByFullDnis*/
SELECT
    *
FROM tbl_company_dnis
WHERE 1=1
    AND full_dnis = #{fullDnis}
</script>        
    """})
    Optional<DaoCompanyDnis> findByFullDnis(String fullDnis);

    @Select({"""
<script>
/*RepoCompanyDnis.listDnisByCompany*/
SELECT *
FROM tbl_company_dnis
WHERE 1=1
    AND fk_company = #{fkCompany}
    <if test='useCategory != null and \"\".equals(useCategory) == false'>
        AND use_category = #{useCategory}
    </if>
    AND fd_use_yn = 'Y'
</script>
    """})
    List<DaoCompanyDnisCamelCase> listDnisByCompany(Long fkCompany, String useCategory);
}