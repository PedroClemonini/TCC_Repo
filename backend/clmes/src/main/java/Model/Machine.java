package Model;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Database.DBconnection;

public class Machine {

	private int id;
	private Timestamp datetimeStart;
	private Timestamp datetimeEnd;
	private Double productionLog;
	


	public Machine(int id, Timestamp datetimeStart, Timestamp datetimeEnd) {
		this.id = id;
		this.datetimeStart = datetimeStart;
		this.datetimeEnd = datetimeEnd;
		
	}
	
	public Machine() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDatetimeStart() {
		return datetimeStart;
	}

	public void setDatetimeStart(Timestamp datetimeStart) {
		this.datetimeStart = datetimeStart;
	}

	public Timestamp getDatetimeEnd() {
		return datetimeEnd;
	}

	public void setDatetimeEnd(Timestamp datetimeEnd) {
		this.datetimeEnd = datetimeEnd;
	}

	public Double getProductionLog() {
		return this.productionLog;
	}

	public void setProductionLog(Double productionLog) {
		this.productionLog = productionLog;
	}


}



