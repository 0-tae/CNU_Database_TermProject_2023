package cnu2023.cnu_database_termproject_2023.options;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "OPTIONS")
@IdClass(OptionsPK.class)
@Getter
@NoArgsConstructor
@ToString
public class Options {
    @Id
    @Column(name = "LICENSEPLATENO")
    private String licensePlateNo;

    @Id
    @Column(name = "OPTIONNAME")
    private String optionName;

    @Builder
    public Options(String licensePlateNo, String optionName) {
        this.licensePlateNo = licensePlateNo;
        this.optionName = optionName;
    }
}
