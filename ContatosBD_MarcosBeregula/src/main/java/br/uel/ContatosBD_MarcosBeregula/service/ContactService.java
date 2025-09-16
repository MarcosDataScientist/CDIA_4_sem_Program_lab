package br.uel.ContatosBD_MarcosBeregula.service;

import br.uel.ContatosBD_MarcosBeregula.model.Contact;
import br.uel.ContatosBD_MarcosBeregula.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RuntimeException("No find a contact with id: "+ id));
    }

    public Contact addContact(Contact c) {
        return contactRepository.save(c);
    }

    public void removeContact(Long id) {
        if(!contactRepository.existsById(id)){
            throw new RuntimeException("Contact not found by id: "+ id);
        }
        contactRepository.deleteById(id);
    }

    public Contact updateContact(Long id, Contact contactAtt) {
        return contactRepository.findById(id)
                .map(c->{
                    c.setName(contactAtt.getName());
                    c.setEmail(contactAtt.getEmail());
                    c.setPhone(contactAtt.getPhone());
                    c.setAddress(contactAtt.getAddress());
                    c.setBirthDate(contactAtt.getBirthDate());
                    return contactRepository.save(c);
                })
                .orElseThrow(()->
                        new RuntimeException("No find a contact with id: "+ id));
    }
}
