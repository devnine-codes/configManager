package com.aice.repo;

import com.aice.dao.presence.DaoPresence;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoPresence {

    @Select({"""
<script>
/*RepoPresence.findAiWorkStatus*/
SELECT
    enable_yn
FROM tbl_ai_conf_work
WHERE 1=1
    AND fk_company = #{seqCompany}
    AND fk_company_staff_ai = #{seqStaffAi}
LIMIT 1
</script>
    """})
    DaoPresence findAiWorkStatus(DaoPresence req);

    @Update({"""
<script>
/*RepoPresence.updateAiWorkStatus*/
UPDATE tbl_ai_conf_work SET
    enable_yn = #{activationYn}
    ,fk_modifier = 0
    ,fd_moddate = now()
WHERE 1=1
    AND fk_company = #{seqCompany}
    AND fk_company_staff_ai = #{seqStaffAi}
</script>
    """})
    int updateAiWorkStatus(DaoPresence req);

    @Insert({"""
<script>
/*RepoPresence.insertMemberStatus*/
INSERT INTO tbl_member_res_status
(
    solution_type
    ,seq_member
    ,res_status
    ,disp_mode
    ,fk_writer
    ,fd_regdate
)
VALUES
(
    #{solutionType}
    ,#{seqMember}
    ,#{resStatus}
    ,#{dispMode}
    ,0
    ,now()
);
</script>
    """})
    int insertMemberStatus(DaoPresence req);

    @Select({"""
<script>
/*RepoPresence.findMemberStatus*/
SELECT
    mrs.res_status
    ,mrs.disp_mode
FROM tbl_member_res_status mrs
INNER JOIN tbl_company_staff cs ON mrs.seq_member = cs.pk_company_staff
WHERE 1=1
    AND cs.fk_company = #{seqCompany}
    AND cs.fd_company_master_yn = 'Y'
    <if test='solutionType != null and "".equals(solutionType) == false'>
    AND mrs.solution_type = #{solutionType}
    </if>
LIMIT 1
</script>
    """})
    DaoPresence findMemberStatus(DaoPresence req);

    @Update({"""
<script>
/*RepoPresence.updateMemberStatus*/
UPDATE tbl_member_res_status
SET
    fd_moddate = NOW()
    <if test="resStatus != null">
    ,res_status = #{resStatus}
    </if>
    <if test="dispMode != null">
    ,disp_mode = #{dispMode}
    </if>
WHERE seq_member IN (
    SELECT cs.pk_company_staff
    FROM tbl_company_staff cs
    WHERE cs.fk_company = #{seqCompany}
      AND cs.fd_company_master_yn = 'Y'
    )
</script>
    """})
    int updateMemberStatus(DaoPresence req);

    @Select({"""
<script>
/*RepoPresence.findCallForwardStatus*/
SELECT
    call_fwd_yn
FROM tbl_company_staff
WHERE 1=1
    AND fk_company = #{seqCompany}
    AND fd_company_master_yn = 'Y'
</script>
    """})
    DaoPresence findCallForwardStatus(DaoPresence req);

    @Update({"""
<script>
/*RepoPresence.updateCallForwardStatus*/
UPDATE tbl_company_staff SET
    call_fwd_yn = #{callFwdYn}
    ,fk_modifier = 0
    ,fd_moddate = now()
WHERE 1=1
    AND fk_company = #{seqCompany}
    AND fd_company_master_yn = 'Y'
</script>
    """})
    int updateCallForwardStatus(DaoPresence req);
}