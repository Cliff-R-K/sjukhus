package spike;

import java.util.List;

import dao.StudentDao;
import model.Student;

/**
 * This is just a test class to test high level functionality
 * of the service and domain (model) layer.
 * @author awi (Åke Wallin)
 * @version 2018-10-18
 */
public class StudentTest {

	
	public static void main(String[] args) {
		
		List<Student> list = new StudentDao().getAll();
		
		for(Student student : list)
			student.print();
		
	

	}

}
