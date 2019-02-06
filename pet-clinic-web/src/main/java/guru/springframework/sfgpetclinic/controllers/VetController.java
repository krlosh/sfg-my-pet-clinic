package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }


    @RequestMapping({"", "/", "index", "index.html", "vets.html"})
    public String listVets(Model model){
        model.addAttribute("vets", this.vetService.findAll());
        return "vets/index";
    }

    @GetMapping("/api/list")
    public @ResponseBody Set<Vet> listVetsAsJson(){
        return this.vetService.findAll();
    }
}
