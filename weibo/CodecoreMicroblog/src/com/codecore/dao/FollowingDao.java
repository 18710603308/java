package com.codecore.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.codecore.dbutil.DBConn;
import com.codecore.entity.UserInfo;
/**
 * 
 * @author Vincent
 *	��ȡ��ע�������Ϣ
 */
public class FollowingDao {
	
	//��ǰ�û�����ע�˵���Ϣ
	public List<UserInfo> getFollowingByUid(final int uid , int pageSize, int pageNo){
		List<UserInfo> followinglist = new ArrayList<UserInfo>();
		String strSQL = "select * from userinfo where u_id =any " +
				"(select f_gid from friends where f_uid= " +
				"(select u_id from userinfo where u_id=?)) " +
				"or u_id =any (select f_uid from friends where f_state=2 and " +
				"(f_gid= any (select u_id from userinfo where u_id=?))) limit ?, ?";
		//���ݿ�����
		DBConn dbConn = new DBConn();
		ResultSet rs = dbConn.execQuery(strSQL, new Object[]{uid, uid,pageSize*(pageNo-1), pageSize});
		try {
			//���ؽ����
			while(rs.next()) {
				UserInfo following = new UserInfo();
				following.setU_id(rs.getInt("u_id"));
				following.setU_addr(rs.getString("u_addr"));
				following.setU_nick(rs.getString("u_nick"));
				following.setU_img(rs.getString("u_img"));
				followinglist.add(following);
			}
			return followinglist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			dbConn.closeConn();
		}
		
	}
	

}
