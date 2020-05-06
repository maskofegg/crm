package org.roger.crm.controller;

import org.roger.crm.model.Client;
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
public class CompanyController {
	private final Map<String, Object> res = new HashMap<>() ;
	@Autowired
	private CompanyRepository repository ;

	CompanyController(){
		res.put("status", true) ;
	}

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('GET_COMPANY')")
	public Map<String, Object> list(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "per_page", defaultValue = "10") Integer perPage
	) {
		try {
			Page<Company> companies = repository.findAll(PageRequest.of(page, perPage));
			res.put("page", page);
			res.put("per_page", perPage);
			res.put("total", repository.count());
			List<Company> data = new LinkedList<>();
			for (Company company : companies) {
				data.add(company);
			}
			res.put("data", data);
		} catch (Exception e) {
			res.put("status", false) ;
			res.put("message", e.getMessage()) ;
		}
		return res ;
	}

	@RequestMapping(value = "/company/{company_id}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('GET_COMPANY')")
	public Map<String, Object> get(@PathVariable Integer company_id) {
		try {
			Company company = repository.getOne(company_id) ;
			res.put("data", company);
		} catch (Exception e) {
			res.put("status", false) ;
			res.put("message", e.getMessage()) ;
		}
		return res ;
	}

	@RequestMapping(value = "/company", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADD_COMPANY')")
	public Map<String, Object> add (@RequestBody CompanyRequest request, Authentication authentication) {
		Company company = new Company() ;
		BeanUtils.copyProperties(request, company);
		company.setCreatedAt(new Date());
		company.setCreatedBy(authentication.getName());
		company.setUpdatedAt(new Date());
		company.setUpdatedBy(authentication.getName());
		repository.save(company) ;
		res.put("data", company.getId()) ;

		return res ;
	}

	@RequestMapping(value = "/company/{company_id}", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('EDIT_COMPANY')")
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
			res.put("data", company.getId()) ;
		} else {
			res.put("status", false) ;
			res.put("message", "Cannot find company: " + company_id) ;
		}
		return res ;
	}
	@RequestMapping(value = "/company/{company_id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('DELETE_COMPANY')")
	public Map<String, Object> delete (
			@PathVariable("company_id") Integer company_id) {

		Optional<Company> repo = repository.findById(company_id) ;
		if(repo.isPresent()) {
			repository.deleteById(company_id);
			res.put("data", company_id) ;
		} else {
			res.put("status", false) ;
			res.put("message", "Cannot find company: " + company_id) ;
		}
		return res ;
	}
}