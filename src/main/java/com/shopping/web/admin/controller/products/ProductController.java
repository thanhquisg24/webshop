package com.shopping.web.admin.controller.products;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.services.catalog.category.CategoryService;
import com.shopping.core.business.services.catalog.manufacture.ManufactureService;
import com.shopping.core.business.services.catalog.product.ProductService;
import com.shopping.core.business.services.catalog.product.attribute.ProductOptionService;
import com.shopping.core.business.services.catalog.product.attribute.ProductOptionValueService;
import com.shopping.core.model.catalog.category.Category;
import com.shopping.core.model.catalog.product.Product;
import com.shopping.web.admin.dto.ProductDTO;
import com.shopping.web.support.MessageHelper;
import com.shopping.web.support.UserSessionHelper;
import com.shopping.web.utils.GenericResponse;
import com.shopping.web.validator.ProductDTOValidator;

@Controller
public class ProductController {
	
		@Autowired
		private CategoryService categoryService;
		
		@Autowired
		private ManufactureService manufactureService;
		
		@Autowired
		private ProductService productService;
		
		@Autowired
		ProductOptionService productOptionService;
		
		@Autowired
		ProductOptionValueService productOptionValueService;
	  @Autowired
	   private ProductDTOValidator validator;
	   

	   
		private void AddAtributeIntoForm(Model model) throws ServiceException, JsonProcessingException{
		
			model.addAttribute("manufacturers", manufactureService.findAll());
			model.addAttribute("categorieslist", categoryService.listAll());
			ObjectMapper objectMapper = new ObjectMapper();
			model.addAttribute("optionList",objectMapper.writeValueAsString(productOptionService.listAll()));
			model.addAttribute("optionValueList",objectMapper.writeValueAsString(productOptionValueService.listAll()));	
			
		}
	   
	//@PreAuthorize("hasRole('PRODUCTS')")

	@RequestMapping(value="/admin/product/createProduct", method=RequestMethod.GET)
	public String displayProductCreate(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return displayProduct(null,model,request,response);

	}
	@RequestMapping(value="/admin/product/editProduct", method=RequestMethod.GET)
	public String editProduct(@RequestParam("id") Long id,Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return displayProduct(id,model,request,response);

	}
	private String displayProduct(Long productId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductDTO dto=new ProductDTO();
		if(productId!=null){
			Product pro=productService.getProduct(productId);
			//mapping dto data
			if( pro!=null){
				/*info*/
				dto.setId(pro.getId());
				dto.setName(pro.getName());
				dto.setSku(pro.getSku());
				dto.setShort_description(pro.getShort_description());
				dto.setFull_description(pro.getFull_description());
				dto.setFeature(pro.isFeature());
				/*end info*/
				/*pices*/
				dto.setPrice(pro.getPrice());
				dto.setOld_price(pro.getOld_price());
				dto.setProduct_cost(pro.getProduct_cost());
				dto.setDiscount(pro.isDiscount());
				dto.setPercent_discount(pro.getPercent_discount());
				dto.setPrice_discount(pro.getPrice_discount());
				
				/*end prices*/
				/*inventory*/
				dto.setStock_quantity(pro.getStock_quantity());
				dto.setDisplay_stock_availability(pro.isDisplay_stock_availability());
				dto.setDisplay_stock_qty(pro.isDisplay_stock_qty());
				dto.setMinimum_stock_qty(pro.getMinimum_stock_qty());
				dto.setMinimum_cart_qty(pro.getMinimum_cart_qty());
				dto.setMaximum_cart_qty(pro.getMaximum_cart_qty());
				
				/*end inventory*/
				/*shipping*/
				dto.setProductWidth(pro.getProductWidth());
				dto.setProductHeight(pro.getProductHeight());
				dto.setProductLength(pro.getProductLength());
				dto.setProductWeight(pro.getProductWeight());
				dto.setProduct_is_free_shipping(pro.isProduct_is_free_shipping());
				/*end shipping*/
				/*mapping*/
				
				dto.setManufacture_id(pro.getManufacturer().getId());
				Set<Integer> setCatetmp=new HashSet<Integer>();
				 for (Category c : pro.getCategories()) {
					 setCatetmp.add(c.getId());
				    }
			
				 dto.setCategories(setCatetmp);
				/*end mapping*/
				/*seo*/
				dto.setMetatagTitle(pro.getMetatagTitle());
				dto.setMetatagDescription(pro.getMetatagDescription());
				dto.setMetatagKeywords(pro.getMetatagKeywords());
				dto.setSeUrl(pro.getSeUrl());
				/*end seo*/
			}
		}
		AddAtributeIntoForm(model);
		model.addAttribute("productDTO", dto);
		return "admin/catalogue/product/form";

	}
	@PostMapping("/admin/product/save")
	public String  saveProduct(@Valid  ProductDTO  productDTO,BindingResult result, Model model,RedirectAttributes ra,Principal principal) throws ServiceException, JsonProcessingException{
		 //Validation code
	    validator.validate(productDTO, result);
	    
		//result.rejectValue("sku","file.too.large","File too large!");
		if (result.hasErrors()) {
			//System.out.println(result.getAllErrors().toString());
			//System.out.println(productDTO);
			AddAtributeIntoForm(model);
			return "admin/catalogue/product/form";
		}
		boolean isNewProduct= !productService.exists(productDTO.getId());
		//productService.exists(productDTO.getId());
		if(!productService.exists(productDTO.getId())){//tao moi
			if(productService.exists(productDTO.getSku())){
				result.rejectValue("sku", "productDTO.error.sku.exists", "SKU is exists!");
				AddAtributeIntoForm(model);
				return "admin/catalogue/product/form";
			}
		}
		try{
			//mapping dto data
			Product persist=new Product();
			/*info*/
			persist.setId(productDTO.getId());
			persist.setName(productDTO.getName());
			persist.setSku(productDTO.getSku());
			persist.setShort_description(productDTO.getShort_description());
			persist.setFull_description(productDTO.getFull_description());
			persist.setFeature(productDTO.isFeature());
			/*end info*/
			/*pices*/
			persist.setPrice(productDTO.getPrice());
			persist.setOld_price(productDTO.getOld_price());
			persist.setProduct_cost(productDTO.getProduct_cost());
			persist.setDiscount(productDTO.isDiscount());
			persist.setPercent_discount(productDTO.getPercent_discount());
			persist.setPrice_discount(productDTO.getPrice_discount());
			
			/*end prices*/
			/*inventory*/
			persist.setStock_quantity(productDTO.getStock_quantity());
			persist.setDisplay_stock_availability(productDTO.isDisplay_stock_availability());
			persist.setDisplay_stock_qty(productDTO.isDisplay_stock_qty());
			persist.setMinimum_stock_qty(productDTO.getMinimum_stock_qty());
			persist.setMinimum_cart_qty(productDTO.getMinimum_cart_qty());
			persist.setMaximum_cart_qty(productDTO.getMaximum_cart_qty());
			
			/*end inventory*/
			/*shipping*/
			persist.setProductWidth(productDTO.getProductWidth());
			persist.setProductHeight(productDTO.getProductHeight());
			persist.setProductLength(productDTO.getProductLength());
			persist.setProductWeight(productDTO.getProductWeight());
			persist.setProduct_is_free_shipping(productDTO.isProduct_is_free_shipping());
			/*end shipping*/
			
			/*mapping*/
			persist.setManufacturer(manufactureService.getByID(productDTO.getManufacture_id()));
			Set<Category> setCatetmp=new HashSet<Category>();
			
			 for (int n : productDTO.getCategories()) {
				 Category cattmp=new Category();
				 cattmp.setId(n);
				 setCatetmp.add(cattmp);
			    }
		
			persist.setCategories(setCatetmp);
			/*end mapping*/
			/*seo*/
			persist.setMetatagTitle(productDTO.getMetatagTitle());
			persist.setMetatagDescription(productDTO.getMetatagDescription());
			persist.setMetatagKeywords(productDTO.getMetatagKeywords());
			persist.setSeUrl(productDTO.getSeUrl());
			/*end seo*/
			persist.getAuditSection().setModifiedBy(UserSessionHelper.getPrincipalName(principal));
			if(isNewProduct){
				persist=productService.create(persist);
				 MessageHelper.addSuccessAttribute(ra, "save.product.new.success");
			}else{
				persist=productService.update(persist);
				 MessageHelper.addSuccessAttribute(ra, "save.product.edit.success");
			
			}
			 return "redirect:/admin/product/editProduct?id="+persist.getId();
			}catch(ServiceException ex){
				ex.printStackTrace();
				MessageHelper.addErrorAttribute(ra, "save.fail");
				 return "redirect:/admin/product";
					//throw(ex);
		
			}
		/*MessageHelper.addErrorAttribute(ra, "save.fail");
		 return "redirect:/admin/product";*/
			//System.out.println(productDTO.toString());
			//return "admin/catalogue/product/form";
	}
	
	@PostMapping("/admin/product/delete")
	@ResponseBody
	public ResponseEntity<GenericResponse> delete(@RequestParam("id") Long id) throws IOException, ServiceException{
		if(productService.exists(id)){
			productService.delete(id);
		}
		else{
			return new ResponseEntity<GenericResponse>(new GenericResponse("Product is not exists!"), HttpStatus.CONFLICT);
		}
		//return new ResponseEntity<GenericResponse>(new GenericResponse("Product is not exists!"), HttpStatus.CONFLICT);
		return new ResponseEntity<GenericResponse>(new GenericResponse("success"), HttpStatus.OK);
	}
	
	

}
