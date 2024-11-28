package mqtt;


public interface IMachineDAO {
	void logOnToOff(Machine machine);
	void logOffToOn(Machine machine);
	void get(Machine machine);
	void update(Machine usuario);
	void delete(int id);
}
