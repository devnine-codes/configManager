package com.aice.repo;

import com.aice.dao.DaoAiWork;
import com.aice.dao.DaoCompanyStaff;
import com.aice.dto.DtoAiInfo;
import com.aice.dto.DtoAiInfoRequest;
import com.aice.dto.AiRoleData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoCompanyAi {

    @Insert({"""
<script>
/*RepoAiWork.insert*/
insert into tbl_ai_conf_work (
    fk_company
    ,fk_company_staff_ai
    ,ai_work_cd
    ,p_ai_work_cd
    ,front_status
    ,bot_status
    ,enable_yn
    ,open_company_yn
    ,open_company_addr_yn
    ,open_company_level_yn
    ,open_email_yn
    ,open_birth_yn
    ,use_yn
    ,fd_regdate
) values
(
    #{fkCompany}
    ,#{fkCompanyStaffAi}
    ,'CTGR1_MANAGE'
    ,null
    ,'INIT'
    ,'COMPLETE'
    ,'Y'
    ,'Y'
    ,'Y'
    ,'Y'
    ,'Y'
    ,'Y'
    ,'Y'
    ,now()
)
,(
    #{fkCompany}
    ,#{fkCompanyStaffAi}
    ,'CTGR2_RECEPTIONIST'
    ,'CTGR1_MANAGE'
    ,null
    ,null
    ,'Y'
    ,'Y'
    ,'Y'
    ,'Y'
    ,'Y'
    ,'Y'
    ,'Y'
    ,now()
)
</script>        
    """})
    @Options(useGeneratedKeys = true, keyProperty = "pkAiConfWork")
    void insertWork(DaoAiWork daoAiWork);

    @Insert({"""
<script>
/*RepoAiWork.insertWorkTask*/
insert into tbl_ai_conf_work_task
(fk_company,fk_company_staff_ai,ai_work_cd,p_ai_work_cd,enable_yn,term_unit,term_val,use_yn,fd_regdate)
values
(#{fkCompany},#{fkCompanyStaffAi},'CTGR3_DEPART_INFO','CTGR2_RECEPTIONIST','Y','H',0,'Y',now())
</script>
    """})
    @Options(useGeneratedKeys = true, keyProperty = "pkAiConfWorkTask")
    void insertWorkTask(DaoAiWork daoAiWork);

    @Insert({"""
<script>
/*RepoAiWork.insertDayOn*/
insert into tbl_ai_conf_day_on (fk_company,fk_company_staff_ai,week_num,time_from_hh,time_from_min,time_to_hh,time_to_min,work_type,time_type,msg_intro,msg_close,use_yn,fd_regdate) values
 (#{fkCompany},#{fkCompanyStaffAi},1,0,0,0,0,'W','WORK_ON',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},1,0,0,0,0,'R','REST_LUNCH',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},1,0,0,0,0,'R','REST_DINNER',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},2,9,0,18,0,'W','WORK_ON',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},2,12,0,13,0,'R','REST_LUNCH',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},2,0,0,0,0,'R','REST_DINNER',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},3,9,0,18,0,'W','WORK_ON',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},3,12,0,13,0,'R','REST_LUNCH',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},3,0,0,0,0,'R','REST_DINNER',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},4,9,0,18,0,'W','WORK_ON',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},4,12,0,13,0,'R','REST_LUNCH',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},4,0,0,0,0,'R','REST_DINNER',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},5,9,0,18,0,'W','WORK_ON',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},5,12,0,13,0,'R','REST_LUNCH',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},5,0,0,0,0,'R','REST_DINNER',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},6,9,0,18,0,'W','WORK_ON',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},6,12,0,13,0,'R','REST_LUNCH',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},6,0,0,0,0,'R','REST_DINNER',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},7,0,0,0,0,'W','WORK_ON',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},7,0,0,0,0,'R','REST_LUNCH',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},7,0,0,0,0,'R','REST_DINNER',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},99,9,0,18,0,'W','WORK_ON',null,null,'Y',now())
,(#{fkCompany},#{fkCompanyStaffAi},99,12,0,13,0,'R','REST_LUNCH',null,null,'Y',now())
</script>            
"""})
    @Options(useGeneratedKeys = true, keyProperty = "pkAiConfDayOn")
    void insertDayOn(DaoAiWork daoAiWork);

    @Insert({"""
<script>
/*RepoAiWork.insertIntro*/
insert into tbl_ai_conf_intro (fk_company,fk_company_staff_ai,msg_before,msg_body,default_yn,warn_yn,use_yn,fd_regdate) values
(#{fkCompany},#{fkCompanyStaffAi},null,null,'Y','N','Y',now())
</script>            
"""})
    @Options(useGeneratedKeys = true, keyProperty = "pkAiConfIntro")
    void insertIntro(DaoAiWork daoAiWork);

    @Select({"""
<script>
/*RepoAiWork.find*/
select
    *
from tbl_ai_conf_work
where 1=1
    and fk_company_staff_ai = #{fkCompanyStaffAi}    
</script>
            """})
    List<DaoAiWork> findAiWorkByStaff(DaoAiWork daoAiWork);

    @Select({"""
<script>
/*RepoCompanyAi.listAiByCompany*/
SELECT
	pk_company_staff,
	fk_company,
	fd_staff_status_code,
	fd_staff_ai_uid,
	quick_start_status,
	quick_start_bot_status,
	date_format(quick_start_from, '%Y%m%d') AS quick_start_from,
	date_format(quick_start_to, '%Y%m%d') AS quick_start_to,
	bot_display_yn
FROM tbl_company_staff
WHERE 1=1
	AND fk_company=#{fkCompany}
	<if test='fdStaffAiYn != null and "".equals(fdStaffAiYn) == false'>
		AND fd_staff_ai_yn=#{fdStaffAiYn}
	</if>
	AND fd_staff_ai_yn='Y'
</script>	
	"""})
    List<DaoCompanyStaff> listAisByCompany2(DaoCompanyStaff daoCompanyStaff);

    @Select({"""
<script>
/*RepoCompanyAi.listAiByCompany*/
<![CDATA[
SELECT
    cs.pk_company_staff as aiStaffSeq,
    cs.fk_company as companySeq,
    cs.fd_staff_name,
    cs.fd_staff_ai_uid,
    CASE
        WHEN date_add(cs.fd_regdate, interval +1 month) <= SYSDATE() THEN ''
        ELSE 'new'
    END AS 'new_ai',
    cs.fd_default_ai,
    wpw.disp_name AS staff_work_code_name,
    cs.fd_staff_persona,
    REGEXP_REPLACE(cd.full_dnis, '(02|.{3})(.+)(.{4})', '\\\\1-\\\\2-\\\\3') as dnis_num,
    c.fd_name,
    cs.fd_staff_status_code,
    acw.front_status,
    aimg.disp_name,
    aimg.fk_ai_policy_avatar_img as ai_policy_avatar_img,
    cs.quick_start_status,
    cs.quick_start_bot_status,
    date_format(cs.quick_start_from, '%Y%m%d') AS quick_start_from,
    date_format(cs.quick_start_to, '%Y%m%d') AS quick_start_to,
    cs.bot_display_yn,
    acw.bot_status AS botStatus,
    acw.bot_id AS botId,
    acw.ai_work_cd AS botRole
FROM tbl_company_staff cs
INNER JOIN tbl_code c ON cs.fd_staff_status_code = c.pk_code
LEFT JOIN tbl_ai_conf_work acw ON cs.pk_company_staff = acw.fk_company_staff_ai
    AND acw.p_ai_work_cd IS NULL
LEFT JOIN tbl_ai_policy_work wpw ON acw.ai_work_cd = wpw.ai_work_cd
LEFT JOIN tbl_company_dnis cd ON cs.pk_company_staff = cd.fk_company_staff_ai
    AND cs.fk_company = cd.fk_company
LEFT JOIN tbl_ai_conf_avatar_img aimg ON cs.pk_company_staff = aimg.fk_company_staff_ai
    AND aimg.part_type = 'face'
WHERE 1=1
    AND cs.fd_staff_ai_yn = 'Y'
    AND cs.fk_company = #{fkCompany}
]]>
</script>	
    """})
    List<DtoAiInfo> listAisByCompany(DaoCompanyStaff daoCompanyStaff);

    @Update({"""
<script>
/*RepoCompanyAi.updateAi*/
UPDATE tbl_company_staff SET
    <if test="quickStartStatus != null">
        quick_start_status=#{quickStartStatus},
    </if>
    <if test="quickStartBotStatus != null">
        quick_start_bot_status=#{quickStartBotStatus},
    </if>
    <if test="quickStartFrom != null">
        quick_start_from=#{quickStartFrom},
    </if>
    <if test="quickStartTo != null">
        quick_start_to=#{quickStartTo},
    </if>
    <if test="botDisplayYn != null">
        bot_display_yn=#{botDisplayYn},
    </if>
    fd_moddate=now()
WHERE 1=1
    AND fk_company=#{fkCompany}
    AND pk_company_staff=#{pkCompanyStaff}
    AND fd_staff_ai_yn='Y'
</script>
    """})
    void updateAi(DaoCompanyStaff daoCompanyStaff);


    @Select({"""
<script>
/*RepoCompanyAi.getAiList*/
SELECT
    pk_company_staff AS aiStaffSeq,
    fk_company AS companySeq
FROM
    tbl_company_staff
WHERE 1=1
    <if test="solutionType != null">
    AND solution_type = #{solutionType}
    </if>
    AND fd_staff_ai_yn = 'Y'
    AND fd_staff_status_code = 'A1101'
    <if test='fromDate != null and "".equals(fromDate) == false'>
    <choose>
        <when test='fromDate.matches("^\\\\d{4}$")'>
            AND date_format(fd_regdate, '%Y%m%d') BETWEEN #{fromDate} AND #{toDate}
        </when>
        <when test='fromDate.matches("^\\\\d{6}$")'>
            AND date_format(fd_regdate, '%Y%m') BETWEEN #{fromDate} AND #{toDate}
        </when>
        <when test='fromDate.matches("^\\\\d{8}$")'>
            AND date_format(fd_regdate, '%Y%m%d') BETWEEN #{fromDate} AND #{toDate}
        </when>
    </choose>
    </if>
</script>
    """})
    List<AiRoleData> getAiList(DaoCompanyStaff daoCompanyStaff);

    @Select({"""
<script>
/*RepoCompanyAi.getAiList2*/
SELECT
    cs.fk_company as companySeq,
    cs.pk_company_staff as aiStaffSeq,
    acw.ai_work_cd as role
FROM
    tbl_company_staff cs
INNER JOIN tbl_ai_conf_work acw ON cs.pk_company_staff = acw.fk_company_staff_ai
WHERE 1=1
    AND acw.p_ai_work_cd IS NULL
    AND cs.fd_staff_ai_yn = 'Y'
    AND cs.fd_staff_status_code = 'A1101'
    <if test='aiRole != null and !aiRole.isEmpty()'>
    AND acw.ai_work_cd IN
        <foreach item="role" collection="aiRole" open="(" separator="," close=")">
            CONCAT('CTGR1_', #{role})
        </foreach>
    </if>
    <if test='fromDate != null and "".equals(fromDate) == false and toDate != null and "".equals(toDate) == false'>
        AND date_format(acw.fd_regdate, '%Y%m%d') BETWEEN #{fromDate} AND #{toDate}
    </if>
</script>
    """})
    List<AiRoleData> getAiList2(DtoAiInfoRequest req);
}
