# configManager

## 📋 프로젝트 소개
`configManager`는 다양한 설정(회원, AI 등)을 **RESTful API**로 관리하는 서비스입니다. 이 프로젝트는 CRUD 기능을 통해 설정 데이터를 효율적으로 처리하며, 외부 API와의 연동을 통해 필요한 정보를 동적으로 가져올 수 있도록 설계되었습니다.

---

## 🚀 주요 기능
- **회원 설정 관리**: 사용자 정보를 생성, 조회, 수정, 삭제(CRUD) 기능 제공.
- **AI 설정 관리**: AI 모델 및 파라미터 제어.
- **외부 API 연동**: 외부 데이터와 통신하여 설정에 필요한 정보 로딩.
- **RESTful API 제공**: 설정 관리 작업을 위한 표준화된 API 인터페이스 제공.

---

## 🛠️ 기술 스택
- **Backend**: Java, Spring Boot
- **Build Tool**: Maven
- **Database**: MariaDB, MongoDB
- **API 연동**: 외부 API와 데이터 통합
- **테스트 도구**: JUnit, Postman

---

## 📖 API 사용법

### 1. 회원 설정 생성 (POST)
```http
POST /config-manager/api/config/user
Content-Type: application/json
{
  "username": "example",
  "email": "user@example.com"
}
```

### 2. 회원 설정 조회 (GET)
```http
GET /config-manager/api/config/user/{userId}
```

### 4. Health Check API (GET)
```http
GET /config-manager/health/check
```

---

## ⚙️ 설치 및 실행 방법

1. **프로젝트 클론**
   ```bash
   git clone git@github.com:devnine-codes/configManager.git
   cd configManager
   ```

2. **의존성 설치 및 빌드**
   ```bash
   mvn clean install
   ```

3. **애플리케이션 실행**
   기본 포트는 `8151`로 설정됩니다.
   ```bash
   mvn spring-boot:run
   ```

4. **API 테스트**  
   Postman을 이용해 제공된 API를 테스트할 수 있습니다.

---

## 📄 기여 방법
1. 이슈 또는 버그를 [Issues](https://github.com/devnine-codes/configManager/issues)에 등록하세요.
2. 새로운 기능 제안은 Pull Request로 제출해주세요.

---

## 🌐 주요 설정
- **Context Path**: `/config-manager`
- **Health Check**: `/config-manager/health/check`
- **기본 포트**: `8151`

---

## 📧 문의
- Email: dev.nine0@gmail.com
