package br.uel.ContatosBD_MarcosBeregula.service;

import br.uel.ContatosBD_MarcosBeregula.model.Contact;
import br.uel.ContatosBD_MarcosBeregula.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> listContacts() {
        return contactRepository.findAll();
    }

    public Contact searchContact(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found by id: " + id));
    }

    public Contact addContact(Contact c) {
        if(contactRepository.existsContacByEmail(c.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"This email is already in use");
        };

        if(contactRepository.existsContactByPhone(c.getPhone())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"This phone is already in use");
        };

        return contactRepository.save(c);
    }

    public void removeContact(Long id) {
        if(!contactRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found by id: " + id);
        }
        contactRepository.deleteById(id);
    }

    public Contact updateContact(Long id, Contact contactAtt) {
        return contactRepository.findById(id)
                .map(c->{
                    if (contactRepository.existsContactByEmailAndIdNot(contactAtt.getEmail(), id)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"Email already in use");
                    }
                    if (contactRepository.existsContactByPhoneAndIdNot(contactAtt.getPhone(), id)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"Phone already in use");
                    }
                    c.setName(contactAtt.getName());
                    c.setEmail(contactAtt.getEmail());
                    c.setPhone(contactAtt.getPhone());
                    c.setAddress(contactAtt.getAddress());
                    c.setBirthDate(contactAtt.getBirthDate());
                    return contactRepository.save(c);
                })
                .orElseThrow(()->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found by id: " + id));
    }
}
