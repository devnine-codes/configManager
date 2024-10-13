package com.aice.repo;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;

import com.aice.dao.DaoConfigManager;

@Mapper
public interface RepoAiConfNoti {
    @Select({"""
<script>/*RepoAiConfNoti.findAllPaging*/
select
    aa.pk_ai_conf_noti seq_ai_conf_noti
    ,aa.fk_company seq_company
    ,aa.fk_company_staff_ai seq_staff_ai
    ,aa.check_type
    ,aa.sms_yn
    ,aa.kakao_yn
    ,aa.push_yn
    ,aa.msg_body
    ,aa.use_yn
    ,aa.fk_writer
    ,aa.fd_regdate
    ,aa.fk_modifier
    ,aa.fd_moddate
from (
    select
        a.*
    from
        tbl_ai_conf_noti a
    where 1=1
        and a.use_yn='Y'
    <if test='reqApi.seqCompany != null and \"\".equals(reqApi.seqCompany) == false'>
        and a.fk_company=#{reqApi.seqCompany}
    </if>
    <if test='reqApi.seqStaffAi != null and \"\".equals(reqApi.seqStaffAi) == false'>
        and a.fk_company_staff_ai=#{reqApi.seqStaffAi}
    </if>
    <if test='reqApi.checkType != null and \"\".equals(reqApi.checkType) == false'>
        and a.check_type=#{reqApi.checkType}
    </if>
    <if test='pageable.sort.empty == false'>
    <foreach collection='pageable.sort.orders' item='itemOrder' open='order by' seperator=',' >
        ${itemOrder.property} ${itemOrder.direction}
    </foreach>
    </if>
    limit 2147483647
) aa
limit
    #{pageable.offset},#{pageable.pageSize}
</script>"""})
    public List<DaoConfigManager> findAllPaging(
            @Param("pageable") Pageable pageable
            ,@Param("reqApi") DaoConfigManager reqApi
        );

    @Select({"""
<script>/*RepoAiConfNoti.findAllPagingCnt*/
select
    count(*)
from (
    select
        a.*
    from
        tbl_ai_conf_noti a
    where 1=1
        and a.use_yn='Y'
    <if test='reqApi.seqCompany != null and \"\".equals(reqApi.seqCompany) == false'>
        and a.fk_company=#{reqApi.seqCompany}
    </if>
    <if test='reqApi.seqStaffAi != null and \"\".equals(reqApi.seqStaffAi) == false'>
        and a.fk_company_staff_ai=#{reqApi.seqStaffAi}
    </if>
    <if test='reqApi.checkType != null and \"\".equals(reqApi.checkType) == false'>
        and a.check_type=#{reqApi.checkType}
    </if>
    limit 2147483647
) aa
</script>"""})
    public int findAllPagingCnt(
            @Param("reqApi") DaoConfigManager reqApi
        );

    @Select({"""
<script>/*RepoAiConfNoti.findOne*/
select
    aa.pk_ai_conf_noti seq_ai_conf_noti
    ,aa.fk_company seq_company
    ,aa.fk_company_staff_ai seq_staff_ai
    ,aa.check_type
    ,aa.sms_yn
    ,aa.kakao_yn
    ,aa.push_yn
    ,aa.msg_body
    ,aa.use_yn
    ,aa.fk_writer
    ,aa.fd_regdate
    ,aa.fk_modifier
    ,aa.fd_moddate
from (
    select
        a.*
    from
        tbl_ai_conf_noti a
    where 1=1
        and a.use_yn='Y'
    <if test='reqApi.seqCompany != null and \"\".equals(reqApi.seqCompany) == false'>
        and a.fk_company=#{reqApi.seqCompany}
    </if>
    <if test='reqApi.seqStaffAi != null and \"\".equals(reqApi.seqStaffAi) == false'>
        and a.fk_company_staff_ai=#{reqApi.seqStaffAi}
    </if>
    <if test='reqApi.checkType != null and \"\".equals(reqApi.checkType) == false'>
        and a.check_type=#{reqApi.checkType}
    </if>
    limit 1
) aa
</script>"""})
    public DaoConfigManager findOne(
            @Param("reqApi") DaoConfigManager reqApi
        );

    @Insert({"""
<script>/*RepoAiConfNoti.upsert*/
insert into tbl_ai_conf_noti (
     fk_company
    ,fk_company_staff_ai
    ,check_type
    ,sms_yn
    ,kakao_yn
    ,push_yn
    ,msg_body
    ,use_yn
    ,fk_writer
    ,fd_regdate
    ,fk_modifier
    ,fd_moddate
    <if test='seqAiConfNoti != null and \"\".equals(seqAiConfNoti) == false'>
    ,pk_ai_conf_noti
    </if>
) values (
     #{seqCompany}
    ,#{seqStaffAi}
    ,ifnull(#{checkType},'CUSTOMER')
    ,ifnull(#{smsYn},'Y')
    ,ifnull(#{kakaoYn},'Y')
    ,ifnull(#{pushYn},'Y')
    ,#{msgBody}
    ,ifnull(#{useYn},'Y')
    ,0
    ,now()
    ,0
    ,now()
    <if test='seqAiConfNoti != null and \"\".equals(seqAiConfNoti) == false'>
    ,#{seqAiConfNoti}
    </if>
)
on duplicate key update
     fk_company=values(fk_company)
    ,fk_company_staff_ai=values(fk_company_staff_ai)
    ,check_type=values(check_type)
    ,sms_yn=values(sms_yn)
    ,kakao_yn=values(kakao_yn)
    ,push_yn=values(push_yn)
    ,msg_body=values(msg_body)
    ,use_yn=values(use_yn)
    ,fk_modifier=0
    ,fd_moddate=now()
</script>"""})
    @Options(useGeneratedKeys=true,keyProperty="seqAiConfNoti")
    public void upsert(DaoConfigManager reqApi);
}
























