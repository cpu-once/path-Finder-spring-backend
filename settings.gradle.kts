rootProject.name = "pathFinder-spring-backend"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
// VERSION_CATALOGS는 최신 Gradle에서 기본 활성화되어 있으므로 생략해도 됩니다.

// :core:math 모듈을 실제 폴더인 core/core-math와 매핑합니다.
include(":core:math")
project(":core:math").projectDir = file("core/core-math")

// :apps:path-finder-app 모듈을 실제 폴더인 apps/pathfinder-app와 매핑합니다.
include(":apps:path-finder-app")
project(":apps:path-finder-app").projectDir = file("apps/pathfinder-app")
