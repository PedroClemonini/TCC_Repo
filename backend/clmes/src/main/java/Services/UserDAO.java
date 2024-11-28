package Services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Database.DBconnection;
import Model.User;

public class UserDAO implements IUserDAO{

	private Connection conn = null;

	public UserDAO() {

		this.conn = new DBconnection().getConnection();
	}

	@Override
	public void create(User usuario) { 
		try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO user(name,email,password) values (?,?,?) ");
			statement.setString(1, usuario.getName());
			statement.setString(2, usuario.getEmail());
			statement.setString(3, usuario.getPassword());
			statement.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User get(Long id) {

		return null;
	}
	
	public boolean login(User user) {
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * from user where email = ?");
			statement.setString(1, user.getEmail());
			
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				if(rs.getString("password").contentEquals(user.getPassword())) {
					return true;
				}
				
			}
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public void update(User usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
