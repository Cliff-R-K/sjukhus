package view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dao.UserDao;
import model.User;

public class Main_method {
	UserDao userdao = new UserDao();

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
		System.out.println("\nAlla Userer i databasen:\n");
		for (User s : userdao.getAll()) {
			s.print();
		}
	}
	
	
}
