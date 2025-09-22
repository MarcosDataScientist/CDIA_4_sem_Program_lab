package br.uel.ContatosBD_MarcosBeregula.repository;

import br.uel.ContatosBD_MarcosBeregula.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsContactByPhone(String phone);

    boolean existsContacByEmail(String email);

    boolean existsContactByEmailAndIdNot(String email, Long id);
    boolean existsContactByPhoneAndIdNot(String phone, Long id);

}
