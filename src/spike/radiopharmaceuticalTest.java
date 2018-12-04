package spike;

import java.util.Date;

import model.Calibration;
import model.Room;
import model.Substance;
import model.Supplier;
import model.User;

public class radiopharmaceuticalTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Substance sub = new Substance(1, "substanceP", 2.0);
		Room room = new Room("303-01-01", "rum på nedervåningen");
		Supplier sup = new Supplier("SupplierP");
		User user = new User(1,"UserP");
		Calibration cal = new Calibration(0, new Date("2018-01-01") ,2.9);
	}

}
