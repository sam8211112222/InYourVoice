package com.productphoto.controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.productphoto.model.ProductPhotoService;

@WebServlet("/productphoto/YUproductPhotoServlet")
public class YUProductPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductPhotoService service;

	@Override
	public void init() throws ServletException {
		super.init();
		service = new ProductPhotoService();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			String id = req.getParameter("id");
			String photoId = req.getParameter("photoId");

			byte[] fileData = null;
			if (id != null) {
				 fileData = service.getImage(id);
			} else if (photoId != null) {
				 fileData = service.getImageByPhotoId(photoId);
			}

			if (fileData != null) {
				res.setContentType("image/jpeg");
				res.setContentLength(fileData.length);

				ByteArrayInputStream bis = new ByteArrayInputStream(fileData);
				OutputStream out = res.getOutputStream();

				// Copy the contents of the file to the output stream
				byte[] buf = new byte[1024];
				int count = 0;
				while ((count = bis.read(buf)) >= 0) {
					out.write(buf, 0, count);
				}
				out.close();
				bis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
