# TODO

## Questions arising from the Spring Data docs

See [this page](https://docs.spring.io/spring-data/cassandra/docs/current/reference/html)

### Usage of prepared statements

Those should be [automatically enabled by default](https://docs.spring.io/spring-data/cassandra/docs/current/reference/html/#cassandra.template.prepared-statements.cassandra-template)
when using `CassandraTemplate`. As for the repository, there's not much to prepare, but **still not clear if they are used**.

### Default method in Repository interface

Check `getLineageById` defined in `TaxonRepository`: is it the right place?

### Multiple repositories

Does that somehow generate multiple Session objects? If so, how to avoid?

### Autowiring

Is `@Autowired` optional e.g. in `DataLoader`? If so, to what extent can it be dropped elsewhere?
(in other words: how much freedom/cleverness does the IOC controller afford?)

## Questions from other sources

### Case sensitivity of index

Index on `name` is case-insensitive, but that does not seem to have any effect.
If querying on `name = 'Pholcidae'` a match is found, if `= 'pholcidae'` it isn't.
Same in CQL console.

### HTML response vs. plain-text

Compare the output of

```
curl -i "localhost:8080/taxa/aa"
curl -i "localhost:8080/taxa/aa dd"
```

the second retuns an HTML error page, the first does not. Why?