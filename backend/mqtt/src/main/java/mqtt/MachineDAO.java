package mqtt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;




public class MachineDAO implements IMachineDAO {

	private Connection conn = null;

	@Override
	public void logOnToOff(Machine machine) {
		this.conn = new DBconnection().getConnection();
		try {
			CallableStatement statement = conn.prepareCall("CALL on_to_off_log(?, ?, ?)");           
			statement.setInt(1, machine.getId());
			statement.setTimestamp(2, machine.getDatetimeStart());
			statement.setDouble(3, machine.getProductionLog());
			statement.execute();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void logOffToOn(Machine machine) {
		this.conn = new DBconnection().getConnection();
		try {
			CallableStatement statement = conn.prepareCall("CALL off_to_on_log(?, ?, ?)");           
			statement.setInt(1, machine.getId());
			statement.setTimestamp(2, machine.getDatetimeStart());
			statement.setDouble(3, machine.getProductionLog());
			statement.execute();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void get(Machine machine) {
		
		try (Connection conn = new DBconnection().getConnection();
				CallableStatement statement = conn.prepareCall("CALL load_machine_date(?)")) {

			statement.setInt(1, machine.getId());
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Timestamp startDate = rs.getTimestamp("log_date_start");
					Timestamp endDate = rs.getTimestamp("log_date_end");
					BigDecimal estimateProd = rs.getBigDecimal("estimate_production_hour");
					BigDecimal prodUnit = rs.getBigDecimal("production_unit");

					machine.loadData(
					    startDate,
					    endDate,
					    estimateProd != null ? estimateProd.doubleValue() : 0.0,
					    prodUnit != null ? prodUnit.doubleValue() : 0.0
					);
					}
				}
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Machine machine) {
		this.conn = new DBconnection().getConnection();
		try {
			CallableStatement statement = conn.prepareCall("CALL update_production(?, ?, ?, ?)");           
			statement.setInt(1, machine.getId());
			statement.setTimestamp(2, machine.getDatetimeStart());
			statement.setTimestamp(3, machine.getDatetimeEnd());
			statement.setDouble(4, machine.getProductionLog());
			statement.execute();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

}
