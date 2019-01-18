package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.service.CrudService;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public Owner findById(Long aLong) {
        return super.findById(aLong);
    }


    @Override
    public void deleteById(Long aLong) {
        super.deleteById(aLong);
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public Owner save(Owner object) {
        if(object==null) {
            return null;
        }
        if(object.getPets() != null){
            object.getPets().forEach(pet -> {
                if(pet.getPetType() == null) {
                    throw new RuntimeException("Pet Type is required");
                }
                if(pet.getPetType().getId() == null) {
                    pet.setPetType(this.petTypeService.save(pet.getPetType()));
                }
                if(pet.getId()==null){
                    Pet savedPet = this.petService.save(pet);
                    pet.setId(savedPet.getId());
                }
            });
        }
        return super.save(object);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(owner ->  lastName.equalsIgnoreCase(owner.getLastName()))
                .findFirst()
                .orElse(null);
    }
}
