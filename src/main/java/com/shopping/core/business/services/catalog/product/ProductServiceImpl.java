package com.shopping.core.business.services.catalog.product;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.repositories.catalog.product.ProductRepository;
import com.shopping.core.business.services.catalog.category.CategoryService;
import com.shopping.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.shopping.core.business.services.catalog.product.attribute.ProductOptionService;
import com.shopping.core.business.services.catalog.product.attribute.ProductOptionValueService;
import com.shopping.core.business.services.catalog.product.image.ProductImageService;
import com.shopping.core.model.catalog.category.Category;
import com.shopping.core.model.catalog.product.Product;
import com.shopping.core.model.catalog.product.ProductCriteria;
import com.shopping.core.model.catalog.product.ProductList;
import com.shopping.core.model.catalog.product.image.ProductImage;


@Service("productService")
@Transactional(rollbackFor = com.shopping.core.business.exception.ServiceException.class)
public class ProductServiceImpl  implements ProductService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryService categoryService;
	

	@Autowired
	ProductOptionService productOptionService;
	
	@Autowired
	ProductOptionValueService productOptionValueService;
	
	//@Autowired
	//ProductAttributeService productAttributeService;
	
	/*@Autowired
	ProductRelationshipService productRelationshipService;// san pham lien quan
	
	@Autowired
	SearchService searchService;
	*/
	@Autowired
	ProductImageService productImageService;
	/*
	@Autowired
	CoreConfiguration configuration;
	
	/*@Inject
	ProductReviewService productReviewService;*/
	



	
	@Override
	public List<Product> getProducts(List<Long> categoryIds) throws ServiceException {
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Set ids = new HashSet(categoryIds);
		return productRepository.getProductsListByCategories(ids);
		
	}
	
	public Product getById(Long productId) {
		return productRepository.getById(productId);
	}
	



	
	@Override
	public Product getBySeUrl( String seUrl) {
		return productRepository.getByFriendlyUrl( seUrl);
	}

	@Override
	public Product getProduct(long productId)
			throws ServiceException {
		Product product =  productRepository.getById(productId);
		

	//	CatalogServiceHelper.setToAvailability(product);
		return product;
	}

	@Override
	public List<Product> getProductsByCategory(Category category) throws ServiceException {
		
		if(category==null) {
			throw new ServiceException("The category is null");
		}
		
		//Get the category list
		StringBuilder lineage = new StringBuilder().append(category.getLineage()).append(category.getId()).append("/");
		List<Category> categories = categoryService.listByLineage(lineage.toString());
		Set<Integer> categoryIds = new HashSet<Integer>();
		for(Category c : categories) {
			
			categoryIds.add(c.getId());
			
		}
		
		categoryIds.add(category.getId());
		
		//Get products
		List<Product> products = productRepository.getProductsListByCategories(categoryIds);
		
		//Filter availability
		
		return products;
	}
	
	@Override
	public ProductList listBySearch( ProductCriteria criteria) {
		
		return productRepository.listBySearch( criteria);
	}
	


	@Override
	public Product getByCode(String productCode) {
		return productRepository.getByCode(productCode);
	}
		


	

	@Override
	public void delete(Product product) throws ServiceException, IOException {
		LOGGER.debug("Deleting product");
		Validate.notNull(product, "Product cannot be null");

		product = this.getById(product.getId());//Prevents detached entity error
		product.setCategories(null);
		
		Set<ProductImage> images = product.getImages();
		
		for(ProductImage image : images) {
			productImageService.removeProductImage(image);
		}
		
		product.setImages(null);
		
		//delete reviews
		/*List<ProductReview> reviews = productReviewService.getByProductNoCustomers(product);
		for(ProductReview review : reviews) {
			productReviewService.delete(review);
		}
		*/
		//related - featured
		/*List<ProductRelationship> relationships = productRelationshipService.listByProduct(product);
		for(ProductRelationship relationship : relationships) {
			productRelationshipService.delete(relationship);
		}*/
		
		productRepository.delete(product);
		//searchService.deleteIndex(product.getMerchantStore(), product);
		
	}
	
	@Override
	public Product create(Product product) throws ServiceException {
		return productRepository.saveAndFlush(product);
		//	searchService.index( product);
	}
	
	@Override
	public Product update(Product product) throws ServiceException {
		return productRepository.saveAndFlush(product);
		//searchService.index(product);
	}
	@Override
	public Product saveOrUpdate(Product persist) {
		// TODO Auto-generated method stub
		return productRepository.saveAndFlush(persist);
	}

	@Override
	public boolean exists(Long id) {
		// TODO Auto-generated method stub
		return productRepository.exists(id);
	}

	@Override
	public boolean exists(String sku) {
		// TODO Auto-generated method stub
		return productRepository.existsSKU(sku);
	}

	@Override
	public void delete(Long id) throws ServiceException , IOException{
		Product product = this.getById(id);//Prevents detached entity error
		product.setCategories(null);
		
		Set<ProductImage> images = product.getImages();
		
		for(ProductImage image : images) {
			productImageService.removeProductImage(image);
		}
		
		product.setImages(null);
		
		//delete reviews
		/*List<ProductReview> reviews = productReviewService.getByProductNoCustomers(product);
		for(ProductReview review : reviews) {
			productReviewService.delete(review);
		}
		*/
		//related - featured
		/*List<ProductRelationship> relationships = productRelationshipService.listByProduct(product);
		for(ProductRelationship relationship : relationships) {
			productRelationshipService.delete(relationship);
		}*/
		
		productRepository.delete(product);
		//searchService.deleteIndex(product.getMerchantStore(), product);
		
	}

	
	
/*
	private void saveOrUpdate(Product product) throws ServiceException {
		LOGGER.debug("Save or update product ");
		Validate.notNull(product,"product cannot be null");
		Validate.notNull(product.getAvailabilities(),"product must have at least one availability");
		Validate.notEmpty(product.getAvailabilities(),"product must have at least one availability");
		
		
		//List of original images
		Set<ProductImage> originalProductImages = null;
		
		if(product.getId()!=null && product.getId()>0) {
			originalProductImages = product.getImages();
		}
		
		/** save product first 
		super.save(product);

		
		/**
		 * Image creation needs extra service to save the file in the CMS
		 
		List<Long> newImageIds = new ArrayList<Long>();
		Set<ProductImage> images = product.getImages();
		
		try {
			
			if(images!=null && images.size()>0) {
				for(ProductImage image : images) {
					if(image.getImage()!=null && (image.getId()==null || image.getId()==0L)) {
						image.setProduct(product);
						
				        InputStream inputStream = image.getImage();
				        ImageContentFile cmsContentImage = new ImageContentFile();
				        cmsContentImage.setFileName( image.getProductImage() );
				        cmsContentImage.setFile( inputStream );
				        cmsContentImage.setFileContentType(FileContentType.PRODUCT);

						productImageService.addProductImage(product, image, cmsContentImage);
						newImageIds.add(image.getId());
					} else {
						productImageService.save(image);
						newImageIds.add(image.getId());
					}
				}
			}
			
			//cleanup old images
			if(originalProductImages!=null) {
				for(ProductImage image : originalProductImages) {
					if(!newImageIds.contains(image.getId())) {
						productImageService.delete(image);
					}
				}
			}
			
		} catch(Exception e) {
			LOGGER.error("Cannot save images " + e.getMessage());
		}
		


	}*/


}
