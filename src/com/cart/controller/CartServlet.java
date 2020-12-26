package com.cart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cart.model.CartVO;

@WebServlet("/cart/cartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String SESSION_CART_KEY = "session_cart_key";

	private HttpServletRequest request;

	public CartServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 取得session
		HttpSession session = request.getSession();

		Object object = session.getAttribute(SESSION_CART_KEY);
		String action = req.getParameter("action");

		// 購物車是列表
		List<CartVO> cartList = null;
		if (object == null) {// 如果得到的是空值，就產生一個ArrayList
			cartList = new ArrayList<CartVO>();
		} else {// 如果購物車有清單，把他存回去session裡，並強制轉型成(List<CartVO>)
			cartList = (List<CartVO>) object;
		}

		if (!"checkout".equals(action)) {

			// 刪除購物車中的商品
			if ("delete".equals(action)) {
				String delete_cartProduct = req.getParameter("id");

				// 從集合中刪除一個商品
				Iterator<CartVO> item = cartList.iterator();
				while (item.hasNext()) {// 是否存在下一個對象
					CartVO items = item.next();// 拿到購物車中所有商品
					if (delete_cartProduct.equals(items.getProduct_id())) {// 判斷。如果商品id和傳過來的id一樣，則刪除
						item.remove();
						break;

						// 新增商品至購物車中
					} else if ("add".equals(action)) {
						req.getParameter("qty");
					}
					boolean match = false;

					// 取得後來新增的商品
					CartVO productItem = getItem(req);

					// 新增第一個商品
					if (cartList == null) {
						cartList = new ArrayList<CartVO>();
						cartList.add(productItem);
					} else {
						for (int i = 0; i < cartList.size(); i++) {
							CartVO prodItems = cartList.get(i);

							// 假若新增的商品和購物車的商品一樣時
							if (prodItems.getProduct_id().equals(productItem.getProduct_id())) {
								prodItems.setProduct_quantity(
										prodItems.getProduct_quantity() + productItem.getProduct_quantity());
								cartList.set(i, productItem);
								match = true;
							}
						}
						if (!match) {
							cartList.add(productItem);
						}
					}
					session.setAttribute("shoppingcart", cartList);
					String url = "/EShop.jsp";
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, res);
				}
			} else if ("checkout".equals(action)) {
				// 結帳，計算購物車商品價錢總數
				float total = 0;
				for (int i = 0; i < cartList.size(); i++) {
					CartVO order = cartList.get(i);
					double price = order.getProduct_price();
					int quantity = order.getProduct_quantity();
					total += (price * quantity);

				}
				String amount = String.valueOf(total);
				req.setAttribute("amount", amount);
				String url = "";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
			}

		}
	}

	private CartVO getItem(HttpServletRequest req) {
		String product_id = req.getParameter("product_id");
		String productphoto_id = req.getParameter("productphoto_id");
		String product_name = req.getParameter("product_name");
		String product_price = req.getParameter("product_price");
		String product_quantity = req.getParameter("product_quantity");
		CartVO item = new CartVO();
		item.setProduct_id(product_id);
		item.setProductphoto_id(productphoto_id);
		item.setProduct_name(product_name);
		item.setProduct_price((new Double(product_price)).doubleValue());
		item.setProduct_quantity((new Integer(product_quantity)).intValue());

		return item;
	}
}
