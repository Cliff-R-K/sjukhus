package spike;

import java.util.ArrayList;

import dao.SupplierDao;
import model.Supplier;

public class SupplierTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Supplier sup = new Supplier(1,"TestSupplier3");
		SupplierDao supDao = new SupplierDao();
		supDao.save(sup);
		String[] params = new String[1];
		params[0]="name";

		supDao.update(new Supplier(21,"updatedSupplier"), params);
		ArrayList<Supplier> list = new ArrayList<>();
		list=(ArrayList<Supplier>) supDao.getAll();
		list.get(1).print();
		supDao.delete(sup);

	}

}
