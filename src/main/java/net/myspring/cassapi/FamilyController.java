package net.myspring.cassapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/families")
@SpringBootApplication
public class FamilyController {

    @Autowired
    private FamilyRepository familyRepository;

	/*
	@PostConstruct
	public void prePopulate(){
		familyRepository.save(new Family("Theridiidae", "widows and the like"));
		familyRepository.save(new Family("Pholcidae", "daddy long-legs"));
		familyRepository.save(new Family("Araneidae", "orb-weavers"));
	}*/

    @GetMapping()
    public Iterable<Family> families(){
        return familyRepository.findAll(CassandraPageRequest.first(10)).toList();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Family> getFamilyByName(@PathVariable String name){
        Optional<Family> familyOpt = familyRepository.findById(name);
        if (familyOpt.isPresent()){
            return ResponseEntity.ok(familyOpt.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Family> postFamily(@RequestBody Family family){
        familyRepository.save(family);
        return new ResponseEntity(family, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Family> putFamily(@RequestBody Family family){
        if(familyRepository.existsById(family.getName())){
            familyRepository.save(family);
            return new ResponseEntity(family, HttpStatus.OK);
        }else{
            familyRepository.save(family);
            return postFamily(family);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteFamily(@PathVariable String name){
        if(familyRepository.existsById(name)) {
            familyRepository.deleteById(name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

//	// Provided by the Starter
//	@Autowired
//	private AstraClient astraClient;
//
//	// Spring Data using the CqlSession initialized by the starter
//	@Autowired
//	private CassandraTemplate cassandraTemplate;
//
//	@GetMapping("/api/devops/organizationid")
//	public String showOrganizationId() {
//		return astraClient.apiDevopsOrganizations().organizationId();
//	}
//
//	@GetMapping("/api/spring-data/datacenter")
//	public String showDatacenterNameWithSpringData() {
//		return cassandraTemplate.getCqlOperations()
//				.queryForObject("SELECT data_center FROM system.local", String.class);
//	}
//
//	@GetMapping("/api/cql/datacenter")
//	public String showDatacenterNameWithCqlSession() {
//		return astraClient.cqlSession()
//				.execute("SELECT data_center FROM system.local")
//				.one().getString("data_center");
//	}

}
