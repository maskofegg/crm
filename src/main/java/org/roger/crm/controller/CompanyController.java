package org.roger.crm.controller;

import org.roger.crm.model.Company;
import org.roger.crm.model.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class CompanyController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private CompanyRepository companyRepository ;

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public Page<Company> list(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "perPage", defaultValue = "10") Integer perPage
	) {
		return companyRepository.findAll(PageRequest.of(page, perPage));
	}

	@RequestMapping(value = "/company/{company_id}", method = RequestMethod.GET)
	public Company get(@PathVariable Integer company_id) {
		Optional<Company> repo = companyRepository.findById(company_id) ;
		return repo.orElse(null);
	}

	@RequestMapping(value = "/company", method = RequestMethod.POST)
	public Map<String, Object> add (@RequestBody Company company) {
		company.setCreatedAt(new Date());
		company.setUpdatedAt(new Date());

		companyRepository.save(company) ;
		Map<String, Object> res = new HashMap<>();
		res.put("company_id", company.getId()) ;

		return res ;
	}

	@RequestMapping(value = "/company/{company_id}", method = RequestMethod.PUT)
	public Map<String, Object> edit (
			@PathVariable("company_id") Integer company_id,
			@RequestBody Company company1)
	{
		Optional<Company> repo = companyRepository.findById(company_id) ;
		if(repo.isPresent()) {
			Company company = repo.get() ;
			company.setUpdatedAt(new Date());
			company.setName(company1.getName());
			company.setAddress(company1.getAddress());
			companyRepository.save(company) ;
		}
		//TODO Return 404
		Map<String, Object> res = new HashMap<>();

		return res ;
	}
	@RequestMapping(value = "/company/{company_id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete (
			@PathVariable("company_id") Integer company_id) {

		Optional<Company> repo = companyRepository.findById(company_id) ;
		if(repo.isPresent()) {
			companyRepository.deleteById(company_id);
		}
		Map<String, Object> res = new HashMap<>();

		return res ;
	}
}