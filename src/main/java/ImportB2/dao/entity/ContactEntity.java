package ImportB2.dao.entity;

public class ContactEntity {

    private Long id;
    private Long idCustomer;
    private Integer type;
    private String contact;

    public ContactEntity() {
    }

    public ContactEntity(Long id, Long idCustomer, Integer type, String contact) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.type = type;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
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
        return "ContactEntity{" +
                "id=" + id +
                ", idCustomer=" + idCustomer +
                ", type=" + type +
                ", contact='" + contact + '\'' +
                '}';
    }
}
