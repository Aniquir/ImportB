package ImportB2;

import ImportB2.dao.jdbc.JDBCConnection;
import ImportB2.service.csv.ImportFromCsvServiceImpl;
import ImportB2.service.xml.ImportFromXmlServiceImpl;

public class App {

    public static void main(String[] args) {

        //create tables if not exist
        JDBCConnection jdbcConnection = new JDBCConnection();
        jdbcConnection.getConnectionAndCreateTablesIfNotExist();

        //upload data from xml file
        String xmlPath = "src//main/resources/dane-osoby.xml";
        ImportFromXmlServiceImpl importFromXmlServiceImpl = new ImportFromXmlServiceImpl();
        importFromXmlServiceImpl.uploadDataFromXml(xmlPath);

        //upload data from csv file
        String csvPath = "src//main/resources/dane-osoby.txt";
        ImportFromCsvServiceImpl importFromCsvServiceImpl = new ImportFromCsvServiceImpl();
        importFromCsvServiceImpl.uploadDataFromXml(csvPath);
    }
}
