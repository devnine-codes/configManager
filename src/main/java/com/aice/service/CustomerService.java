package com.aice.service;

import com.aice.dao.DaoCompanyCustomer;
import com.aice.dao.ResponseApi;
import com.aice.repo.RepoCompanyCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    RepoCompanyCustomer repoCompanyCustomer;

    /**
     * 고객사의 고객리스트 조회
     * @param companySeq
     * @param customerName
     * @param customerEmail
     * @param customerMobile
     * @param customerPhone
     * @param pageable
     * @return
     */
    public ResponseEntity<ResponseApi> findCustomers(
            Long companySeq,
            String customerName,
            String customerEmail,
            String customerMobile,
            String customerPhone,
            Pageable pageable
    ) {
        DaoCompanyCustomer searchCriteria = new DaoCompanyCustomer();
        searchCriteria.setFkCompany(companySeq);
        searchCriteria.setFdCustomerName(customerName);
        searchCriteria.setFdCustomerEmail(customerEmail);
        searchCriteria.setFdCustomerMobile(customerMobile);
        searchCriteria.setFdCustomerPhone(customerPhone);

        try {
            List<DaoCompanyCustomer> customers = repoCompanyCustomer.findCustomers(pageable, searchCriteria);
            int totalCustomers = repoCompanyCustomer.countCustomers(searchCriteria);
            Page<DaoCompanyCustomer> customerPage = new PageImpl<>(customers, pageable, totalCustomers);

            return ResponseEntity.ok(ResponseApi.success("Customers found.", customerPage));
        } catch (Exception e) {
            log.error("Error finding customers. searchCriteria: {}, error: {}", searchCriteria.toJsonTrim(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseApi.error("Error finding customers.", e.getMessage()));
        }
    }
}
