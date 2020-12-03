package JZ.com.mr.controler.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import JZ.com.mr.dao.HistorySearchDao;
import JZ.com.mr.entity.Course;
import JZ.com.mr.entity.History;
import JZ.com.mr.service.CourseService;

/**
 * Servlet implementation class AddHistory
 */
@WebServlet("/AddHistory")
public class AddHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddHistory() {
        super();
        // TODO Auto-generated constructor stub
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
		response.setContentType("text/html;charset=utf-8");
		//获取数据，通过流的方式
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						request.getInputStream(), "utf-8")); 
		String json = reader.readLine();
		Gson gson=new Gson(); 
		History h=gson.fromJson(json, History.class);
		new HistorySearchDao().addHistory(h.getUser_name(), h.getSearch()); 
	}

}
