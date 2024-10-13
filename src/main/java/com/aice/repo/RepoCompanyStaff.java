package com.aice.repo;

import com.aice.dao.DaoCompanyStaff;
import com.aice.dao.dnis.DaoCompanyDnis;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * RepoCompany : tbl_company_staff
 * 2023.11.27. Description : 전체적으로 수정 예정
 */
@Repository
public interface RepoCompanyStaff {

	@Select({"""
<script>
/*RepoCompanyStaff.findByStatusCode*/
SELECT
	fk_company
	,pk_company_staff
FROM tbl_company_staff
WHERE fd_staff_status_code='A1101'
</script>	
	"""})
	List<DaoCompanyStaff> findByStatusCode();

	@Select({"""
<script>
/*RepoCompanyStaff.findMasterStaffByCompany*/
SELECT
	pk_company_staff
FROM tbl_company_staff
WHERE 1=1
	AND fk_company = #{fkCompany}
	AND fd_company_master_yn = 'Y'
</script>		
	"""})
	Long findMasterStaffByCompany(Long fkCompany);

	@Select({"""
<script>
/*RepoCompanyStaff.findAiStaffByCompanyAndStaff*/
SELECT
	pk_company_staff
FROM tbl_company_staff
WHERE 1=1
	AND fk_company = #{fkCompany}
	AND pk_company_staff = #{pkCompanyStaff}
	AND fd_staff_ai_yn = 'Y'
	AND fd_staff_status_code = 'A1101'
LIMIT 1
</script>		
	"""})
	Optional<DaoCompanyStaff> findAiStaffByCompanyAndStaff(Long fkCompany, Long pkCompanyStaff);

	@Select({"""
<script>
/*RepoCompanyStaff.findCompanyByStaff*/
SELECT
	fk_company
FROM tbl_company_staff
WHERE 1=1
	AND pk_company_staff = #{pkCompanyStaff}
</script>		
	"""})
	Long findCompanyByStaff(Long pkCompanyStaff);

	@Select({"""
<script>
/*RepoCompanyStaff.findByStaff*/
SELECT
	pk_company_staff
FROM tbl_company_staff
WHERE 1=1
	AND pk_company_staff = #{pkCompanyStaff}
</script>		
	"""})
	Optional<DaoCompanyStaff> findByStaff(Long pkCompanyStaff);

	@Update({"""
<script>
/*RepoCompanyStaff.updateInterj*/
UPDATE tbl_company_staff SET
    interj_yn = #{interjYn}
WHERE 1=1
    AND pk_company_staff=#{pkCompanyStaff}
</script>
    """})
	void updateInterj(DaoCompanyStaff daoCompanyStaff);
}
