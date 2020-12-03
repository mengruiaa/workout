package JZ.com.zft.workout.plan.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import JZ.com.zft.workout.plan.dao.PlanDaoImpl;

/**
 * Servlet implementation class AddPlanServlet
 */
@WebServlet("/AddPlan")
public class AddPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPlanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		String user_phone = request.getParameter("user_phone");
		String plan_name = request.getParameter("plan_name");
		PlanDaoImpl planDao = new PlanDaoImpl();
		boolean b = planDao.isExistPlan("select * from my_plan where phone='" + user_phone + "' and plan_name='" +  plan_name + "'");
		if(b) {
			writer.write("had");
		}else {
			boolean flag = planDao.addPlan(user_phone, plan_name);
			if(flag) {
				writer.write("successfully");
			}else {
				writer.write("failed");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
