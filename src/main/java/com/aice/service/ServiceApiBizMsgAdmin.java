package com.aice.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.aice.conf.ConfFeign;
import com.aice.dao.bizMsg.BizMsgRequestData;

@FeignClient(
        name="api-biz-msg-kr"
        ,url="${biz-msg-kr.hostname}"
        ,decode404=true
        ,configuration={ConfFeign.class}
)
public interface ServiceApiBizMsgAdmin {
    // 스윗트래커 비즈엠 관리자 api 연동
    // [개발서버] https://dev-alimtalk-api.bizmsg.kr:1443
    // [운영서버] https://alimtalk-api.bizmsg.kr

    // 발신 프로필 추가시 인증번호 요청
    // /v2/sender/token
    @GetMapping(
        value="/v2/sender/token"
    )
    Map<String,Object> senderToken(
        @RequestParam(value="plusFriend") String plusFriend //@스윗트래커
        ,@RequestParam(value="phoneNumber") String phoneNumber //고객사 관리자 핸드폰 번호
    );

    // 발신프로필 카테고리 전체 조회
    @GetMapping(
        value="/v2/sender/category/all"
    )
    Map<String,Object> senderCategoryAll();

    // 발신프로필 카테고리 조회
    // /v2/sender/category

    // 발신 프로필 추가 (V3)
    @PostMapping(
        value="/v3/sender/create"
    )
    Map<String,Object> senderCreate(
        @RequestHeader(value="userId") String userId
        ,@RequestParam String plusFriend
        ,@RequestParam String phoneNumber
        ,@RequestParam String token
        ,@RequestParam String categoryCode
    );

    // 발신 프로필 삭제
    // /v2/sender/delete

    // 발신 프로필 조회 (V3)
    // /v3/sender

    // 발신 프로필 목록 조회
    @GetMapping(
        value="/v2/sender/list"
    )
    Map<String,Object> senderList(
        @RequestHeader(value="userId") String userId
    );

    // 발신 프로필 문자 사용 여부 설정
    // /v2/sender/set/sms

    // 발신프로필 휴면 해제
    // /v2/sender/recover

    // 템플릿 등록
    @PostMapping(
        value="/v2/template/create"
    )
    List<Map<String,Object>> templateCreate(
        @RequestHeader("userId") String userId
        ,@RequestBody List<BizMsgRequestData> req
    );

    // 템플릿 검수요청
    @PostMapping(
        value="/v2/template/request"
    )
    List<Map<String,Object>> templateRequest(
        @RequestHeader("userId") String userId
        ,@RequestBody List<BizMsgRequestData> req
    );

    // 템플릿 검수요청(파일첨부)
    // /v2/template/request_with_file

    // 템플릿 검수요청 취소
    @PostMapping(
        value="/v2/template/cancel_request"
    )
    List<Map<String,Object>> templateCancelRequest(
        @RequestHeader("userId") String userId
        ,@RequestBody List<BizMsgRequestData> req
    );

    // 템플릿 승인 취소
    @PostMapping(
        value="/v2/template/cancel_approval"
    )
    List<Map<String,Object>> templateCancelApproval(
        @RequestHeader("userId") String userId
        ,@RequestBody List<BizMsgRequestData> req
    );

    // 템플릿 상태조회
    // /v2/template

    // 템플릿 수정
    @PostMapping(
        value="/v2/template/update"
    )
    Map<String,Object> templateUpdate(
        @RequestHeader("userId") String userId
        ,@RequestBody BizMsgRequestData req
    );

    // 템플릿 삭제
    // /v2/template/delete
    @PostMapping(
        value="/v2/template/delete"
    )
    List<Map<String,Object>> templateDelete(
        @RequestHeader("userId") String userId
        ,@RequestBody List<BizMsgRequestData> req
    );

    // 템플릿 목록조회
    @GetMapping(
        value="/v2/template/list"
    )
    Map<String,Object> templateList(
        @RequestHeader(value="userId") String userId
        ,@RequestParam(value="senderKey") String senderKey
    );

    // 템플릿 카테고리 전체조회
    @GetMapping(
        value="/v2/template/category/all"
    )
    Map<String,Object> templateCategoryAll();

    // 템플릿 카테고리 조회
    // /v2/template/category

    // 템플릿 휴면 해제
    // /v2/template/dormant/release

    // 템플릿 사용 중지
    // /v2/template/stop

    // 템플릿 사용 중지 해제
    // /v2/template/reuse

    // 알림톡 상단 이미지 업로드
    // /v1/image/alimtalk/template

    // 알림톡 아이템 이미지 업로드
    // /v1/image/alimtalk/itemHighlight

    // 템플릿 메시지유형 전환(BA -> AD, EX -> MI)
    // /v2/ template/convertAddCh

    // 최근 변경 템플릿 목록 조회
    // /v2/template/last_modified

    // 그룹 목록 조회
    // /v2/sender/group

    // 그룹에 발신 프로필 추가
    // /v2/sender/group/add

    // 그룹에 발신 프로필 삭제
    // /v2/sender/group/remove

    /*
     * 문자메시지 발신번호 관리
문자메시지 발신번호 사전 등록을 위해 비즈엠의 계정명과 계정키가 필수로 입력 되어야 하며
비즈엠 홈페이지(https://www.bizmsg.kr) [내 정보] 페이지에서 확인 가능합니다.
휴대폰 인증 또는 ARS 인증 방식의 경우 등록시 바로 승인되며, 서류 인증의 경우 관리자
심사를 거친 후 승인됩니다.
• 사전에 등록한 발신번호로만 문자메시지 전송이 가능합니다.
• 발신번호는 전기통신사업법 제 84 조 2 에 의거, 발신번호 사전등록제에 따라 본인인증 과정을
거쳐야합니다.
• 타인 명의의 발신번호 사용 시 불이익이 발생할 수 있습니다.
• 휴대폰 인증번호 / ARS 인증 전화를 수신할 수 없는 대표번호, 특수번호인 경우 ‘통신서비스
이용증명원'을 제출하여야 하며 가입 통신사에서 발급받을 수 있습니다.
• 서류 인증 요청 시, 영업일 기준 2 일 이내에 심사 후 처리해드립니다
     */
    // 발신번호 추가시 인증번호 요청
    // /v2/callback/auth

    // 발신번호 추가
    @PostMapping(
        value="/v2/callback/create"
        ,consumes=MediaType.MULTIPART_FORM_DATA_VALUE
    )
    Map<String,Object> callbackCreate(
        @RequestHeader(value="userId") String userId
        ,@RequestHeader(value="userkey") String userkey
        ,@RequestPart(required=true) MultipartFile file
        ,@RequestPart String callback
        ,@RequestPart(required=false) String token
        ,@RequestPart String comments
        ,@RequestPart String method
    );

    // 발신번호 삭제
    @PostMapping(
        value="/v2/callback/delete"
    )
    List<Map<String,Object>> callbackDelete(
        @RequestHeader("userId") String userId
        ,@RequestHeader("userkey") String userkey
        ,@RequestBody List<BizMsgRequestData> req
    );

    // 발신번호 상태조회
    // /v2/callback

    // 발신번호 목록조회
    @GetMapping(
        value="/v2/callback/list"
    )
    Map<String,Object> callbackList(
        @RequestHeader(value="userId") String userId
        ,@RequestHeader(value="userkey") String userkey
        ,@RequestParam(value="page") String page
        ,@RequestParam(value="rowCount",required=false) String rowCount
    );
}




























