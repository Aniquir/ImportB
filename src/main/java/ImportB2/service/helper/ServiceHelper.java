package ImportB2.service.helper;

import ImportB2.dao.dto.ContactDto;

public class ServiceHelper {

    public ContactDto createContactDto(String contact) {

        ContactDto contactDto = new ContactDto();

        if (contact.matches(".*\\d+.*")) {
            if (contact.length() < 9) {
                contactDto.setType(0);
                contactDto.setContact(contact);
            } else {
                contactDto.setType(2);
                contactDto.setContact(contact);
            }
        } else if (contact.contains("@") && contact.contains(".")) {
            contactDto.setType(1);
            contactDto.setContact(contact);
        } else if (contact.matches(".*[a-zA-Z]+.*")) {
            contactDto.setType(3);
            contactDto.setContact(contact);
        }
        return contactDto;
    }
}
