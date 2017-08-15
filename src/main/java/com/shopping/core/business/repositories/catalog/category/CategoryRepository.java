package com.shopping.core.business.repositories.catalog.category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopping.core.model.catalog.category.Category;



public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryRepositoryCustom {
	

	@Query("select c from Category c  where c.seoUrl like ?1  order by c.sortOrder asc")
	List<Category> listByFriendlyUrl( String friendlyUrl);
	
	
	@Query("select c from Category c  where c.seoUrl=?1 ")
	Category findByFriendlyUrl( String friendlyUrl);
	
	@Query("select c from Category c where c.name like %?1% order by c.sortOrder asc")
	List<Category> findByName( String name );

	

	@Query("select c from Category c  where c.id in (?1)  order by c.sortOrder asc")
	List<Category> findByIds( List<Integer> ids);
	
	@Query("select c from Category c where  c.id = ?1")
	Category findById(Integer categoryId);
	
	//@Query("select c from Category c left join fetch c.descriptions cd join fetch c.merchantStore cm where cd.language.id=?2 and c.id = ?1")
	//List<Category> findById(Long categoryId, Integer languageId);

	@Query("select c from Category c where c.id=?1")
	public Category findOne(Integer categoryId);
	
	@Query("select distinct c from Category c  where  c.lineage like %?1% order by c.lineage, c.sortOrder asc")
	public List<Category> findByLineage( String linenage);
	
	@Query("select distinct c from Category c where  c.depth >= ?1 order by c.lineage, c.sortOrder asc")
	public List<Category> findByDepth( Integer depth);
	
	
	//@Query("select distinct c from Category c left join fetch c.descriptions cd join fetch c.merchantStore cm left join fetch c.parent cp where cm.id=?1 and cp.id=?2 and cd.language.id=?3 order by c.lineage, c.sortOrder asc")
	//public List<Category> findByParent(Integer merchantId, Long parentId, Integer languageId);
	
	@Query("select distinct c from Category c left join fetch c.parent cp where cp.id=?1  order by c.lineage, c.sortOrder asc")
	public List<Category> findByParent(Integer parentId);
	
	@Query("select distinct c from Category c where c.name like %:searchName%")
	public Page<Category> findAlldistinct(@Param("searchName") String searchName, Pageable pageRequest);
	
	@Query("select distinct c from Category c  where  c.depth=0 order by c.lineage, c.sortOrder asc")
	public List<Category> findAllRoot();


	
}
