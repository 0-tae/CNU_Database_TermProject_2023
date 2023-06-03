package cnu2023.cnu_database_termproject_2023.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "CUSTOMER")
@Entity
@Getter
@NoArgsConstructor
@ToString
public class Customer {

    @Builder
    public Customer(String cno, String name, String passwd, String email) {
        this.cno = cno;
        this.name = name;
        this.passwd = passwd;
        this.email = email;
    }

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