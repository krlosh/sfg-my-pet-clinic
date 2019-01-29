package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PetControllerTest {

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @Mock
    OwnerService ownerService;

    @InjectMocks
    PetController controller;

    MockMvc mockMvc;

    private Set<PetType> petTypes;

    @BeforeEach
    public void setUp(){
        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().id(1L).name("Dog").build());
        petTypes.add(PetType.builder().id(2L).name("Cat").build());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void initCreationForm() throws Exception{
        when(this.ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
        when(this.petTypeService.findAll()).thenReturn(petTypes);
        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"));
        verifyZeroInteractions(this.petService);
    }

    @Test
    public void processCreationForm() throws Exception {
        when(this.ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
        when(this.petTypeService.findAll()).thenReturn(petTypes);
        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
        verify(this.petService).save(any());
    }

    @Test
    public void initUpdateForm() throws Exception{
        when(this.ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
        when(this.petTypeService.findAll()).thenReturn(petTypes);
        when(this.petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());
        mockMvc.perform(get("/owners/1/pets/2/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"));
        verifyZeroInteractions(this.petService);
    }

    @Test
    public void processUpdateForm() throws Exception {
        when(this.ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
        when(this.petTypeService.findAll()).thenReturn(petTypes);
        mockMvc.perform(post("/owners/1/pets/2/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
        verify(this.petService).save(any());
    }
}
