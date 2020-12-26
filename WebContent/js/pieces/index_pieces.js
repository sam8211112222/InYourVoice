$.datetimepicker.setLocale('zh'); // kr ko ja en
$(function(){
	 $('#start_date1').datetimepicker({
	  format:'Y-m-d',
	  onShow:function(){
	   this.setOptions({
	    maxDate:$('#end_date1').val()?$('#end_date1').val():false
	   })
	  },
	  timepicker:false
	 });
	 
	 $('#end_date1').datetimepicker({
	  format:'Y-m-d',
	  onShow:function(){
	   this.setOptions({
	    minDate:$('#start_date1').val()?$('#start_date1').val():false
	   })
	  },
	  timepicker:false
	 });
});

$.datetimepicker.setLocale('zh'); // kr ko ja en
$(function(){
	 $('#start_date2').datetimepicker({
	  format:'Y-m-d',
	  onShow:function(){
	   this.setOptions({
	    maxDate:$('#end_date2').val()?$('#end_date2').val():false
	   })
	  },
	  timepicker:false
	 });
	 
	 $('#end_date2').datetimepicker({
	  format:'Y-m-d',
	  onShow:function(){
	   this.setOptions({
	    minDate:$('#start_date2').val()?$('#start_date2').val():false
	   })
	  },
	  timepicker:false
	 });
});
