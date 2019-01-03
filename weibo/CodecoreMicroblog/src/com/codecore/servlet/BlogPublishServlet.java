package com.codecore.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.codecore.dao.BlogPublish;
import com.codecore.entity.Blog;
import com.codecore.tools.DataTool;

public class BlogPublishServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2055608971857845897L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �������������ַ���
		request.setCharacterEncoding("utf-8");
		int u_id = Integer.parseInt(request.getParameter("curUid"));
		String b_img = request.getParameter("b_img");
		Date date = new Date(System.currentTimeMillis());
		Date b_time = date;
		Blog blog = new Blog();
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileload = new ServletFileUpload(factory);
			// ��������ļ��ߴ磬������4MB
			fileload.setSizeMax(4194304);
			List<FileItem> files = fileload.parseRequest(request);
			// ѭ����������ÿһ������
			Iterator<FileItem> iterator = files.iterator();
			while (iterator.hasNext()) {
				FileItem item = iterator.next();
				// �����ǰ������ͨ���
				if (item.isFormField()) {
					if ("b_content".equals(item.getFieldName())) {
						blog.setB_content(item.getString("utf-8"));
					}
					if ("u_id".equals(item.getFieldName())) {
						blog.setU_id(Integer.parseInt(item.getString("utf-8")));
					}
				} else {
					// ��û���ļ��������ļ�������·��
					String filename = item.getName();
					if (filename != null) {
						File file = new File(filename);
						DataTool dataTool=new DataTool();
						filename=dataTool.getDate()+file.getName();
						File filetoserver = new File(this.getServletContext()
								.getRealPath("/upload/pic"), filename);
						item.write(filetoserver);
						b_img = request.getContextPath()
								+ "/upload/pic/"
								+ filename
										.substring(filename.lastIndexOf("\\") + 1);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ����ǰ̨�ͻ�������
		// ��������
		// ��װ������
		blog.setB_img(b_img);
		blog.setB_time(b_time);
		blog.setU_id(u_id);
		// ����DAO�����΢������
		BlogPublish blogpublish = new BlogPublish();
		blogpublish.blogpublish(blog);

		// ���ݴ��������пͻ�����Ӧ
		response.sendRedirect("../ShowAttentionBlogServlet?curUid=" + u_id);
	}
}
