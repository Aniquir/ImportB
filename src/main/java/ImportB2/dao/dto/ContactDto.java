package ImportB2.dao.dto;

public class ContactDto {

    private Integer type;
    private String contact;

    public ContactDto() {
    }

    public ContactDto(Integer type, String contact) {
        this.type = type;
        this.contact = contact;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "ContactDto{" +
                "type=" + type +
                ", contact='" + contact + '\'' +
                '}';
    }
}
