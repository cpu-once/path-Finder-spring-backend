## 🏗️ IFC 4.3 데이터 중심(Data-centric) DB 구축 가이드

이 가이드는 IFC 4.3 표준 스펙을 읽어와서, 여러분의 데이터베이스(PostgreSQL 추천)에 자동으로 800여 개의 테이블을 생성하는 SQL을 만들어주는 과정을 담고
있습니다.

### 1단계: 준비물 설치 (1분 소요)

컴퓨터에 파이썬(Python)이 설치되어 있어야 합니다. 터미널(CMD)을 열고 아래 명령어를 복사해서 입력하세요.

##### IFC 스키마를 읽기 위한 도구 설치

```bash
pip install ifcopenshell
```

### 2단계: 최신 IFC 4.3 설계도(EXPRESS) 확보

건물과 인프라의 모든 정의가 담긴 '설계도 파일'이 필요합니다.

buildingSMART 공식 GitHub에 접속합니다.

오른쪽 상단의 [Download raw file] 버튼을 눌러 IFC.exp 파일을 다운로드합니다.

여러분이 작업할 폴더에 이 파일을 넣으세요.

### 3단계: 마법의 추출 스크립트 작성

폴더에 make_ifc_db.py라는 파일을 만들고, 아래 코드를 그대로 복사해서 붙여넣으세요. 이 코드가 800개의 엔티티를 분석해 SQL 문으로 변환해줍니다.

```Python
import ifcopenshell

def create_ifc_sql_script(output_file="create_ifc_schema.sql"):
    # 1. IFC 4.3 스키마 로드
    schema = ifcopenshell.schema_by_name("IFC4X3")
    all_entities = schema.entities() # 약 800여 개의 엔티티 추출

    with open(output_file, "w", encoding="utf-8") as f:
        f.write("-- IFC 4.3 전용 자동 생성 스키마\n")
        f.write("CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\";\n\n")

        for entity in all_entities:
            entity_name = entity.name()
            # 상속받은 모든 속성을 가져옴
            attributes = entity.all_attributes()

            f.write(f"-- Entity: {entity_name}\n")
            f.write(f"CREATE TABLE IF NOT EXISTS ifc_{entity_name.lower()} (\n")

            # 모든 테이블에 공통으로 들어가는 필수 필드 (보안 및 식별용)
            columns = [
                "    internal_id SERIAL PRIMARY KEY",
                "    project_id INTEGER NOT NULL",       # BOLA 보안을 위한 프로젝트 ID
                "    global_id VARCHAR(22)",            # IFC 표준 고유 ID
                "    entity_type TEXT DEFAULT '" + entity_name + "'"
            ]

            # IFC 속성들을 DB 컬럼으로 매핑
            for attr in attributes:
                attr_name = attr.name().lower()
                # 예약어 피하기
                if attr_name in ["order", "group", "user", "level"]:
                    attr_name = f"ifc_{attr_name}"

                # 타입 매핑 (간소화 버전)
                raw_type = str(attr.type_of_attribute())
                sql_type = "TEXT" # 기본값
                if "REAL" in raw_type or "NUMBER" in raw_type: sql_type = "DOUBLE PRECISION"
                elif "INTEGER" in raw_type: sql_type = "INTEGER"
                elif "BOOLEAN" in raw_type: sql_type = "BOOLEAN"

                columns.append(f"    {attr_name} {sql_type}")

            # 유연성을 위한 마지막 JSONB 컬럼 (비정형 데이터 저장용)
            columns.append("    extra_data JSONB")

            f.write(",\n".join(columns))
            f.write("\n);\n")

            # 보안을 위한 인덱스 추가 (BOLA 방어)
            f.write(f"CREATE INDEX IF NOT EXISTS idx_{entity_name.lower()}_project ON ifc_{entity_name.lower()}(project_id);\n\n")

    print(f"✅ 성공! {output_file} 파일이 생성되었습니다.")

if __name__ == "__main__":
    create_ifc_sql_script()
```

### 4단계: 실행 및 SQL 결과물 확인

이제 터미널에서 아래 명령어를 입력해 스크립트를 실행합니다.

```bash
python make_ifc_db.py
```

잠시 후 폴더에 create_ifc_schema.sql 파일이 생깁니다. 이 파일을 열어보면 800여 개의 테이블 생성 구문이 아름답게 적혀 있을 것입니다.

### 5단계: DB에 적용하기 (마무리)

생성된 SQL 파일을 여러분의 데이터베이스(DBeaver, pgAdmin 등)에서 실행만 하면 끝납니다.

💡 초보자를 위한 팁: 왜 이렇게 만드나요?
project_id: "내 데이터만 본다"는 보안 규칙(BOLA)을 지키기 위해 모든 테이블에 넣었습니다.
extra_data (JSONB): IFC 표준은 너무 복잡해서 가끔 테이블에 정의되지 않은 데이터가 들어올 때가 있습니다. 그때 프로그램이 멈추지 않고 데이터를 담아두기 위한 '
비상 주머니'입니다.
Entity 명칭: 원래 IFC 명칭 앞에 ifc_를 붙여서, 일반적인 SQL 단어들과 충돌하지 않게 배려했습니다.

### 6단계: 스키마 정합 확인하기 - IFC : RDB

생성된 DB 스키마가 실제 IFC 4.3 표준과 일치하는지 확인하는 **'정합성 검증'**은 서비스의 신뢰도를 결정하는 핵심 공정입니다. 800개가 넘는 테이블을 일일이 눈으로
대조할 수는 없으므로, 표준 스펙(EXPRESS)의 메타데이터와 DB의 메타데이터를 소스 코드 레벨에서 직접 비교하는 자동화 검증 방식을 사용해야 합니다.1. 정합성 검증의 3대
지표단순히 "테이블이 있다"를 넘어 아래 세 가지가 일치해야 '정합성이 맞다'고 할 수 있습니다.검증 항목내용확인 방법수량 정합IFC 4.3 표준 엔티티 개수(816개)와 생성된
테이블 개수가 일치하는가?SELECT count(*) vs 스키마 리스트 비교속성 정합각 테이블의 컬럼명과 타입이 표준의 Attribute 정의와 일치하는가?DB 메타데이터(
Information Schema) 추출 및 대조계층 정합상위 클래스(부모)의 속성이 하위 클래스(자식) 테이블에 모두 포함되었는가?상속 트리 구조 재귀적 검사2. 자동화 검증
스크립트 가이드 (Python)아무것도 모르는 개발자도 이 스크립트를 돌려보면 "내 DB가 표준과 몇 % 일치하는지" 바로 알 수 있습니다.Pythonimport
ifcopenshell
import psycopg2 # PostgreSQL 기준

def verify_schema_integrity():

# 1. 표준 데이터 로드 (기준점)

standard_schema = ifcopenshell.schema_by_name("IFC4X3")
standard_entities = {e.name().lower() for e in standard_schema.entities()}

    # 2. 실제 생성된 DB 테이블 로드 (검증 대상)
    # 실제 환경에 맞춰 DB 접속 정보를 수정하세요.
    conn = psycopg2.connect(database="ifc_db", user="admin")
    cur = conn.cursor()
    cur.execute("""
        SELECT table_name
        FROM information_schema.tables
        WHERE table_name LIKE 'ifc_%'
    """)
    db_tables = {row[0].replace('ifc_', '') for row in cur.fetchall()}

    # 3. 누락된 테이블 확인
    missing = standard_entities - db_tables
    extra = db_tables - standard_entities

    print(f"📊 [검증 결과 보고서]")
    print(f"- 표준 엔티티 수: {len(standard_entities)}")
    print(f"- 생성된 테이블 수: {len(db_tables)}")
    print(f"- 정합성 점수: {(1 - len(missing)/len(standard_entities))*100:.2f}%")

    if missing:
        print(f"⚠️ 누락된 엔티티 (일부): {list(missing)[:10]}")

    conn.close()

if __name__ == "__main__":
verify_schema_integrity()

3. 샘플 데이터를 통한 '라운드 트립(Round-trip)' 테스트가장 확실한 정합성 확인법은 **"실제 파일을 넣었다가 다시 뽑아보는 것"**입니다.Input:
   buildingSMART에서 제공하는 공식 IFC 4.3 샘플 파일을 준비합니다.Process: 개발한 임포터(Importer)를 통해 DB에 저장합니다.Output: DB
   데이터를 다시 IFC 파일로 추출(Export)합니다.Compare: 원본 파일과 추출된 파일의 구조를 IfcValidator 같은 도구로 비교합니다.💡 핵심 팁:파일 크기가
   똑같을 필요는 없습니다. 하지만 GlobalId가 유지되고, 객체 간의 **연결 관계(Relationship)**가 깨지지 않았다면 아키텍처 정합성이 확보된 것으로
   간주합니다.4. 전문가의 '한 끗' 조언: ADR 기록정합성을 확인하다 보면, 표준과 의도적으로 다르게 설계해야 하는 경우가 반드시 생깁니다. (예: 성능을 위해 일부
   테이블을 합치거나, JSONB를 쓴 경우)이때는 ADR(Architecture Decision Record) 문서를 남겨야 합니다.결정: "IfcPropertySet 산하
   400개 엔티티는 테이블화하지 않고 JSONB 컬럼에 담기로 함."이유: "JOIN 비용 과다로 인한 성능 저하 방지."영향: "조회 속도는 5배 빨라지나, 표준
   SQL만으로는 특정 속성 검색 시 JSON 쿼리 문법이 필요함."이런 기록이 있어야 나중에 다른 개발자가 봐도 "이건 틀린 게 아니라 의도된 설계구나"라고 이해할 수
   있습니다.5. 최종 체크리스트 (배포 전 확인)[ ] 모든 테이블에 project_id 컬럼이 누락 없이 들어가 있는가?[ ] IFC 표준의 REAL 타입이 DB의
   DOUBLE PRECISION으로 정확히 매핑되었는가?[ ] 816개 엔티티 중 우리 서비스에서 쓰지 않는 '추상 엔티티(Abstract Entity)'들은 제외
   처리되었는가?[ ] 대소문자 구분 문제로 인해 테이블명이 꼬이지 않았는가? (ifc_ 접두사 사용 확인)이제 스키마 정합성 확인까지 끝났다면, 실제로 데이터를 밀어넣었을
   때 '얼마나 빠르게 조회되는지' 성능 테스트가 궁금해지실 것 같습니다. 혹시 800개 테이블 전체에 대해 인덱스(Index) 전략을 어떻게 가져갈지도 가이드라인에 포함할까요?
