package JZ.com.zft.workout.motion.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JZ.com.zft.workout.entity.Motion;
import JZ.utils.DBUtil;

public class MotionDaoImpl {
	private List<Motion> motions;
	public MotionDaoImpl() {
		motions = new ArrayList<Motion>();
	}
	
	public List<Motion> getMotions(String planName){
		try {
			DBUtil dbUtil = DBUtil.getInstance();
			ResultSet rs = dbUtil.queryDate("select * from motion_list where plan_name = '" + planName + "'");
			while(rs.next()) {
				int id = rs.getInt("id");
				String plan_name = rs.getString("plan_name");
				String motion_name = rs.getString("motion_name");
				String motion_img = rs.getString("motion_img");
				int motion_star = rs.getInt("motion_star");
				int motion_count = rs.getInt("motion_count");
				int motion_time = rs.getInt("motion_time");
				String motion_desc = rs.getString("plan_desc");
				String plan_head = rs.getString("plan_head");
				int rest_time = rs.getInt("rest_time");
				Motion motion = new Motion();
				motion.setId(id);
				motion.setPlanName(plan_name);
				motion.setMotionName(motion_name);
				motion.setMotionImg(motion_img);
				motion.setMotionStar(motion_star);
				motion.setMotionCount(motion_count);
				motion.setMotionTime(motion_time);
				motion.setMotionDesc(motion_desc);
				motion.setPlanHead(plan_head);
				motion.setRestTime(rest_time);
				motions.add(motion);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return motions;
	}
}
