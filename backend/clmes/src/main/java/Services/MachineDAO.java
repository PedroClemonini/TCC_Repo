package Services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.DBconnection;
import Model.Machine;

public class MachineDAO {
	private Machine machine;
	private Connection conn;
	
	public MachineDAO(Machine machine) {
		this.machine = machine;
	}
	
	public List<Machine> getLogs(){
		this.conn = new DBconnection().getConnection();
		List<Machine> list = new ArrayList<Machine>();

		try {
			CallableStatement statement = conn.prepareCall("CALL get_production_logs(?,?,?)");
			statement.setInt(1, machine.getId());
			statement.setTimestamp(2, machine.getDatetimeStart());
			statement.setTimestamp(3, machine.getDatetimeEnd());

			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Machine model = new Machine();
				model.setDatetimeStart(rs.getTimestamp("log_date"));
				model.setProductionLog(rs.getBigDecimal("production_unit").doubleValue());
				list.add(model);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
}
