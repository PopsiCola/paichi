var sliderStart = false;
var x,y; // 鼠标最初位置
var back_width = 300;
var mask_width = 50;
var ly;
var lx;
var backImg;
var frontImg;
var yArray;

$(function(){
    /*// 初始化验证码
    $.ajax({
        type : "get",// 请求方式
        url : "/getImgInfo",// 发送请求地址
        dataType : "json",
        async : false,
        // 请求成功后的回调函数有两个参数
        success : function(data) {
            lx = data.xLocation;
            ly = data.yLocation;
            backImg = "/createVerfiyImg/" + data.backName;
            frontImg = "/createVerfiyImg/" + data.markName;
            initImg(backImg, frontImg, ly);
            initMovement()
        },
        error : function(){
            alert("error")
        }
    });*/
});

// 拼图验证码用---计算和
function sum(x1, x2){
    return x1 + x2
}

// 拼图验证码用---初始化图片
function initImg(backgroundImg, markImg, yLocation){
    $('#back_img').attr('src', backgroundImg);
    $('#before_img').attr('src', markImg);
    $('.slide_img_mark').css('margin-top', yLocation);
}

// 拼图验证码用---设置回调
function initMovement(){
    $('.slider_arror')[0].addEventListener('mousedown', sliderPush);
    window.addEventListener('mousemove', sliderDrug);
    window.addEventListener('mouseup', sliderEnd);
}

// 拼图验证码用---按下滑动按钮回调方法
function sliderPush(e){
    sliderStart = true
    x = e.clientX || e.touches[0].clientX;
    y = e.clientY || e.touches[0].clientY;
    $('.slider_tip').hide();
    $('.slider_mask').css('background-color', '#deee97');
    $('#arror_icon').prop('src', '/verifyImage/icon/right_arror.png');


    yArray = []
}

// 拼图验证码用---开始滑动回调方法， 改变arror位置和mask大小
function sliderDrug(e){
    if (!sliderStart)
        return false;

    // 获取鼠标移动位置
    const eventX = e.clientX || e.touches[0].clientX;
    const eventY = e.clientY || e.touches[0].clientY;
    const moveX = eventX - x;
    const moveY = eventY - y;

    // 存放y轴坐标，便于后续判断
    yArray.push(moveY);

    // 确保边界
    if(moveX < 0|| moveX + parseInt(mask_width) > back_width)
        return false;

    // 改变slider_arror的位置和mask大小
    $('.slider_arror').css('margin-left', moveX);
    $('.slider_mask').css('width', moveX);
    $('.slide_img_mark').css('margin-left', moveX);
}

// 拼图验证码用---结束时进行判断回调方法
function sliderEnd(e){
    if (!sliderStart)
        return false;

    sliderStart = false;
    finalX = $('.slide_img_mark').css('margin-left');
    // 2像素的误差
    finalX = finalX.toString().slice(0, -2);
    if(finalX < (lx - 2) || finalX > (lx + 2)){
        failed()
    } else if(yVerify()){
        console.log("verify success");
        success();
    } else{
        console.log("verify failed");
        failed();
    }
}

// 拼图验证码用---检测y轴变化
function yVerify(){
    if(yArray.length < 1)
        return false;
    var sumY = yArray.reduce(sum);
    var average = sumY / yArray.length;
    // 简单看看，y没变过算不对劲
    var same = true;
    for (let i = 0;i < yArray.length; i++){
        if(yArray[i] != average)
            same = false
    }
    return !same
}

// 拼图验证码用---验证成功回调方法
function success(){
    $('#arror_icon').prop('src', '/verifyImage/icon/green_correct.png');
    $('.slider_mask').css('background-color', '#79e77e');
    layer.msg('验证成功',
        {
            time: 500, //500ms后自动关闭
        },
        function () {

            //发送邮箱验证码
            doPyzmNext();
            //重新拼图验证
            $('#back_img').attr('src', '')
            $('#before_img').attr('src', '');
            reset();
    });
    //关闭拼图验证码
    $("#yzm_blackbg").fadeOut();
    $("#yzm_yzmbox").fadeOut();

}

// 拼图验证码用---失败重置
function failed(){

    $('#arror_icon').prop('src', '/verifyImage/icon/red_error.png');
    $('.slider_mask').css('background-color', '#e73c4a');
    layer.msg('验证失败，请重新验证！', {
        time: 1000, //1000ms后自动关闭
    });
    setTimeout(reset, 1000);
}

// 拼图验证码用---修改位置和重置颜色
function reset(){

    // 初始化验证码
    $('.slider_mask').css('background-color', '#deee97');
    $('.slider_arror').css('margin-left', 0);
    $('.slider_mask').css('width', 0);
    $('.slide_img_mark').css('margin-left', 0);
    $('#arror_icon').prop('src', '/verifyImage/icon/right_arror.png');
    $('.slider_tip').show();

}
