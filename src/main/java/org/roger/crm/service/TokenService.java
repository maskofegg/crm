package org.roger.crm.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TokenService implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<? extends GrantedAuthority> permissions ;
		switch (username) {
			case "admin":
				permissions = AuthorityUtils.createAuthorityList(
						"ADD_COMPANY",
						"EDIT_COMPANY",
						"GET_COMPANY",
						"DELETE_COMPANY",
						"ADD_CLIENT", 
						"EDIT_CLIENT",
						"GET_CLIENT",
						"DELETE_CLIENT") ;
				return new User(
						"admin",
						"$2a$10$aECjcwzw6CRsuIx2x6/44OqCL4rw5ilaPiRcoJCGJpru.E5Pa6kSy",
						permissions);
			case "manager":
				permissions = AuthorityUtils.createAuthorityList(
						"EDIT_COMPANY",
						"GET_COMPANY",
						"DELETE_COMPANY",
						"EDIT_CLIENT",
						"GET_CLIENT",
						"DELETE_CLIENT"
				) ;
				return new User(
						"manager",
						"$2y$12$cd3dnj05YlGSI3lhjCpDf.gKC5RiyIIPI1IRiWQhcHBHMx/yD/W/W",
						permissions);
			case "operator":
				permissions = AuthorityUtils.createAuthorityList(
						"GET_COMPANY",
						"ADD_COMPANY",
						"GET_CLIENT",
						"ADD_CLIENT"
				) ;
				return new User(
						"operator",
						"$2y$12$vD2gi9I7wJrSR7JDvpdy8uRnjc9uMFWIUsyl8mCo8QdhQkarECCsG",
						permissions);
		}

		throw new UsernameNotFoundException("帳號密碼錯誤");
	}
}