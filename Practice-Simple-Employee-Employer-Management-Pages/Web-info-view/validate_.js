$.extend($.fn.validatebox.defaults.rules, {
    zip: {
        validator: function(value){
        	return /^[1-9]\d{5}$/i.test(value);
        },
        message: '邮政编码格式不正确'
    },
	phoneNo: {
    validator: function (value) {
        return /^\d{7}$/i.test(value);
    },
    message: '座机号必须为7位'
}
 /*   notNull:{
        validator: function (value) {
        	return /.+/i.test(value);
        },
        message: '不能为空'    	
    }*/
});


function gosearch(url)
{
	if(url) {
		window.location=url;
	}else{
		var adress=window.location.toString();
		if(adress.indexOf("add")!=-1){
			adress=adress.split("add")[0];
			
		}else if(adress.indexOf("edit")!=-1){
			adress=adress.split("edit")[0];
		}else{
			adress=adress.split("main")[0];
		}
	
		window.location=adress+"list";
	}
		   
}

$(function(){

	$("#submitlink").bind("click",function(){
		if($("#editform").form('validate')){
			 document.forms.editform.submit();		
		}
	});
	
	
})