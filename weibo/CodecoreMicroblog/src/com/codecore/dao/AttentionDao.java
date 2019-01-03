package com.codecore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.codecore.dbutil.DBConn;
import com.codecore.entity.Blog;
import com.codecore.entity.UserInfo;

/**
 * @version1.0
 * @author Vincent
 * �͹�עҳ��ص�DAO��
 */
public class AttentionDao {
	 
	//��ӹ�ע
	public boolean addAttention(final int uid, final int fid) {
		DBConn dbConn = new DBConn();
		String sqlInsert = "insert into friends (f_uid, f_gid, f_state) values (?, ?, ?)";
		String sqlUpdate = "update friends set f_state=2 where f_uid=? and f_gid=?";
		int affected = 0;
		if (isAttention2(uid, fid)==true) {
			if (isAttention(uid, fid)==true) {
				affected = dbConn.execOther(sqlInsert, new Object[] {
						uid, fid, 1});
			} else
				affected = dbConn.execOther(sqlUpdate, new Object[] {
						fid, uid });
		}
		return affected > 0 ? true : false;
	}

	// �ж�uid�Ƿ���Լ�fidΪ��ע,ֻ���ж�fid�Ƿ��Ѿ���uid��ע
	private static boolean isAttention(final int uid, final int fid) {
		DBConn dbConn = new DBConn();
		String sql = "select * from friends where f_uid=? and f_gid=?";
		ResultSet rs = dbConn.execQuery(sql, new Object[] { fid, uid });
		boolean flag = false;
		try {
			if (rs.next()) {
				flag = false;
			} else
				flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return flag;
	}

	/////------------�ж�uid�Ƿ��Ѿ���עfid--------/////////////
	private static boolean isAttention2(final int uid, final int fid) {
		DBConn dbConn = new DBConn();
		String sql = "select * from friends where f_uid=? and f_gid=?";
		ResultSet rs = dbConn.execQuery(sql, new Object[] { uid, fid});
		boolean flag = false;
		try {
			if (rs.next()) {
				flag = false;//�ѹ�ע
			} else
				flag = true;//δ��ע
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return flag;
	}

	// ͳ�ƹ�ע����
	public long accountAttention(final int id) {
		DBConn dbConn = new DBConn();
		String sql = "select count(*) from friends where (f_uid=?) or (f_gid=? and f_state=2)";
		//select count(*) from friends where (f_uid=(select u_id from userinfo where u_id=? ) and f_state=1) or (f_uid=(select u_id from userinfo where u_id=? ) and f_state=2)
		
		ResultSet rs = dbConn.execQuery(sql, new Object[] { id, id });
		try {
			rs.next();
			long num = Long.parseLong(rs.getString("count(*)"));
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		} finally {
			dbConn.closeConn();
		}
	}

	// ��ȡ��ǰ�û�u_id ��ע����Ϣ
	public List<UserInfo> getAttention(final int id) {
		List<UserInfo> lstInfos = new ArrayList<UserInfo>();
		String sql = "SELECT * from userinfo where u_id= " +
				"any (select f_gid from friends where (f_uid=(select u_id from userinfo where u_id=?) and " +
				"f_state=1) or (f_uid=(select u_id from userinfo where u_id=?) and f_state=2))";
		DBConn dbConn = new DBConn();
		ResultSet rs = dbConn.execQuery(sql, new Object[] {id, id});
		try {
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setU_id(rs.getInt("u_id"));
				userInfo.setU_img(rs.getString("u_img"));
				userInfo.setU_addr(rs.getString("u_addr"));
				userInfo.setU_nick(rs.getString("u_nick"));
				userInfo.setU_sign(rs.getString("u_sign"));
				userInfo.setU_url(rs.getString("u_url"));
				lstInfos.add(userInfo);
			}
			return lstInfos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			dbConn.closeConn();
		}
	}

	// ͳ�Ʒ�˿��
	public long accountFans(final int id) {
		DBConn dbConn = new DBConn();
		String sql = "select count(*) from friends where (f_uid=? and f_state=2) or f_gid=?";
		ResultSet rs = dbConn.execQuery(sql, new Object[] {id, id});
		try {
			rs.next();
			long num = Long.parseLong(rs.getString("count(*)"));
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		} finally {
			dbConn.closeConn();
		}
	}
	//��ȡ��ǰ�û���Ϣ
	public UserInfo getUserInfo(final int id){
		DBConn dbConn=new DBConn();
		String sql="select * from userinfo where u_id=?";
		ResultSet rs=dbConn.execQuery(sql, new Object[]{id});
		UserInfo userInfo=new UserInfo();
		try {
			while (rs.next()) {
				userInfo.setU_id(rs.getInt("u_id"));
				userInfo.setU_addr(rs.getString("u_addr"));
				userInfo.setU_nick(rs.getString("u_nick"));
				userInfo.setU_img(rs.getString("u_img"));
				userInfo.setU_date(rs.getString("u_date"));
				userInfo.setU_sign(rs.getString("u_sign"));
				userInfo.setU_url(rs.getString("u_url"));
				userInfo.setU_sex(rs.getString("u_sex"));
				userInfo.setU_account(rs.getString("u_account"));
				userInfo.setU_password(rs.getString("u_password"));
			}
			return userInfo;
		} catch (SQLException e) {
			
			return userInfo;
		}finally{
			dbConn.closeConn();
		}
	}
	
	//��ȡ��ǰ�û���Ϣ
	public UserInfo getUserInfo(final String account){
		DBConn dbConn=new DBConn();
		String sql="select * from userinfo where u_account=?";
		ResultSet rs=dbConn.execQuery(sql, new Object[]{account});
		UserInfo userInfo=new UserInfo();
		try {
			while (rs.next()) {
				userInfo.setU_id(rs.getInt("u_id"));
				userInfo.setU_addr(rs.getString("u_addr"));
				userInfo.setU_nick(rs.getString("u_nick"));
				userInfo.setU_img(rs.getString("u_img"));
				userInfo.setU_date(rs.getString("u_date"));
				userInfo.setU_sign(rs.getString("u_sign"));
				userInfo.setU_url(rs.getString("u_url"));
				userInfo.setU_sex(rs.getString("u_sex"));
			}
			return userInfo;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return userInfo;
		}finally{
			dbConn.closeConn();
		}
	}
	
	//��ȡ�û���Ϣ
	public ArrayList<UserInfo> getUserInfoList(final int id){
		DBConn dbConn=new DBConn();
		ArrayList<UserInfo> userInfos=new ArrayList<UserInfo>();
		String sql="select * from userinfo where u_id=?";
		ResultSet rs=dbConn.execQuery(sql, new Object[]{id});
		try {
			while (rs.next()) {
				UserInfo userInfo=new UserInfo();
				userInfo.setU_id(rs.getInt("u_id"));
				userInfo.setU_nick(rs.getString("u_nick"));
				userInfos.add(userInfo);
			}
			return userInfos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return userInfos;
		}finally{
			dbConn.closeConn();
		}
	}

	//��ȡ��ǰ�û�������ע������΢������
	public ArrayList<Blog> getBlog(final int id, int pageSize, int pageNo){
		DBConn dbConn=new DBConn();
		ArrayList<Blog> lstBlogs=new ArrayList<Blog>();
		String sql="select * from blog where (u_id= any( select f_gid from friends where " +
				"(f_uid=? and f_state=1) or (f_uid=? and f_state=2))or u_id=?  or u_id= any " +
				"(select f_uid from friends where f_gid=? and f_state=2)) order by b_time desc limit ?, ?";
		ResultSet rs=dbConn.execQuery(sql, new Object[]{id, id, id,id, pageSize*(pageNo-1), pageSize});
		try {
			while (rs.next()) {
				Blog blog=new Blog();
				blog.setB_content(rs.getString("b_content"));
				blog.setB_time(rs.getDate("b_time"));
				blog.setU_id(rs.getInt("u_id"));
				blog.setB_img(rs.getString("b_img"));
				blog.setB_degree(rs.getInt("b_degree"));
				blog.setB_id(rs.getInt("b_id"));
				blog.setB_date(rs.getTime("b_time"));
				lstBlogs.add(blog);
			}
			return lstBlogs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}finally{
			dbConn.closeConn();
		}
	}
	
	//ͳ�Ƶ�ǰ�û�΢����
	public long accountBlogs(final int id){
		DBConn dbConn=new DBConn();
		String sql="select count(*) from blog where u_id=?";
		ResultSet rs=dbConn.execQuery(sql, new Object[]{id});
		try {
			long num=0;
			while (rs.next()) {
				num=rs.getInt("count(*)");
			}
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		}finally{
			dbConn.closeConn();
		}
	}
	//ͳ�Ƶ�ǰ�û���������ע�˵�΢������
	public int allBlogs(final int id){
		DBConn dbConn=new DBConn();
		String sql="SELECT count(*) FROM blog where u_id= any (select f_gid from friends where (f_uid=? and f_state=1)" +
				" or (f_uid=? and f_state=2)) or u_id=?";
		ResultSet rs=dbConn.execQuery(sql, new Object[]{id, id, id});
		try {
			int num=0;
			while (rs.next()) {
				num=rs.getInt("count(*)");
			}
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		}finally{
			dbConn.closeConn();
		}
	}
	
	//�����û�ID����û�ͷ��
	public String getPortrait(final int id){
		DBConn dbConn=new DBConn();
		String sql="select u_img from userinfo where u_id=?";
		ResultSet rs=dbConn.execQuery(sql, new Object[]{id});
		String p=null;
		try {
			while (rs.next()) {
				p=rs.getString("u_img");
			}
			return p;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}finally{
			dbConn.closeConn();
		}
	}
	//�����û�ID����û��ǳ�
	public String getNick(final int id){
		DBConn dbConn=new DBConn();
		String sql="select u_nick from userinfo where u_id=?";
		ResultSet rs=dbConn.execQuery(sql, new Object[]{id});
		String nick=null;
		try {
			while (rs.next()) {
				nick=rs.getString("u_nick");
			}
			return nick;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}finally{
			dbConn.closeConn();
		}
	}
	//�û�ɾ��΢��
	public boolean deleteBlog(final int uid, final int bid){
		DBConn dbConn=new DBConn();
		String sql="delete from blog where u_id=? and b_id=?";
		int result=dbConn.execOther(sql, new Object[]{uid, bid});
		dbConn.closeConn();
		return result>0?true:false;
	}
/*	//�����Ñ�Cookie
	public void insertUserSession(UserInfo userInfo, String sessionid){
		DBConn dbConn=new DBConn();
		String sql="update userinfo set u_cookie=? where u_id=?";
		dbConn.execOther(sql, new Object[]{sessionid, userInfo.getU_id()});
		dbConn.closeConn();
	}
	//����û��Ƿ񱣴�Cookie
	public boolean getAutoLoginState(String username, String sessionid){
		DBConn dbConn=new DBConn();
		String sql="select * from userinfo where u_account=? and u_cookie=?";
		ResultSet rs=dbConn.execQuery(sql, new Object[]{username, sessionid});
		boolean flag=false;
		try {
			while (rs.next()) {
				flag=true;
			}
			return flag;
		} catch (SQLException e) {
			return flag;
		}finally{
			dbConn.closeConn();
		}
	}*/
}
