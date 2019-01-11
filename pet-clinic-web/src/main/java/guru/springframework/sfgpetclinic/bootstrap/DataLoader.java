package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import guru.springframework.sfgpetclinic.service.VetService;
import guru.springframework.sfgpetclinic.service.map.OwnerServiceMap;
import guru.springframework.sfgpetclinic.service.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.loadOwners();
        this.loadVets();
    }

    private void loadOwners() {

        PetType dogType = new PetType();
        dogType.setName("Dog");
        PetType savedDogType = this.petTypeService.save(dogType);

        PetType catType = new PetType();
        catType.setName("Cat");
        PetType savedCatType = this.petTypeService.save(catType);

        System.out.println("Loaded VetTypes");

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("123 Rainbow");
        owner1.setCity("Miami");
        Pet firstPet = new Pet();
        firstPet.setPetType(savedDogType);
        firstPet.setOwner(owner1);
        firstPet.setName("MyDog");
        firstPet.setBirthDate(LocalDate.now());
        owner1.getPets().add(firstPet);

        this.ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fionna");
        owner2.setLastName("Glenanne");
        owner2.setAddress("123 Rainbow");
        owner2.setCity("Miami");
        owner2.setTelephone("666666666");
        Pet secondPet = new Pet();
        secondPet.setPetType(savedCatType);
        secondPet.setName("MyCat");
        secondPet.setOwner(owner2);
        secondPet.setBirthDate(LocalDate.now());
        owner2.getPets().add(secondPet);

        this.ownerService.save(owner2);
        System.out.println("Loaded owners");
    }

    private void loadVets() {
        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");

        this.vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessy");
        vet2.setLastName("Porter");

        this.vetService.save(vet2);
        System.out.println("Loaded vets");

    }

}
