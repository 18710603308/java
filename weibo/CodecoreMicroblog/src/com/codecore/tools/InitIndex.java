package com.codecore.tools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.codecore.dao.CountUser;
import com.codecore.dao.UploadFaceDao;
/**
 * ��ҳ������
 * @author Vincent
 *
 */
public class InitIndex implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
	
	}

	//��ʼ����ҳͷ��
	public void contextInitialized(ServletContextEvent context) {
		//List list = new StuDao().getStus();
		CountUser count=new CountUser();
		UploadFaceDao upload=new UploadFaceDao();
		context.getServletContext().setAttribute("info", count.countUser());
		context.getServletContext().setAttribute("face", upload.uploadFace());
	}
}
