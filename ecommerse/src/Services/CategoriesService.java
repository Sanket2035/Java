package Services;

import java.util.List;

import DAO.CategoriesDAO;
import Models.Categories;

public class CategoriesService {
	// addCategory(name, description)
	public static boolean addCategory(String name, String description) {
		Categories category = new Categories();
		if(name.isEmpty()) {
			System.out.println("Category name cannot be empty!");
			return false;
		} else {
			category.setCategory(name);
		}
		
		category.setDescription(description);
		
		return CategoriesDAO.save(category);
	}
	
    // getAllCategories()
	public static List<Categories> getAllCategories(){
		return CategoriesDAO.findAll();
	}
	
    // updateCategory(id, name, description)
	public static boolean updateCategory(int id, String name, String description) {
		Categories category = CategoriesDAO.findById(id);
		if(id<=0) {
			System.out.println("Enter a valid id!");
			return false;
		} 
		
		if(name.isEmpty()){
			System.out.println("Category name cannot be empty!");
			return false;
		}
		
		if(category != null) {
				return CategoriesDAO.update( new Categories(id,name,description));
		}
		return false;
	}
	
    // deleteCategory(id)
	public static boolean deleteCategory(int id) {
		Categories category = CategoriesDAO.findById(id);
		if(id<=0) {
			System.out.println("Enter a valid id!");
			return false;
		} 
		
		if(category != null) {
				return CategoriesDAO.delete(id);
		}
		return false;
	}
}
