package com.shopping.web.admin.controller.products;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.services.catalog.product.ProductService;
import com.shopping.core.business.services.catalog.product.image.ProductImageService;
import com.shopping.core.model.catalog.product.image.ProductImage;
import com.shopping.web.admin.dto.ProductImageDTO;
import com.shopping.web.support.Message;
import com.shopping.web.support.Message.Type;
import com.shopping.web.utils.GenericResponse;
import com.shopping.web.utils.JsonResponse;
import com.shopping.web.utils.UploadImageBean;




@Controller
public class ProductImagesController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImagesController.class);
	
	@Autowired
	UploadImageBean uploadImageBean;
	
	@Autowired
	ProductImageService productImageService;
	
	@Autowired
	private ProductService productService;
	@GetMapping("/admin/product-image/upload")
	public String showFormUpload(){
		return "admin/catalogue/product/upload_demo";
	}
	
	@PostMapping("/admin/product-image/save")
	@ResponseBody
	public ResponseEntity<GenericResponse> saveAndUpload(@Valid ProductImageDTO productImageDTO) throws IOException, ServiceException{

		String image_name=	uploadImageBean.processProductImage(productImageDTO.getFile());
		
		ProductImage product_imageobj=new ProductImage();
		product_imageobj.setAltTag(productImageDTO.getAltTag());
		product_imageobj.setDefaultImage(productImageDTO.isDefaultImage());
		product_imageobj.setDescription("");
		product_imageobj.setDisplayOrder(productImageDTO.getDisplayOrder());
		product_imageobj.setProduct(productService.getProduct(productImageDTO.getProductId()));
		product_imageobj.setProductImage(image_name);
		product_imageobj.setName(image_name);
		product_imageobj.setTitle(productImageDTO.getTitle());
		productImageService.saveOrUpdate(product_imageobj);
		return new ResponseEntity<GenericResponse>(new GenericResponse("success"), HttpStatus.OK);
	}
	@GetMapping("/admin/product-image/test")
	public ResponseEntity<Message>  tt() throws IOException{
		uploadImageBean.deleteProductImage("d19efdf7-8e4b-4702-917e-f00198cf58d5.jpg");
		Message m=new Message("aaaa",Type.DANGER);
		return new ResponseEntity<Message>(m, HttpStatus.OK);
		
	}
	@PostMapping("/admin/product-image/get")
	@ResponseBody
	public JsonResponse<ProductImage> listImageByproductID(@RequestParam("productId") Long id) throws ServiceException{
		List<ProductImage> result=productImageService.getProductImages(id);
		if(result==null){
			result=new ArrayList<ProductImage>();
		}
		return new JsonResponse<ProductImage>(result)  ;
		
	}
	@GetMapping("/admin/product-image/get1")
	@ResponseBody
	public JsonResponse<ProductImage> listImageByproductIDpost(@RequestParam("productId") Long id) throws ServiceException{
		List<ProductImage> result=productImageService.getProductImages(id);
		if(result==null){
			result=new ArrayList<ProductImage>();
		}
		return new JsonResponse<ProductImage>(result)  ;
		
	}
	@PostMapping("/admin/product-image/ProductPictureDelete")
	@ResponseBody
	public  ResponseEntity<GenericResponse> deleteImage(@RequestParam("id")Long imageId) throws ServiceException, IOException{
		
		ProductImage product_image=productImageService.getImage(imageId);
		productImageService.removeProductImage(product_image);
		return new ResponseEntity<GenericResponse>(new GenericResponse("success"), HttpStatus.OK);
		
	}
	
}
