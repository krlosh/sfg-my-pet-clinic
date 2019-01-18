package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    private OwnerMapService ownerMapService;

    private final Long ownerId = 1L;
    private final String lastName="Smith";

    @BeforeEach
    void setUp() {

        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        this.ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = this.ownerMapService.findAll();

        assertEquals(1,owners.size());
    }

    @Test
    void findById() {
        Owner owner = this.ownerMapService.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveExistingId() {
        Long id = 2L;

        Owner owner2 = Owner.builder().id(id).build();

        Owner savedOwner = this.ownerMapService.save(owner2);
        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNoId(){
        Owner savedOwner = this.ownerMapService.save(Owner.builder().build());

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void deleteById() {
        this.ownerMapService.deleteById(ownerId);
        assertEquals(0, this.ownerMapService.findAll().size());
    }

    @Test
    void delete() {
        this.ownerMapService.delete(this.ownerMapService.findById(ownerId));
        assertEquals(0, this.ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner smith  = this.ownerMapService.findByLastName(lastName);
        assertNotNull(smith);
        assertEquals(lastName, smith.getLastName());
    }

    @Test
    void findByLastNameNotFound() {
        Owner owner  =this.ownerMapService.findByLastName("foo");
        assertNull(owner);
    }
}