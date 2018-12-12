package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.SubstanceDao;
import model.Substance;

class SubstanceTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		boolean check = false;
		Substance substance = new SubstanceDao().get(2);
		List<Substance> subList = new SubstanceDao().getAll();
		for(Substance t : subList) {
			if(t.getName() == "Co-57") {
				check = true;
			}
		}
		assertFalse(check, "Ämnet 'Co-57' finns inte");
		assertEquals("Cr-51", substance.getName(), "Ämnet 'Cr-51' finns inte");
	}
}
