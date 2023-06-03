package cnu2023.cnu_database_termproject_2023.customer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class CustomerSessionDto {
    String cno;
    String sessionId;
}