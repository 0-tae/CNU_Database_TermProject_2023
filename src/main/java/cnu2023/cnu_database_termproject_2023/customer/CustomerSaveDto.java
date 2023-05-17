package cnu2023.cnu_database_termproject_2023.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString

public class CustomerSaveDto {
    private String cno;
    private String name;
    private String passwd;
    private String email;

    @Builder
    public CustomerSaveDto(String cno, String name, String passwd, String email) {
        this.cno = cno;
        this.name = name;
        this.passwd = passwd;
        this.email = email;
    }

    public Customer toEntity(){
        return Customer.builder().cno(cno)
                                .name(name)
                                .passwd(passwd)
                                .email(email).build();
    }
}
