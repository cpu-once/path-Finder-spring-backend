# Git Commit Convention & Workflow Guidelines

이 문서는 프로젝트의 일관된 소스코드 관리와 버전 관리를 위한 Git 커밋 메시지 규칙 및 워크플로우를 정의합니다.

## 1. Commit Message Prefix Rules
커밋 메시지 제목은 아래의 Prefix 중 하나로 시작해야 합니다. 이 규칙은 Semantic Versioning과 자동으로 연동될 수 있도록 돕습니다.

- **`feat` (Feature)**
  - 새로운 기능(Feature)을 코드베이스에 추가할 때 사용합니다.
  - (일반적으로 Semantic Versioning에서 **MINOR** 버전 업데이트를 유발합니다.)
- **`fix` (Bug Fix)**
  - 소프트웨어의 결함이나 버그를 수정할 때 사용합니다.
  - (일반적으로 Semantic Versioning에서 **PATCH** 버전 업데이트를 유발합니다.)
- **`refactor`**
  - 버그 수정이나 기능 추가 없이, 내부 구조(아키텍처), 가독성, 효율성 등을 개선하는 코드 리팩토링 시 사용합니다.
- **`chore`**
  - 소스 코드나 테스트 코드를 변경하지 않는 일상적인 유지보수 작업에 사용합니다.
  - 예: 의존성(라이브러리) 업데이트, 빌드 프로세스 변경, 보조 도구 설정 수정 등
- **`docs`**
  - 오직 문서(Documentation)와 관련된 추가 또는 수정 시 사용합니다.
  - 예: README 파일 수정, 주석(Inline comments) 업데이트 등
- **`test`**
  - 누락된 테스트를 구현하거나 기존 테스트 코드를 수정/보완할 때 사용합니다.

---

## 2. Commit Management: Elephant Commit & Squash Commit

프로젝트에서는 개발 과정의 세세한 이력과 최종 메인 브랜치의 깔끔한 이력을 동시에 챙기기 위해 다음의 전략을 사용합니다.

### 2.1 코끼리 커밋 (Elephant Commit / Micro Commit) 나누기
개발(진행 중) 브랜치에서는 작업 단위를 최대한 잘게 쪼개어 자주 커밋하는 것을 권장합니다.
- **목적**: 작업 중간에 문제가 생겼을 때 쉽게 특정 시점(단위)으로 롤백하거나 변경 사항을 추적하기 위함입니다.
- **방법**:
  - 특정 클래스를 생성하거나 작은 로직 하나를 구현할 때마다 망설이지 않고 수시로 `git commit`을 남깁니다.
  - 메시지 예시: `feat: add initial User entity`, `fix: correct typo in UserController`, `chore: update dependencies` 등

### 2.2 스쿼시 커밋 (Squash Commit)으로 통합하기
작업이 모두 완료되고 PR(Pull Request)을 올려 `main` 브랜치로 병합(Merge)하기 직전에는, 지저분한 '코끼리 커밋'들을 하나로 압축(Squash)해야 합니다.
- **목적**: `main` 브랜치의 히스토리를 깔끔하고 의미 있는 단위(보통 PR 단위 1개)로 관리하기 위함입니다.
- **방법 (CLI)**:
  1. `git rebase -i HEAD~[합칠 커밋 개수]`
  2. 에디터가 열리면 가장 첫 번째 커밋은 `pick`으로 두고, 나머지 합칠 커밋들은 `squash` (또는 `s`)로 변경 후 저장합니다.
  3. 새로운 에디터 창에서 기존 커밋 메시지들을 지우고, 하나의 통합된 깔끔한 커밋 메시지(위 Prefix 규칙 준수)를 작성합니다.
- **방법 (GitHub/GitLab PR)**:
  - PR 화면에서 Merge 옵션 중 **"Squash and Merge"**를 선택합니다.
  - 이 방식을 사용하면 로컬에서 복잡하게 Rebase를 하지 않아도 자동으로 하나의 커밋으로 깔끔하게 압축되어 메인 브랜치에 반영됩니다. (가장 권장되는 방식입니다.)
