package com.aice.repo;

import com.aice.dao.DaoSecretary;
import com.aice.dao.secretary.SecretaryDao;
import com.aice.dto.DtoSecretary;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RepoSecretary : tbl_company_staff
 */

@Repository
public interface RepoSecretary {
    @Select({"""
<script>
/*RepoSecretary.selectAutoResponse*/
SELECT
    fk_company,
    pk_company_staff,
    auto_response_yn
FROM
    tbl_company_staff
WHERE 1=1
    AND fk_company = #{fkCompany}
    <if test='pkCompanyStaff != null and "".equals(pkCompanyStaff) == false'>
    AND pk_company_staff = #{pkCompanyStaff}
    </if>
</script>
            """})
    List<SecretaryDao> selectAutoResponse(SecretaryDao req);

    @Select({"""
<script>
/*RepoSecretary.searchAutoResponse*/
SELECT
    a.fk_company,
    b.fd_company_name,
    a.pk_company_staff,
    a.fd_staff_name,
    a.fd_staff_id,
    a.auto_response_yn,
    a.fd_staff_status_code
FROM
    tbl_company_staff a
JOIN
    tbl_company b ON a.fk_company = b.pk_company
WHERE 1=1
    <if test='fkCompany != null and "".equals(fkCompany) == false'>
    AND a.fk_company = #{fkCompany}
    </if>
    <if test='fdCompanyName != null and "".equals(fdCompanyName) == false'>
    AND b.fd_company_name = #{fdCompanyName}
    </if>
    <if test='pkCompanyStaff != null and "".equals(pkCompanyStaff) == false'>
    AND a.pk_company_staff = #{pkCompanyStaff}
    </if>
    <if test='fdStaffName != null and "".equals(fdStaffName) == false'>
    AND a.fd_staff_name = #{fdStaffName}
    </if>
    <if test='fdStaffId != null and "".equals(fdStaffId) == false'>
    AND a.fd_staff_id = #{fdStaffId}
    </if>
    <if test='autoResponseYn != null and "".equals(autoResponseYn) == false'>
    AND a.auto_response_yn = #{autoResponseYn}
    </if>
    
</script>
            """})
    List<SecretaryDao> searchAutoResponse(SecretaryDao req);

    @Update({"""
<script>
/*RepoSecretary.updateAutoResponse*/
UPDATE tbl_company_staff SET
	auto_response_yn = #{autoResponseYn}
WHERE 1=1
	AND fk_company = #{fkCompany}
	AND pk_company_staff = #{pkCompanyStaff}
</script>
	"""})
    void updateAutoResponse(SecretaryDao req);

    @Update({"""
<script>
/*RepoSecretary.updateResStatus*/
UPDATE tbl_company_staff SET
    fd_staff_response_status_code = #{responseStatusCode}
WHERE 1=1
    AND fk_company = #{fkCompany}
    AND fd_company_master_yn = 'Y'
</script>        
    """})
    void updateResStatus(SecretaryDao req);

    @Update({"""
<script>
/*RepoSecretary.updateAiWorkEnable*/
UPDATE tbl_ai_conf_work SET
    enable_yn = #{enableYn}
WHERE 1=1
    AND fk_company = #{fkCompany}
    AND fk_company_staff_ai = #{pkCompanyStaff}
</script>
    """})
    void updateAiWorkEnable(SecretaryDao req);

    @Select({"""
<script>
/*RepoSecretary.getAppMode*/
select
    acw.contact_ai_yn
    ,acw.logging_yn
    ,mrs.res_status
    ,mrs.disp_mode
from tbl_ai_conf_work acw, tbl_member_res_status mrs
where 1=1
and acw.ai_work_cd='CTGR1_HANDS'
and acw.fk_company = (select fk_company from tbl_company_staff where pk_company_staff = #{seqMember})
and mrs.seq_member = #{seqMember}
and mrs.solution_type = 'B14'
</script>        
    """})
    DtoSecretary findWorkAndStatusBySeqMember(Long seqMember);

    @Select({"""
<script>
SELECT
    res_status
    ,disp_mode
FROM tbl_member_res_status
WHERE seq_member = #{seqMember}
AND solution_type = 'B14'
</script>        
    """})
    DtoSecretary findAppModeBySeqMember(Long seqMember);

    @Update({"""
<script>
/*RepoSecretary.updateWorkInfo*/
UPDATE tbl_ai_conf_work SET
    contact_ai_yn = #{contactAiYn}
    ,logging_yn = #{loggingYn}
WHERE 1=1
   AND fk_company = (select fk_company from tbl_company_staff where pk_company_staff = #{seqMember})
   AND ai_work_cd='CTGR1_HANDS'
   AND use_yn = 'Y'
</script>
    """})
    void updateWorkInfo(
            DaoSecretary req
    );

    @Update({"""
<script>
/*RepoSecretary.updateMemberResStatus*/
UPDATE tbl_member_res_status SET
    res_status = #{resStatus}
    ,disp_mode = #{dispMode}
WHERE 1=1
    AND seq_member = #{seqMember}
    AND solution_type = 'B14'
</script>
    """})
    void updateMemberResStatus(
            DaoSecretary req
    );

    @Update({"""
<script>
/*RepoSecretary.updateCallFwdYn*/
UPDATE tbl_company_staff SET
    call_fwd_yn = #{callFwdYn}
WHERE 1=1
    AND pk_company_staff = #{seqMember}
</script>        
    """})
    void updateCallFwdYn(
            DaoSecretary req
    );

    @Select({"""
<script>
/*RepoSecretary.findMobileMainYn*/
SELECT
  mobile_main_yn
FROM tbl_conf_dial_ext_aihandy
WHERE 1=1
AND fk_company = (SELECT fk_company FROM tbl_company_staff WHERE pk_company_staff = #{seqMember})
AND dnis_type = 'aihandy'
</script>
    """})
    DtoSecretary findMobileMainYn(
            Long seqMember
    );

    /** appMode insert를 위한 임시 쿼리 */
    @Select({"""
<script>
/*RepoSecretary.findMasterByMember*/
SELECT
    solution_type
    ,fk_company
FROM tbl_company_staff
WHERE pk_company_staff = #{seqMember}
</script>
    """})
    SecretaryDao findMasterByMember(
            Long seqMember
    );
    @Select({"""
<script>
/*RepoSecretary.insertMemberStatus*/
INSERT INTO tbl_member_res_status
(solution_type, seq_member, res_status, disp_mode, fk_writer, fd_regdate)
VALUES
('B14', #{seqMember}, 'STATUS_ONLINE', 'AIHANDY_MODE_ONLINE', 0, now())
</script>        
    """})
    void insertMemberStatus(
            Long seqMember
    );
}