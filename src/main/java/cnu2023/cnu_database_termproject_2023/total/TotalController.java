package cnu2023.cnu_database_termproject_2023.total;

import cnu2023.cnu_database_termproject_2023.carmodel.CarModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RestController
@Slf4j
public class TotalController {
    private final TotalService totalService;
    public TotalController(TotalService totalService) {
        this.totalService = totalService;
    }

    @GetMapping("/total/CarModelPerRental")
    private List<CarModelPerRentalDto> readCarModelPerRentalList() throws SQLException {
        return totalService.getCarModelPerRentalList();
    }

    @GetMapping("/total/PaymentAndRentalPerYear")
    private List<PaymentAndRentalPerYearDto> readPaymentAndRentalPerYearList(@RequestParam int year,
                                                                       @RequestParam String modelName) throws SQLException {
        return totalService.getPaymentAndRentalPerYearList(year,modelName);
    }
    @GetMapping("/total/VIPCustomer")
    private List<VIPCustomerDto> readVIPCustomerList() throws SQLException {
        return totalService.getVIPCustomerList();
    }
}
