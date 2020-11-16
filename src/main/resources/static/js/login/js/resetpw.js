$(document).ready(function(){
	var isEMail=false;
	var isMobile=false;
	var isPassword=false;
	var isYzm=false;
	var isMobileYzm=false;

	$("#mobile").val('');
	$("#send_mobile_code").attr("disabled","disabled");

	$("#mobile_getpw_tab").click(function(){
		$("#email_getpw_tab").removeClass("regist_type_selected");
		$(this).addClass("regist_type_selected");
		$("#email_form").hide();
		$("#mobile_form").show();
	});

	$("#email_getpw_tab").click(function(){
		$("#mobile_getpw_tab").removeClass("regist_type_selected");
		$(this).addClass("regist_type_selected");
		$("#mobile_form").hide();
		$("#email_form").show();
	});


	$("#email").focus(function(){
		$("#email_tips").html('<p><span class="tips"></span>请输入您的注册邮箱</p>');
	});

	// 鼠标离开输入框，点击其他位置
	$("#email").blur(function(){
			var email=$("#email").val();
			if(testEmail(email)==false){
				$('#email_tips').html('<p  class="tips_error_p"><span class="tips_error"></span>请输入正确的邮箱地址</p>');
				return false;
			} else {
				$('#email_tips').html('<p><span class="tips_right"></span></p>');
			}
			/*$.post("/resetPwdByEmail", {"email": email},function(data){
				if(data.code=='1'){

					isEMail=true;
				}else{
					isMobile=false;
					$('#email_tips').html('<p  class="tips_error_p"><span class="tips_error"></span>'+data.msg+'</p>');
				}
			}, "json");*/
		}
	);





	$("#mobile").focus(function(){
		$("#mobile_tips").html('<p><span class="tips"></span>请输入您的注册手机号</p>');
	});
	$("#mobile").blur(function(){
		layer.msg('手机找回密码功能暂时下架，请通过邮箱找回密码', {
			time : 1000
		});
		/*var mobile=$("#mobile").val();
		var r=testMobile(mobile);
		if(r!=true){
			$('#mobile_tips').html('<p class="tips_error_p"><span class="tips_error"></span>'+r+'</p>');
			$("#send_mobile_code").attr("disabled","disabled");
			return false;
		}
		$.post("/ajax/resetpw.php", {"mobile":mobile,"check":'true'},function(data){
			if(data.code=='1'){
				$('#mobile_tips').html('<p><span class="tips_right"></span></p>');
				$("#send_mobile_code").removeAttr("disabled");
				isMobile=true;
			}else{
				isMobile=false;
				$('#mobile_tips').html('<p class="tips_error_p"><span class="tips_error"></span>'+data.msg+'</p>');
				$("#send_mobile_code").attr("disabled","disabled");
			}
		}, "json");*/
	});

	$("#mobile_code").focus(function(){
		$("#mobile_code_tips").html('<p><span class="tips"></span>请输入验证码</p>');
	});
	$("#mobile_code").blur(function(){
		var mobile_code=$("#mobile_code").val();
		var mobile=$("#mobile").val();
		if(!mobile_code){
			$('#mobile_code_tips').html('<p class="tips_error_p"><span class="tips_error"></span>请输入验证码</p>');
			return false;
		}
		var r=testMobile(mobile);
		if(r!=true){
			$('#mobile_tips').html('<p class="tips_error_p"><span class="tips_error"></span>'+r+'</p>');
			$("#send_mobile_code").attr("disabled","disabled");
			return false;
		}
		if(isMobile==false){
			$("#mobile_tips").html('<p class="tips_error_p"><span class="tips_error"></span>手机号码有误</p>');
			return false;
		}
		/*$.post("/ajax/resetpw.php", {"mobile_code":mobile_code,"mobile":mobile,"check":'true'},function(data){
			if(data.code=='1'){
				$('#mobile_code_tips').html('<p><span class="tips_right"></span></p>');
				isMobileYzm=true;
			}else{
				isMobileYzm=false;
				$('#mobile_code_tips').html('<p class="tips_error_p"><span class="tips_error"></span>'+data.msg+'</p>');
			}
		}, "json");*/
	});


	// 发送手机验证码 TODO：停用！无手机短信套餐，暂时停用
	$("#send_mobile_code").click(function(){

		layer.msg('功能完善中，暂时停用，请使用邮箱找回密码。');

		/*var mobile=$("#mobile").val();
		if(!isMobile){
			$('#send_mobile_code_tips').html('<p class="tips_error_p"><span class="tips_error"></span>请输入正确的手机号码</p>');
			return false;
		}
		$.post("/ajax/send_mobile_code_getpw.php", {"mobile":mobile},function(data){
			if(data.code=='1'){
				$("#send_mobile_code").attr("disabled","disabled");
				var endtime = new Date().getTime();
				endtime=endtime+60000;						
				$.cookie('endtime',endtime,{expires: 1}); 
				counttime();
			}else{
				isMobile=false;
				$('#send_mobile_code_tips').html('<p class="tips_error_p"><span class="tips_error"></span>'+data.msg+'</p>');
				$("#send_mobile_code").removeAttr("disabled");
			}
		}, "json");*/

	});

	// 手机密码提交
	$("#mobile_getpw_submit").unbind('click').click(function(){

		layer.msg('手机找回密码功能暂时下线，请通过邮箱找回密码', {
			time: 1000
		});

		if(!isMobile || !isMobileYzm){
			return false;
		}
		var mobile=$("#mobile").val();
		var mobile_code=$("#mobile_code").val();
		$.post("/ajax/resetpw.php", {"mobile": mobile,"mobile_code":mobile_code, "getpw_type":'mobile'},function(data){
			if(data.code=='1'){
				window.location.href="http://i.meishi.cc/resetpasswd.php?c="+data.msg;
			}else{
				switch (data.code) {
				    case -2:
				        $('#mobile_tips').html('<p  class="tips_error_p"><span class="tips_error"></span>'+data.msg+'</p>');
				        break;
				    case -3:
						$('#mobile_code_tips').html('<p  class="tips_error_p"><span class="tips_error"></span>'+data.msg+'</p>');
				        break;
				    default:
						alert(data.msg);    
				        break;
				}
			}
		}, "json");

	})


	$("#email_getpw_submit").unbind('click').click(function(){

		var email=$("#email").val();

		console.log(email);

		$.post("/mail/sendResetPwd", {"account": email, "accountType" : 1},function(data){
			console.log(data);
			if(data.code == 0){
				window.location.href= "/resetPwd_success.html?email=" + email;
			}else{
				switch (data.code) {
				    case -2:
				        $('#email_tips').html('<p  class="tips_error_p"><span class="tips_error"></span>'+data.msg+'</p>');
				        break;
				    default:
						alert(data.msg);    
				        break;
				}
			}
		}, "json");

	});

	// 重置密码提交
	$("#summitResetPwd").click(function () {

		// 重置密码参数
		var pt = $("#pt").val();		//token
		var pwd = $("#mm1").val();		//密码
		var confirmPwd = $("#mm2").val();		//确认密码

		if (pwd == null || pwd == "" || confirmPwd == null || confirmPwd == "") {
			$("#msg").html('<div class="status">密码不能为空</div>');
		} else if (pwd.length < 5 || pwd.length> 15) {
			$("#msg").html('<div class="status">密码长度大于5并且小于15位</div>');
		} else if (pwd != confirmPwd) {
			$("#msg").html('<div class="status">两次输入的密码不一致</div>');
		} else {
			// 按钮变为不可用，防止重复提交
			$("#summitResetPwd").attr("disabled", true)

			$.ajax({
				url: "/regist/resetPwd",
				type: "POST",
				data: {"pt": pt, "pwd": pwd, "confirmPwd": confirmPwd},
				dataType: "JSON",
				success: function (result) {

					if (result.code == 0) {
						// 成功
						$(".main.clearfix").html(
							'<div class="main_w clearfix">\n' +
							'\t\t\t<div class="" id="resetpw_sucess">\n' +
							'\t<img class="ok_u29" width="80px" src="/image/ok_u29.png"> <h1>恭喜，重设密码成功</h1>\n' +
							'<br>\n' +
							'\t<p class="login"><a href="/toLogin">马上登录</a></p> <br>\n' +
							'\t\t\t</div>\n' +
							'\n' +
							'\t\t</div>');
					} else {
						// 失败
						layer.confirm(result.msg,{
							btn: ['确定'],
							title: '提示'
							},
							function () {
								location.href = "/toLogin";
							}
						);
					}
				}
			})
		}


	});




	function counttime(){
		var endtime=$.cookie('endtime');
		var nowtime = new Date().getTime(); 				
		var youtime = endtime-nowtime;
		var seconds = Math.floor(youtime/1000);
		if(endtime<=nowtime){
		        $('#send_mobile_code').val("重新获取验证码");
				$("#send_mobile_code").removeAttr("disabled");
		}else{
		        $("#send_mobile_code").val("重新获取验证码("+seconds+")"); 
				$("#send_mobile_code").attr("disabled","disabled");
		}
		t=setTimeout(counttime,1000);
	};

	function checkcounttime(){
		var endtime=$.cookie('endtime');
        var nowtime = new Date().getTime();
		if(endtime<=nowtime){
			$('#send_mobile_code').val("免费获取验证码");
		}else{
			counttime();
		}
	}	
	checkcounttime();
	function testEmail(str){
		var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if(reg.test(str)){
			return true;
		}else{
			return false;
		}
	}
	function testMobile(mobile){
		mobile=Trim(mobile);
	    if(mobile.length == 0)
	     return '手机号不能为空';
	    var patrn = /(^13[0-9]{9}$|^15[0-9]{9}$|^18[0-9]{9}$|^17[0-9]{9}$|^14[0-9]{9}$)/;
	    if (!patrn.exec(mobile)) {
	        return '手机号码有误';
	    }else{
	    	return true;    	
	    }	    
	}

	

	function Trim(string){
		return string.replace(/(^\s*)|(\s*$)|(\n)/g, "");
	}
});




jQuery.cookie = function(name, value, options) {
          if (typeof value != 'undefined') {
                    options = options || {};
                    if (value === null) {
                              value = '';
                              options = $.extend({}, options);
                              options.expires = -1;
                    }
                    var expires = '';
                    if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
                              var date;
                              if (typeof options.expires == 'number') {
                                        date = new Date();
                                        date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
                              } else {
                                        date = options.expires;
                              }
                              expires = '; expires=' + date.toUTCString();
                    }
                    var path = options.path ? '; path=' + (options.path) : '';
                    var domain = options.domain ? '; domain=' + (options.domain) : '';
                    var secure = options.secure ? '; secure' : '';
                    document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
          } else {
                    var cookieValue = null;
                    if (document.cookie && document.cookie != '') {
                              var cookies = document.cookie.split(';');
                              for (var i = 0; i < cookies.length; i++) {
                                        var cookie = jQuery.trim(cookies[i]);
                                        if (cookie.substring(0, name.length + 1) == (name + '=')) {
                                                  cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                                                  break;
                                        }
                              }
                    }
                    return cookieValue;
          }
};