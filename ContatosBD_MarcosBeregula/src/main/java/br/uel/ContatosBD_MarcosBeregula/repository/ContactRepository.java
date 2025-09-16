package br.uel.ContatosBD_MarcosBeregula.repository;

import br.uel.ContatosBD_MarcosBeregula.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
