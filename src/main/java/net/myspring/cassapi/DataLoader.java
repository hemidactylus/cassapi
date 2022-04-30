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
	}


}
