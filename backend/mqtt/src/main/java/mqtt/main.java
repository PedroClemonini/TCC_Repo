package mqtt;

import java.sql.Timestamp;

public class main {

	public static void main(String[] args) {

		MqttProtocol mqtt = new MqttProtocol("tcp","192.168.0.35","1883","java");
		int qos = 0;
		mqtt.connect();
		mqtt.subscribe("machines/tear/#", qos,new MachineCallback(new Machine()));
		teste teste = new teste(mqtt);
	}
}
