package br.uel.ContatosBD_MarcosBeregula.controller;

import br.uel.ContatosBD_MarcosBeregula.model.Contact;
import br.uel.ContatosBD_MarcosBeregula.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    private ContactController(ContactService contactService) {
        this.contactService = contactService;
    };

    @GetMapping
    public String list(Model model) {
        model.addAttribute("contact", contactService.listContacts());
        //return "contacts/list";
        return "contacts/list_bootstrap";
    }

    @GetMapping("/new")
    public String newContact(Model model) {
        model.addAttribute("contact", new Contact());
        //return "contacts/form";
        return "contacts/form_bootstrap";
    }

    @PostMapping
    public String registration(@Valid @ModelAttribute("contact") Contact contact,
                               BindingResult errors,
                               Model model,
                               RedirectAttributes ra) {
        //O que esse 'if' faz?
        if (errors.hasErrors()) return "contacts/form";

        try {
            contactService.addContact(contact);
            ra.addFlashAttribute("message", "Contact successfully added");
            return "redirect:/contacts";
        } catch (ResponseStatusException e) {
            String msg = e.getReason() != null ? e.getReason() : "Invalid data";
            if (msg.toLowerCase().contains("email")) {
                errors.rejectValue("email", "duplicate", msg);
            } else if (msg.toLowerCase().contains("phone")) {
                errors.rejectValue("phone", "duplicate", msg);
            } else {
                errors.reject("globalError", msg);
            }

            model.addAttribute("contact", contactService.searchContact(contact.getId()));
            //return "contacts/form";
            return "contacts/form_bootstrap";
        }
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable Long id,
                         RedirectAttributes ra) {
        contactService.removeContact(id);
        ra.addFlashAttribute("message", "Contact successfully removed");
        return "redirect:/contacts";
    }

    @PutMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute Contact contact,
                         BindingResult error,
                         RedirectAttributes ra,
                         Model model) {

        //Captura erros de construção de objeto
        //if (error.hasErrors()) return "contacts/form";
        if (error.hasErrors()) return "contacts/form_bootstrap";

        //Captura erros de validação de campos únicos
        try {
            contactService.updateContact(id, contact);
            ra.addFlashAttribute("message", "Contact successfully updated");
            return "redirect:/contacts";
        } catch (ResponseStatusException ex) {
            var status = ex.getStatusCode();
            var reason = ex.getReason() != null ? ex.getReason() : "Invalid data";

            if (status.value() == 400) {
                if (reason.toLowerCase().contains("email")) {
                    error.rejectValue("email", "duplicate", "Email already in use");
                } else if (reason.toLowerCase().contains("phone")) {
                    error.rejectValue("phone", "duplicate", "Phone already in use");
                } else {
                    error.reject("badRequest", reason);
                }

                //Mantém na página do formulário
                //return "contacts/form";
                return "contacts/form_bootstrap";
            } else if (status.value() == 404) {
                ra.addFlashAttribute("error", reason);
                return "redirect:/contacts";
            }

            // fallback
            ra.addFlashAttribute("error", reason);
            return "redirect:/contacts";
        }
    }

    @GetMapping("/edit/{id}")
    public String openEditing(@PathVariable Long id,
                              Model model) {
        model.addAttribute("contact", contactService.searchContact(id));
        //return "contacts/form";
        return "contacts/form_bootstrap";
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
