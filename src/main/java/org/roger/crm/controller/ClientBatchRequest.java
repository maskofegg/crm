package org.roger.crm.controller;

import java.util.List;

public class ClientBatchRequest {
    private Integer company_id;
    private List<ClientRequest> clients ;

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public List<ClientRequest> getClients() {
        return clients;
    }

    public void setClients(List<ClientRequest> clients) {
        this.clients = clients;
    }
}
