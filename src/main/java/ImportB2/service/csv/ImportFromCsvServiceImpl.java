package ImportB2.service.csv;

import ImportB2.dao.dto.CustomerDto;
import ImportB2.dao.jdbc.JDBCConnection;
import ImportB2.service.helper.ServiceHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class ImportFromCsvServiceImpl implements ImportFromCsvService {

    private ServiceHelper serviceHelper = new ServiceHelper();

    public void uploadDataFromXml(String csv) {

        String line = "";
        String separator = ",";
        JDBCConnection jdbcConnection = new JDBCConnection();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csv))) {

            while ((line = bufferedReader.readLine()) != null) {

                String[] customerData = line.split(separator);

                CustomerDto customerDto = new CustomerDto();
                customerDto.setName(customerData[0]);
                customerDto.setSurname(customerData[1]);

                if (customerData[2].length() > 0) {
                    customerDto.setAge(Integer.valueOf(customerData[2]));
                }

                customerDto.setCity(customerData[3]);
                jdbcConnection.insertRecordCustomerIntoTable(customerDto);

                insertContactToDatabase(jdbcConnection, customerData, customerDto);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertContactToDatabase(JDBCConnection jdbcConnection, String[] customerData, CustomerDto customerDto) throws SQLException {

        for (int i = 4; i < customerData.length; i++) {
            jdbcConnection.insertRecordContactIntoTable(serviceHelper.createContactDto(customerData[i]), customerDto);
        }
    }
}
