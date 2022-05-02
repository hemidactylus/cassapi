package net.myspring.cassapi;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.*;

public interface SpeciesRepository extends CassandraRepository<Species, UUID> {

    Optional<Species> findByName(String name);

}
