# AI를 활용한 협업 메신저

이 프로젝트는 협업이 필요한 기업을 대상으로, 메신저 대화 중 놓치기 쉬운 정보를 보완하기 위해 OpenAI API를 활용해 특정 기간의 대화 내용을 요약해주는 협업 지원형 메신저 서비스입니다.

- 담당 영역 : 메신저 탭(WebSocket 기반 실시간 메시징)

## 핵심 기능
- 실시간 메신저 기능(WebSocket 기반)
- 특정 기간의 메신저 대화 내용을 AI로 요약하는 기능
- 프로젝트 진행 기간을 확인할 수 있는 프로젝트 관리 탭
- 업무 상태(예정/진행중/완료)를 확인할 수 있는 업무 관리 탭
- 공지 및 정보 공유를 위한 게시판 기능
- 인사 관리자가 사원을 관리할 수 있는 인사 관리 탭

## 메신저 기능 상세 (담당 영역)
- WebSocket을 사용해 실시간 메시지 송수신 기능을 구현
- 사용자 간 1:1 메시지 흐름을 기준으로 메신저 구조 설계
- 메시지 전송/수신 시 서버를 통해 메시지 상태를 관리
- 특정 기간의 대화 로그를 조회하여 OpenAI API로 요약 요청
- 요약 결과를 메신저 화면에서 확인할 수 있도록 연동

## 기술 스택
- Backend: Java, Spring Boot, Spring Data JPA, WebSocket
- AI: OpenAI API
- DB: Oracle (Oracle Cloud)
- Build/Tools: Gradle, Git

## 실행 방법
### Backend
- 요구사항: Java 21, Gradle
- 설정: application.properties (Oracle Cloud DB 접속 정보 / OpenAI API Key)
- 실행: ./gradlew bootRun

## 아키텍처 개요
- Client(웹) ↔ Server(Spring Boot)
- 실시간 메시지: WebSocket
- 데이터 저장: Oracle Cloud DB
- 요약 기능: OpenAI API 호출 → 요약 결과 저장/전달

## 설계 선택
- WebSocket 선택 이유: 실시간 양방향 통신이 필요한 메신저 특성에 적합
- Oracle 선택 이유: 사용자/업무/메시지 로그 등 정합성이 중요한 관계형 데이터 중심
- Redis/RabbitMQ 미도입: 프로젝트 범위와 학습 목표를 고려해 운영/복잡도 증가를 피하고 핵심 흐름 구현에 집중

## 트러블슈팅
- 문제: WebSocket 연결이 끊겼을 때 메시지 유실 가능
- 접근: 재연결/예외 처리 흐름 정리
- 결과: 사용자 경험 저하를 줄이기 위한 처리 추가

## 아쉬운 점 및 개선 아이디어
- 단체 채팅 확장 시 메시지 처리량 증가에 대비해 Redis 캐시 또는 메시지 큐(RabbitMQ) 도입 검토
- 요약 기능의 프롬프트/요약 기준 고도화
