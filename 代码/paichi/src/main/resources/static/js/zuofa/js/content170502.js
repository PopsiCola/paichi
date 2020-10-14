document.domain = "localhost";
$(function(){
	var news_id = $("#news_id").val();
	var ntitle = $("#news_title").val();
	var from_search = $("#from_search").val();
	var title_length = $(".cp_main_info_w .title a").html().length;
	if(title_length <= 8){
	}else if(title_length >8 && title_length <= 11){
		$(".cp_main_info_w .info1 .title").css({"font-size":"24px"});
	}else if(title_length >11 ){
		$(".cp_main_info_w .info1 .title").css({"font-size":"18px"});
	}
	if(title_length >16){
		$(".cp_main_info_w .info1 .title").css({"line-height":"22px"});
	}
	$("#addToFav_con").click(function(){
		Alert("", "iframe:https://i.meishi.cc/ajax/add_caidan_new.php?id=" + news_id + "&title=" + ntitle + "", "600", "415", "false", "", "true", "img");
	});
	$(".similar_cp img,#listtyle1_w img,.userupload_box img").lazyload({
		placeholder :"https://css.meishij.net/n/images/grey.gif",
		effect:"fadeIn"
	});
	get_onclick(news_id,1,from_search);
	var h1 = $("#fixedad").offset().top;
	var h2;
	var eh = $("#fixedad").height();
	var h4 =($("#con_xscp").length > 0 && $("#con_xscp").offset().top) || $("#con_xscp2").offset().top;
	$(window).scroll(function(){
		h2 = $(window).scrollTop();
		var h3 = h1-h2;
		if(h3<0){
			if((h4-h2)>eh){
				$("#fixedad").css({"position":"fixed","top":"0px"});
			}else if((h4-h2)<eh){
				$("#fixedad").css({"position":"absolute","top":"auto","bottom":"0px"});
			}
		}else if(h3>0){
			$("#fixedad").css({"position":"relative","top":"0px"});
		}
	});
	$(".userupload_box li").mouseenter(function(){
		var _this = $(this);
		_this.find(".zan").show();
	});
	$(".userupload_box li").mouseleave(function(){
		var _this = $(this);
		_this.find(".zan").hide();
	});
	$(".userupload_box  .zan").click(function(){
		var _this = $(this);
		var cid = _this.attr('value');
		var flag = _this.hasClass("zaned");
		var zan;
		if (flag) {
			zan = "zaned";
		} else {
			zan = "zan";
		}
		var js = document.createElement("script");
		js.language = "javascript";
		js.src = "https://reply.meishij.net/ajax/dish_zan.php?act="+zan+"&cid="+cid+"&_" +(new Date()).getTime();
		document.body.appendChild(js)
	});
	$("#erweima_t").hover(function(){
		$(this).addClass("erweima_t_hover");
		$("#ewmbox").show();
	},function(){
		$(this).removeClass("erweima_t_hover");
		$("#ewmbox").hide();
	});
	$("#ewmbox").hover(function(){
		$(this).show();
		$("#erweima_t").addClass("erweima_t_hover");
	},function(){
		$(this).hide();
		$("#erweima_t").removeClass("erweima_t_hover");
	});
	var hl = $(".cp_body_left").height();
	var hr = $(".cp_body_right").height();
	if(hr > hl){
		$(".cp_body_left").height(hr);
	}
	$("#cp_comlist_tab1").click(function(){
		$("#comment_order").val('new');
		$(this).siblings().removeClass("current");
		$(this).addClass("current");
		get_more_pl(news_id,1);
	});
	$("#cp_comlist_tab2").click(function(){
		$("#comment_order").val('hot');
		$(this).siblings().removeClass("current");
		$(this).addClass("current");
		get_more_pl(news_id,1);
	});
	$(".zzzzan").live("click",function(){
		var _this = $(this);
		var news_id=$("#news_id").val();
		var plid=_this.attr('plid');

		if(_this.children('em').attr("class")=='con_comlist_zanbtn'){
			var zan=0;
		}else if(_this.children('em').attr("class")=='con_comlist_zanbtned'){
			var zan=1;
		}else{
			alert('出错了^_^');
			return false;
		}
		var news_pl_zan_url="https://reply.meishij.net/ajax/news_pl_zan.php";
		var cjs = document.createElement("script");
		cjs.language = "javascript";
		cjs.src = news_pl_zan_url+"?news_id="+news_id+"&plid="+plid+"&zan=" +zan;
		document.body.appendChild(cjs);
	});
});
function saveComment(obj){
	$(obj).ajaxSubmit(function(data) {
		alert(data);
	});
	return false;
}
function submit_reply(r_id) {
	var saytext = $('#text_' + r_id).val();
	var reply_id = $('#reply_id_' + r_id).val();
    var news_id = $('#news_id').val();
	if(saytext) {
		$('#text_' + r_id).val('');
        $.getScript('https://reply.meishij.net/pl/news.php?c=' + encodeURIComponent(saytext) + '&nid=' + news_id + '&rid=' + reply_id + '&_' +(new Date()).getTime(),function() {})	} else {
		alert('请输入内容~');
	}
}
function submit_comment() {
	var saytext = $('#cp_com_textarea').val();
	var news_id = $('#news_id').val();
	var cp_com_type = $('#cp_com_type').val();
	if(saytext) {
		$('#cp_com_textarea').val('');
		$('#cp_com_textarea').keyup();
		$('#cp_com_type').val(0);
		$.getScript('https://reply.meishij.net/pl/news.php?c=' + encodeURIComponent(saytext) + '&nid=' + news_id + '&pt=' + cp_com_type + '&_' +(new Date()).getTime(),function() {})
	} else {
		alert('请输入内容~');
	}
}
function get_more_pl(id,page){
	$('#newspls').html("<p style=\"text-align:center;padding:30px 0;\"><img src=\"http://www.meishij.net/static/images/loading.gif\" border=0 /></p>");
	location.href = '#newspls';
	var orderby= $("#comment_order").val();
	if(orderby!='new' && orderby!='hot'){
		orderby='new';
	}

	$.post('/ajax/get_more_pl_n.php?id=' + id + '&page=' + page, {'orderby':orderby},
	function(data) {
		if(data != '') {
			$('#newspls').html(data);
			bind_re();
		}
	})
}
function add_fav(id) {
	$.getScript('https://i.meishi.cc/ajax/add_nfav.php?in=1&obj_id=' + id + '&_' +(new Date()).getTime(),function() {})
}
function get_onclick(id, classid, from_search) {
	var js = document.createElement("script");
	js.language = "javascript";
	js.src = "https://click.meishij.net/pl/click.php?from_search="+from_search+"&classid=" + classid + "&id=" + id + "&addclick=1&_" +(new Date()).getTime();
	document.body.appendChild(js)
}
function canshu_error(){
	alert('参数不合法!');
}