package org.roger.crm.controller;

import org.roger.crm.model.Client;
import org.roger.crm.model.ClientRepository;
import org.roger.crm.model.Company;
import org.roger.crm.model.CompanyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class ClientController {
	@Autowired
	private ClientRepository repository ;
	@Autowired
	private CompanyRepository companyRepository ;

	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public Page<Client> list(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "per_page", defaultValue = "10") Integer perPage,
			@RequestParam(name = "company_id", required = false) Integer company_id
	) {
		if(company_id != null)
			return repository.findClientsByCompany(companyRepository.getOne(company_id), PageRequest.of(page, perPage));

		return repository.findAll(PageRequest.of(page, perPage));
	}

	@RequestMapping(value = "/client/{client_id}", method = RequestMethod.GET)
	public Client get(@PathVariable Integer client_id) {
		Optional<Client> repo = repository.findById(client_id) ;
		return repo.orElse(null);
	}

	@RequestMapping(value = "/client", method = RequestMethod.POST)
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
			Map<String, Object> res = new HashMap<>();
			res.put("client_id", client.getId()) ;

			return res ;
		}

		//TODO Return 404
		return null ;
	}

	@RequestMapping(value = "/client/batch", method = RequestMethod.POST)
	public Map<String, Object> batch (@RequestBody ClientBatchRequest request, Authentication authentication) {
		Optional<Company> repo = companyRepository.findById(request.getCompany_id()) ;
		if(repo.isPresent()) {
			Company company = repo.get() ;
			request.getClients().forEach(clientRequest -> {
				Client client = new Client() ;
				BeanUtils.copyProperties(clientRequest, client);
				client.setCreatedAt(new Date());
				client.setCreatedBy(authentication.getName());
				client.setUpdatedAt(new Date());
				client.setUpdatedBy(authentication.getName());
				client.setCompany(company);
				repository.save(client);
			});
			Map<String, Object> res = new HashMap<>();

			return res ;
		}

		//TODO Return 404
		return null ;
	}


	@RequestMapping(value = "/client/{client_id}", method = RequestMethod.PUT)
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
		}
		//TODO Return 404
		Map<String, Object> res = new HashMap<>();

		return res ;
	}
	@RequestMapping(value = "/client/{client_id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete (
			@PathVariable("client_id") Integer client_id) {

		Optional<Client> repo = repository.findById(client_id) ;
		if(repo.isPresent()) {
			repository.deleteById(client_id);
		}
		//TODO Return 404
		Map<String, Object> res = new HashMap<>();

		return res ;
	}
}