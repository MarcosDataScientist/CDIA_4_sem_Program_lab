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
    public ResponseEntity<?> search(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.searchContact(id));
    }

    @PostMapping
    public ResponseEntity<String> registration(@Valid @RequestBody Contact contact) {
        contactService.addContact(contact);
        return ResponseEntity.ok("Contact successfully added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        contactService.removeContact(id);
        return ResponseEntity.ok("Contact successfully removed");
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable Long id,
            @RequestBody Contact updatedContact){
        contactService.updateContact(id, updatedContact);
        return ResponseEntity.ok("Contact successfully updated");
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
