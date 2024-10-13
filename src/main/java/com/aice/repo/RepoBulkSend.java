package com.aice.repo;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;

import com.aice.dao.bulksend.DaoBulkSendPlan;

public interface RepoBulkSend {
    @Insert({"""
<script>
/*RepoBulkSend.upsertBulkPlan*/
insert into tbl_bulk_send_plan (
    fk_company
    ,fk_staff_ai
    ,number_from
    ,full_dnis
    ,dnis
    ,reserve_yn
    ,reserve_dt
    ,channel_type
    ,msg_title
    ,msg_body
    ,path_attach
    ,cnt_user
    ,agree_ad_yn
    ,fk_staff
    ,chk_level
    ,memo
    ,send_status
    ,send_dt_from
    ,fk_writer
    ,fd_regdate
    <if test='pkBulkSendPlan != null and \"\".equals(pkBulkSendPlan) == false'>
    ,pk_bulk_send_plan
    </if>
) values (
    #{fkCompany}
    ,#{fkStaffAi}
    ,#{numberFrom}
    ,#{fullDnis}
    ,#{dnis}
    ,ifnull(#{reserveYn},'N')
    ,str_to_date(#{reserveDt},'%Y%m%d%H%i%s')
    ,#{channelType}
    ,#{msgTitle}
    ,#{msgBody}
    ,#{pathAttach}
    ,#{cntUser}
    ,ifnull(#{agreeAdYn},'N')
    ,#{fkStaff}
    ,ifnull(#{chkLevel},'C1003')
    ,#{memo}
    ,ifnull(#{sendStatus},'INIT')
    ,now()
    ,#{fkStaffAi}
    ,now()
    <if test='pkBulkSendPlan != null and \"\".equals(pkBulkSendPlan) == false'>
    ,#{pkBulkSendPlan}
    </if>
)
on duplicate key update
    fk_company=values(fk_company)
    ,fk_staff_ai=values(fk_staff_ai)
    ,number_from=values(number_from)
    ,full_dnis=values(full_dnis)
    ,dnis=values(dnis)
    ,reserve_yn=values(reserve_yn)
    ,reserve_dt=values(reserve_dt)
    ,channel_type=values(channel_type)
    ,msg_title=values(msg_title)
    ,msg_body=values(msg_body)
    ,path_attach=values(path_attach)
    ,cnt_user=values(cnt_user)
    ,agree_ad_yn=values(agree_ad_yn)
    ,fk_staff=values(fk_staff)
    ,chk_level=values(chk_level)
    ,memo=values(memo)
    ,send_status=values(send_status)
    ,send_dt_from=values(send_dt_from)
    ,send_dt_to=str_to_date(#{sendDtTo},'%Y-%m-%d %T')
    ,fk_modifier=values(fk_staff_ai)
    ,fd_moddate=now()
</script>
    """})
    @Options(useGeneratedKeys=true,keyProperty="pkBulkSendPlan")
    public void upsertBulkPlan(DaoBulkSendPlan daoBulkSendPlan);

    @Insert({"""
<script>
/*RepoBulkSend.upsertBulkMember*/
insert into tbl_bulk_send_member (
    fk_bulk_send_plan
    ,number_to
    ,fk_customer
    ,channel_type
    ,send_log_id
    ,send_status
    ,send_dt_from
    ,send_dt_to
    ,cnt_retry
    ,fk_writer
    ,fd_regdate
) values
<foreach collection='listMember' item='itemMember' separator=',' >
(
     #{pkBulkSendPlan}
    ,#{itemMember.numberTo}
    ,#{itemMember.fkCustomer}
    ,#{channelType}
    ,#{itemMember.sendLogId}
    ,ifnull(#{itemMember.sendStatus},'INIT')
    ,str_to_date(#{itemMember.sendDtFrom},'%Y-%m-%d %T')
    ,str_to_date(#{itemMember.sendDtTo},'%Y-%m-%d %T')
    ,ifnull(#{itemMember.cntRetry},0)
    ,#{fkStaffAi}
    ,now()
)
</foreach>
on duplicate key update
     fk_customer=values(fk_customer)
    ,channel_type=values(channel_type)
    ,send_log_id=values(send_log_id)
    ,send_status=values(send_status)
    ,send_dt_from=values(send_dt_from)
    ,send_dt_to=values(send_dt_to)
    ,cnt_retry=values(cnt_retry)
    ,fk_modifier=#{fkStaffAi}
    ,fd_moddate=now()
</script>
    """})
    public void upsertBulkMember(DaoBulkSendPlan daoBulkSendPlan);

    @Select({"""
<script>
/*RepoBulkSend.findAllPagingBulkPlan*/
select
    pk_bulk_send_plan
    ,fk_company
    ,fk_staff_ai
    ,number_from
    ,full_dnis
    ,dnis
    ,reserve_yn
    ,date_format(reserve_dt,'%Y-%m-%d %T') reserve_dt
    ,channel_type
    ,msg_title
    ,msg_body
    ,path_attach
    ,cnt_user
    ,agree_ad_yn
    ,fk_staff
    ,chk_level
    ,memo
    ,send_status
    ,date_format(send_dt_from,'%Y-%m-%d %T') send_dt_from
    ,date_format(send_dt_to,'%Y-%m-%d %T') send_dt_to
    ,fk_writer
    ,date_format(fd_regdate,'%Y-%m-%d %T') fd_regdate
    ,fk_modifier
    ,date_format(fd_moddate,'%Y-%m-%d %T') fd_moddate
from
    tbl_bulk_send_plan
where 1=1
    <if test='req.pkBulkSendPlan != null and \"\".equals(req.pkBulkSendPlan) == false'>
    and pk_bulk_send_plan=#{req.pkBulkSendPlan}
    </if>
    <if test='req.fkCompany != null and \"\".equals(req.fkCompany) == false'>
    and fk_company=#{req.fkCompany}
    </if>
    <if test='req.channelType != null and \"\".equals(req.channelType) == false'>
    and channel_type=#{req.channelType}
    </if>
    <if test='req.sendStatus != null and \"\".equals(req.sendStatus) == false'>
    and send_status=#{req.sendStatus}
    </if>
    <if test='req.fkStaff != null and \"\".equals(req.fkStaff) == false'>
    and fk_staff=#{req.fkStaff}
    </if>
    <if test='req.sendDtFrom != null and \"\".equals(req.sendDtFrom) == false'>
    and send_dt_from <![CDATA[ >= ]]> str_to_date(#{req.sendDtFrom},'%Y-%m-%d %T')
    </if>
    <if test='req.sendDtTo != null and \"\".equals(req.sendDtTo) == false'>
    and send_dt_from <![CDATA[ < ]]> str_to_date(#{req.sendDtTo},'%Y-%m-%d %T')
    </if>
<if test='pageable.sort.empty == false'>
    <foreach collection='pageable.sort.orders' item='itemOrder' open='order by' seperator=',' >
        ${itemOrder.property} ${itemOrder.direction}
    </foreach>
</if>
limit
    #{pageable.offset},#{pageable.pageSize}
</script>
    """})
    public List<DaoBulkSendPlan> findAllPagingBulkPlan(
        @Param("pageable") Pageable pageable
        ,@Param("req") DaoBulkSendPlan req
    );

    @Select({"""
<script>
/*RepoBulkSend.countAllPagingBulkPlan*/
select
    count(*)
from
    tbl_bulk_send_plan
where 1=1
    <if test='req.pkBulkSendPlan != null and \"\".equals(req.pkBulkSendPlan) == false'>
    and pk_bulk_send_plan=#{req.pkBulkSendPlan}
    </if>
    <if test='req.fkCompany != null and \"\".equals(req.fkCompany) == false'>
    and fk_company=#{req.fkCompany}
    </if>
    <if test='req.channelType != null and \"\".equals(req.channelType) == false'>
    and channel_type=#{req.channelType}
    </if>
    <if test='req.sendStatus != null and \"\".equals(req.sendStatus) == false'>
    and send_status=#{req.sendStatus}
    </if>
    <if test='req.fkStaff != null and \"\".equals(req.fkStaff) == false'>
    and fk_staff=#{req.fkStaff}
    </if>
    <if test='req.sendDtFrom != null and \"\".equals(req.sendDtFrom) == false'>
    and send_dt_from <![CDATA[ >= ]]> str_to_date(#{req.sendDtFrom},'%Y-%m-%d %T')
    </if>
    <if test='req.sendDtTo != null and \"\".equals(req.sendDtTo) == false'>
    and send_dt_from <![CDATA[ < ]]> str_to_date(#{req.sendDtTo},'%Y-%m-%d %T')
    </if>
</script>
    """})
    public int countAllPagingBulkPlan(
        @Param("req") DaoBulkSendPlan req
    );
}


















