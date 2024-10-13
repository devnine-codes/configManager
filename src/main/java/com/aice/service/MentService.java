package com.aice.service;

import com.aice.dao.ment.DaoMent;
import com.aice.dao.ResponseApi;
import com.aice.enums.EnumEntityType;
import com.aice.repo.RepoCompanyStaff;
import com.aice.repo.RepoMent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Table : tbl_ai_conf_intro
 * description : 등록된 인사말, 종료인사말을 조회한다.
 */
@Slf4j
@Service
public class MentService {
    @Autowired
    RepoMent repoMent;
    @Autowired
    RepoCompanyStaff repoCompanyStaff;

    /**
     * 등록
     * @param entityType
     * @param id
     * @param daoMent
     * @return
     */
    public ResponseEntity<?> addMessage(
            String entityType
            ,Long id
            ,DaoMent daoMent
    ) {
        if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
            daoMent.setFkCompany(id);
            daoMent.setFkCompanyStaffAi(repoCompanyStaff.findMasterStaffByCompany(id));
        } else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
            daoMent.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
            daoMent.setFkCompanyStaffAi(id);
        } else {
            log.warn("Invalid entityType: {}", entityType);
            return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
        }

        try {
            DaoMent item = repoMent.findByCompanyAndStaff(daoMent);

            if (item != null) {
                log.warn("Already exists. req: {}, entityType:{}, id: {}", item.toJsonTrim(), entityType, id);
                return ResponseEntity.ok(ResponseApi.fail("Already exists.", item));
            }

            repoMent.insert(daoMent);
            log.info("Success. req: {}, entityType: {}, id: {}", daoMent.toJsonTrim(), entityType, id);
            return ResponseEntity.ok(ResponseApi.success("Success.", daoMent));
        } catch (Exception e) {
            log.error("Error while adding message. entityType: {}, id: {}", entityType, id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed.", e.getMessage()));
        }
    }

    /**
     * 조회
     * @param entityType
     * @param id
     * @return
     */
    public ResponseEntity<?> findMessage(
            String entityType
            ,Long id
    ) {
        DaoMent daoMent = new DaoMent();

        if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
            daoMent.setFkCompany(id);
        } else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
            daoMent.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
            daoMent.setFkCompanyStaffAi(id);
        } else {
            log.warn("Invalid entityType: {}", entityType);
            return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
        }

        try {
            List<String> msgList = new ArrayList<>();

            Map<String, Object> map = new HashMap<>();
            DaoMent item = repoMent.findByCompanyAndStaff(daoMent);

            if (item == null) {
                log.warn("No data found. entityType: {}, id: {}", entityType, id);
                return ResponseEntity.ok(ResponseApi.fail("No data found.", daoMent));
            }

            List<String> messageBodies = Arrays.asList(
                    item.getMsgBody(),
                    item.getMsgBody2(),
                    item.getMsgBody3(),
                    item.getMsgBody4(),
                    item.getMsgBody5(),
                    item.getMsgBody6()
            );

            for (String messageBody : messageBodies) {
                if (isNotNullOrEmpty(messageBody)) {
                    msgList.add(messageBody);
                }
            }

            if (isNotNullOrEmpty(item.getMsgOff())) {
                map.put("msgOff", item.getMsgOff());
            }

            if (isNotNullOrEmpty(item.getMsgBefore())) {
                map.put("emergencyMsg", item.getMsgBefore());
            } else {
                map.put("emergencyMsg", "");
            }

            if (!msgList.isEmpty()) {
                map.put("msgIntro", msgList);
            }

            if (map.isEmpty()) {
                log.info("Success: {}, entityType: {}, id: {}", map, entityType, id);
                return ResponseEntity.ok(ResponseApi.success("No messages data", map));
            }

            log.info("Success: {}, entityType: {}, id: {}", map, entityType, id);
            return ResponseEntity.ok(ResponseApi.success("Success", map));
        } catch (Exception e) {
            log.error("Error while retrieving message. entityType: {}, id: {}", entityType, id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to retrieve message", e.getMessage()));
        }
    }

    /**
     * 수정
     * @param entityType
     * @param id
     * @param daoMent
     * @return
     */
    public ResponseEntity<?> setMessage(
            String entityType
            ,Long id
            ,DaoMent daoMent
    ) {
        if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
            daoMent.setFkCompany(id);
            daoMent.setFkCompanyStaffAi(repoCompanyStaff.findMasterStaffByCompany(id));
        } else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
            daoMent.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
            daoMent.setFkCompanyStaffAi(id);
        } else {
            log.warn("Invalid entityType: {}", entityType);
            return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
        }

        try {
            DaoMent item = repoMent.findByCompanyAndStaff(daoMent);

            if (item == null) {
                log.warn("No data found. entityType: {}, id: {}", entityType, id);
                return ResponseEntity.ok(ResponseApi.fail("No data found.", daoMent));
            }

            repoMent.update(daoMent);
            log.info("Success: {}, entityType: {}, id: {}", daoMent.toJsonTrim(), entityType, id);
            return ResponseEntity.ok(ResponseApi.success("Success.", daoMent));
        } catch (Exception e) {
            log.error("Error while adding message. entityType: {}, id: {}", entityType, id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed.", e.getMessage()));
        }
    }

    /**
     * 삭제
     * @param entityType
     * @param id
     * @return
     */
    public ResponseEntity<?> deleteMessage(
            String entityType
            ,Long id
    ) {
        DaoMent daoMent = new DaoMent();
        if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
            daoMent.setFkCompany(id);
            daoMent.setFkCompanyStaffAi(repoCompanyStaff.findMasterStaffByCompany(id));
        } else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
            daoMent.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
            daoMent.setFkCompanyStaffAi(id);
        } else {
            log.warn("Invalid entityType: {}", entityType);
            return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
        }

        try {
            DaoMent item = repoMent.findByCompanyAndStaff(daoMent);

            if (item == null) {
                log.warn("No data found. entityType: {}, id: {}", entityType, id);
                return ResponseEntity.ok(ResponseApi.fail("No data found.", daoMent));
            }

            repoMent.delete(daoMent);
            log.info("Success: {}, entityType: {}, id: {}", daoMent.toJsonTrim(), entityType, id);
            return ResponseEntity.ok(ResponseApi.success("Success.", daoMent));
        } catch (Exception e) {
            log.error("Error while adding message. entityType: {}, id: {}", entityType, id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed.", e.getMessage()));
        }
    }

    /**
     * AI팀에서 02:00에 배치를 돌려 ment정보를 조회해갈때 사용
     * 2024-08-02 모든 회사에 대해 한 건씩 조회 해가서 변경사항이 있는 건에 대해서 정보를 제공하려고 만들었으나
     * 호출하지 않기로 함.
     * @return
     */
    public ResponseEntity<?> listByModifyDate() {
        try {
            List<Map<String, Object>> resultList = new ArrayList<>();
            List<DaoMent> daoMentList = repoMent.listByModifyDate();

            if (daoMentList.isEmpty()) {
                log.info("No data found.");
                return ResponseEntity.ok(ResponseApi.success("No data found.", null));
            }

            for (DaoMent item : daoMentList) {
                Map<String, Object> map = new HashMap<>();
                List<String> msgList = new ArrayList<>();

                List<String> messageBodies = Arrays.asList(
                    item.getMsgBody(),
                    item.getMsgBody2(),
                    item.getMsgBody3(),
                    item.getMsgBody4(),
                    item.getMsgBody5(),
                    item.getMsgBody6()
                );

                for (String messageBody : messageBodies) {
                    if (isNotNullOrEmpty(messageBody)) {
                        msgList.add(messageBody);
                    }
                }

                if (isNotNullOrEmpty(item.getMsgBefore())) {
                    map.put("emergencyMsg", item.getMsgBefore());
                } else {
                    map.put("emergencyMsg", "");
                }

                map.put("msgIntro", msgList);
                map.put("aiStaffSeq", item.getFkCompanyStaffAi());

                resultList.add(map);
            }

            log.info("Success: {}", resultList);
            return ResponseEntity.ok(ResponseApi.success("Success", resultList));
        } catch (Exception e) {
            log.error("Error while retrieving message. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to retrieve message", e.getMessage()));
        }
    }

    private boolean isNotNullOrEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
