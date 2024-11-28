package mqtt;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Machine {
	private Integer id;
	private Timestamp datetimeStart;
	private Timestamp datetimeEnd;
	private Double estimateProductionPerHour;
	private Double productionLog;
	private Integer state;

	private List<MachineObserver> observers;


	public Machine(int id, Double estimateProductionHour) {
		this.id = id;
		this.estimateProductionPerHour = estimateProductionHour;
		this.observers = new ArrayList<MachineObserver>();
	}

	public Machine() {
		this.observers = new ArrayList<MachineObserver>();
	}

	public void subscribe(MachineObserver observer) {
		observers.add(observer);
	}

	public void remove(MachineObserver observer) {
		int index = observers.indexOf(observer);
		if (index > -1) {
			observers.remove(observer);
		}
	}

	public void notification(String operation) {
		for (MachineObserver o : observers) {
			o.update(operation);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getEstimateProductionHour() {
		return estimateProductionPerHour;
	}

	public void setEstimateProductionHour(Double value) {
		this.estimateProductionPerHour = value;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setRealPoductionHour(Timestamp datetime, Integer newState) {
		System.out.println(this.getState());
		if(newState.equals(0) && this.getState().equals(0)) {
			this.productionLog = 0.0;
			this.setDatetimeEnd(datetime);
			this.setState(newState);
			notification("update");
		}else if(newState.equals(1) && this.getState().equals(0)) {
			this.productionLog = 0.0;
			this.setDatetimeStart(datetime);
			this.setDatetimeEnd(datetime);
			this.setState(newState);
			notification("offToOn");
		}else if(newState.equals(1) && this.getState().equals(1)) {
			this.productionLog = this.getEstimateProductionHour() * ((datetime.getTime() - this.getDatetimeStart().getTime())/3600000);
			this.setDatetimeEnd(datetime);
			this.setState(newState);
			notification("update");
		} else if(newState.equals(0) && this.getState().equals(1)) {
			this.productionLog = this.getEstimateProductionHour() * ((datetime.getTime() - this.getDatetimeStart().getTime())/3600000);
			this.setDatetimeStart(datetime);
			this.setDatetimeEnd(datetime);
			this.setState(newState);
			notification("onToOff");
		}


	}


	public Integer getState() {
		return this.state;
	}

	public Double getProductionLog() {
		return productionLog;
	}

	public void setProductionLog(Double productionLog) {
		this.productionLog = productionLog;
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

	public void loadData(Timestamp startdate, Timestamp endate, Double estimateprod, Double produnit) {
		this.setEstimateProductionHour(estimateprod);
		this.setDatetimeStart(startdate);
		this.setDatetimeEnd(endate);
		
		if(produnit >= 0 || produnit.equals(null)) { 
			this.setState(0);
		}else {
			this.setState(1);
		}
	}

}
