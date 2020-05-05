package org.roger.crm.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
	Page<Client> findClientsByCompany(Company company, Pageable pageable) ;
}
