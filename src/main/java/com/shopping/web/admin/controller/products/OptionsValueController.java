package com.shopping.web.admin.controller.products;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.services.catalog.product.attribute.ProductOptionValueService;
import com.shopping.core.model.catalog.product.attribute.ProductOption;
import com.shopping.core.model.catalog.product.attribute.ProductOptionValue;
import com.shopping.web.support.MessageHelper;
import com.shopping.web.support.UploadHelper;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.*;

@Controller
public class OptionsValueController {

	

	@Autowired
	ProductOptionValueService productOptionValueService;
	
	@Autowired
	LabelUtils messages;
	


	
	private static final Logger LOGGER = LoggerFactory.getLogger(OptionsValueController.class);
	
	@RequestMapping(value={"/admin/options-value/list"}, method=RequestMethod.GET)
	@ResponseBody
	public Page<ProductOptionValue> getAllList(	@RequestParam(value="page",required=false,defaultValue="1") int page,
									    @RequestParam(value="pageSize",required=false,defaultValue="15") int pageSize,
									    @RequestParam(value="option_val_name",required=false,defaultValue="") String option_val_name,
								HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PageRequest pageRequest=new  PageRequest(page-1,pageSize , Direction.ASC, "code","productOptionValueSortOrder");
		Page<ProductOptionValue> pageCatgory=productOptionValueService.findAll(option_val_name,pageRequest);
		return pageCatgory;
	}
	
	
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value={"/admin/options-value/","/admin/options-value"}, method=RequestMethod.GET)
	public String displayOptions(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "admin/catalogue/options-value/list";
	}
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/options-value/editOptionValue", method=RequestMethod.GET)
	public String displayOptionEdit(@RequestParam("id") long optionId, HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws Exception {
		return displayOption(optionId,request,response,model,locale);
	}
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/options-value/createOptionValue", method=RequestMethod.GET)
	public String displayOption(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws Exception {
		return displayOption(null,request,response,model,locale);
	}
	
	private String displayOption(Long optionId, HttpServletRequest request, HttpServletResponse response,Model model,Locale locale) throws Exception {

		ProductOptionValue option = new ProductOptionValue();
		
		if(optionId!=null && optionId!=0) {//edit mode
			
			
			option = productOptionValueService.getById(optionId);

			if(option==null) {
				return "redirect:/admin/options-value/";
			}
		}	
		model.addAttribute("optionValue", option);
		return "admin/catalogue/options-value/form";
		
		
	}
		
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/options-value/saveOptionValue", method=RequestMethod.POST)
	public String saveOption(@Valid @ModelAttribute("optionValue") ProductOptionValue optionValue, BindingResult result, Model model, HttpServletRequest request,RedirectAttributes ra, Locale locale) throws Exception {
		
		ProductOptionValue dbEntity =	null;	

		if(optionValue.getId() != null && optionValue.getId() >0) { //edit entry
			
			//get from DB
			dbEntity = productOptionValueService.getById(optionValue.getId());
			
			if(dbEntity==null) {
				return "redirect:/admin/options-value/";
			}
		}
		
		//validate if it contains an existing code
		ProductOptionValue byCode = productOptionValueService.getByCode( optionValue.getCode());
		if(byCode!=null) {
			result.rejectValue("code", "message.code.exist" ,messages.getMessage("message.code.exist", locale));
		}

		if (result.hasErrors()) {
			return "admin/catalogue/options-value/form";
		}
	
	    if(optionValue.getImage()!=null && !optionValue.getImage().isEmpty()) {

			String imageName =UploadHelper.doUpload(request, "optionValue", optionValue.getImage());
            optionValue.setProductOptionValueImage(imageName);

		}
		try{
			productOptionValueService.saveOrUpdate(optionValue);
			MessageHelper.addSuccessAttribute(ra, "save.success");
		}
		catch(ServiceException e){
			MessageHelper.addErrorAttribute(ra, "save.fail");
		}
		return "redirect:/admin/options-value";
	}

	
	
	/*
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/optionsvalues/remove.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> deleteOptionValue(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String sid = request.getParameter("optionValueId");

		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		AjaxResponse resp = new AjaxResponse();

		
		try {
			
			Long id = Long.parseLong(sid);
			
			ProductOptionValue entity = productOptionValueService.getById(store, id);

			if(entity==null || entity.getMerchantStore().getId().intValue()!=store.getId().intValue()) {

				resp.setStatusMessage(messages.getMessage("message.unauthorized", locale));
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);			
				
			} else {
				
				productOptionValueService.delete(entity);
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
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/optionsvalues/removeImage.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> removeImage(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String optionValueId = request.getParameter("optionId");

		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		AjaxResponse resp = new AjaxResponse();

		
		try {
			
			Long id = Long.parseLong(optionValueId);
			
			ProductOptionValue optionValue = productOptionValueService.getById(store, id);

			contentService.removeFile(store.getCode(), FileContentType.PROPERTY, optionValue.getProductOptionValueImage());
			
			store.setStoreLogo(null);
			optionValue.setProductOptionValueImage(null);
			productOptionValueService.update(optionValue);
		
		
		} catch (Exception e) {
			LOGGER.error("Error while deleting product", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
	
	

	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("catalogue", "catalogue");
		activeMenus.put("catalogue-options", "catalogue-options");
		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("catalogue");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
		//
		
	}
*/
}
