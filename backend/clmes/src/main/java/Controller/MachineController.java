package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import multitools.Json;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.google.gson.Gson;

import Model.Machine;
import Services.MachineDAO;

/**
 * Servlet implementation class Machine
 */
@WebServlet("/Machine")
public class MachineController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MachineController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Machine> list = new ArrayList<Machine>();
		
		Integer id = Integer.valueOf(request.getParameter("machine_id"));
		
		LocalDateTime startDate = LocalDateTime.ofInstant(
				Instant.parse(request.getParameter("start_date")),ZoneId.of("UTC"));
		LocalDateTime endDate = LocalDateTime.ofInstant(
				Instant.parse(request.getParameter("end_date")),ZoneId.of("UTC"));
		
		
		Machine machine = new Machine(id,Timestamp.valueOf(startDate),Timestamp.valueOf(endDate));
		MachineDAO dao = new MachineDAO(machine);
		list = dao.getLogs();
		Gson json = new Gson();
	
		response.getWriter().println(json.toJson(list));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
