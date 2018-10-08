package ImportB2.service.xml;

import ImportB2.dao.dto.ContactDto;
import ImportB2.dao.dto.CustomerDto;
import ImportB2.dao.jdbc.JDBCConnection;
import ImportB2.service.helper.ServiceHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImportFromXmlServiceImpl implements ImportFromXmlService {

    private ServiceHelper serviceHelper = new ServiceHelper();

    public void uploadDataFromXml(String xml) {

        try {

            File xmlFile = new File(xml);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.normalize();

            NodeList nodeListCustomer = document.getElementsByTagName("person");

            for (int i = 0; i < nodeListCustomer.getLength(); i++) {

                Node nodeCustomer = nodeListCustomer.item(i);
                CustomerDto customerDto = new CustomerDto();

                if (nodeCustomer.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) nodeCustomer;

                    customerDto.setName(element.getElementsByTagName("name").item(0).getTextContent());
                    customerDto.setSurname(element.getElementsByTagName("surname").item(0).getTextContent());

                    if (element.getElementsByTagName("age").getLength() > 0) {
                        customerDto.setAge(Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()));
                    }

                    customerDto.setCity(element.getElementsByTagName("city").item(0).getTextContent());

                    JDBCConnection jdbcConnection = new JDBCConnection();
                    jdbcConnection.insertRecordCustomerIntoTable(customerDto);

                    List<ContactDto> contactDtoList = new ArrayList<>();
                    String allContacts = (element.getElementsByTagName("contacts").item(0).getTextContent());
                    List<String> contactsList = Arrays.asList(allContacts.split("\n"));

                    addContactDtoToList(contactDtoList, contactsList);

                    insertContactToDatabase(customerDto, jdbcConnection, contactDtoList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertContactToDatabase(CustomerDto customerDto, JDBCConnection jdbcConnection, List<ContactDto> contactDtoList) throws SQLException {
        for (int j = 0; j < contactDtoList.size(); j++) {

            jdbcConnection.insertRecordContactIntoTable(contactDtoList.get(j), customerDto);
        }
    }

    private void addContactDtoToList(List<ContactDto> contactDtoList, List<String> contactsList) {
        for (int j = 0; j < contactsList.size(); j++) {

            if (contactsList.get(j).length() > 1 && !contactsList.get(j).endsWith(" ")) {
                contactDtoList.add(serviceHelper.createContactDto(contactsList.get(j).trim()));
            }
        }
    }
}
