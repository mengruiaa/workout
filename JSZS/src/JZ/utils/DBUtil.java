package JZ.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	// ����MySql��������Ϣ
	private static String driver;
	private static String connStr;
	private static String user;
	private static String pwd;

	private static Connection conn;
	//���嵱ǰ��Ķ�����Ϊ���ԣ�����ģʽ����Ҫ��
	private static DBUtil dbUtil;
	//��̬�����
	static {
		try {
			loadDBProperty("DBConfig.properties");
			connectToDB();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private DBUtil() {	
	}
	//�ṩ��ȡ��ǰ��Ķ���Ľӿ�
	public static DBUtil getInstance() throws ClassNotFoundException, SQLException {
		if(null == dbUtil) {
			dbUtil = new DBUtil();
		}
		return dbUtil;
	}
 
	private static void loadDBProperty(String pFile) throws IOException, ClassNotFoundException, SQLException {
		// ����Properties����
		Properties prop = new Properties();
		// ���������ļ�
		prop.load(DBUtil.class.getClassLoader().getResourceAsStream(pFile));
		driver = prop.getProperty("DRIVER");
		connStr = prop.getProperty("CONN_STR");
		user = prop.getProperty("USER");
		pwd = prop.getProperty("PWD");
	}

	// ��ȡ���Ӷ���
	private static void connectToDB() throws SQLException, ClassNotFoundException {
		if (null == conn || conn.isClosed()) {
			Class.forName(driver);
			conn = DriverManager.getConnection(connStr, user, pwd);
		}
	}

	/**
	 * ��ѯ����
	 * 
	 * @param sql ��ѯ���ݵ�sql���
	 * @return ��ѯ�������ݼ�
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ResultSet queryDate(String sql) throws ClassNotFoundException, SQLException {
		Statement stmt = conn.createStatement();
		// ִ�в�ѯ
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}

	/**
	 * �ж������Ƿ����
	 * 
	 * @param sql ��ѯ��sql���
	 * @return �����򷵻�true�����򷵻�false
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean isExist(String sql) throws ClassNotFoundException, SQLException {
		Statement stmt = conn.createStatement();
		// ִ�в�ѯ
		ResultSet rs = stmt.executeQuery(sql);
		return rs.next();
	}

	/**
	 * ��������
	 * 
	 * @param sql ִ�в����sql���
	 * @return �����¼������
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int addDataToTable(String sql) throws ClassNotFoundException, SQLException {
		Statement stmt = conn.createStatement();
		return stmt.executeUpdate(sql);
	}

	/**
	 * �޸Ļ�ɾ������
	 * 
	 * @param sql ��������SQL���
	 * @return �޸Ļ�ɾ���ļ�¼����
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public int updateData(String sql) throws ClassNotFoundException, SQLException {
		Statement stmt = conn.createStatement();
		return stmt.executeUpdate(sql);

	}

	/**
	 * �ر����ݿ�����
	 * 
	 * @throws SQLException
	 */
	public void closeDB() throws SQLException {
		if (null != conn && !conn.isClosed()) {
			conn.close();
		}
	}
}
