package com.codecore.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codecore.dao.AttentionDao;
import com.codecore.dao.BlogPublish;
import com.codecore.entity.Blog;

public class DispatchBlogServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8616688705329981787L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		int bid=Integer.parseInt(request.getParameter("bid"));
		int uid=Integer.parseInt(session.getAttribute("userId").toString());
		BlogPublish blogPublish=new BlogPublish();
		Blog blog=new Blog();
		//��ȡ����΢����Ϣ
		blog=blogPublish.getBlogById(bid);
		//��ȡ����΢��ת������
		int dispatchTime=0;
		dispatchTime=blog.getB_degree();
		//ת��΢��
		blogPublish.dispatchBlog(blog, dispatchTime, uid);
		ArrayList<Blog> listAll=new ArrayList<Blog>();
	 	AttentionDao attentionDao=new AttentionDao();
		//---------//////////---��ҳ����-------------------//	 	
	 	//��ҳ
	 	String pageNumberStr = request.getParameter("pageNumber");  
	    int pageNumber = 1;  
	    if(pageNumberStr!=null && !pageNumberStr.isEmpty())  
	    {  
	        pageNumber = Integer.parseInt(pageNumberStr);  
	    }  
	    int pageSize = 3; //��ҳ��С  
	    int totalPosts = attentionDao.allBlogs(uid); //��������  
	    int totalPages = (int)totalPosts/pageSize + ((totalPosts%pageSize)>0?1:0); //����ó�����ҳ��  
	    session.setAttribute("pageSize", pageSize);  
	    session.setAttribute("totalPosts", totalPosts);  
	    session.setAttribute("pageNumber", pageNumber);  
	    session.setAttribute("totalPages", totalPages);  
	 	listAll=attentionDao.getBlog(uid, pageSize, pageNumber);
	 	session.setAttribute("listAll", listAll);
		response.sendRedirect("/CodecoreMicroblog/home.jsp");
	}

}
