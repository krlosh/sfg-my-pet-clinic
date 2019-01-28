package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String listOwners(Model model){
        model.addAttribute("owners", this.ownerService.findAll());
        return "owners/index";
    }

    @RequestMapping("/find")
    public String findOwners(Model model){
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }
    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model){
        if(owner.getLastName() == null){
            owner.setLastName("");
        }
        List<Owner> ownerList = this.ownerService.findAllByLastNameLike(owner.getLastName());
        if(ownerList.isEmpty()){
            result.rejectValue("lastName", "not found" , "not found");
            return "owners/findOwners";
        }
        else if(ownerList.size() == 1){
            Owner ownerResult = ownerList.get(0);
            return "redirect:/owners/"+ownerResult.getId();
        }
        else {
            model.addAttribute("owners", ownerList);
            return "owners/ownersList";
        }
    }

    @RequestMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject(this.ownerService.findById(ownerId));
        return modelAndView;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result){
        if(result.hasErrors()){
            return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
        }
        Owner savedOwner = this.ownerService.save(owner);
        return "redirect:/owners/"+savedOwner.getId();
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateForm(@PathVariable Long ownerId, Model model){
        model.addAttribute("owner", this.ownerService.findById(ownerId));
        return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateForm(@Valid Owner owner, BindingResult result, @PathVariable Long ownerId) {
        if(result.hasErrors()){
            return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
        }
        owner.setId(ownerId);
        Owner savedOwner = this.ownerService.save(owner);
        return "redirect:/owners/"+savedOwner.getId();
    }
}
