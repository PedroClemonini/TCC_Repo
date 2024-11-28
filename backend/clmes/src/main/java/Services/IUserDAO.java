package Services;
import Model.User;

public interface IUserDAO {
	   void create(User usuario);
	    User get(Long id);
	    void update(User usuario);
	    void delete(Long id);
}
