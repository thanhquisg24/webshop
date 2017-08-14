package com.shopping.web.support;

import java.security.Principal;

public class UserSessionHelper {

	
	public static String getPrincipalName(Principal principal) {
		if(principal==null) {
			return "admin@tester.com";
		}
		return principal.getName();
	}
}
