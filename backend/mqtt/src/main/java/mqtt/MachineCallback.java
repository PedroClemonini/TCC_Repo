package mqtt;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MachineCallback implements MqttCallback, MachineObserver {

	private Machine machine;
	private MachineDAO dao;
	
	public MachineCallback(Machine machine) {
		this.machine = machine;
		machine.subscribe(this);
		dao = new MachineDAO();
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("mensagem recebida" + message);
		if(topic.contains("state")) {
			if(machine.getId() == null){
				machine.setId(Integer.parseInt(topic.split("/")[2]));
				dao.get(machine);
			}
			String data =new String(message.getPayload());
			LocalDateTime datetime = LocalDateTime.parse(data.substring(0, 19));
			
			Timestamp timestamp = Timestamp.valueOf(datetime);
			
			Integer value =  Integer.valueOf(data.substring(20,21));
			machine.setRealPoductionHour(timestamp,value);
			
			
		}

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}


	@Override
	public void update(String operation) {
		
		if(operation.equals("offToOn")) {
			dao.logOffToOn(machine);
			return;
		}else if(operation.equals("onToOff")) {
			dao.logOnToOff(machine);
			return;
		}else {
		dao.update(machine);
		return;
		}
		
	}

}
