package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.SupplierDao;
import model.Supplier;

class SupplierTest {

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
	}

}
