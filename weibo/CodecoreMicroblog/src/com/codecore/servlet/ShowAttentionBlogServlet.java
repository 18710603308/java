package com.codecore.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.codecore.dao.AttentionDao;
import com.codecore.entity.Blog;
import com.codecore.entity.UserInfo;

public class ShowAttentionBlogServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6095035046760894217L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
//	 	List<UserInfo> atteInfos=new ArrayList<UserInfo>();
//	 	UserInfo userInfo=new UserInfo();
	 	AttentionDao attentionDao=new AttentionDao();
	 	HttpSession session=request.getSession();
	 	//��ȡ��ǰ�û�id
	 	int uid=0;
	 	if (request.getParameter("curUid")!=null) {
	 		uid=Integer.parseInt(request.getParameter("curUid"));
		}else{
			UserInfo userInfo=(UserInfo)session.getAttribute("userInfo");
			uid=userInfo.getU_id();
		}
	 	ArrayList<Blog> listAll=new ArrayList<Blog>();
	 	
	 	///////////----------��ҳ����--------////////
	 	int pageNumber = 0;  
	 	if (request.getParameter("pageNumber")!=null) {
	 		pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}else
			pageNumber=1;
	    int pageSize = 3; //��ҳ��С  
	    int totalPosts = attentionDao.allBlogs(uid); //��������  
	    int totalPages = (int)totalPosts/pageSize + ((totalPosts%pageSize)>0?1:0); //����ó�����ҳ��  
	    session.setAttribute("pageSize", pageSize);  
	    session.setAttribute("totalPosts", totalPosts);  
	    session.setAttribute("pageNumber", pageNumber);  
	    session.setAttribute("totalPages", totalPages);
	 	listAll=attentionDao.getBlog(uid, pageSize, pageNumber);
	 	//////////-------��ҳ����----------/////////////
	 	session.setAttribute("listAll", listAll);
	 	response.sendRedirect("/CodecoreMicroblog/home.jsp");
	}
}
