package mqtt;


import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttProtocol {

	MqttAsyncClient client;
	IMqttToken connectedToken;
	public MqttProtocol(String brokerProtocol,String uri, String port, String clientId) {
		brokerProtocol = brokerProtocol + "://" + uri + ":" + port;
		MemoryPersistence persistence = new MemoryPersistence();
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		try {
			client = new MqttAsyncClient(brokerProtocol, clientId, persistence);
	
		} catch (MqttException e) {
			e.printStackTrace();
		}

	}

	public void connect() {

		try {
			this.connectedToken = client.connect(null, new IMqttActionListener() {
				@Override
				public void onSuccess(IMqttToken asyncActionToken) {
					System.out.println("Conectado!");
				}

				@Override
				public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
					System.out.println("Falha ao conectar: " + exception.getMessage());
				}
			});
		} catch (MqttException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void subscribe(String topic, int qos,MqttCallback callback) {
		try {
			connectedToken.waitForCompletion();
			client.setCallback(callback);
			client.subscribe(topic, qos, null, new IMqttActionListener() {
				@Override
				public void onSuccess(IMqttToken asyncActionToken) {
					System.out.println("Inscrito no tópico: " + topic);
					
				}

				@Override
				public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
					System.err.println("Falha ao se inscrever no tópico: " + exception.getMessage());
				}
			});
		} catch (MqttException e) {
			System.err.println("Erro ao se inscrever no tópico: " + e.getMessage());
		}
	}

	// Método para publicar uma mensagem
	public void publish(String topic, String payload, int qos) {
		try {
			MqttMessage message = new MqttMessage(payload.getBytes());
			message.setQos(qos);
			client.publish(topic, message, null, new IMqttActionListener() {
				@Override
				public void onSuccess(IMqttToken asyncActionToken) {
					//System.out.println("Mensagem publicada no tópico: " + topic);
				}

				@Override
				public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
					System.err.println("Falha ao publicar mensagem: " + exception.getMessage());
				}
			});
		} catch (MqttException e) {
			System.err.println("Erro ao publicar mensagem: " + e.getMessage());
		}
	}

	// Método para desconectar
	public void disconnect() {
		try {
			client.disconnect();
			System.out.println("Desconectado do broker.");
		} catch (MqttException e) {
			System.err.println("Erro ao desconectar: " + e.getMessage());
		}
	}
}


