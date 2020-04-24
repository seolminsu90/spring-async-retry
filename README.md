# spring-async-retry
스프링 Retry 샘플

## 개요
- 비동기에서도 Retryable 적용되는지 테스트해보기 위해서 작성해봄
- Template 기반 방식, Annotation 기반 방식 쌤플 남김

## Test
```bash
curl -XGET localhost:8080/test # use template
curl -XGET localhost:8080/test2 # use annotation
```
