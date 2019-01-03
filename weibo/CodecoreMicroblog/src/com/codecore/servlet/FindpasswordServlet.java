package com.codecore.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.codecore.dao.UserDao;
import com.codecore.entity.UserInfo;

public class FindpasswordServlet extends HttpServlet {

	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7437955871310285611L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	 doPost(request,response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 request.setCharacterEncoding("utf-8");
	        //��ǰ̨��ȡ�û���Email���ܱ�����
			String u_mail=request.getParameter("email");
	        String u_question=request.getParameter("question");
	        UserDao userDao=new UserDao();
	        //�����û�����
	        UserInfo userInfo=(UserInfo)userDao.findPassword(u_mail, u_question);
	        if(userInfo!=null){
	        	//���û������䷢������
	        	SimpleEmail email = new SimpleEmail();
	    		//���÷��������ķ�������ַ
	    		email.setHostName("smtp.163.com");
	    		try {
	    			//�����ռ�������
	    			email.addTo(userInfo.getU_mail(),userInfo.getU_account());
	    			//����������
	    			email.setFrom("CodecoreBlog@163.com", "Codecore");
	    			//���Ҫ�������֤�������û��������룬�ֱ�Ϊ���������ʼ���������ע����û���������
	    			email.setAuthentication("CodecoreBlog", "codecore");
	    			//�����ʼ�������
	    			email.setSubject("Codecore�������͵��ʼ�������");
	    			//�ʼ�������Ϣ
	    			email.setMsg("�װ���"+userInfo.getU_account()+"���ã���л��ע��Codecore΢��ϵͳ����������ǣ�"+userInfo.getU_password()+"�������Ʊ��ܣ��Է���������ȡ������");
	    			email.send();
	    			response.sendRedirect("../findpassword.jsp?msg=10");
	    		} catch (EmailException e) {
	    			// TODO Auto-generated catch block
	    			response.sendRedirect("../findpassword.jsp?msg=7");
	    		}
	        }
	        else{
	        	response.sendRedirect("../findpassword.jsp?msg=7");
	        }
	}

}
