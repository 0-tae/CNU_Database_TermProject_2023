package options;

import lombok.Getter;

import java.io.Serializable;



@Getter
public class OptionsPK implements Serializable {
    private String licensePlateNo;
    private String optionName;
}
