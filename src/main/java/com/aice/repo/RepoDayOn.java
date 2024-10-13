package com.aice.repo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import com.aice.dao.businesstime.DaoDayOn;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * RepoDayOn : tbl_ai_conf_day_on
 */
@Repository
public interface RepoDayOn {
	@Insert({"""
<script>
/*RepoDayOn.insert*/
INSERT INTO tbl_ai_conf_day_on (
	fk_company
	,fk_company_staff_ai
	,week_num
	,time_from_hh
	,time_from_min
	,time_to_hh
	,time_to_min
	,work_type
	,time_type
	,msg_intro
	,msg_close
	,enable_yn
	,use_yn
	,fk_writer
	,fd_regdate
) values (
	#{fkCompany}
	,#{fkCompanyStaffAi}
	,#{weekNum}
	,#{timeFromHh}
	,#{timeFromMin}
	,#{timeToHh}
	,#{timeToMin}
	,#{workType}
	,#{timeType}
	,#{msgIntro}
	,#{msgClose}
	,'Y'
	,'Y'
	,#{fkWriter}
	,now()
)
</script>
	"""})
	@Options(useGeneratedKeys = true, keyProperty = "pkAiConfDayOn")
	void insert(DaoDayOn daoDayOn);

	@Update({"""
<script>
/*RepoDayOn.updateDayOn*/
UPDATE tbl_ai_conf_day_on SET
	time_from_hh = #{timeFromHh}
	,time_from_min = #{timeFromMin}
	,time_to_hh = #{timeToHh}
	,time_to_min = #{timeToMin}
	,msg_intro = #{msgIntro}
	,msg_close = #{msgClose}
	,enable_yn = #{enableYn}
	,use_yn = #{useYn}
	,fk_modifier = #{fkModifier}
	,fd_moddate = now()
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
	AND week_num = #{weekNum}
	AND time_type = #{timeType}
</script>
	"""})
	void update(DaoDayOn daoDayOn);
	@Delete({"""
<script>
/*RepoDayOn.delete*/
DELETE FROM tbl_ai_conf_day_on
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
	AND week_num = #{weekNum}
	AND time_type = #{timeType}
</script>
	"""})
	void delete(DaoDayOn req);
	@Select({"""
<script>
/*RepoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn*/
SELECT
	week_num,
	time_from_hh,
	time_from_min,
	time_to_hh,
	time_to_min,
	msg_intro,
	msg_close,
	enable_yn,
	use_yn
FROM tbl_ai_conf_day_on
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
	AND week_num = #{weekNum}
	AND time_type = #{timeType}
	AND use_yn = 'Y'
LIMIT 1
</script>
	"""})
	DaoDayOn findStaffAndWeekNumAndTimeTypeAndUseYn(DaoDayOn req);

	@Select({"""
<script>
/*RepoDayOn.findByStaffAndNow*/
SELECT
	pk_ai_conf_day_on,
	fk_company,
	fk_company_staff_ai,
	week_num,
	time_from_hh,
	time_from_min,
	time_to_hh,
	time_to_min,
	work_type,
	time_type,
	msg_intro,
	msg_close,
	enable_yn,
	use_yn,
	fk_writer,
	date_format(fd_regdate, '%Y%m%d') AS fd_regdate,
	fk_modifier,
	date_format(fd_moddate, '%Y%m%d') AS fd_moddate
FROM tbl_ai_conf_day_on
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
    AND use_yn = 'Y'
    <choose>
		<when test='weekNum != null and "".equals(weekNum) == false'>
			AND week_num = #{weekNum}
		</when>
		<otherwise>
			AND week_num = (SELECT DAYOFWEEK(CURDATE()))
		</otherwise>
	</choose>
	AND CURTIME() BETWEEN TIME(CONCAT(time_from_hh, ':', time_from_min))
	AND TIME(CONCAT(time_to_hh, ':', time_to_min))
ORDER BY
	CASE
		WHEN time_type = 'REST_DINNER' THEN 1
		WHEN time_type = 'REST_LUNCH' THEN 2
		ELSE 3
	END LIMIT 1
</script>
	"""})
	DaoDayOn findByStaffAndNow(DaoDayOn req);


	@Select({"""
<script>
/*RepoDayOn.searchByStaffAndNow*/
SELECT
	pk_ai_conf_day_on,
	fk_company,
	fk_company_staff_ai,
	week_num,
	time_from_hh,
	time_from_min,
	time_to_hh,
	time_to_min,
	work_type,
	time_type,
	msg_intro,
	msg_close,
	enable_yn,
	use_yn,
	fk_writer,
	date_format(fd_regdate, '%Y%m%d') AS fd_regdate,
	fk_modifier,
	date_format(fd_moddate, '%Y%m%d') AS fd_moddate
FROM tbl_ai_conf_day_on
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
    AND use_yn = 'Y'
    <choose>
		<when test='searchDate != null and "".equals(searchDate) == false'>
			AND week_num = (SELECT DAYOFWEEK(#{searchDate}))
			AND #{searchTime} BETWEEN TIME(CONCAT(time_from_hh, ':', time_from_min))
			AND TIME(CONCAT(time_to_hh, ':', time_to_min))
		</when>
		<otherwise>
			AND week_num = (SELECT DAYOFWEEK(CURDATE()))
			AND CURTIME() BETWEEN TIME(CONCAT(time_from_hh, ':', time_from_min))
			AND TIME(CONCAT(time_to_hh, ':', time_to_min))
		</otherwise>
	</choose>
ORDER BY
	CASE
		WHEN time_type = 'REST_DINNER' THEN 1
		WHEN time_type = 'REST_LUNCH' THEN 2
		ELSE 3
	END LIMIT 1
</script>
	"""})
	DaoDayOn searchByStaffAndNow(DaoDayOn req);
}
