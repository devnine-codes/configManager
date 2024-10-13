package com.aice.repo;

import com.aice.dao.extension.DaoExtension;
import com.aice.dao.extension.DaoExtensionOb;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoExtension {
    @Insert({"""
<script>
/*RepoExtension.createExtension*/
INSERT into tbl_conf_dial_ext_aihandy (
    fk_company
    ,fk_company_staff_ai
    ,dnis
    ,ext_num
    ,dial_num
    ,full_dnis
    ,dnis_type
    ,vgw_auth_pw
    ,use_yn
    ,fk_writer
    ,fd_regdate
) VALUES (
    #{fkCompany}
    ,#{fkCompanyStaffAi}
    ,#{dnis}
    ,#{extNum}
    ,#{dialNum}
    ,#{fullDnis}
    ,#{dnisType}
    ,#{vgwAuthPw}
    ,'Y'
    ,#{fkWriter}
    ,now()
)
</script>
    """})
    @Options(useGeneratedKeys = true, keyProperty = "pkConfDialExtAihandy")
    void createExtension(DaoExtension req);

    @Select({"""
<script>
/*RepoExtension.findOneExtension*/
SELECT
    pk_conf_dial_ext_aihandy
    ,fk_company
    ,fk_company_staff_ai
    ,dnis
    ,ext_num
    ,dial_num
    ,full_dnis
    ,dnis_type
    ,use_category
    ,mobile_main_yn
    ,vgw_auth_pw
    ,use_yn
    ,fk_writer
    ,date_format(fd_regdate,'%Y-%m-%d %T') fd_regdate
    ,fk_modifier
    ,date_format(fd_moddate,'%Y-%m-%d %T') fd_moddate
FROM
    tbl_conf_dial_ext_aihandy
WHERE 1=1
    <if test='extNum != null and "".equals(extNum) == false'>
    AND ext_num = #{extNum}
    </if>
    <if test='dialNum != null and "".equals(dialNum) == false'>
    AND dial_Num = #{dialNum}
    </if>
    <if test='fkCompany != null and "".equals(fkCompany) == false'>
    AND fk_company = #{fkCompany}
    </if>
    <if test='fkCompanyStaffAi != null and "".equals(fkCompanyStaffAi) == false'>
    AND fk_company_staff_ai = #{fkCompanyStaffAi}
    </if>
    AND dnis_type = 'aihandy'
   LIMIT 1
</script>
    """})
    DaoExtension findOneExtension(DaoExtension req);

    @Select({"""
<script>
/*RepoExtension.findExtension*/
SELECT
    tcde.pk_conf_dial_ext_aihandy
    ,tcde.fk_company
    ,tcde.fk_company_staff_ai
    ,tcde.dnis
    ,tcde.ext_num
    ,tcde.dial_num
    ,tcde.full_dnis
    ,tcde.dnis_type
    ,tcde.use_category
    ,tcde.mobile_main_yn
    ,tcde.vgw_auth_pw
    ,tcde.use_yn
    ,tcs.call_fwd_yn
FROM tbl_conf_dial_ext_aihandy tcde
JOIN tbl_company_staff tcs ON tcs.fk_company = tcde.fk_company
WHERE 1=1
    AND ext_num = #{extNum}
    AND tcs.fd_company_master_yn = 'Y'
LIMIT 1 
</script>
    """})
    DaoExtension findExtension(String extNum);

    @Select({"""
<script>
/*RepoExtension.findAllExtension*/
SELECT
    tcde.pk_conf_dial_ext_aihandy
    ,tcde.fk_company
    ,tcde.fk_company_staff_ai
    ,tcde.dnis
    ,tcde.ext_num
    ,tcde.dial_num
    ,tcde.full_dnis
    ,tcde.dnis_type
    ,tcde.use_category
    ,tcde.mobile_main_yn
    ,tcde.vgw_auth_pw
    ,tcde.use_yn
    ,tcde.fk_writer
    ,date_format(tcde.fd_regdate,'%Y-%m-%d %T') fd_regdate
    ,tcde.fk_modifier
    ,date_format(tcde.fd_moddate,'%Y-%m-%d %T') fd_moddate
    ,tcs.call_fwd_yn
FROM tbl_conf_dial_ext_aihandy tcde
JOIN tbl_company_staff tcs ON tcs.fk_company = tcde.fk_company
WHERE 1=1
    <if test='pkConfDialExtAihandy != null and "".equals(pkConfDialExtAihandy) == false'>
    AND tcde.pk_conf_dial_ext_aihandy = #{pkConfDialExtAihandy}
    </if>
    <if test='fkCompany != null and "".equals(fkCompany) == false'>
    AND tcde.fk_company = #{fkCompany}
    </if>
    <if test='fkCompanyStaffAi != null and "".equals(fkCompanyStaffAi) == false'>
    AND tcde.fk_company_staff_ai = #{fkCompanyStaffAi}
    </if>
    <if test='dnis != null and "".equals(dnis) == false'>
    AND tcde.dnis = #{dnis}
    </if>
    <if test='extNum != null and "".equals(extNum) == false'>
    AND tcde.ext_num = #{extNum}
    </if>
    <if test='dialNum != null and "".equals(dialNum) == false'>
    AND tcde.dial_Num = #{dialNum}
    </if>
    <if test='fullDnis != null and "".equals(fullDnis) == false'>
    AND tcde.full_dnis = #{fullDnis}
    </if>
    AND tcs.fd_company_master_yn = 'Y'
</script>
    """})
    List<DaoExtension> findAllExtension(DaoExtension req);

    @Select({"""
<script>
/*RepoExtension.findOneByDialNum*/
SELECT
    *
FROM tbl_conf_dial_ext_aihandy
WHERE dial_num = #{dialNum}
AND dnis_type = #{dnisType}
</script>
            """})
    Optional<DaoExtension> findOneByDialNum(DaoExtension req);

/** UPDATE는 좀 더 고려해야함 */
    @Update({"""
<script>
/*RepoExtension.updateExtension*/
UPDATE tbl_conf_dial_ext_aihandy set
    fd_moddate=now()
WHERE 1=1
    and fd_use_yn='Y'
    and fk_company=#{fkCompany}
    and fk_company_staff_ai=#{fkCompanyStaffAi}
    and dnis = #{fdDnis}
</script>
    """})
    void updateExtension(DaoExtension req);

    @Delete({"""
<script>
/*RepoExtension.deleteExtension*/
DELETE FROM tbl_conf_dial_ext_aihandy
WHERE 1=1
    AND ext_num = #{extNum}
</script>
	"""})
    void deleteExtension(DaoExtension req);

    @Select({"""
<script>
/*RepoExtension.existsExtensionByDialNumAndExtNum*/
SELECT
    *
FROM tbl_conf_dial_ext_aihandy
WHERE 1=1
    <if test='fkCompany != null and "".equals(fkCompany) == false'>
    AND fk_company = #{fkCompany}
    </if>
    <if test='fkCompanyStaffAi != null and "".equals(fkCompanyStaffAi) == false'>
    AND fk_company_staff_ai = #{fkCompanyStaffAi}
    </if>
    <if test='dialNum != null and "".equals(dialNum) == false'>
    AND dial_num=#{dialNum}
    </if>
    <if test='extNum != null and "".equals(extNum) == false'>
    AND ext_num=#{extNum}
    </if>
    <if test='dnisType != null and "".equals(dnisType) == false'>
    AND dnis_type = #{dnisType}
    </if>
</script>
    """})
    Optional<DaoExtension> existsExtensionByDialNumAndExtNum(DaoExtension req);

    @Select({"""
<script>
/*RepoExtension.findById*/
SELECT
    *
FROM tbl_conf_dial_ext_aihandy
WHERE pk_conf_dial_ext_aihandy = #{pkConfDialExtAihandy}
</script>
    """})
    Optional<DaoExtension> findById(Long pkConfDialExtAihandy);
    @Update({"""
<script>
/*RepoExtension.updateFwdYn*/
UPDATE tbl_company_staff tcs
JOIN tbl_conf_dial_ext_aihandy tcde ON tcs.fk_company = tcde.fk_company
SET tcs.call_fwd_yn = #{callFwdYn}
WHERE tcde.ext_num = #{extNum}
AND tcs.fd_company_master_yn = 'Y'
</script>
    """})
    void updateFwdYn(DaoExtension req);

    @Select({"""
<script>
/*RepoExtension.findDnis*/
SELECT dnis FROM tbl_conf_dial_ext_aihandy WHERE dnis = #{dnis}
</script>        
    """})
    Optional<DaoExtension> findDnis(String dnis);

    @Select({"""
<script>
/*RepoExtension.findExtObByMember*/
SELECT
    *
FROM tbl_conf_dial_ext_ob_aihandy
WHERE 1=1
AND seq_staff = #{seqMember}
AND use_yn = 'Y'
LIMIT 1
</script>
    """})
    DaoExtensionOb findExtObByMember(Long seqMember);

    @Update({"""
/*RepoExtension.updateExtObByMember*/
UPDATE tbl_conf_dial_ext_ob_aihandy SET
    fd_moddate = now()
    ,agree_yn = #{obAgree}
WHERE 1=1
AND seq_staff = #{seqMember}
AND use_yn = 'Y'
    """})
    int updateExtObByMember(DaoExtensionOb req);


    /** Extension API에서 직접 생성*/
    @Insert({"""
<script>
/*RepoExtension.insertExtOb*/
INSERT INTO tbl_conf_dial_ext_ob_aihandy (solution_type, seq_company, seq_staff, ob_type, origin_dnis, fd_regdate)
SELECT 
    'B14'
    ,a.fk_company
    ,b.pk_company_staff
    ,'aihandy'
    ,#{fullDnis}
    ,now()
FROM 
    tbl_conf_dial_ext_aihandy a
JOIN 
    tbl_company_staff b ON a.fk_company = b.fk_company 
        AND b.fd_company_master_yn = 'Y'
        AND a.dnis_type = 'aihandy'
        AND a.fk_company = #{fkCompany}
</script>    
    """})
    void insertExtOb(Long fkCompany, String fullDnis);

    @Delete({"""
<script>
/*RepoExtension.deleteExtOb*/
DELETE FROM tbl_conf_dial_ext_ob_aihandy WHERE seq_company = #{fkCompany}
</script>
    """})
    void deleteExtOb(Long fkCompany);

    @Update({"""
<script>
/*RepoExtension.updateMobileMainYn*/
UPDATE tbl_conf_dial_ext_aihandy SET
    mobile_main_yn = #{mobileMainYn}
WHERE 1=1
AND fk_company = ( SELECT fk_company FROM tbl_company_staff WHERE pk_company_staff = #{seqMember} )
AND dnis_type = 'aihandy'
AND use_yn = 'Y'
</script>
    """})
    Integer updateMobileMainYn(DaoExtension req);

    @Select({"""
<script>
/*RepoExtension.getObNumberByMemberOrCompany*/
SELECT
    origin_dnis
    ,ob_type
    ,a.dial_num
    ,a.full_dnis
FROM tbl_conf_dial_ext_aihandy a
JOIN tbl_conf_dial_ext_ob_aihandy b ON a.fk_company = b.seq_company
WHERE 1=1
AND a.dnis_type = 'aihandy'
AND b.seq_staff = #{seqMember}
AND b.use_yn = 'Y'
LIMIT 1
</script>
    """})
    DaoExtension getObNumberByMemberOrCompany(Long seqMember);

    @Update({"""
<script>
/*RepoExtension.updateObNumber*/
UPDATE tbl_conf_dial_ext_ob_aihandy SET
    fd_moddate=now()
    ,origin_dnis = #{obNumber}
WHERE seq_staff = #{seqMember}
AND use_yn = 'Y'
</script>        
    """})
    Integer updateObNumber(Long seqMember, String obNumber);

    @Select({"""
<script>
/*RepoExtension.findExtension*/
SELECT 1 FROM tbl_conf_dial_ext_aihandy
WHERE 1=1
AND fk_company = (SELECT fk_company FROM tbl_company_staff WHERE pk_company_staff=#{seqMember})
AND dnis_type = 'aihandy'
AND (dial_num = #{obNumber} OR full_dnis = #{obNumber})
AND use_yn = 'Y'
</script>        
    """})
    Integer findExtensionByCompanyAndObNumber(Long seqMember, String obNumber);
}
