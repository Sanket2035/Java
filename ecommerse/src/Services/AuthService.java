package Services;

import DAO.UserDAO;
import Models.Users;
import Session.session;
import Utils.PasswordUtils;
import Utils.Validator;

public class AuthService {
	public static boolean register(String name,String email, String password, String role) {
		Users user = new Users();
		if(!name.isEmpty()) {
			if(UserDAO.isUserNameTaken(name)) {
				System.out.println("This Username is not available");
			} else {
				user.setUsername(name);
			}
		} else {
			System.out.println("Username cannot be empty!");
			return false;
		}
		
		if(Validator.isValidEmail(email)) {
			if(UserDAO.isEmailRegistered(email)) {
				System.out.println("Email is already registered!");
				return false;
			}else {
				user.setEmail(email);
			}
		} else {
			System.out.println("Enter a valid Email!");
		}
		
		if(Validator.isValidPassword(password)) {
			user.setPass(PasswordUtils.hashPassword(password));
		} else {
			System.out.println("Enter a valid password!");
			return false;
		}
		
		role.toUpperCase();
		if(role.equals("ADMIN") || role.equals("CUSTOMER")) {
			user.setRole(role);
		} else {
			System.out.println("Enter a valid role (ADMIN/CUSTOMER)!");
			return false;
		}
		
		return UserDAO.save(user);
	}
	
	public static boolean login(String email, String password) {
		Users user = null;
		if(UserDAO.isEmailRegistered(email)) {
			user = UserDAO.findByEmail(email);
			
			if(PasswordUtils.verifyPassword(password, user.getPass())) {
				session.login(user);
				return true;
			} else {
				System.out.println("Wrong Password! Try Again.");
			}
		} else {
			System.out.println("Email is not registered! Please register before login.");
		}
		return false;
	}
}
