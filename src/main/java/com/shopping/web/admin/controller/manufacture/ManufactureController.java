package com.shopping.web.admin.controller.manufacture;


import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopping.core.business.exception.ServiceException;


import com.shopping.core.business.services.catalog.manufacture.ManufactureService;
import com.shopping.core.model.catalog.product.manufacturer.Manufacturer;
import com.shopping.web.support.MessageHelper;
import com.shopping.web.support.UploadHelper;
import com.shopping.web.support.UserSessionHelper;



@Controller
public class ManufactureController {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(manufactureController.class);
	
	
	@Autowired
	ManufactureService manufactureService;
	
	@RequestMapping(value={"/admin/manufacture/","/admin/manufacture"}, method=RequestMethod.GET)
	public String displayManufacturer(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "admin/catalogue/manufacturer/list";
	}
	
	
	
	@RequestMapping(value={"/admin/manufacture/list"}, method=RequestMethod.GET)
	@ResponseBody
	public Page<Manufacturer> getAllList(	@RequestParam(value="page",required=false,defaultValue="1") int page,
									    @RequestParam(value="pageSize",required=false,defaultValue="15") int pageSize,
									    @RequestParam(value="manufacture_name",required=false,defaultValue="") String manufacture_name,
								HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PageRequest pageRequest=new  PageRequest(page-1,pageSize , Direction.ASC, "name");
		Page<Manufacturer> pageCatgory=manufactureService.findAll(manufacture_name,pageRequest);
		return pageCatgory;
	}
	

	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/manufacture/editManufacture", method=RequestMethod.GET)
	public String displaymanufactureEdit(@RequestParam("code") String code, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return displaymanufacture(code,model,request);

	}
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/manufacture/createManufacture", method=RequestMethod.GET)
	public String displaymanufactureCreate(Model model, HttpServletRequest request) throws Exception {
		return displaymanufacture(null,model,request);

	}
	
	
	
	private String displaymanufacture(String code, Model model, HttpServletRequest request) throws Exception {


		Manufacturer manufacture = new Manufacturer();
		
		if(code!=null && !code.isEmpty()) {//edit mode
			manufacture = manufactureService.getByCode(code);
		}
		model.addAttribute("manufacture", manufacture);
		return "admin/catalogue/manufacturer/form";
	}
	
	//@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/manufacture/save", method=RequestMethod.POST)
	public String savemanufacture(@Valid @ModelAttribute("manufacture")  Manufacturer manufacture, BindingResult result, Model model,
                       HttpServletRequest request,MultipartHttpServletRequest request_multipart,Principal principal,RedirectAttributes ra)  throws Exception {
		if(manufacture.getId() != null && manufacture.getId() >0) { //edit entry
			//get from DB
			Manufacturer currentmanufacture = manufactureService.getByCode(manufacture.getCode());
			
			if(currentmanufacture==null) {
				return displaymanufactureCreate(model,request);//add new
			}
		}
		if (result.hasErrors()) {
			return "admin/catalogue/manufacturer/form";
		}
		//seting upload image
		String oldImage=request.getParameter("oldImage");
		MultipartFile file=request_multipart.getFile("fileupload");
		if(file!=null){
			if(file.isEmpty()){
				manufacture.setImage(oldImage);
			}else{
				manufacture.setImage(UploadHelper.doUpload(request, "manufacture", file));
			}
		}
		
		//end setting upload image
		
		manufacture.getAuditSection().setModifiedBy(UserSessionHelper.getPrincipalName(principal));
		try{
			manufactureService.saveOrUpdate(manufacture);
			MessageHelper.addSuccessAttribute(ra, "save.success");
		}catch(ServiceException e){
			MessageHelper.addErrorAttribute(ra, "save.fail");
		}
	   return "redirect:/admin/manufacture";

	}


}
