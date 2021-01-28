$(function(){
    // 美食菜单初始化
    recipe_orders(1);

    // 监听最热、最新点击
    $("#zuixin").click(function (e) {
        $("#zuire").removeClass('current');
        $("#zuixin").addClass('current');
        // 最新
        recipe_orders(1);
    });
    $("#zuire").click(function (e) {

        $("#zuixin").removeClass('current');
        $("#zuire").addClass('current');
        // 最热
        recipe_orders(2);
    });
});

// 美食菜单 recipeType：1:最新，2：最热
function recipe_orders(recipeType) {
    let data = {};
    data['recipeType'] = recipeType;
    data['limit'] = 10;
    data['current'] = 1;

    $.ajax({
        type: 'POST',
        url: ctx + "recipe/recipe_orders",
        dataType: "JSON",
        data: data,
        success: function (result) {
            // 分页  每页显示 3 * 6
            layui.use(['laypage', 'layer'], function(){
                var laypage = layui.laypage
                    ,layer = layui.layer;

                //完整功能
                laypage.render({
                    elem: 'page_list'
                    ,count: result.data.ordersCount     // 总条数
                    ,theme: '#ff3232'
                    ,limit: 10  // 固定每页显示10个
                    ,groups: 3  // 连续出现页码的个数
                    ,layout: ['prev', 'page', 'next', 'refresh','count', 'skip']
                    ,jump: function(obj, first){
                        data['limit'] = obj.limit;
                        data['current'] = obj.curr;

                        // 首次不执行
                        if (!first) {
                            $.ajax({
                                type: "POST",
                                url: ctx + "recipe/recipe_orders",
                                data: data,    //四个空格的缩进
                                dataType: "json",                   //服务端返回的数据格式
                                success: function (result) {
                                    // 查询成功回调函数

                                    var data = result.data;
                                    let ordersHtml = recipeOrdersHtml(data);
                                    $(".cdlist_main").html(ordersHtml);
                                },
                                fail: function (result) {
                                    // 查询失败回调函数
                                    layer.msg(result.data.msg,{
                                        time: 1000
                                    });
                                }
                            });
                        } else {
                            // 只有初始化执行
                            let ordersHtml = recipeOrdersHtml(result.data);
                            $(".cdlist_main").html(ordersHtml);
                        }
                    }
                });
            });
        },
        fail: function (result) {
            // 查询失败回调函数

            layer.msg(result.data.msg,{
                time: 1000
            });
        }
    });
}

// 拼接菜单html
function recipeOrdersHtml(data) {

    var ordersHtml = '';
    for (let i = 0; i<data.recipeOrders.length; i++) {
        let reciepOrders = data.recipeOrders[i];
        ordersHtml += '<div class="cdlist_item_style1 clearfix">\n' +
            '        <h3><a href="/recipeOrders/recipeOrderDetail?uid='+ reciepOrders.userOrder.userId +'">'+ reciepOrders.userOrdersTitle +'</a></h3>\n' +
            '    <span class="cpcount"><em>'+ reciepOrders.recipesCount +'</em>篇菜谱</span>\n' +
            '    <div class="info">\n' +
            '        <dl>\n' +
            '        <dd><strong class="tag">'+ reciepOrders.lastUpdateDate +'</strong></dd>\n' +
            '    </dl>\n' +
            '    <a class="author" href="https://i.meishi.cc/cook.php?id='+ reciepOrders.userOrder.userId +'">\n' +
            '        <img src="'+ reciepOrders.userOrder.userIcon +'">\n' +
            '        <strong>'+ reciepOrders.userOrder.userName +'</strong>\n' +
            '        </a>\n' +
            '        </div>\n' +
            '        <ul class="cplist">\n';

            // 每篇菜单最多只显示5中菜品
            let count = reciepOrders.recipesCount > 5 ? 5 : reciepOrders.recipesCount;
            for (let j=0; j<count; j++) {
                ordersHtml += '<li><a href="/recipeOrders/recipeOrderDetail?uid='+ reciepOrders.userOrder.userId +'">\n' +
                    '        <img src="'+ reciepOrders.recipes[j].recipeImg +'"><h4>'+ reciepOrders.recipes[j].recipeName +'</h4></a></li>\n';
            }
        ordersHtml += '    </ul>\n' +
            '    </div>';
    }

    return ordersHtml;
}