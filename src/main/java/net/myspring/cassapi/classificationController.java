package net.myspring.cassapi;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.Statement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.apache.avro.generic.GenericData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@SpringBootApplication
public class classificationController {

    @Autowired
    private TaxonRepository taxonRepository;

    @Autowired
    private CassandraTemplate cassandraTemplate;

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

    @GetMapping("/taxa/by_name/{name}")
    public ResponseEntity<Taxon> getTaxonByName(@PathVariable String name){
        //
        SimpleStatement select = SimpleStatement.builder("SELECT * FROM taxon WHERE name=?")
                .addPositionalValues(name)
                .build();

        Taxon taxon = cassandraTemplate.selectOne(select, Taxon.class);

        if(taxon == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(taxon);
        }

    }

    @GetMapping("/taxa/children/{id}")
    public ResponseEntity<List<Taxon>> getChildrenTaxa(@PathVariable UUID id){
        //
        SimpleStatement select = SimpleStatement.builder("SELECT * FROM taxon WHERE parent=?")
                .addPositionalValues(id)
                .build();

        List<Taxon> taxa = cassandraTemplate.select(select, Taxon.class)
                .stream()
                .filter(taxon -> !taxon.getParent().equals(taxon.getId()))
                .collect(toList());

        return ResponseEntity.ok(taxa);

    }

    @GetMapping("/taxa/lineage/{id}")
    public ResponseEntity<List<Taxon>> getLineageById(@PathVariable UUID id){
        Optional<List<Taxon>> lineage = taxonRepository.getLineageById(id);
        if (lineage.isPresent()){
            return ResponseEntity.ok(lineage.get());
        }else{
            return ResponseEntity.notFound().build();
        }
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
