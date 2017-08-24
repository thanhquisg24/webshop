package com.shopping.web.admin.controller.products;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shopping.web.admin.dto.ProductDTO;

@Controller
public class ProductController {

	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/product/createProduct", method=RequestMethod.GET)
	public String displayProductCreate(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return displayProduct(null,model,request,response);

	}
	private String displayProduct(Long productId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductDTO dto=new ProductDTO();
		model.addAttribute("productDTO", dto);
		return "admin/catalogue/product/form";

	}
}
