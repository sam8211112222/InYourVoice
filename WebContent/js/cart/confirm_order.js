
function validateForm(){
	return $("#customerForm").validate({
		  rules: {
			  orderName: {
				  required: true
			  },
			  orderMail: {
				  required: true
			  },
			  orderPhone: {
				  required: true
			  },
			  orderAddress: {
				  required: true
			  },
			  select: {
				  required: true
			  },
			  cardNumber: {
				  required: true
			  },
			  cardName: {
				  required: true
			  },
			  cardMonth: {
				  required: true
			  },
			  cardYear: {
				  required: true
			  },
			  cardCvv: {
				  required: true
			  }
		    
		  },
		  messages:{
			  orderName: {
			      required: "<span style='color:red'>請輸入顧客名稱</span>"
			  },
			  orderMail: {
				  required: "<span style='color:red'>請輸入電子信箱</span>"
			  },
			  orderPhone: {
				  required: "<span style='color:red'>請輸入連絡電話</span>"
			  },
			  orderAddress: {
				  required: "<span style='color:red'>請輸入地址</span>"
			  },
			  select: {
				  required: "<span style='color:red'>請選擇發卡銀行</span>"
			  },
			  cardNumber: {
				  required: "<span style='color:red'>請輸入卡號</span>"
			  },
			  cardName: {
				  required: "<span style='color:red'>請輸入持卡者姓名</span>"
			  },
			  cardMonth: {
				  required: "<span style='color:red'>請輸入有效月份</span>"
			  },
			  cardYear: {
				  required: "<span style='color:red'>請輸入有效年份</span>"
			  },
			  cardCvv: {
				  required: "<span style='color:red'>請輸入安全碼</span>"
			  }
			  
		  }
		}).form();
}

function checkOut() {
	if(validateForm()){
		document.getElementById("customerForm").submit();
	}
	
}
// 判斷email
function checkUserEntryMail() {

	let userEntry = $("#order-customer-mail").val();
	
	let errMsg = $("#order-customer-mail-span");
	if (userEntry.length === 0) {
		errMsg.text('*請輸入字串');
	} else if (userEntry.indexOf('@') === -1) {
		errMsg.text('*請提供合法的Email帳號');
		
	} else if (userEntry.length >= 1 && userEntry.indexOf('@') != -1) {
		errMsg.text('');
	}
}

$("#order-customer-mail").on("blur", function() {
	checkUserEntryMail();
});

// 判斷電話
function checkUserEntryPhone() {

	let userEntry = $("#order-customer-phone").val();
	let errMsg = $("#order-customer-phone-span");
	if (userEntry.length === 0) {
		errMsg.text('*連絡電話請勿空白');
	} else if (!(/^1[34578]\d{9}$/.test(userEntry))) {
		errMsg.text('*連絡電話格式不對');
	} else if (userEntry.length > 11) {
		errMsg.text('');
	}
}
$("#order-customer-phone").on("blur", function() {
	checkUserEntryPhone();
});

// 判斷名字
function checkUserEntryName() {

	let userEntry = $("#order-customer-name").val();
	let errMsg = $("#order-customer-name-span");
	if (userEntry.length === 0) {
		errMsg.text('*名字請勿空白');
	} else if (userEntry.length >= 1) {
		errMsg.text('');
	}
}
$("#order-customer-name").on("blur", function() {
	checkUserEntryName();
});

// 判斷地址
function checkUserEntryAddress() {

	let userEntry = $("#order-customer-address").val();
	let errMsg = $("#order-customer-address-span");
	if (userEntry.length === 0) {
		errMsg.text('*地址請勿空白');
	} else if (userEntry.length >= 1) {
		errMsg.text('');
	}
}
$(function() {
	$("#order-customer-address").on("blur", function() {
		checkUserEntryAddress();
	});

	$("#twzipcode_ADV").twzipcode({
		zipcodeIntoDistrict : true, // 郵遞區號自動顯示在地區
		countyName : "event_city", // 自訂城市 select 標籤的 name 值
		districtName : "event_cityarea", // 自訂地區 select 標籤的 name 值
		countySel : "<%=eventVO.getEvent_city()%>",
		districSel : "<%=eventVO.getEvent_cityarea()%>",
		css : [ "form-control","form-control" ]
	});
});
