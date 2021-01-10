package com.productphoto.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.event.model.EventService;
import com.event.model.EventVO;
import com.product.model.ProductVO;
import com.productphoto.model.ProductPhotoService;
import com.productphoto.model.ProductPhotoVO;

/**
 * Servlet implementation class ShowProductPhoto
 */
@WebServlet("/ShowProductPhoto")
public class ShowProductPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowProductPhoto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/gif");
		ServletOutputStream sos =  res.getOutputStream();
		ProductPhotoService ProductPhotoSVC = new ProductPhotoService();
		String productphoto_id = null;
		ProductPhotoVO productPhotoVO = null;

		if (req.getParameter("productphoto_id") != null && req.getParameter("productphoto_id").trim().length() != 0) {
			productphoto_id = req.getParameter("productphoto_id");
			productPhotoVO = ProductPhotoSVC.getOneProductPhoto(productphoto_id);
		}
		
		try {
//			if ("getEventPoster".equals(req.getParameter("action"))) {
					sos.write(productPhotoVO.getProductphoto_photo());
//			} else if ("getEventSeat".equals(req.getParameter("action"))) {
//					sos.write(eventVO.getEvent_seat());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sos.close();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, res);
	}

}
