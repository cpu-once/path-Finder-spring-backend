1. 스키마의 논리적 근간: EXPRESS 언어와 IFC 4.3
   공식 HTML 문서는 결과물일 뿐입니다. 그 이면의 논리를 이해하기 위해 다음이 필요합니다.

EXPRESS 언어 (ISO 10303-11): IFC는 EXPRESS라는 정보 모델링 언어로 작성되었습니다. 엔티티 간의 상속(Inheritance), 역관계(Inverse
Relationship), 그리고 데이터 제약 조건인 WHERE 구문을 이해하려면 EXPRESS의 기본 문법을 파악해야 합니다.

IFC 4.3 (인프라 확장): 건축 위주의 IFC4를 넘어 교량, 철도, 도로 등 토목 인프라까지 확장된 최신 표준입니다. 향후 공공 프로젝트나 대규모 인프라 BIM을 고려한다면
반드시 학습 범위에 포함해야 합니다.

2. 개발자 생태계와 강력한 도구 (Open Source)
   소프트웨어 공학 관점에서 IFC를 다루기 위해 전 세계적으로 가장 평판이 높은 프로젝트들을 참고해야 합니다.

IfcOpenShell (Python/C++): * 핵심 인물: Thomas Krijnen (메인 maintainer).

설명: 깃허브 스타 수가 가장 많고 실무에서 표준적으로 사용되는 라이브러리입니다. 단순히 파일을 여는 것을 넘어, 형상(Geometry)을 생성하고 속성을 자동 추출하는 스크립트를
작성하는 능력이 필수입니다.

BlenderBIM / Bonsai:

핵심 인물: Dion Moult.

설명: 기존 소프트웨어(Revit 등)가 IFC를 '수출(Export)'용으로만 다루는 것과 달리, IFC를 네이티브 데이터 포맷으로 사용하는 오픈소스 BIM 저작 도구입니다. 이
도구의 작동 원리를 공부하면 IFC 파일의 한 줄 한 줄이 어떻게 실제 모델로 구현되는지 깊이 있게 이해할 수 있습니다.

3. 정보 전달 및 검증 표준 (The New IFC)
   데이터는 정확할 때만 가치가 있습니다. 이를 위해 최근 빌딩스마트(buildingSMART)에서 밀고 있는 기술들입니다.

IDS (Information Delivery Specification): 과거의 복잡한 MVD(Model View Definition)를 대체하는 컴퓨터 판독 가능 데이터 검증
표준입니다. "모든 벽에는 내화 등급이 있어야 한다"와 같은 규칙을 XML 형태로 정의하고 자동 검증하는 기술입니다.

bSDD (buildingSMART Data Dictionary): 전 세계의 분류 체계(Uniclass, OmniClass 등)를 IFC 엔티티와 연결해주는 클라우드
서비스입니다. 데이터의 의미론적 상호운용성을 위해 필수적인 요소입니다.

BCF (BIM Collaboration Format): 모델 데이터 자체가 아닌, 모델에 대한 '이슈'와 '의사소통'을 주고받는 표준입니다.

Memo, 어디까지 프로젝트로 진행할까?
