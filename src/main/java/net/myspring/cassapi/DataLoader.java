package net.myspring.cassapi;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class DataLoader {

    private TaxonRepository taxonRepository;

    public DataLoader(TaxonRepository taxonRepository){
        this.taxonRepository = taxonRepository;
    }

	@PostConstruct
	public void prePopulate(){
        UUID idAnimals = UUID.fromString("d309cf29-309b-44be-882b-8da2d6a5e735");
        UUID idArachnids = UUID.fromString("65043f8b-32e8-4fe2-bf4e-0aa7a2819f99");
        UUID idPholciidae = UUID.fromString("4883c31f-a83b-40ee-9ad2-d0bf5d709965");
        UUID idSalticidae = UUID.fromString("015988be-4492-4d78-8b88-370dd60b1b7f");

        taxonRepository.save(new Taxon(
                idAnimals,
                idAnimals,
                "Animals",
                "kingdom",
                "The whole animal kingdom"
        ));
        taxonRepository.save(new Taxon(
                idArachnids,
                idAnimals,
                "Arachnids",
                "class",
                "Spiders, scorpions, opiliones, ticks, mites and friends"
        ));
        taxonRepository.save(new Taxon(
                idPholciidae,
                idArachnids,
                "Pholciidae",
                "family",
                "Daddy long-legs"
        ));
        taxonRepository.save(new Taxon(
                idSalticidae,
                idArachnids,
                "Salticidae",
                "family",
                "Jumping spiders"
        ));

        UUID idLoop1 = UUID.fromString("b09b68e2-68aa-4040-9fa8-c802a80c04f5");
        UUID idLoop2 = UUID.fromString("21611fee-41e1-45df-b6cb-90153d44178e");
        UUID idLoop3 = UUID.fromString("5836b62c-8337-4687-9e63-a2b4c23e5d25");
        taxonRepository.save(new Taxon(idLoop1, idLoop2, "Paper",    "loopyGroup", "just testing cycle detection"));
        taxonRepository.save(new Taxon(idLoop2, idLoop3, "Rock",     "loopyGroup", "just testing cycle detection"));
        taxonRepository.save(new Taxon(idLoop3, idLoop1, "Scissors", "loopyGroup", "just testing cycle detection"));

    }


}
