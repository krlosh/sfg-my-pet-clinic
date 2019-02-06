package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.service.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    private VetService vetService;

    @InjectMocks
    private VetController vetController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.vetController).build();
    }

    @Test
    void listVets() throws Exception {
        when(this.vetService.findAll()).thenReturn(Set.of(Vet.builder().id(1L).build(), Vet.builder().id(2L).build()));
        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(view().name("vets/index"))
                .andExpect(model().attributeExists("vets"));
    }

    @Test
    void listVetsAsJson() throws Exception {
        when(this.vetService.findAll()).thenReturn(Set.of(Vet.builder().id(1L).build(), Vet.builder().id(2L).build()));

        mockMvc.perform(get("/vets/api/list")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk() )
                .andExpect(jsonPath("$" ,hasSize(2)));
    }
}