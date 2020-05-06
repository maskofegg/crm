package org.roger.crm.controller;

import org.roger.crm.model.Client;
import org.roger.crm.model.ClientRepository;
import org.roger.crm.model.Company;
import org.roger.crm.model.CompanyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class ClientController {
	private final Map<String, Object> res = new HashMap<>() ;
	@Autowired
	private ClientRepository repository ;
	@Autowired
	private CompanyRepository companyRepository ;

	ClientController(){
		res.put("status", true) ;
	}

	@RequestMapping(value = "/client", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('GET_CLIENT')")
	public Map<String, Object> list(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "per_page", defaultValue = "10") Integer perPage,
			@RequestParam(name = "company_id", required = false) Integer company_id
	) {
		try {
			Page<Client> clients;
			if (company_id != null)
				clients = repository.findClientsByCompany(companyRepository.getOne(company_id), PageRequest.of(page, perPage));
			else
				clients = repository.findAll(PageRequest.of(page, perPage));
			res.put("page", page);
			res.put("per_page", perPage);
			res.put("total", repository.count());
			List<Client> data = new LinkedList<>();
			for (Client client : clients) {
				data.add(client);
			}
			res.put("data", data);
		} catch (Exception e) {
			res.put("status", false) ;
			res.put("message", e.getMessage()) ;
		}
		return res ;
	}

	@RequestMapping(value = "/client/{client_id}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('GET_CLIENT')")
	public Map<String, Object> get(@PathVariable Integer client_id) {
		try {
			Client client = repository.getOne(client_id) ;
			res.put("data", client);
		} catch (Exception e) {
			res.put("status", false) ;
			res.put("message", e.getMessage()) ;
		}
		return res ;
	}

	@RequestMapping(value = "/client", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADD_CLIENT')")
	public Map<String, Object> add (@RequestBody ClientRequest request, Authentication authentication) {
		Optional<Company> company = companyRepository.findById(request.getCompany_id()) ;
		if(company.isPresent()) {
			Client client = new Client() ;
			BeanUtils.copyProperties(request, client);
			client.setCreatedAt(new Date());
			client.setCreatedBy(authentication.getName());
			client.setUpdatedAt(new Date());
			client.setUpdatedBy(authentication.getName());

			client.setCompany(company.get());
			repository.save(client);
			res.put("data", client.getId()) ;

		} else {
			res.put("status", false) ;
			res.put("message", "Cannot find company: " + request.getCompany_id()) ;
		}
		return res ;
	}

	@RequestMapping(value = "/client/batch", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADD_CLIENT')")
	public Map<String, Object> batch (@RequestBody ClientBatchRequest request, Authentication authentication) {
		Optional<Company> repo = companyRepository.findById(request.getCompany_id()) ;
		if(repo.isPresent()) {
			Company company = repo.get() ;
			List<Integer> data = new LinkedList<>() ;
			request.getClients().forEach(clientRequest -> {
				Client client = new Client() ;
				BeanUtils.copyProperties(clientRequest, client);
				client.setCreatedAt(new Date());
				client.setCreatedBy(authentication.getName());
				client.setUpdatedAt(new Date());
				client.setUpdatedBy(authentication.getName());
				client.setCompany(company);
				repository.save(client);
				data.add(client.getId()) ;
			});
			res.put("data", data) ;

		} else {
			res.put("status", false) ;
			res.put("message", "Cannot find company: " + request.getCompany_id()) ;
		}
		return res ;
	}


	@RequestMapping(value = "/client/{client_id}", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('EDIT_CLIENT')")
	public Map<String, Object> edit (
			@PathVariable("client_id") Integer client_id,
			@RequestBody CompanyRequest request, Authentication authentication)
	{
		Optional<Client> repo = repository.findById(client_id) ;
		if(repo.isPresent()) {
			Client client = repo.get() ;
			BeanUtils.copyProperties(request, client);
			client.setUpdatedAt(new Date());
			client.setUpdatedBy(authentication.getName());
			repository.save(client) ;
			res.put("data", client_id) ;
		} else {
			res.put("status", false) ;
			res.put("message", "Cannot find client: " + client_id) ;
		}
		return res ;
	}
	@RequestMapping(value = "/client/{client_id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('DELETE_CLIENT')")
	public Map<String, Object> delete (
			@PathVariable("client_id") Integer client_id) {

		Optional<Client> repo = repository.findById(client_id) ;
		if(repo.isPresent()) {
			repository.deleteById(client_id);
			res.put("data", client_id) ;
		} else {
			res.put("status", false) ;
			res.put("message", "Cannot find client: " + client_id) ;
		}
		return res ;
	}
}