package net.myspring.cassapi;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaxonRepository extends CassandraRepository<Taxon, UUID> {

    default Optional<List<Taxon>> getLineageById(UUID id){
        List<Taxon> taxa = new ArrayList<>();
        UUID childId = id;

        Optional<Taxon> nextTaxonOpt = findById(childId);
        if(!nextTaxonOpt.isPresent()){
            return Optional.empty();
        }

        Taxon thisTaxon = nextTaxonOpt.get();
        taxa.add(thisTaxon);
        while(!thisTaxon.getId().equals(thisTaxon.getParent())){
            nextTaxonOpt = findById(thisTaxon.getParent());
            if(!nextTaxonOpt.isPresent()){
                break;
            }
            thisTaxon = nextTaxonOpt.get();
            taxa.add(thisTaxon);
        }
        //
        return Optional.of(taxa);
    }

}
