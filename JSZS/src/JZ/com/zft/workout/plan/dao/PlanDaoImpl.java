package JZ.com.zft.workout.plan.dao;

import java.awt.image.RescaleOp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JZ.com.zft.workout.entity.Plan;
import JZ.utils.DBUtil;

public class PlanDaoImpl {
	private List<Plan> plans;
	public PlanDaoImpl() {
		plans = new ArrayList<Plan>();
	}

	public List<Plan> getPlans(String typeName){
		try {
			DBUtil dbUtil = DBUtil.getInstance();
			ResultSet rs = dbUtil.queryDate("select * from plan_list where type_name=" + "'" + typeName + "'");
			while(rs.next()) {
				int id = rs.getInt("id");
				String type_name = rs.getString("type_name");
				String plan_name = rs.getString("plan_name");
				int star = rs.getInt("plan_star");
				String plan_imgurl = rs.getString("plan_imgurl");
				String planinfo = rs.getString("planinfo");
				String planinfoImg = rs.getString("planinfo_img");
				String planPeople = rs.getString("plan_people");
				String planTime = rs.getString("plan_time");
				Plan plan = new Plan();
				plan.setId(id);
				plan.setTypeName(type_name);
				plan.setPlanName(plan_name);
				plan.setPlanStar(star);
				plan.setPlanImg(plan_imgurl);
				plan.setPlaninfo(planinfo);
				plan.setPlaninfoImg(planinfoImg);
				plan.setPlanPeople(planPeople);
				plan.setPlanTime(planTime);
				plans.add(plan);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return plans;
	}
	
	public boolean isExistPlan(String sql) {
		boolean b = false;
		try {
			DBUtil dbUtil = DBUtil.getInstance();
			b = dbUtil.isExist(sql);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	public boolean addPlan(String phone, String plan_name) {
		boolean b = false;
		try {
			DBUtil dbUtil = DBUtil.getInstance();
			dbUtil.addDataToTable("insert into my_plan(phone,plan_name)" + "value('" + phone + "','" + plan_name + "')");
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
}
