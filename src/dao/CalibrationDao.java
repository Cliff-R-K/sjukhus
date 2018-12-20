package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


import db.DbConnectionManager;
import model.Calibration;
/*
public class CalibrationDao implements IDao<Calibration>{
	
	DbConnectionManager conn = null;

	public CalibrationDao() {
		conn = DbConnectionManager.getInstance();
	}
		@Override
		public Calibration get(int id) {
			Calibration calibration = null;
			try {
				ResultSet rs = conn.excecuteQuery("SELECT * FROM calibrations WHERE idcalibration = " +id);
				if(!rs.next())
					throw new NoSuchElementException();
				else {
					calibration = new Calibration(rs.getInt(1), rs.getTimestamp(2).toLocalDateTime(),rs.getDouble(3));
				}
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return calibration;
		}

		@Override
		public List<Calibration> getAll() {
			ArrayList<Calibration> list = new ArrayList<>();
			
			try {
				ResultSet rs = conn.excecuteQuery("SELECT * FROM calibrations");
				while(rs.next()) {
					list.add(new Calibration(rs.getInt(1), rs.getTimestamp(2).toLocalDateTime(),rs.getDouble(3)));
				}
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}

		@Override
		public boolean save(Calibration t) {
			PreparedStatement ps = null;
			boolean saveSuccess = false;
			
			try {
				String queryString = "INSERT INTO calibrations (calibration_date, mbq) VALUES(?,?)";
				ps = conn.prepareStatement(queryString);
				ps.setTimestamp(1, java.sql.Timestamp.valueOf(t.getDate()));
				ps.setDouble(2, t.getMbq());
				if(ps.executeUpdate() == 1)
					saveSuccess = true;
			} catch (Exception e) {
				System.err.println("Could not save");
			}
			
			System.out.println("Save Success");
			return saveSuccess;
		}

		@Override
		public void update(Calibration t, String[] params) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void delete(Calibration t) {
			PreparedStatement ps = null;
			boolean saveSuccess = false;
			try {
				String queryString = "DELETE FROM calibrations WHERE idcalibration=?";
				ps = conn.prepareStatement(queryString);
				ps.setInt(1, t.getId());
				
				if(ps.executeUpdate() == 1)
					saveSuccess = true;
			} catch (Exception e) {
				System.err.println("Could not delete");
				return;
			}
			
			System.out.println("Delete Success");
			
		}
	

}
*/