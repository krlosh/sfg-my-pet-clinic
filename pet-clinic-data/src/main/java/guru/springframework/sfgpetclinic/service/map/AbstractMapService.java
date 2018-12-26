package guru.springframework.sfgpetclinic.service.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AbstractMapService<T, ID> {

    protected HashMap<ID,T> map = new HashMap<>();

    public Set<T> findAll(){
        return new HashSet<>(map.values());
    }

    public T findById(ID id){
        return this.map.get(id);
    }

    public T save(ID id, T object){
        return this.map.put(id, object);
    }

    public void deleteById(ID id){
        this.map.remove(id);
    }

    public void delete(T object) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(object));
    }
}
