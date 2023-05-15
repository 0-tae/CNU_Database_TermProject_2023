package customer;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Builder;
import org.apache.catalina.util.CustomObjectInputStream;


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

    public CustomerSaveDto(){}

    public Customer toEntity(){
        return Customer.builder().cno(cno)
                                .name(name)
                                .passwd(passwd)
                                .email(email).build();
    }
}
