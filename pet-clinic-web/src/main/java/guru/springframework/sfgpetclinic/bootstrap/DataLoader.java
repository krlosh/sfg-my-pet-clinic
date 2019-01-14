package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import guru.springframework.sfgpetclinic.service.SpecialityService;
import guru.springframework.sfgpetclinic.service.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = this.petTypeService.findAll().size();
        if(count == 0) {
            this.loadOwners();
            this.loadVets();
        }
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

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = this.specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = this.specialityService.save(surgery);

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedRadiology);

        this.vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessy");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(savedSurgery);

        this.vetService.save(vet2);
        System.out.println("Loaded vets");

    }

}
