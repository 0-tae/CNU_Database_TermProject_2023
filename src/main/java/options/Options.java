package options;

import jakarta.persistence.*;


@Entity
@Table(name = "OPTIONS")
@IdClass(OptionsPK.class)
public class Options {
    @Id
    @Column(name = "LICENSEPLATENO")
    private String licensePlateNo;

    @Id
    @Column(name = "OPTIONNAME")
    private String optionName;
}
