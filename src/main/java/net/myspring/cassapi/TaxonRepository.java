package net.myspring.cassapi;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.http.ResponseEntity;

import java.util.*;

public interface TaxonRepository extends CassandraRepository<Taxon, UUID> {

    Optional<Taxon> findByName(String name);

    default Optional<List<Taxon>> getLineageById(UUID id){
        List<Taxon> taxa = new ArrayList<>();
        UUID childId = id;

        Optional<Taxon> nextTaxonOpt = findById(childId);
        if(!nextTaxonOpt.isPresent()){
            return Optional.empty();
        }

        Set<UUID> foundIds = new HashSet<>();

        Taxon thisTaxon = nextTaxonOpt.get();
        taxa.add(thisTaxon);
        foundIds.add(thisTaxon.getId());

        while(!foundIds.contains(thisTaxon.getParent())){
            nextTaxonOpt = findById(thisTaxon.getParent());
            if(!nextTaxonOpt.isPresent()){
                break;
            }
            thisTaxon = nextTaxonOpt.get();
            taxa.add(thisTaxon);
            foundIds.add(thisTaxon.getId());
        }
        //
        return Optional.of(taxa);
    }

}
