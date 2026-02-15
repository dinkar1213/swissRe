# springBoot Assignment

Small Spring Boot project that reads an employees CSV, analyzes organizational issues, and contains unit tests. This repository includes an `OrganizationAnalyzer` used by a CLI `Main` class and Spring Boot components used for tests and caching demonstrations.

## What this project contains

- `src/main/java/com/test/springBoot/assignment/Main.java` - CLI entry point that reads a CSV and prints salary/reporting analysis.
- `src/main/java/com/test/springBoot/assignment/EmployeeCsvReader.java` - CSV parsing logic (returns `Map<String, EmployeeRecord>`).
- `src/main/java/com/test/springBoot/assignment/EmployeeRecord.java` - POJO for employee fields.
- `src/main/java/com/test/springBoot/assignment/OrganizationAnalyzer.java` - Business logic: `analyzeSalaries(...)` and `analyzeReportingLength(...)`.
- `src/main/java/com/test/springBoot/config/CacheConfig.java` - (added) Spring `CacheManager` bean; uses `ConcurrentMapCacheManager`.
- `src/main/java/com/test/springBoot/assignment/CachedValueService.java` - (added) small `@Service` demonstrating `@Cacheable` usage.
- `src/test/java/com/test/springBoot/assignment/OrganizationAnalyzerTest.java` - unit tests for analyzer (fixed to use JUnit Jupiter assertions).
- `src/test/java/com/test/springBoot/assignment/CachedValueServiceTest.java` - (added) test proving caching works.

## Why a `CacheManager` was added

Spring failed at startup with:

> A component required a bean of type 'org.springframework.cache.CacheManager' that could not be found.

To fix this a minimal cache configuration was added in `src/main/java/com/test/springBoot/config/CacheConfig.java`:

- It declares a `@Bean` returning a `ConcurrentMapCacheManager` (in-memory cache suitable for tests and small apps).
- `@EnableCaching` is present on `src/main/java/com/test/springBoot/Application.java` so Spring processes `@Cacheable` annotations.

This resolves the startup error and allows `@Cacheable` methods to use the cache.

## Prerequisites

- Java 11+ (JDK)
- Maven (the wrapper `./mvnw` is included)

