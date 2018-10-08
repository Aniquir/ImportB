package ImportB2.dao.jdbc;

import ImportB2.dao.dto.ContactDto;
import ImportB2.dao.dto.CustomerDto;

import java.sql.*;

public class JDBCConnection {

    private static final String CUSTOMERS_TABLE_NAME = "CUSTOMERS";
    private static final String CONTACTS_TABLE_NAME = "CONTACTS";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/personsdata?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "training";
    private static final String PASSWORD = "training";

    public void getConnectionAndCreateTablesIfNotExist() {

        try (Connection dbConnection = getDBConnection()) {

            if (dbConnection != null) {

                createTablesIfNotExist(dbConnection);
                System.out.println("Connection to database: ON");

            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private void createTablesIfNotExist(Connection conn) throws SQLException {

        String sqlCreateCustomers = "CREATE TABLE IF NOT EXISTS " + CUSTOMERS_TABLE_NAME
                + " (`ID_CUSTOMER` INT AUTO_INCREMENT NOT NULL,"
                + " `NAME` VARCHAR(45) NOT NULL,"
                + "`SURNAME` VARCHAR(45) NOT NULL,"
                + "`AGE` INT NULL,"
                + "`CITY` VARCHAR(45) NOT NULL,"
                + "PRIMARY KEY (`ID_CUSTOMER`))";

        String sqlCreateContacts = "CREATE TABLE IF NOT EXISTS " + CONTACTS_TABLE_NAME
                + " (`ID_CONTACT` INT AUTO_INCREMENT NOT NULL,"
                + " `ID_CUSTOMER` INT NOT NULL,"
                + " `TYPE` INT NOT NULL,"
                + " `CONTACT` VARCHAR(45) NOT NULL,"
                + " PRIMARY KEY (`ID_CONTACT`),"
                + " CONSTRAINT `ID_CUSTOMER`"
                + " FOREIGN KEY (`ID_CUSTOMER`)"
                + " REFERENCES CUSTOMERS(`ID_CUSTOMER`)"
                + " ON UPDATE CASCADE"
                + " ON DELETE CASCADE)";

        Statement stmt = conn.createStatement();
        stmt.execute(sqlCreateCustomers);
        stmt.execute(sqlCreateContacts);
    }

    public void insertRecordCustomerIntoTable(CustomerDto customerDto) {

        try {

            Connection dbConnection = null;
            PreparedStatement preparedStatement = null;

            String insertTableSQL = "INSERT INTO CUSTOMERS"
                    + "(NAME, SURNAME, AGE, CITY) VALUES"
                    + "(?,?,?,?)";

            try {
                dbConnection = getDBConnection();
                preparedStatement = dbConnection.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, customerDto.getName());
                preparedStatement.setString(2, customerDto.getSurname());

                if (customerDto.getAge() != null) {
                    preparedStatement.setInt(3, customerDto.getAge());
                } else {
                    preparedStatement.setNull(3, Types.INTEGER);
                }

                preparedStatement.setString(4, customerDto.getCity());

                preparedStatement.executeUpdate();

                System.out.println("Record is inserted into CUSTOMERS table!");

            } catch (SQLException e) {

                System.out.println(e.getMessage());

            } finally {

                if (preparedStatement != null) {
                    preparedStatement.close();
                }

                if (dbConnection != null) {
                    dbConnection.close();
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void insertRecordContactIntoTable(ContactDto contactDto, CustomerDto customerDto) throws SQLException {

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO CONTACTS"
                + "(ID_CUSTOMER, TYPE, CONTACT) VALUES"
                + "((SELECT MAX(ID_CUSTOMER) FROM CUSTOMERS),?,?)";

        try {
            dbConnection = getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, contactDto.getType());
            preparedStatement.setString(2, contactDto.getContact());

            preparedStatement.executeUpdate();

            System.out.println("Record is inserted into CONTACTS table!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    private static Connection getDBConnection() {

        Connection dbConnection;

        try {

            Class.forName(DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());
        }

        try {

            dbConnection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return null;
    }
}

