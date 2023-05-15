package customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "CUSTOMER")
@Entity
@Data
public class Customer {

    @Builder
    public Customer(String cno, String name, String passwd, String email) {
        this.cno = cno;
        this.name = name;
        this.passwd = passwd;
        this.email = email;
    }

    public Customer(){}

    @Id
    @Column(name = "CNO")
    private String cno;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWD")
    private String passwd;

    @Column(name = "EMAIL")
    private String email;
}