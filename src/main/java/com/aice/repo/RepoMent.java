package com.aice.repo;

import com.aice.dao.ment.DaoMent;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoMent {

    @Insert({"""
<script>
/*RepoMent.insert*/
INSERT INTO tbl_ai_conf_intro (
    fk_company
    ,fk_company_staff_ai
    ,msg_before
    ,msg_body
    ,msg_body2
    ,msg_body3
    ,msg_body4
    ,msg_body5
    ,msg_body6
    ,msg_after
    ,msg_off
    ,default_yn
    ,warn_yn
    ,use_yn
    ,fk_writer
    ,fd_regdate
) VALUES (
    #{fkCompany}
    ,#{fkCompanyStaffAi}
    ,#{msgBefore}
    ,#{msgBody}
    ,#{msgBody2}
    ,#{msgBody3}
    ,#{msgBody4}
    ,#{msgBody5}
    ,#{msgBody6}
    ,#{msgAfter}
    ,#{msgOff}
    ,#{defaultYn}
    ,#{warnYn}
    ,#{useYn}
    ,#{fkWriter}
    ,now()
)
</script>        
    """})
    void insert(DaoMent daoMent);

    @Update({"""
<script>
/*RepoMent.update*/
UPDATE tbl_ai_conf_intro SET 
    msg_before = #{msgBefore}
    ,msg_body = #{msgBody}
    ,msg_body2 = #{msgBody2}
    ,msg_body3 = #{msgBody3}
    ,msg_body4 = #{msgBody4}
    ,msg_body5 = #{msgBody5}
    ,msg_body6 = #{msgBody6}
    ,msg_after = #{msgAfter}
    ,msg_off = #{msgOff}
    ,default_yn = #{defaultYn}
    ,warn_yn = #{warnYn}
    ,use_yn = #{useYn}
    ,fk_modifier = #{fkModifier}
    ,fd_moddate = now()
WHERE 1=1
    AND fk_company_staff_ai = #{fkCompanyStaffAi}
</script>
    """})
    void update(DaoMent daoMent);

    @Delete({"""
<script>
/*RepoMent.delete*/
DELETE FROM tbl_ai_conf_intro
WHERE 1=1
    AND fk_company_staff_ai = #{fkCompanyStaffAi}
</script>        
    """})
    void delete(DaoMent daoMent);

    @Select({"""
<script>
/*RepoMent.findByCompanyAndStaff*/
SELECT
    msg_body,
    msg_body2,
    msg_body3,
    msg_body4,
    msg_body5,
    msg_body6,
    msg_off,
    msg_before,
    warn_yn,
    use_yn
FROM tbl_ai_conf_intro
WHERE 1=1
    <if test='fkCompany != null and "".equals(fkCompany) == false'>
		AND fk_company = #{fkCompany}
	</if>
	<if test='fkCompanyStaffAi != null and "".equals(fkCompanyStaffAi) == false'>
		AND fk_company_staff_ai = #{fkCompanyStaffAi}
	</if>
	<if test='fkCompanyStaffAi == null or "".equals(fkCompanyStaffAi) == true'>
		AND fk_company_staff_ai is null
	</if>
LIMIT 1
</script>
	"""})
    DaoMent findByCompanyAndStaff(DaoMent daoMent);


    @Select({"""
/*RepoMent.listByModifyDate*/
SELECT
    fk_company_staff_ai,
    msg_body,
    msg_body2,
    msg_body3,
    msg_body4,
    msg_body5,
    msg_body6,
    msg_off,
    msg_before,
    warn_yn,
    use_yn
FROM tbl_ai_conf_intro
WHERE 1=1
AND DATE_FORMAT(fd_moddate, '%Y%m%d') = DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 DAY), '%Y%m%d')
    """})
    List<DaoMent> listByModifyDate();
}
