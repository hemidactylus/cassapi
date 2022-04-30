package net.myspring.cassapi;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface TaxonRepository extends CassandraRepository<Taxon, UUID> {
}
