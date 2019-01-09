package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.model.BaseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AbstractMapService<T extends BaseEntity, ID extends Long> {

    protected HashMap<Long,T> map = new HashMap<>();

    public Set<T> findAll(){
        return new HashSet<>(map.values());
    }

    public T findById(ID id){
        return this.map.get(id);
    }

    public T save(T object){
        if(object == null){
            throw new IllegalArgumentException("Object must not be null");
        }
        if(object.getId() == null) {
            object.setId(this.nextId());
        }
        return this.map.put(object.getId(), object);
    }

    public void deleteById(ID id){
        this.map.remove(id);
    }

    public void delete(T object) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(object));
    }

    private Long nextId(){
        if (map.isEmpty()) {
            return 1L;
        }
        return Collections.max(map.keySet())+1;
    }
}
