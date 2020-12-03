package JZ.com.ws.act;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import JZ.com.ws.entity.User;
import JZ.com.ws.service.Userservice;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserLoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ���ñ��뷽ʽ
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj"); 
		// ��ȡ���ݣ�ͨ�����ķ�ʽ
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		String json = reader.readLine();
		User user = new Gson().fromJson(json, User.class);
		System.out.println(user.toString());
		Boolean judgeStatement = false;
		try {
			Userservice userservice = new Userservice();

			judgeStatement = userservice.isExistUser(user);
			if (judgeStatement) {
				response.getWriter().write("�˻���¼�ɹ�");
			} else {
				response.getWriter().write("���˻�����");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
