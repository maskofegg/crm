package org.roger.crm.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Company")
public class Company {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="address")
    private String address;
    @Column(name="created_by")
    private String created_by;
    @Column(name="created_at")
    private Date created_at;
    @Column(name="updated_by")
    private String updated_by;
    @Column(name="updated_at")
    private Date updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedBy() {
        return created_by;
    }

    public void setCreatedBy(String created_by) {
        this.created_by = created_by;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public String getUpdatedBy() {
        return updated_by;
    }

    public void setUpdatedBy(String updated_by) {
        this.updated_by = updated_by;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }

    @OneToMany
    @JoinColumn(name = "company_id")
    private List<Client> clients;
    public List<Client> getClients () {
        return this.clients ;
    }
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
