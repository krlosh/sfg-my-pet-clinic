package guru.springframework.sfgpetclinic.formatter;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String s, Locale locale) throws ParseException {
        PetType foundPetType = this.petTypeService.findByName(s);
        if(foundPetType!=null){
            return foundPetType;
        }
        throw new ParseException("Pet type not found searching by name "+s,0);
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
