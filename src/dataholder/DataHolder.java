package dataholder;

import model.RegRadio;

public class DataHolder {
	
	private static RegRadio savedRadio;

	public static RegRadio getSavedRadio() {
		System.out.println("saved");
		System.out.println(savedRadio);
		return savedRadio;
	}
	public static void setSavedRadio(RegRadio radio) {
		savedRadio = radio;
	}
}
