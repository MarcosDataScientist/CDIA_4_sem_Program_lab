package br.uel.ContatosBD_MarcosBeregula.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name= "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name can't be empty.")
    @Size(max = 100, message = "The name must be no longer than 100 characters.")
    private String name;

    @NotBlank(message = "The phone can't be empty")
    private String phone;

    @Email(message = "The email address is invalid..")
    @NotBlank(message = "Email is required.")
    @Size(max = 150, message = "The email must be no longer than 150 characters.")
    private String email;

    @Size(max = 150, message = "The address must be no longer than 150 characters.")
    private String address;

    @NotNull(message = "The birth date can't be empty.")
    @DateTimeFormat
    @Column(name = "birthDate")
    private Date birthDate;

    //Empty constructor
    public Contact(){
    };

    public Contact(String name, String phone, String email, String address, Date birthDate) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public Date getBirthDate() {
        return birthDate;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
