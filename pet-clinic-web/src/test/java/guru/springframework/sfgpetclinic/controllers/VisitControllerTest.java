package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.service.PetService;
import guru.springframework.sfgpetclinic.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Mock
    PetService petService;

    @InjectMocks
    VisitController visitController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.visitController).build();
    }

    @Test
    void initNewVisitForm() throws Exception {
        when(this.petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());
        this.mockMvc.perform(get("/owners/1/pets/2/visits/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("pets/createOrUpdateVisitForm"))
            .andExpect(model().attributeExists("pet"))
            .andExpect(model().attributeExists("visit"));
        verifyZeroInteractions(this.visitService);
    }

    @Test
    void processNewVisitForm() throws Exception {
        when(this.petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());
        this.mockMvc.perform(post("/owners/1/pets/2/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
        verify(this.visitService).save(any());
    }

    @Test
    void initUpdateVisitForm() throws Exception {
        when(this.petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());
        this.mockMvc.perform(get("/owners/1/pets/2/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("visit"));
        verifyZeroInteractions(this.visitService);
    }

    @Test
    void processUpdateVisitForm() throws Exception {
        when(this.petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());
        this.mockMvc.perform(post("/owners/1/pets/2/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
        verify(this.visitService).save(any());
    }
}