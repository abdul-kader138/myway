package com.sotouch.myway.service.category;

import java.util.List;

import javax.annotation.Resource;

import org.hurryapp.fwk.service.BaseCrudService;

import com.sotouch.myway.data.dao.CategoryDAO;
import com.sotouch.myway.data.dao.ItemDAO;
import com.sotouch.myway.data.model.Category;
import com.sotouch.myway.data.model.CategoryContent;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.SubCategory;
import com.sotouch.myway.data.model.SubCategoryContent;


public class SubCategoryService extends BaseCrudService {

	@Resource (name="categoryDao")
	private CategoryDAO categoryDao;

	@Resource (name="itemDao")
	private ItemDAO itemDao;

	public SubCategoryService() {
	}
	
	//=========================================================================
	// METHODES METIER
	//=========================================================================
	public void transformCategoryToSubCategory(Integer categoryId, Integer destCategoryId) {
		// Récupération de la catégorie
		Category category = categoryDao.findById(categoryId);
		
		// Récupération de la catégorie destination
		Category destCategory = categoryDao.findById(destCategoryId);

		// Création nouvelle sous-catégorie
		SubCategory subCategory = new SubCategory();
		subCategory.setCategory(destCategory);
		subCategory.setName(category.getName());
		subCategory.setColor(category.getColor());
		SubCategoryContent subCatContent = null;
		for (CategoryContent catContent : category.getCategoryContents()) {
			subCatContent = new SubCategoryContent();
			subCatContent.setSubCategory(subCategory);
			subCatContent.setName(catContent.getName());
			subCatContent.setLanguage(catContent.getLanguage());
			subCatContent.setIndex(catContent.getIndex());
			subCategory.getSubCategoryContents().add(subCatContent);
		}
		subCategory = (SubCategory) entityDao.saveOrUpdate(subCategory);
		
		// Mise à jour des items pointant sur la categorie
		List<Item> items = itemDao.findByCategory(categoryId);
		for (Item item : items) {
			item.setCategory(destCategory);
			item.setSubCategory(subCategory);
			itemDao.saveOrUpdate(item);
		}
		
		// Suppression de la catégorie
		categoryDao.delete(category);
	}
	
}
