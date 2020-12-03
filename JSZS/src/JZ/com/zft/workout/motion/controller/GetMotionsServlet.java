package JZ.com.zft.workout.motion.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import JZ.com.zft.workout.entity.Motion;
import JZ.com.zft.workout.motion.dao.MotionDaoImpl;

/**
 * Servlet implementation class GetMotionsServlet
 */
@WebServlet("/GetMotions")
public class GetMotionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMotionsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String planName = request.getParameter("planName");
		MotionDaoImpl motionDao = new MotionDaoImpl();
		List<Motion> motions = motionDao.getMotions(planName);
		Gson gson = new GsonBuilder().serializeNulls().setDateFormat("YY:MM:dd").create();
		String res = gson.toJson(motions);
		PrintWriter writer = response.getWriter();
		writer.write(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
