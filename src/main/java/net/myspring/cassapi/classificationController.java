package net.myspring.cassapi;

import org.apache.avro.generic.GenericData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@SpringBootApplication
public class classificationController {

    @Autowired
    private TaxonRepository taxonRepository;

//    @GetMapping("???")
//    public Iterable<Taxon> families(){
//        return taxonRepository.findAll(CassandraPageRequest.first(10)).toList();
//    }

    @GetMapping("/taxa/{id}")
    public ResponseEntity<Taxon> getTaxonById(@PathVariable UUID id){
        Optional<Taxon> taxonOpt = taxonRepository.findById(id);
        if (taxonOpt.isPresent()){
            return ResponseEntity.ok(taxonOpt.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/taxa/lineage/{id}")
    public ResponseEntity<List<Taxon>> getLineageById(@PathVariable UUID id){
        List<Taxon> taxa = new ArrayList<>();
        UUID childId = id;

        Optional<Taxon> nextTaxonOpt = taxonRepository.findById(childId);
        if(!nextTaxonOpt.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Taxon thisTaxon = nextTaxonOpt.get();
        taxa.add(thisTaxon);
        while(!thisTaxon.getId().equals(thisTaxon.getParent())){
            nextTaxonOpt = taxonRepository.findById(thisTaxon.getParent());
            if(!nextTaxonOpt.isPresent()){
                break;
            }
            thisTaxon = nextTaxonOpt.get();
            taxa.add(thisTaxon);
        }
        //
        return ResponseEntity.ok(taxa);
    }

//    @PostMapping()
//    public ResponseEntity<Taxon> postTaxon(@RequestBody Taxon taxon){
//        taxonRepository.save(taxon);
//        return new ResponseEntity(taxon, HttpStatus.CREATED);
//    }

//    @PutMapping()
//    public ResponseEntity<Taxon> putTaxon(@RequestBody Taxon taxon){
//        if(taxonRepository.existsById(taxon.getName())){
//            taxonRepository.save(taxon);
//            return new ResponseEntity(taxon, HttpStatus.OK);
//        }else{
//            taxonRepository.save(taxon);
//            return postTaxon(taxon);
//        }
//    }

//    @DeleteMapping("/{name}")
//    public ResponseEntity<Void> deleteTaxon(@PathVariable String name){
//        if(taxonRepository.existsById(name)) {
//            taxonRepository.deleteById(name);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }else{
//            return ResponseEntity.notFound().build();
//        }
//    }

}
