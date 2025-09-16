package br.uel.ContatosBD_MarcosBeregula.controller;

import br.uel.ContatosBD_MarcosBeregula.model.Contact;
import br.uel.ContatosBD_MarcosBeregula.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    private ContactController(ContactService contactService) {
        this.contactService = contactService;
    };

    @GetMapping
    public ResponseEntity<List<Contact>> list() {
        return ResponseEntity.ok(contactService.listContacts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> search(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(contactService.searchContact(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Contact> registration(@Valid @RequestBody Contact contact) {
        Contact save = contactService.addContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try{
            contactService.removeContact(id);
            return ResponseEntity.ok("Contact successfully removed");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Contact> update(
            @PathVariable Long id,
            @RequestBody Contact updatedContact){
        try{
            Contact update = contactService.updateContact(id, updatedContact);
            return ResponseEntity.ok(update);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/_template")
    public Map<String, Object> contactTemplate() {
        return Map.of(
                "name", "John Doe",
                "phone", "+55 43 99999-9999",
                "email", "john@example.com",
                "address", "Rua X, 123 - Londrina/PR",
                "birthDate", "1995-08-20"
        );
    }
}
