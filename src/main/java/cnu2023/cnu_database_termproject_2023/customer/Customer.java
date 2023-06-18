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
@Builder
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Customer {

    @Id
    @Column(name = "CNO")
    private String cno;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWD")
    private String passwd;

    @Column(name = "EMAIL")
    private String email;

    @Column(name="IS_RENTING")
    private int is_renting;

    public void setRenting(int rent){
        is_renting=rent;
    }
}