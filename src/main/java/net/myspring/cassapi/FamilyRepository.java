package net.myspring.cassapi;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface FamilyRepository extends CassandraRepository<Family, String> {
}
