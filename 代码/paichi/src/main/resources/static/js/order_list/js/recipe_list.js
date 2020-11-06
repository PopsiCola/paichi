$(function(){
    // 美食菜单初始化
    recipe_orders();
});

// 美食菜单
function recipe_orders() {
    let data = {};
    data['recipeType'] = 1;
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
    for (let i = 0; i < data.recipeOrders.length; i++) {
        ordersHtml += '<div class="cdlist_item_style1 clearfix">\n' +
            '        <h3><a href="ordersDetail">我的菜单</a></h3>\n' +
            '    <span class="cpcount"><em>'+ data.recipeOrders[i].ordersCount +'</em>篇菜谱</span>\n' +
            '    <div class="info">\n' +
            '        <dl>\n' +
            '        <dd><strong class="tag">'+ data.recipeOrders[i].recipes[0].timestamp +'</strong></dd>\n' +
            '    </dl>\n' +
            '    <a class="author" href="https://i.meishi.cc/cook.php?id=12947507">\n' +
            '        <img src="'+ data.recipeOrders[i].userIcon +'">\n' +
            '        <strong>'+ data.recipeOrders[i].userName +'</strong>\n' +
            '        </a>\n' +
            '        </div>\n' +
            '        <ul class="cplist">\n';

            for (let j=0; j<data.recipeOrders[i].recipes.length; j++) {
                ordersHtml += '<li><a href="/recipeOrders/recipeOrderDetail?uid='+ data.recipeOrders[i].userId +'">\n' +
                    '        <img src="'+ data.recipeOrders[i].recipes[j].recipeImg +'"><h4>'+ data.recipeOrders[i].recipes[j].recipeName +'</h4></a></li>\n';
            }
        ordersHtml += '    </ul>\n' +
            '    </div>';
    }

    return ordersHtml;
}