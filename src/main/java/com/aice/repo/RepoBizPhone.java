package com.aice.repo;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.aice.dao.bizphone.DaoBizPhone;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoBizPhone {
    @Select({"""
<script>
/*RepoBizPhone.findByDnis*/
select *
from tbl_company_biz_phone
where
    fk_company=#{fkCompany}
    and dnis=#{dnis}
</script>
    """})
    public Optional<DaoBizPhone> findByDnis(Long fkCompany, String dnis);

    @Select({"""
<script>
/*RepoBizPhone.findAll*/
select
     pk_company_phone
    ,solution_type
    ,user_type
    ,fk_company
    ,biz_phone_name
    ,biz_phone_num
    ,dnis
    ,reg_status
    ,doc_status
    ,doc_main_status
    ,call_forward_status
    ,default_yn
    ,inbound_yn
    ,outbound_yn
    ,phone_yn
    ,sms_yn
    ,enable_yn
    ,use_yn
    ,use_order
    ,file_path_main_num
    ,file_path_join_cert
    ,file_path_company_cert
    ,file_path_attorney
    ,file_path_trade_cert
    ,file_path_employ_cert
    ,biz_cate_step_1
    ,biz_cate_step_2
    ,biz_cate_step_3
    ,fk_writer
    ,date_format(fd_regdate,'%Y-%m-%d %T') fd_regdate
    ,fk_modifier
    ,date_format(fd_moddate,'%Y-%m-%d %T') fd_moddate
from
    tbl_company_biz_phone
where 1=1
    and use_yn='Y'
    and fk_company=#{fkCompany}
    <if test='bizPhoneNum != null and \"\".equals(bizPhoneNum) == false'>
    and biz_phone_num=#{bizPhoneNum}
    </if>
    <if test='dnis != null and \"\".equals(dnis) == false'>
    and dnis=#{dnis}
    </if>
    <if test='inboundYn != null and \"\".equals(inboundYn) == false'>
    and inbound_yn=#{inboundYn}
    </if>
    <if test='outboundYn != null and \"\".equals(outboundYn) == false'>
    and outbound_yn=#{outboundYn}
    </if>
    <if test='phoneYn != null and \"\".equals(phoneYn) == false'>
    and phone_yn=#{phoneYn}
    </if>
    <if test='smsYn != null and \"\".equals(smsYn) == false'>
    and sms_yn=#{smsYn}
    </if>
    <if test='enableYn != null and \"\".equals(enableYn) == false'>
    and enable_yn=#{enableYn}
    </if>
</script>
    """})
    public List<DaoBizPhone> findAll(DaoBizPhone req);

    @Select({"""
<script>
/*RepoBizPhone.findOne*/
select
     pk_company_phone
    ,solution_type
    ,user_type
    ,fk_company
    ,biz_phone_name
    ,biz_phone_num
    ,dnis
    ,reg_status
    ,doc_status
    ,doc_main_status
    ,call_forward_status
    ,default_yn
    ,inbound_yn
    ,outbound_yn
    ,phone_yn
    ,sms_yn
    ,enable_yn
    ,use_yn
    ,use_order
    ,file_path_main_num
    ,file_path_join_cert
    ,file_path_company_cert
    ,file_path_attorney
    ,file_path_trade_cert
    ,file_path_employ_cert
    ,biz_cate_step_1
    ,biz_cate_step_2
    ,biz_cate_step_3
    ,fk_writer
    ,date_format(fd_regdate,'%Y-%m-%d %T') fd_regdate
    ,fk_modifier
    ,date_format(fd_moddate,'%Y-%m-%d %T') fd_moddate
from
    tbl_company_biz_phone
where 1=1
    and use_yn='Y'
    and fk_company=#{fkCompany}
    and biz_phone_num=#{bizPhoneNum}
limit 1
</script>
    """})
    public DaoBizPhone findOne(DaoBizPhone req);

    @Select({"""
<script>
/*RepoBizPhone.findDefaultOne*/
select
    *
from tbl_company_biz_phone
where 1=1
    and use_yn='Y'
    and default_yn='Y'
    and fk_company=#{fkCompany}
</script>        
    """})
    DaoBizPhone findDefaultOne(DaoBizPhone req);

    @Insert({"""
<script>
/*RepoBizPhone.create*/
insert into tbl_company_biz_phone (
     solution_type
    ,user_type
    ,fk_company
    ,biz_phone_name
    ,biz_phone_num
    ,dnis
    ,reg_status
    ,doc_status
    ,doc_main_status
    ,call_forward_status
    ,default_yn
    ,inbound_yn
    ,outbound_yn
    ,phone_yn
    ,sms_yn
    ,enable_yn
    ,use_yn
    ,use_order
    ,file_path_main_num
    ,file_path_join_cert
    ,file_path_company_cert
    ,file_path_attorney
    ,file_path_trade_cert
    ,file_path_employ_cert
    ,biz_cate_step_1
    ,biz_cate_step_2
    ,biz_cate_step_3
    ,fk_writer
    ,fd_regdate
) values (
     ifnull(#{solutionType},'B11')
    ,ifnull(#{userType},'B2001')
    ,#{fkCompany}
    ,#{bizPhoneName}
    ,#{bizPhoneNum}
    ,#{dnis}
    ,#{regStatus}
    ,#{docStatus}
    ,#{docMainStatus}
    ,#{callForwardStatus}
    ,ifnull(#{defaultYn},'Y')
    ,ifnull(#{inboundYn},'Y')
    ,ifnull(#{outboundYn},'Y')
    ,ifnull(#{phoneYn},'Y')
    ,ifnull(#{smsYn},'Y')
    ,ifnull(#{enableYn},'Y')
    ,ifnull(#{useYn},'Y')
    ,ifnull(#{useOrder},1)
    ,#{filePathMainNum}
    ,#{filePathJoinCert}
    ,#{filePathCompanyCert}
    ,#{filePathAttorney}
    ,#{filePathTradeCert}
    ,#{filePathEmployCert}
    ,ifnull(#{bizCateStep1},'구분1')
    ,ifnull(#{bizCateStep2},'구분2')
    ,ifnull(#{bizCateStep3},'구분3')
    ,#{fkCompany}
    ,now()
)
</script>
    """})
    @Options(useGeneratedKeys=true,keyProperty="pkCompanyPhone")
    public void create(DaoBizPhone req);

    @Update({"""
<script>
/*RepoBizPhone.update*/
update tbl_company_biz_phone set
     solution_type=#{solutionType}
    ,user_type=#{userType}
    ,fk_company=#{fkCompany}
    ,biz_phone_name=#{bizPhoneName}
    ,biz_phone_num=#{bizPhoneNum}
    ,dnis=#{dnis}
    ,reg_status=#{regStatus}
    ,doc_status=#{docStatus}
    ,doc_main_status=#{docMainStatus}
    ,call_forward_status=#{callForwardStatus}
    ,default_yn=#{defaultYn}
    ,inbound_yn=#{inboundYn}
    ,outbound_yn=#{outboundYn}
    ,phone_yn=#{phoneYn}
    ,sms_yn=#{smsYn}
    ,enable_yn=#{enableYn}
    ,use_yn=#{useYn}
    ,use_order=#{useOrder}
    ,file_path_main_num=#{filePathMainNum}
    ,file_path_join_cert=#{filePathJoinCert}
    ,file_path_company_cert=#{filePathCompanyCert}
    ,file_path_attorney=#{filePathAttorney}
    ,file_path_trade_cert=#{filePathTradeCert}
    ,file_path_employ_cert=#{filePathEmployCert}
    ,fk_modifier=values(fk_company)
    ,fd_moddate=now()
where
    use_yn='Y'
    and fk_company=#{fkCompany}
    and biz_phone_num=#{bizPhoneNum}
</script>
    """})
    public void update(DaoBizPhone req);

    @Update({"""
<script>
/*RepoBizPhone.updateDnisWithNull*/
update tbl_company_biz_phone set
    dnis=null
where
    use_yn='Y'
    and fk_company=#{fkCompany}
    and dnis=#{dnis}
</script>
    """})
    public void updateDnisWithNull(Long fkCompany, String dnis);

    @Delete({"""
<script>
/*RepoBizPhone.delete*/
delete from tbl_company_biz_phone
where
    fk_company=#{fkCompany}
    and biz_phone_num=#{bizPhoneNum}
    and dnis=#{dnis}
</script>
    """})
    public void delete(Long fkCompany, String bizPhoneNum, String dnis);
}




















