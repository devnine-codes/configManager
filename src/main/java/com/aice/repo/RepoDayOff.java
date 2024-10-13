package com.aice.repo;

import org.apache.ibatis.annotations.*;

import com.aice.dao.businesstime.DaoDayOff;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * RepoDayOff : tbl_ai_conf_day_off
 */
@Repository
public interface RepoDayOff {

	@Insert({"""
<script>
/*RepoDayOff.insert*/
INSERT INTO tbl_ai_conf_day_off (
	fk_company
	,fk_company_staff_ai
	,day_off_from
	,day_off_to
	,time_type
	,disp_name
	,msg_intro
	,msg_close
	,use_yn
	,fk_writer
	,fd_regdate
) values (
	#{fkCompany}
	,#{fkCompanyStaffAi}
	,#{dayOffFrom}
	,#{dayOffTo}
	,#{timeType}
	,#{dispName}
	,#{msgIntro}
	,#{msgClose}
	,'Y'
	,#{fkWriter}
	,now()
)
</script>
	"""})
	@Options(useGeneratedKeys = true, keyProperty = "pkAiConfDayOff")
	void insert(DaoDayOff daoDayOff);

	@Update({"""
<script>
/*RepoDayOff.updateDayOff*/
UPDATE tbl_ai_conf_day_off SET
	<if test='dayOffFrom != null and "".equals(dayOffFrom) == false'>day_off_from = #{dayOffFrom},</if>
	<if test='dayOffTo != null and "".equals(dayOffTo) == false'>day_off_to = #{dayOffTo},</if>
	<if test='dispName != null and "".equals(dispName) == false'>disp_name = #{dispName},</if>
	<if test='msgIntro != null and "".equals(msgIntro) == false'>msg_intro = #{msgIntro},</if>
	<if test='msgClose != null and "".equals(msgClose) == false'>msg_close = #{msgClose},</if>
	<if test='useYn != null and "".equals(useYn) == false'>use_yn = #{useYn},</if>
	<if test='fkModifier != null and "".equals(fkModifier) == false'>fk_modifier = #{fkModifier},</if>
	fd_moddate = now()
WHERE 1=1
    AND pk_ai_conf_day_off = #{pkAiConfDayOff}
	<if test='fkCompany != null and "".equals(fkCompany) == false'>
		AND fk_company = #{fkCompany}
	</if>
	<if test='fkCompanyStaffAi != null and "".equals(fkCompanyStaffAi) == false'>
		AND fk_company_staff_ai = #{fkCompanyStaffAi}
	</if>
	<if test='fkCompanyStaffAi == null or "".equals(fkCompanyStaffAi) == true'>
		AND fk_company_staff_ai is null
	</if>
</script>
	"""})
	void update(DaoDayOff daoDayOff);

	@Delete({"""
<script>
/*RepoDayOff.delete*/
DELETE FROM tbl_ai_conf_day_off
WHERE 1=1
    AND pk_ai_conf_day_off = #{pkAiConfDayOff}
	<if test='fkCompany != null and "".equals(fkCompany) == false'>
		AND fk_company = #{fkCompany}
	</if>
	<if test='fkCompanyStaffAi != null and "".equals(fkCompanyStaffAi) == false'>
		AND fk_company_staff_ai = #{fkCompanyStaffAi}
	</if>
	<if test='fkCompanyStaffAi == null or "".equals(fkCompanyStaffAi) == true'>
		AND fk_company_staff_ai is null
	</if>
</script>
	"""})
	void delete(DaoDayOff daoDayOff);

	@Select({"""
<script>
/*RepoDayOff.findByStaffAndTimeTypeAndDate*/
SELECT
	date_format(day_off_from, '%Y%m%d') AS day_off_from
	,date_format(day_off_to, '%Y%m%d') AS day_off_to
	,time_type
	,disp_name
	,msg_intro
	,msg_close
	,use_yn
FROM
	tbl_ai_conf_day_off
WHERE 1=1
	AND use_yn = 'Y'
	<if test='fkCompany != null and "".equals(fkCompany) == false'>
		AND fk_company = #{fkCompany}
	</if>
	<if test='fkCompanyStaffAi != null and "".equals(fkCompanyStaffAi) == false'>
		AND fk_company_staff_ai = #{fkCompanyStaffAi}
	</if>
	<if test='fkCompanyStaffAi == null or "".equals(fkCompanyStaffAi) == true'>
		AND fk_company_staff_ai is null
	</if>
	AND time_type = #{timeType}
	<if test='dayOffFrom != null and "".equals(dayOffFrom) == false'>
		<choose>
			<when test='dayOffFrom.matches("^\\\\d{4}$")'>
				AND date_format(day_off_from, '%Y') = #{dayOffFrom}
			</when>
			<when test='dayOffFrom.matches("^\\\\d{6}$")'>
				AND date_format(day_off_from, '%Y%m') = #{dayOffFrom}
			</when>
			<when test='dayOffFrom.matches("^\\\\d{8}$")'>
				AND date_format(day_off_from, '%Y%m%d') = #{dayOffFrom}
			</when>
			<otherwise>
				AND date_format(day_off_from, '%Y%m%d') LIKE CONCAT('%', #{dayOffFrom}, '%')
			</otherwise>
		</choose>
	</if>
ORDER BY day_off_from ASC
</script>
	"""})
	List<DaoDayOff> findByStaffAndTimeTypeAndDate(DaoDayOff daoDayOff);

	@Select({"""
<script>
/*RepoDayOff.findOneByStaffAndTimeTypeAndDate*/
SELECT
	date_format(day_off_from, '%Y%m%d') AS day_off_from
	,date_format(day_off_to, '%Y%m%d') AS day_off_to
	,time_type
	,disp_name
	,msg_intro
	,msg_close
	,use_yn
FROM
	tbl_ai_conf_day_off
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
	<if test='timeType != null or "".equals(timeType) == false'>
		AND time_type = #{timeType}
	</if>
	AND date_format(day_off_from, '%Y%m%d') = #{dayOffFrom}
LIMIT 1
</script>
	"""})
	DaoDayOff findOneByStaffAndTimeTypeAndDate(DaoDayOff daoDayOff);

	/** END */

	// 공휴일 생성
	@Insert({"""
<script>
/*RepoDayOff.insertPublicHoliday*/
INSERT INTO tbl_ai_conf_day_off (fk_company, fk_company_staff_ai, day_off_from, day_off_to, time_type, disp_name, msg_intro, msg_close, use_yn, fd_regdate)
(
	SELECT
		a.fk_company
		<if test='fkCompanyStaffAi == null or "".equals(fkCompanyStaffAi) == true'>
			,null
		</if>
		<if test='fkCompanyStaffAi != null and "".equals(fkCompanyStaffAi) == false'>
			,a.pk_company_staff
		</if>
		,day_off_from
		,day_off_to
		,'REST_PUBLIC'
		,disp_name
		,msg_intro
		,msg_close
		,'Y'
		,now()
	FROM tbl_company_staff a, tbl_ai_conf_day_off b
	WHERE 1=1
		AND a.fd_staff_status_code='A1101'
		AND b.time_type = 'MASTER_PUBLIC'
		AND date_format(day_off_from, '%Y') = #{solYear}
		AND a.pk_company_staff NOT IN
			(
				SELECT fk_company_staff_ai
                FROM tbl_ai_conf_day_off
                WHERE 1=1
                AND time_type='REST_PUBLIC'
                AND fk_company = #{fkCompany}
                AND date_format(day_off_from, '%Y') = #{solYear}
                <if test='fkCompanyStaffAi == null or "".equals(fkCompanyStaffAi) == true'>
                	AND fk_company_staff_ai IS NULL
                </if>
                <if test='fkCompanyStaffAi != null and "".equals(fkCompanyStaffAi) == false'>
					AND fk_company_staff_ai = #{fkCompanyStaffAi}
				</if>
                GROUP BY fk_company_staff_ai
			)
		<if test='fkCompany != null and "".equals(fkCompany) == false'>
			AND a.fk_company = #{fkCompany}
		</if>
		<if test='fkCompanyStaffAi != null and "".equals(fkCompanyStaffAi) == false'>
			AND a.pk_company_staff = #{fkCompanyStaffAi}
		</if>
		<if test='fkCompanyStaffAi == null or "".equals(fkCompanyStaffAi) == true'>
			AND a.fd_company_master_yn = 'Y'
		</if>
)
</script>
	"""})
	int insertPublicHoliday(DaoDayOff daoDayOff);

	@Select({"""
<script>
/*RepoDayOff.findMainPublicHoliday*/
SELECT 1
FROM tbl_ai_conf_day_off
WHERE 1=1
	AND time_type = #{timeType}
	AND date_format(day_off_from, '%Y%m%d') = #{dayOffFrom}
	AND use_yn = 'Y'
</script>
	"""})
	Optional<DaoDayOff> existsHolidays(String timeType, String dayOffFrom);

	@Select({"""
<script>
/*RepoDayOff.findByStaffAndNow*/
SELECT
	time_type
	,disp_name
	,msg_intro
	,msg_close
FROM tbl_ai_conf_day_off
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
	AND <![CDATA[ date_format(day_off_from, '%Y%m%d') <= date_format(now(), '%Y%m%d') ]]>
	AND <![CDATA[ date_format(day_off_to - INTERVAL 1 DAY, '%Y%m%d') >= date_format(now(), '%Y%m%d') ]]>
ORDER BY
	CASE
		WHEN time_type = 'REST_PUBLIC' THEN 1
		WHEN time_type = 'REST_GENERAL' THEN 2
		ELSE 3
	END
LIMIT 1
</script>
	"""})
	DaoDayOff findByStaffAndNow(DaoDayOff daoDayoff);

	@Select({"""
<script>
/*RepoDayOff.findOnePublicHoliday*/
SELECT * FROM tbl_ai_conf_day_off
WHERE 1=1
AND date_format(day_off_from, '%Y%m%d') = date_format(now(), '%Y%m%d')
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
LIMIT 1
</script>
	"""})
	Integer findOnePublicHoliday(DaoDayOff daoDayOff);

    // now API에 오늘날짜 정보조회로 필요
	@Select({"""
<script>
SELECT DAYOFWEEK(CURDATE())
</script>		
	"""})
	Integer dayOfWeek();

	@Select({"""
<script>
/*RepoDayOff.findByStaffAndTimeTypeAndDate*/
SELECT
    pk_ai_conf_day_off
    ,date_format(day_off_from, '%Y%m%d') AS day_off_from
    ,date_format(day_off_to, '%Y%m%d') AS day_off_to
    ,time_type
    ,disp_name
    ,msg_intro
    ,msg_close
FROM
    tbl_ai_conf_day_off
WHERE 1=1
    AND use_yn = 'Y'
    <if test='req.fkCompany != null and "".equals(req.fkCompany) == false'>
        AND fk_company = #{req.fkCompany}
    </if>
    <if test='req.fkCompanyStaffAi != null and "".equals(req.fkCompanyStaffAi) == false'>
        AND fk_company_staff_ai = #{req.fkCompanyStaffAi}
    </if>
    <if test='req.fkCompanyStaffAi == null or "".equals(req.fkCompanyStaffAi) == true'>
        AND fk_company_staff_ai is null
    </if>
    <if test='req.timeType != null and "".equals(req.timeType) == false'>
        AND time_type = #{req.timeType}
    </if>
    <if test='req.dayOffFrom != null and "".equals(req.dayOffFrom) == false'>
        <choose>
            <when test='req.dayOffFrom.matches("^\\\\d{4}$")'>
                AND date_format(day_off_from, '%Y') = #{req.dayOffFrom}
            </when>
            <when test='req.dayOffFrom.matches("^\\\\d{6}$")'>
                AND date_format(day_off_from, '%Y%m') = #{req.dayOffFrom}
            </when>
            <when test='req.dayOffFrom.matches("^\\\\d{8}$")'>
                AND date_format(day_off_from, '%Y%m%d') = #{req.dayOffFrom}
            </when>
            <otherwise>
                AND date_format(day_off_from, '%Y%m%d') LIKE CONCAT('%', #{req.dayOffFrom}, '%')
            </otherwise>
        </choose>
    </if>
    <if test='pageable.sort.empty == false'>
    <foreach collection='pageable.sort.orders' item='itemOrder' open='ORDER BY' separator=','>
        ${itemOrder.property} ${itemOrder.direction}
    </foreach>
    </if>
LIMIT #{pageable.pageSize} OFFSET #{pageable.offset}
</script>
    """})
    List<DaoDayOff> findHolidays(@Param("pageable") Pageable pageable
                                ,@Param("req") DaoDayOff req);

    @Select({"""
    <script>
    /*RepoDayOff.countDayOffsByCriteria*/
    SELECT
        COUNT(*)
    FROM
        tbl_ai_conf_day_off
    WHERE 1=1
        AND use_yn = 'Y'
        <if test='req.fkCompany != null and "".equals(req.fkCompany) == false'>
            AND fk_company = #{req.fkCompany}
        </if>
        <if test='req.fkCompanyStaffAi != null and "".equals(req.fkCompanyStaffAi) == false'>
            AND fk_company_staff_ai = #{req.fkCompanyStaffAi}
        </if>
        <if test='req.fkCompanyStaffAi == null or "".equals(req.fkCompanyStaffAi) == true'>
            AND fk_company_staff_ai is null
        </if>
        <if test='req.timeType != null and "".equals(req.timeType) == false'>
            AND time_type = #{req.timeType}
        </if>
        <if test='req.dayOffFrom != null and "".equals(req.dayOffFrom) == false'>
            <choose>
                <when test='req.dayOffFrom.matches("^\\\\d{4}$")'>
                    AND date_format(day_off_from, '%Y') = #{req.dayOffFrom}
                </when>
                <when test='req.dayOffFrom.matches("^\\\\d{6}$")'>
                    AND date_format(day_off_from, '%Y%m') = #{req.dayOffFrom}
                </when>
                <when test='req.dayOffFrom.matches("^\\\\d{8}$")'>
                    AND date_format(day_off_from, '%Y%m%d') = #{req.dayOffFrom}
                </when>
                <otherwise>
                    AND date_format(day_off_from, '%Y%m%d') LIKE CONCAT('%', #{req.dayOffFrom}, '%')
                </otherwise>
            </choose>
        </if>
    </script>
    """})
    int countHolidays(@Param("req") DaoDayOff req);


    @Select({"""
<script>
SELECT
    pk_ai_conf_day_off
    ,date_format(day_off_from, '%Y%m%d') AS day_off_from
    ,date_format(day_off_to, '%Y%m%d') AS day_off_to
    ,time_type
    ,disp_name
    ,msg_intro
    ,msg_close
FROM tbl_ai_conf_day_off
WHERE pk_ai_conf_day_off = #{req.pkAiConfDayOff}
    <if test='req.fkCompany != null and "".equals(req.fkCompany) == false'>
        AND fk_company = #{req.fkCompany}
    </if>
    <if test='req.fkCompanyStaffAi != null and "".equals(req.fkCompanyStaffAi) == false'>
        AND fk_company_staff_ai = #{req.fkCompanyStaffAi}
    </if>
    <if test='req.fkCompanyStaffAi == null or "".equals(req.fkCompanyStaffAi) == true'>
        AND fk_company_staff_ai is null
    </if>
    <if test='req.timeType != null and "".equals(req.timeType) == false'>
        AND time_type = #{req.timeType}
    </if>
</script>        
    """})
    DaoDayOff findById(@Param("req") DaoDayOff req);
}