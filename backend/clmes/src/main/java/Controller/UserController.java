package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import multitools.Json;

import java.io.IOException;
import java.util.stream.Collectors;
import Model.User;
import Services.UserDAO;

/**
 * Servlet implementation class controller
 */
@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("login".equals(action)) {
			handleLogin(request, response);
		} else if ("register".equals(action)) {
			handleRegister(request, response);
		} else {

			response.sendRedirect("error.jsp");
		}


	}

	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String payload = request.getReader().lines().collect(Collectors.joining());

		Json json = new Json();
		User user = (User) json.parse(payload,"User");
		if (user == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
			return;
		}
		
		try {
            UserDAO dao = new UserDAO();
            if (dao.login(user)) {
                response.setContentType("application/json");
            } else {
                response.sendError(HttpServletResponse.SC_CONFLICT, "User already exists");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error during registration: " + e.getMessage());
        }
	
	}
	private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String payload = request.getReader().lines().collect(Collectors.joining());

		Json json = new Json();
		User user = (User) json.parse(payload,"User");

		if (user == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON payload");
			return;
		}

		try {
			UserDAO dao = new UserDAO();
			dao.create(user);
		}catch(Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot create user");
		}

	}

}



