# CassAPI

### Notes

#### Interchangeability Astra/Cassandra

To switch from Astra to Cassandra, two things:

_first_, comment this Astra dependency in `pom.xml`:

```
<dependency>
  <groupId>com.datastax.astra</groupId>
  <artifactId>astra-spring-boot-starter</artifactId>
  <version>0.3.0</version>
</dependency>
```

_second_, adjust `application.yaml` to replace

```
astra:
  api:
    application-token: ...
    database-id: ...
    database-region: ...
  cql:
    enabled: true
    download-scb:
      enabled: true
    driver-config:
      basic:
        session-keyspace: ...
```

with

```
spring:
  data:
    cassandra:
      schema-action: CREATE_IF_NOT_EXISTS
      contactpoints: ...
      local-datacenter: datacenter1
      keyspace-name: ...
```

Also, of course, make sure your code does not use the Astra SDK
Stargate-specific stuff (e.g. does not import/use `[blabla].astra.AstraClient`).
