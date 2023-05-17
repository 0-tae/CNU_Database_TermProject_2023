package options;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "OPTIONS")
@IdClass(OptionsPK.class)
@Getter
@NoArgsConstructor
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
