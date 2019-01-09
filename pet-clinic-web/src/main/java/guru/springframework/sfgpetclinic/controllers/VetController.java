package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }


    @RequestMapping({"", "/", "index", "index.html"})
    public String listVets(Model model){
        model.addAttribute("vets", this.vetService.findAll());
        return "vets/index";
    }
}
