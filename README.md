# CassAPI

## Setup

As per best practices, we disable schema creation and
handle that by hand with CQL commands, externally to the application.

```
USE <your_keyspace>;
CREATE TABLE taxa (
    id uuid PRIMARY KEY,
    description text,
    level text,
    name text,
    parent uuid
);
CREATE CUSTOM INDEX taxa_name_index ON taxa (name) USING 'StorageAttachedIndex' WITH OPTIONS = {'case_sensitive': true};
CREATE CUSTOM INDEX taxa_parent_index ON taxa (parent) USING 'StorageAttachedIndex';
```

## Notes

#### Template vs Operations

Class `CassandraTemplate` implements interface `CassandraOperations`.

The latter in turn has a `getCqlOperations` method returning a `CqlOperations` object:
this has [lower-level stuff](https://docs.spring.io/spring-data/cassandra/docs/current/api/org/springframework/data/cassandra/core/cql/CqlOperations.html).

#### Annotate table names, etc

Table names, primary/partition keys, column names: better
to explicitly annotate everything in the domain class.

#### Repository and finding by non-key

Just adding the method signature in the repository interface will make
Spring Data Cassandra fill it for you:

```
Optional<Taxon> findByName(String name);
```

#### What to execute with a CassandraTemplate

Both `Query` and `SimpleStatement` work fine:

```
SimpleStatement select1 = SimpleStatement.builder("SELECT * FROM taxon WHERE parent=?")
       .addPositionalValues(id)
       .build();

Query select2 = Query.query(Criteria.where("parent").is(id));

// those are both OK:
cassandraTemplate.select(select1, Taxon.class) [...];
cassandraTemplate.select(select2, Taxon.class) [...];
```

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
      schema-action: NONE
      contactpoints: ...
      local-datacenter: datacenter1
      keyspace-name: ...
```

Also, of course, make sure your code does not use the Astra SDK
Stargate-specific stuff (e.g. does not import/use `[blabla].astra.AstraClient`).
