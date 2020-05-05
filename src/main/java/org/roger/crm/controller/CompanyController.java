package org.roger.crm.controller;

import org.roger.crm.model.Company;
import org.roger.crm.model.CompanyRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class CompanyController {
	@Autowired
	private CompanyRepository repository ;

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public Page<Company> list(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "perPage", defaultValue = "10") Integer perPage
	) {
		return repository.findAll(PageRequest.of(page, perPage));
	}

	@RequestMapping(value = "/company/{company_id}", method = RequestMethod.GET)
	public Company get(@PathVariable Integer company_id) {
		Optional<Company> repo = repository.findById(company_id) ;
		return repo.orElse(null);
	}

	@RequestMapping(value = "/company", method = RequestMethod.POST)
	public Map<String, Object> add (@RequestBody CompanyRequest request, Authentication authentication) {
		Company company = new Company() ;
		BeanUtils.copyProperties(request, company);
		company.setCreatedAt(new Date());
		company.setCreatedBy(authentication.getName());
		company.setUpdatedAt(new Date());
		company.setUpdatedBy(authentication.getName());
		repository.save(company) ;
		Map<String, Object> res = new HashMap<>();
		res.put("company_id", company.getId()) ;

		return res ;
	}

	@RequestMapping(value = "/company/{company_id}", method = RequestMethod.PUT)
	public Map<String, Object> edit (
			@PathVariable("company_id") Integer company_id,
			@RequestBody CompanyRequest request, Authentication authentication)
	{
		Optional<Company> repo = repository.findById(company_id) ;
		if(repo.isPresent()) {
			Company company = repo.get() ;
			BeanUtils.copyProperties(request, company);
			company.setUpdatedAt(new Date());
			company.setUpdatedBy(authentication.getName());
			repository.save(company) ;
		}
		//TODO Return 404
		Map<String, Object> res = new HashMap<>();

		return res ;
	}
	@RequestMapping(value = "/company/{company_id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete (
			@PathVariable("company_id") Integer company_id) {

		Optional<Company> repo = repository.findById(company_id) ;
		if(repo.isPresent()) {
			repository.deleteById(company_id);
		}
		//TODO Return 404
		Map<String, Object> res = new HashMap<>();

		return res ;
	}
}