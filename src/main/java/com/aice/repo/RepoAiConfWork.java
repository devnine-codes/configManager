package com.aice.repo;

import com.aice.dao.DaoAiWork;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoAiConfWork {

    @Select({"""
<script>
/*RepoAiConfWork.findAiWorkByAiSeq*/
SELECT
    *
FROM tbl_ai_conf_work
WHERE 1=1
    AND ai_work_cd = #{aiWorkCd}
    AND fk_company_staff_ai = #{aiStaffSeq}
    AND use_yn = 'Y'
</script>
    """})
    DaoAiWork findAiWork(
            String aiWorkCd
            ,Long aiStaffSeq
    );

    @Update({"""
<script>
/*RepoAiConfWork.updateAiWork*/
UPDATE tbl_ai_conf_work SET
    fd_moddate = now()
    <if test="contactAiYn != null">
    ,contact_ai_yn = #{contactAiYn}
    </if>
    <if test="loggingYn != null">
    ,logging_yn = #{loggingYn}
    </if>
WHERE 1=1
    AND fk_company_staff_ai = #{aiStaffSeq}
    AND use_yn = 'Y'
</script>
    """})
    int updateAiWork(DaoAiWork req);
}
