package spike;

import model.User;
import model.Substance;
import view.Main_method;

public class mainTest {
	public static void main(String[] args) {

		Main_method mm = new Main_method();
		//mm.addUser("TS");
		// mm.addStudent("Anders", "Bagge", "asdsad", "dsadsa", "63564364");

		// mm.getAllStudents();
		// mm.addKurs("DVG555", "Ocaml", 12345, "hel", 7.5, "www.snus.se",
		// "www.knark.se", "2018-09-09", "B");
		// mm.addKursToProgramByName("Databasteknik", "Dataingenjörsprogrammet");
		//mm.deleteUser(9);

//	mm.getKursensLärare("Envariabel");
//	mm.getLärarensKurser("Mikael Forslund");
		// mm.getAllGroups();
		// mm.updateLaboration(new Laboration(4, null, new BetygDao().get(14)),
		// "betyg");
		// mm.updateProjekt(new Projekt(11, "KartAPP", new BetygDao().get(4)), "betyg",
		// "projektNamn");
		// mm.deleteProgram(1);
		//mm.addSubstance("Co-57", 6501.1);
		mm.getAllSubstance();
	}
}
