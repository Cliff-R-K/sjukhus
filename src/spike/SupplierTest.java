package spike;

import java.util.ArrayList;

import dao.SupplierDao;
import model.Supplier;

public class SupplierTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

String[] suppliers = {"Curium Pharma", "GE Helthcare", "Bayer", "SAM Nordic", "NM Gävle"};
SupplierDao supDao = new SupplierDao();

for(String s : suppliers) {
	supDao.save(new Supplier(s));
}

//		Supplier sup = new Supplier("TestSupplier3");
		
//		supDao.save(sup);
//		String[] params = new String[1];
//		params[0]="name";

//		supDao.update(new Supplier(21,"updatedSupplier"), params);
//		ArrayList<Supplier> list = new ArrayList<>();
//		list=(ArrayList<Supplier>) supDao.getAll();
//		list.get(1).print();
//		supDao.delete(sup);

	}

}
