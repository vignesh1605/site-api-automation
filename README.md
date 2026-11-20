# Site API Automation

API tests for DummyJSON using Java 17, Maven, REST Assured, and TestNG.

## Run locally

```powershell
mvn clean test
```

The smoke test calls `GET https://dummyjson.com/test` and checks that it
returns HTTP 200 and `status=ok`.

Test results are written to `target/surefire-reports/`.