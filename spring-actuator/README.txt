# SampleInfoContributor により /info エンドポイントに詳細を追加している
$ curl http://localhost:8080/actuator/info
{"example":{"someKey":"someValue"}}

# SampleHealthIndicator により /health エンドポイントに詳細を追加している
$ curl http://localhost:8080/actuator/health
{"status":"DOWN","details":{"sample":{"status":"DOWN","details":{"counter":"toDown","error":"java.lang.IllegalStateException: status down --"}}, ...

# SampleRestControllerEndPoint により追加されたエンドポイント
$ curl http://localhost:8080/actuator/example/echo/test
test

# システムシャットダウン
$ curl -X POST http://localhost:8080/actuator/shutdown
{"message":"Shutting down, bye..."}

# エンドポイント一覧 ( https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready-endpoints )
$ curl http://localhost:8080/actuator
{
    "_links": {
        "self": {
            "href": "http://localhost:8080/actuator",
            "templated": false
        },
        "auditevents": {
            "href": "http://localhost:8080/actuator/auditevents",
            "templated": false
        },
        "beans": {
            "href": "http://localhost:8080/actuator/beans",
            "templated": false
        },
        "health": {
            "href": "http://localhost:8080/actuator/health",
            "templated": false
        },
        "conditions": {
            "href": "http://localhost:8080/actuator/conditions",
            "templated": false
        },
        "shutdown": {
            "href": "http://localhost:8080/actuator/shutdown",
            "templated": false
        },
        "configprops": {
            "href": "http://localhost:8080/actuator/configprops",
            "templated": false
        },
        "env": {
            "href": "http://localhost:8080/actuator/env",
            "templated": false
        },
        "env-toMatch": {
            "href": "http://localhost:8080/actuator/env/{toMatch}",
            "templated": true
        },
        "info": {
            "href": "http://localhost:8080/actuator/info",
            "templated": false
        },
        "loggers-name": {
            "href": "http://localhost:8080/actuator/loggers/{name}",
            "templated": true
        },
        "loggers": {
            "href": "http://localhost:8080/actuator/loggers",
            "templated": false
        },
        "heapdump": {
            "href": "http://localhost:8080/actuator/heapdump",
            "templated": false
        },
        "threaddump": {
            "href": "http://localhost:8080/actuator/threaddump",
            "templated": false
        },
        "metrics": {
            "href": "http://localhost:8080/actuator/metrics",
            "templated": false
        },
        "metrics-requiredMetricName": {
            "href": "http://localhost:8080/actuator/metrics/{requiredMetricName}",
            "templated": true
        },
        "scheduledtasks": {
            "href": "http://localhost:8080/actuator/scheduledtasks",
            "templated": false
        },
        "httptrace": {
            "href": "http://localhost:8080/actuator/httptrace",
            "templated": false
        },
        "mappings": {
            "href": "http://localhost:8080/actuator/mappings",
            "templated": false
        },
        "example": {
            "href": "http://localhost:8080/actuator/example",
            "templated": false
        }
    }
}