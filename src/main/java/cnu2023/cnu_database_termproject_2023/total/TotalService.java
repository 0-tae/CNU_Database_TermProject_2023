package cnu2023.cnu_database_termproject_2023.total;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TotalService {
    private final EntityManager entityManager;
    private final DataSource dataSource;
    private Connection connection;

    public TotalService(EntityManager entityManager, DataSource dataSource) throws SQLException {
        this.entityManager = entityManager;
        this.dataSource = dataSource;
        connection=dataSource.getConnection();
    }

    public List<CarModelPerRentalDto> getCarModelPerRentalList() throws SQLException {
        List<CarModelPerRentalDto> result=new ArrayList<>();

        String sqlQuery = "SELECT r.MODELNAME AS MODELNAME, " +
                "count(*) AS RENTALCOUNT, " +
                "sum(p.PAYMENT) AS PAYMENTS " +
                "FROM RENTCAR r " +
                "INNER JOIN PREVIOUSRENTAL p " +
                "ON r.LICENSEPLATENO = p.LICENSEPLATENO " +
                "GROUP BY r.MODELNAME";

        PreparedStatement statement=connection.prepareStatement(sqlQuery);
        ResultSet resultSet=statement.executeQuery();

        while(resultSet.next()){
            CarModelPerRentalDto dto=
                    CarModelPerRentalDto.builder().
                            PAYMENTS(resultSet.getString("PAYMENTS")).
                            RENTALCOUNT(resultSet.getInt("RENTALCOUNT")).
                            MODELNAME(resultSet.getString("MODELNAME")).build();

            log.info("{}",dto);
            result.add(dto);
        }

        return result;
    }

    public List<PaymentAndRentalPerYearDto> getPaymentAndRentalPerYearList(int year, String modelName) throws SQLException {
        List<PaymentAndRentalPerYearDto> result=new ArrayList<>();

        String sqlQuery = "SELECT " +
                " CASE GROUPING(r.LICENSEPLATENO) " +
                " WHEN 1 THEN 'All CurrentModel Cars' " +
                " ELSE r.LICENSEPLATENO END AS car, " +
                " CASE GROUPING(EXTRACT(MONTH FROM p.DATERENTED)) " +
                " WHEN 1 THEN 'All Month' " +
                " ELSE to_char(EXTRACT(MONTH FROM p.DATERENTED)) END AS rentdate, " +
                " COUNT(*) \"TOTAL_Rentaled\", " +
                " sum(p.payment) \"TOTAL_Payment\" " +
                " FROM RENTCAR r,PREVIOUSRENTAL p " +
                " WHERE(r.MODELNAME= ? AND EXTRACT(YEAR FROM p.DATERENTED)=?) AND r.LICENSEPLATENO=p.LICENSEPLATENO " +
                " GROUP BY GROUPING SETS(r.LICENSEPLATENO,EXTRACT(MONTH FROM p.DATERENTED)) " +
                " ORDER BY EXTRACT(MONTH FROM p.DATERENTED) ";

        PreparedStatement statement=connection.prepareStatement(sqlQuery);
        statement.setString(1, modelName);
        statement.setInt(2, year);

        ResultSet resultSet=statement.executeQuery();

        while(resultSet.next()){
            PaymentAndRentalPerYearDto dto=
                    PaymentAndRentalPerYearDto.builder().
                            TOTAL_Rentaled(resultSet.getString("TOTAL_Rentaled")).
                            TOTAL_Payment(resultSet.getString("TOTAL_Payment")).
                            CAR(resultSet.getString("car")).
                            RENTDATE(resultSet.getString("rentdate")).build();
            log.info("{}",dto);
            result.add(dto);
        }

        return result;
    }

    public List<VIPCustomerDto> getVIPCustomerList() throws SQLException {
        List<VIPCustomerDto> result=new ArrayList<>();

        String sqlQuery = "SELECT pcno AS CNO, " +
                " TOTAL_MONEY AS TOTAL_Payment, " +
                " RANK() OVER(ORDER BY TOTAL_MONEY DESC)RANK " +
                " FROM (SELECT p.CNO AS pcno, sum(payment) AS TOTAL_MONEY " +
                " FROM PREVIOUSRENTAL p " +
                " GROUP BY p.CNO) sum_table ";

        PreparedStatement statement=connection.prepareStatement(sqlQuery);
        ResultSet resultSet=statement.executeQuery();

        while(resultSet.next()){
            VIPCustomerDto dto=
                    VIPCustomerDto.builder().
                            CNO(resultSet.getString("CNO")).
                            TOTAL_Payment(resultSet.getString("TOTAL_Payment")).
                            RANK(resultSet.getString("RANK")).build();
            log.info("{}",dto);
            result.add(dto);
        }

        return result;
    }
}
