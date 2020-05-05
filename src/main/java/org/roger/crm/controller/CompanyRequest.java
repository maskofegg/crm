package org.roger.crm.controller;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class CompanyRequest {
    private String name;
    private String address;

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
}
