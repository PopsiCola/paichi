$(function(){

    // 用户菜单详情初始化
    user_orders();
});

// 初始化用户菜单页面
function user_orders() {

    let uid = $(".followTa").attr('uid');
    let data = {"userId" : uid};
    layui.use(['laypage', 'layer'], function() {
            var laypage = layui.laypage
                , layer = layui.layer;

            $.ajax({
            type: 'POST',
            url: ctx + "recipeOrders/userOrderDetail",
            dataType: "JSON",
            data: data,
            success: function (result) {

                if (result.code == 0) {
                    console.log(result);
                    let data = result.data;
                    // title
                    $("title").text(''+ data.userOrdersTitle +'_'+ data.userOrder.userName +'的菜单–美食菜单【PaiChi】');
                    $("#order_title").text(data.userOrdersTitle);
                    $("#order_content").text(data.userOrdersContent);
                    $("#last_update_time").text('最后修改：' + data.lastUpdateDate);
                    $("#user_icon_order").attr('src', data.userOrder.userIcon);
                    $("#user_name_order").text(data.userOrder.userName);
                    $("#username").value(data.userOrder.userName);
                    $("#recipesCount").text("菜谱："+ data.recipes.length +" / 关注：0 / 粉丝：1");


                } else {
                    layer.msg(result.msg, {
                        time:1000
                    });
                }

            },
            error: function (result) {
                layer.msg(result.msg, {
                    time: 1000
                });
            }
        });
    });

}
