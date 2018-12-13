package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.SupplierDao;
import dao.UserDao;
import model.Supplier;
import model.User;

class SupplierTest {
	SupplierDao supplierdao = new SupplierDao();
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		Supplier supplier = new SupplierDao().get(1);
		assertEquals("Curium Pharma", supplier.getSupplierName(), "Fel supplier");
		String[] params = new String[1];
		params[0]="name";
		Supplier supplier1 = new SupplierDao().get(1);
		assertEquals("Curium Pharma", supplier1.getSupplierName(), "Fel supplier för Curium Pharma");
		supplierdao.update(new Supplier(1, "Påhittad"), params);
		supplier1 = supplierdao.get(1);
		assertEquals("Påhittad", supplier1.getSupplierName(), "Fel signar för Påhittad");
		supplierdao.update(new Supplier(1, "Curium Pharma"), params);
		supplier1 = supplierdao.get(1);
		assertEquals("Curium Pharma", supplier1.getSupplierName(), "Fel signar för Curium Pharma");
	}
}
