package com.shopping.web.admin.controller.category;


import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.services.catalog.category.CategoryService;
import com.shopping.core.model.catalog.category.Category;
import com.shopping.core.spec.CategorySpecification;
import com.shopping.core.utils.SearchOperation;
import com.shopping.core.utils.SpecSearchCriteria;
import com.shopping.web.support.MessageHelper;
import com.shopping.web.support.UserSessionHelper;



@Controller
public class CategoryController {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping(value={"/admin/categories/","/admin/categories"}, method=RequestMethod.GET)
	public String displayCategories(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "admin/catalogue/categories/list";
	}
	@RequestMapping(value={"/admin/categories/list"}, method=RequestMethod.GET)
	@ResponseBody
	public Page<Category> getAllList(	@RequestParam(value="page",required=false,defaultValue="1") int page,
									    @RequestParam(value="pageSize",required=false,defaultValue="15") int pageSize,
									    @RequestParam(value="category_name",required=false,defaultValue="") String category_name,
								HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PageRequest pageRequest=new  PageRequest(page-1,pageSize , Direction.ASC, "lineage","sortOrder");
		Page<Category> pageCatgory=categoryService.findAll(category_name,pageRequest);
		return pageCatgory;
	}
	

	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/categories/editCategory", method=RequestMethod.GET)
	public String displayCategoryEdit(@RequestParam("id") Integer categoryId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return displayCategory(categoryId,model,request);

	}
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/categories/createCategory", method=RequestMethod.GET)
	public String displayCategoryCreate(Model model, HttpServletRequest request) throws Exception {
		return displayCategory(null,model,request);

	}
	
	
	
	private String displayCategory(Integer categoryId, Model model, HttpServletRequest request) throws Exception {

		//get parent categories
		List<Category> categories = categoryService.findAllRoot();
		Category category = new Category();
		
		if(categoryId!=null && categoryId!=0) {//edit mode
			category = categoryService.getById(categoryId);
		}
		model.addAttribute("category", category);
		model.addAttribute("categories", categories);
		return "admin/catalogue/categories/form";
	}
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/categories/save", method=RequestMethod.POST)
	public String saveCategory(@Valid @ModelAttribute("category")  Category category, BindingResult result, Model model,
			RedirectAttributes ra, HttpServletRequest request,Principal principal)  throws Exception {

		if(category.getId() != null && category.getId() >0) { //edit entry
			
			//get from DB
			Category currentCategory = categoryService.getById(category.getId());
			
			if(currentCategory==null) {
				return displayCategoryCreate(model,request);//add new
			}

		}
		if (result.hasErrors()) {
		
			//System.out.println(result.getAllErrors().toString());
			
			return "admin/catalogue/categories/form";
		}
		//check parent
		if(category.getParent()!=null) {
			if(category.getParent().getId()==-1) {//this is a root category
				category.setParent(null);
				category.setLineage("/");
				category.setDepth(0);
				category.setBreadcrumb(category.getName());
			}
		}
		category.getAuditSection().setModifiedBy(UserSessionHelper.getPrincipalName(principal));
		try{
			categoryService.saveOrUpdate(category);
			MessageHelper.addSuccessAttribute(ra, "save.success");
		}catch(ServiceException e){
			MessageHelper.addErrorAttribute(ra, "save.fail");
		}
	   return "redirect:/admin/categories";

	}
	
	/*
	//category list
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/categories/categories.html", method=RequestMethod.GET)
	public String displayCategories(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		
		setMenu(model,request);
		
		//does nothing, ajax subsequent request
		
		return "catalogue-categories";
	}
	
	@SuppressWarnings({ "unchecked"})
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/categories/paging.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> pageCategories(HttpServletRequest request, HttpServletResponse response) {
		String categoryName = request.getParameter("name");
		String categoryCode = request.getParameter("code");


		AjaxResponse resp = new AjaxResponse();

		
		try {
			
			Language language = (Language)request.getAttribute("LANGUAGE");
				
		
			MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
			
			List<Category> categories = null;
					
			if(!StringUtils.isBlank(categoryName)) {
				
				
				categories = categoryService.getByName(store, categoryName, language);
				
			} else if(!StringUtils.isBlank(categoryCode)) {
				
				categoryService.listByCodes(store, new ArrayList<String>(Arrays.asList(categoryCode)), language);
			
			} else {
				
				categories = categoryService.listByStore(store, language);
				
			}
					
					
			
			for(Category category : categories) {
				
				@SuppressWarnings("rawtypes")
				Map entry = new HashMap();
				entry.put("categoryId", category.getId());
				
				CategoryDescription description = category.getDescriptions().get(0);
				
				entry.put("name", description.getName());
				entry.put("code", category.getCode());
				entry.put("visible", category.isVisible());
				resp.addDataEntry(entry);
				
				
			}
			
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);
			

		
		} catch (Exception e) {
			LOGGER.error("Error while paging categories", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}
		
		String returnString = resp.toJSONString();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
	    return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/categories/hierarchy.html", method=RequestMethod.GET)
	public String displayCategoryHierarchy(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		
		setMenu(model,request);
		
		//get the list of categories
		Language language = (Language)request.getAttribute("LANGUAGE");
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		List<Category> categories = categoryService.listByStore(store, language);
		
		model.addAttribute("categories", categories);
		
		return "catalogue-categories-hierarchy";
	}
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/categories/remove.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> deleteCategory(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String sid = request.getParameter("categoryId");

		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		AjaxResponse resp = new AjaxResponse();

		
		try {
			
			Long id = Long.parseLong(sid);
			
			Category category = categoryService.getById(id);
			
			if(category==null || category.getMerchantStore().getId().intValue() !=store.getId().intValue() ) {

				resp.setStatusMessage(messages.getMessage("message.unauthorized", locale));
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);			
				
			} else {
				
				categoryService.delete(category);
				resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);
				
			}
		
		
		} catch (Exception e) {
			LOGGER.error("Error while deleting category", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/categories/moveCategory.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> moveCategory(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String parentid = request.getParameter("parentId");
		String childid = request.getParameter("childId");

		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		AjaxResponse resp = new AjaxResponse();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

		
		try {
			
			Long parentId = Long.parseLong(parentid);
			Long childId = Long.parseLong(childid);
			
			Category child = categoryService.getById(childId);
			Category parent = categoryService.getById(parentId);
			
			if(child.getParent().getId()==parentId) {
				resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);
				String returnString = resp.toJSONString();
			}

			if(parentId!=1) {
			
				if(child==null || parent==null || child.getMerchantStore().getId()!=store.getId() || parent.getMerchantStore().getId()!=store.getId()) {
					resp.setStatusMessage(messages.getMessage("message.unauthorized", locale));
					
					resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
					String returnString = resp.toJSONString();
					return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
				}
				
				if(child.getMerchantStore().getId()!=store.getId() || parent.getMerchantStore().getId()!=store.getId()) {
					resp.setStatusMessage(messages.getMessage("message.unauthorized", locale));
					resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
					String returnString = resp.toJSONString();
					return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
				}
			
			}
			

			parent.getAuditSection().setModifiedBy(request.getRemoteUser());
			categoryService.addChild(parent, child);
			resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);

		} catch (Exception e) {
			LOGGER.error("Error while moving category", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();
		
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/categories/checkCategoryCode.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> checkCategoryCode(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String code = request.getParameter("code");
		String id = request.getParameter("id");


		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		
		AjaxResponse resp = new AjaxResponse();
		
		
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		if(StringUtils.isBlank(code)) {
			resp.setStatus(AjaxResponse.CODE_ALREADY_EXIST);
			String returnString = resp.toJSONString();
			return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
		}

		
		try {
			
		Category category = categoryService.getByCode(store, code);
		
		if(category!=null && StringUtils.isBlank(id)) {
			resp.setStatus(AjaxResponse.CODE_ALREADY_EXIST);
			String returnString = resp.toJSONString();
			return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
		}
		
		
		if(category!=null && !StringUtils.isBlank(id)) {
			try {
				Long lid = Long.parseLong(id);
				
				if(category.getCode().equals(code) && category.getId().longValue()==lid) {
					resp.setStatus(AjaxResponse.CODE_ALREADY_EXIST);
					String returnString = resp.toJSONString();
					return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
				}
			} catch (Exception e) {
				resp.setStatus(AjaxResponse.CODE_ALREADY_EXIST);
				String returnString = resp.toJSONString();
				return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
			}

		}
		
		
		
		

	
		
			


			resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);

		} catch (Exception e) {
			LOGGER.error("Error while getting category", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();

		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
*/
}
