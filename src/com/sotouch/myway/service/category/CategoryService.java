package com.sotouch.myway.service.category;

import java.util.List;

import javax.annotation.Resource;

import org.hurryapp.fwk.service.BaseCrudService;

import com.sotouch.myway.data.dao.CategoryDAO;
import com.sotouch.myway.data.dao.ItemDAO;
import com.sotouch.myway.data.dao.SubCategoryDAO;
import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.CategoryContent;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.SubCategory;
import com.sotouch.myway.data.model.SubCategoryContent;


public class CategoryService extends BaseCrudService {

	@Resource (name="subCategoryDao")
	private SubCategoryDAO subCategoryDao;

	@Resource (name="itemDao")
	private ItemDAO itemDao;

	public CategoryService() {
	}
	
	@Override
	public void deleteById(Integer id) throws Exception {
		// Suppression des sous-categories
		List<SubCategory> subCategories = subCategoryDao.findByCategory(id);
		for (SubCategory subCategory : subCategories) {
			subCategoryDao.delete(subCategory);
		}
		entityDao.getHibernateTemplate().flush();
		
		// Suppression de la catégorie
		super.deleteById(id);
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public List<Category> findByProject(Integer projectId) {
		return ((CategoryDAO) entityDao).findByProject(projectId);
	}
	
	public void transformSubCategoryToCategory(Integer subCategoryId) {
		// Récupération de la sous-catégorie
		SubCategory subCategory = subCategoryDao.findById(subCategoryId);
		
		// Création nouvelle catégorie
		Category category = new Category();
		category.setProject(subCategory.getCategory().getProject());
		category.setName(subCategory.getName());
		category.setColor(subCategory.getColor());
		CategoryContent catContent = null;
		for (SubCategoryContent subCatContent : subCategory.getSubCategoryContents()) {
			catContent = new CategoryContent();
			catContent.setCategory(category);
			catContent.setName(subCatContent.getName());
			catContent.setLanguage(subCatContent.getLanguage());
			catContent.setIndex(subCatContent.getIndex());
			category.getCategoryContents().add(catContent);
		}
		category = (Category) entityDao.saveOrUpdate(category);
		
		// Mise à jour des items pointant sur la sous-categorie
		List<Item> items = itemDao.findBySubCategory(subCategoryId);
		for (Item item : items) {
			item.setCategory(category);
			item.setSubCategory(null);
			itemDao.saveOrUpdate(item);
		}
		
		// Suppression de la sous-catégorie
		subCategoryDao.delete(subCategory);
	}
}
