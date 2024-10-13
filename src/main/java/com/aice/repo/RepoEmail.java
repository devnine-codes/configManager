package com.aice.repo;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.aice.dao.email.DaoEmail;

public interface RepoEmail {
    @Insert({"""
<script>
/*RepoEmail.insertConfEmail*/
insert into tbl_ai_conf_email (
     fk_company
    ,fk_company_staff_ai
    ,use_type
    ,conf_name
    ,conf_host
    ,conf_port
    ,conf_id
    ,conf_pw
    ,use_auth_yn
    ,use_tls_yn
    ,use_ssl_yn
    ,use_yn
    ,conf_disp_name
    ,fk_writer
    ,fd_regdate
) values (
     #{fkCompany}
    ,#{fkCompanyStaffAi}
    ,#{useType}
    ,#{confName}
    ,#{confHost}
    ,#{confPort}
    ,#{confId}
    ,func_crypt_enc(#{confPw})
    ,#{useAuthYn}
    ,#{useTlsYn}
    ,#{useSslYn}
    ,ifnull(#{useYn},'Y')
    ,#{confDispName}
    ,#{fkCompany}
    ,now()
)
</script>
    """})
    @Options(useGeneratedKeys=true,keyProperty="pkAiConfEmail")
    void insertConfEmail(DaoEmail emailVo);

    @Update({"""
<script>
/*RepoEmail.updateConfEmail*/
update tbl_ai_conf_email set
     conf_name=#{confName}
    ,conf_host=#{confHost}
    ,conf_port=#{confPort}
    ,conf_id=#{confId}
    ,conf_pw=func_crypt_enc(#{confPw})
    ,use_auth_yn=#{useAuthYn}
    ,use_tls_yn=#{useTlsYn}
    ,use_ssl_yn=#{useSslYn}
    ,use_yn=#{useYn}
    ,conf_disp_name=#{confDispName}
    ,fk_modifier=#{fkCompany}
    ,fd_moddate=now()
where
    fk_company=#{fkCompany}
    and use_type=#{useType}
    <if test='fkCompanyStaffAi != null and \"\".equals(fkCompanyStaffAi) == false'>
    and fk_company_staff_ai=#{fkCompanyStaffAi}
    </if>
</script>
    """})
    void updateConfEmail(DaoEmail emailVo);

    @Delete({"""
<script>
/*RepoEmail.deleteConfEmail*/
delete from tbl_ai_conf_email
where
    fk_company=#{fkCompany}
    and use_type=#{useType}
    <if test='fkCompanyStaffAi != null and \"\".equals(fkCompanyStaffAi) == false'>
    and fk_company_staff_ai=#{fkCompanyStaffAi}
    </if>
</script>
    """})
    void deleteConfEmail(DaoEmail emailVo);

    @Select({"""
<script>
/*RepoEmail.findAll*/
select
    pk_ai_conf_email
    ,fk_company
    ,fk_company_staff_ai
    ,use_type
    ,case
        when use_type='B1009' then 'SMTP'
        when use_type='B1016' then 'POP3'
        when use_type='B1017' then 'IMAP'
    end use_type_name
    ,conf_name
    ,conf_host
    ,conf_port
    ,conf_id
    ,func_crypt_dec(conf_pw) conf_pw
    ,use_auth_yn
    ,use_tls_yn
    ,use_ssl_yn
    ,use_yn
    ,conf_disp_name
    ,conf_key
    ,conf_val
    ,fk_writer
    ,date_format(fd_regdate,'%Y-%m-%d %T') fd_regdate
    ,fk_modifier
    ,date_format(fd_moddate,'%Y-%m-%d %T') fd_moddate
from
    tbl_ai_conf_email
where 1=1
    and use_yn='Y'
    and fk_company=#{fkCompany}
    <if test='useType != null and \"\".equals(useType) == false'>
    and use_type=#{useType}
    </if>
    <if test='fkCompanyStaffAi != null and \"\".equals(fkCompanyStaffAi) == false'>
    and fk_company_staff_ai=#{fkCompanyStaffAi}
    </if>
</script>
    """})
    List<DaoEmail> findAll(DaoEmail emailVo);

    @Select({"""
<script>
/*RepoEmail.findOneByUseType*/
select
    pk_ai_conf_email
    ,fk_company
    ,fk_company_staff_ai
    ,use_type
    ,case
        when use_type='B1009' then 'SMTP'
        when use_type='B1016' then 'POP3'
        when use_type='B1017' then 'IMAP'
    end use_type_name
    ,conf_name
    ,conf_host
    ,conf_port
    ,conf_id
    ,func_crypt_dec(conf_pw) conf_pw
    ,use_auth_yn
    ,use_tls_yn
    ,use_ssl_yn
    ,use_yn
    ,conf_disp_name
    ,conf_key
    ,conf_val
    ,fk_writer
    ,date_format(fd_regdate,'%Y-%m-%d %T') fd_regdate
    ,fk_modifier
    ,date_format(fd_moddate,'%Y-%m-%d %T') fd_moddate
from
    tbl_ai_conf_email
where 1=1
    and use_yn='Y'
    and fk_company=#{fkCompany}
    and use_type=#{useType}
    <if test='fkCompanyStaffAi != null and \"\".equals(fkCompanyStaffAi) == false'>
    and fk_company_staff_ai=#{fkCompanyStaffAi}
    </if>
</script>
    """})
    DaoEmail findOneByUseType(DaoEmail emailVo);
}



















