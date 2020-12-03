package JZ.com.zft.workout.plan.controller;

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

import JZ.com.zft.workout.entity.Plan;
import JZ.com.zft.workout.plan.dao.PlanDaoImpl;

/**
 * Servlet implementation class GetPlansServlet
 */
@WebServlet("/GetPlans")
public class GetPlansServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPlansServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取客户端发送的键值对信息
				request.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");
				String typeName = request.getParameter("typeName");
				PlanDaoImpl planDao = new PlanDaoImpl();
				List<Plan> plans = planDao.getPlans(typeName);
				Gson gson = new GsonBuilder().serializeNulls().setDateFormat("YY:MM:dd").create();
				String res = gson.toJson(plans);
				System.out.println(res);
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
