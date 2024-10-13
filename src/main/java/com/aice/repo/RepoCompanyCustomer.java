package com.aice.repo;

import com.aice.dao.DaoCompanyCustomer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoCompanyCustomer {
    @Select({"""
<script>
SELECT
    pk_company_customer,
    fd_customer_name,
    fd_active_state,
    fd_company_dept,
    fd_customer_mobile,
    fd_customer_phone,
    contact_channel_from,
    contact_date_from,
    contact_ai_yn
FROM tbl_company_customer
WHERE 1=1
    <if test='searchCriteria.fkCompany != null and "".equals(searchCriteria.fkCompany) == false'>
        AND fk_company = #{searchCriteria.fkCompany}
    </if>
    <if test='searchCriteria.fdCustomerName != null and "".equals(searchCriteria.fdCustomerName) == false'>
        AND fd_customer_name LIKE CONCAT('%', #{searchCriteria.fdCustomerName}, '%')
    </if>
    <if test='searchCriteria.fdCustomerEmail != null and "".equals(searchCriteria.fdCustomerEmail) == false'>
        AND fd_customer_email LIKE CONCAT('%', #{searchCriteria.fdCustomerEmail}, '%')
    </if>
    <if test='searchCriteria.fdCustomerMobile != null and "".equals(searchCriteria.fdCustomerMobile) == false'>
        AND fd_customer_mobile LIKE CONCAT('%', #{searchCriteria.fdCustomerMobile}, '%')
    </if>
    <if test='searchCriteria.fdCustomerPhone != null and "".equals(searchCriteria.fdCustomerPhone) == false'>
        AND fd_customer_phone LIKE CONCAT('%', #{searchCriteria.fdCustomerPhone}, '%')
    </if>
    <if test='pageable.sort.empty == false'>
    <foreach collection='pageable.sort.orders' item='itemOrder' open='ORDER BY' separator=','>
        ${itemOrder.property} ${itemOrder.direction}
    </foreach>
    </if>
LIMIT #{pageable.pageSize} OFFSET #{pageable.offset}
</script>
    """})
    List<DaoCompanyCustomer> findCustomers(
            @Param("pageable") Pageable pageable,
            @Param("searchCriteria") DaoCompanyCustomer searchCriteria
    );

    @Select({"""
<script>
SELECT
    count(*)
FROM tbl_company_customer
WHERE 1=1
    <if test='searchCriteria.fkCompany != null and "".equals(searchCriteria.fkCompany) == false'>
        AND fk_company = #{searchCriteria.fkCompany}
    </if>
    <if test='searchCriteria.fdCustomerName != null and "".equals(searchCriteria.fdCustomerName) == false'>
        AND fd_customer_name LIKE CONCAT('%', #{searchCriteria.fdCustomerName}, '%')
    </if>
    <if test='searchCriteria.fdCustomerEmail != null and "".equals(searchCriteria.fdCustomerEmail) == false'>
        AND fd_customer_email LIKE CONCAT('%', #{searchCriteria.fdCustomerEmail}, '%')
    </if>
    <if test='searchCriteria.fdCustomerMobile != null and "".equals(searchCriteria.fdCustomerMobile) == false'>
        AND fd_customer_mobile LIKE CONCAT('%', #{searchCriteria.fdCustomerMobile}, '%')
    </if>
    <if test='searchCriteria.fdCustomerPhone != null and "".equals(searchCriteria.fdCustomerPhone) == false'>
        AND fd_customer_phone LIKE CONCAT('%', #{searchCriteria.fdCustomerPhone}, '%')
    </if>
</script>
    """})
    int countCustomers(@Param("searchCriteria") DaoCompanyCustomer searchCriteria);
}
