package com.shopping.web.admin.controller.products;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.services.catalog.product.attribute.ProductOptionService;
import com.shopping.core.model.catalog.category.Category;
import com.shopping.core.model.catalog.product.attribute.ProductOption;
import com.shopping.web.support.MessageHelper;
import com.shopping.web.utils.LabelUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.*;

@Controller
public class OptionsController {
	
	
	@Autowired
	ProductOptionService productOptionService;
	
	@Autowired
	LabelUtils messages;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OptionsController.class);
	
	
	@RequestMapping(value={"/admin/options/list"}, method=RequestMethod.GET)
	@ResponseBody
	public Page<ProductOption> getAllList(	@RequestParam(value="page",required=false,defaultValue="1") int page,
									    @RequestParam(value="pageSize",required=false,defaultValue="15") int pageSize,
									    @RequestParam(value="option_name",required=false,defaultValue="") String option_name,
								HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PageRequest pageRequest=new  PageRequest(page-1,pageSize , Direction.ASC, "code","productOptionSortOrder");
		Page<ProductOption> pageCatgory=productOptionService.findAll(option_name,pageRequest);
		return pageCatgory;
	}
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value={"/admin/options","/admin/options/"}, method=RequestMethod.GET)
	public String displayOptions(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		return "admin/catalogue/options/list";
		
	}
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/options/editOption", method=RequestMethod.GET)
	public String displayOptionEdit(@RequestParam("id") long optionId, HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws Exception {
		return displayOption(optionId,request,response,model,locale);
	}
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/options/createOption", method=RequestMethod.GET)
	public String displayOption(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws Exception {
		return displayOption(null,request,response,model,locale);
	}
	
	private String displayOption(Long optionId, HttpServletRequest request, HttpServletResponse response,Model model,Locale locale) throws Exception {

		ProductOption option = new ProductOption();
		if(optionId!=null && optionId!=0) {//edit mode
			option = productOptionService.getById( optionId);
			if(option==null) {
				return "redirect:/admin/options";
			}
		}
		model.addAttribute("option", option);
		return "admin/catalogue/options/form";
		
		
	}
		
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/options/save", method=RequestMethod.POST)
	public String saveOption(@Valid @ModelAttribute("option") ProductOption option, BindingResult result, Model model, HttpServletRequest request,RedirectAttributes ra, Locale locale) throws Exception {
		

		ProductOption dbEntity =	null;	

		if(option.getId() != null && option.getId() >0) { //edit entry
			//get from DB
			dbEntity = productOptionService.getById(option.getId());
			
			if(dbEntity==null) {
				return "redirect:/admin/options";
			}
		}
		else{
			//validate if it contains an existing code
			ProductOption byCode = productOptionService.getByCode( option.getCode());
			if(byCode!=null) {
				result.rejectValue("code", "message.code.exist" ,messages.getMessage("message.code.exist", locale));
			}
		}
		
		
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors().toString());
			return "admin/catalogue/options/form";
		}
		try{
		productOptionService.saveOrUpdate(option);
			MessageHelper.addSuccessAttribute(ra, "save.success");
		}catch(ServiceException e){
			MessageHelper.addErrorAttribute(ra, "save.fail");
		}
	   return "redirect:/admin/options";

	}

/*
	@RequestMapping(value="/admin/options/remove.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> deleteOption(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String sid = request.getParameter("optionId");

		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		AjaxResponse resp = new AjaxResponse();

		
		try {
			
			Long id = Long.parseLong(sid);
			
			ProductOption entity = productOptionService.getById(id);

			if(entity==null || entity.getMerchantStore().getId().intValue()!=store.getId().intValue()) {

				resp.setStatusMessage(messages.getMessage("message.unauthorized", locale));
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);			
				
			} else {
				
				productOptionService.delete(entity);
				resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);
				
			}
		
		
		} catch (Exception e) {
			LOGGER.error("Error while deleting option", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
*/
}
