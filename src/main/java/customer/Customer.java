package customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "CUSTOMER")
@Entity
public class Customer {
    @Id
    @Column(name = "CNO")
    private String cno;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWD")
    private Integer passwd;

    @Column(name = "EMAIL")
    private String email;
}