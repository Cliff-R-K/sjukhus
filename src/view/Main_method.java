package view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dao.SubstanceDao;
import dao.UserDao;
import model.Substance;
import model.User;

public class Main_method {
	UserDao userdao = new UserDao();
	SubstanceDao substancedao = new SubstanceDao();

//------------------------------User-------------------------------------------------------------------------
	public void addUser(String signature) {
		userdao.save(new User(signature));
	}

	public void deleteUser(int id) {
		userdao.delete(userdao.get(id));
	}

	public void updateUser(User t, String... args) {
		userdao.update(t, args);
	}

	public void getUser(int id) {
		userdao.get(id).print();
	}

	public void getAllUsers() {
		System.out.println("\nAlla Signaturer i databasen:\n");
		for (User s : userdao.getAll()) {
			s.print();
		}
	}

//------------------------------Room-------------------------------------------------------------------------

	
//------------------------------Supplier-------------------------------------------------------------------------

	
//------------------------------Substance-------------------------------------------------------------------------

	public void addSubstance(String substance, double halfLife) {
		substancedao.save(new Substance(substance, halfLife));
	}

	public void deleteSubstance(int id) {
		substancedao.delete(substancedao.get(id));
	}

	public void updateSubstance(Substance t, String... args) {
		substancedao.update(t, args);
	}

	public void getSubstance(int id) {
		substancedao.get(id).print();
	}

	public void getAllSubstance() {
		System.out.println("\nAlla Ämnen i databasen:\n");
		for (Substance s : substancedao.getAll()) {
			s.print();
		}
	}
	
}
