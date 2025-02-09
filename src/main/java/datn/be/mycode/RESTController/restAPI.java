package datn.be.mycode.RESTController;

import datn.be.mycode.entity.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class restAPI {

    List<Person> list = new ArrayList<>();

    @GetMapping("/get")
    public  ResponseEntity<List<Person>> get(){
        return ResponseEntity.ok(this.list);
    }
    @PostMapping
    public  ResponseEntity<Person> post(@RequestBody Person object){
        this.list.add(object);
        return ResponseEntity.ok(object);
    }
    @GetMapping("/filter/{name}")
    public ResponseEntity<List<Person>> filter(@PathVariable("name") String name) {
        List<Person> filteredList = this.list.stream()
                .filter(person -> person.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filteredList);
    }
}
