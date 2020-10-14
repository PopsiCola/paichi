$(function(){

    //默认查询所有数据
    $.ajax({
        type: 'POST',
        url: ctx + "recipe/searchRecipe",
        dataType: "JSON",
        data: JSON.stringify({"limit": 18, "current": 0}),
        contentType: "application/json",
        success: function (result) {
            // 查询成功回调函数
            console.log(result);

            // 分页  每页显示 3 * 6
            layui.use(['laypage', 'layer'], function(){
                var laypage = layui.laypage
                    ,layer = layui.layer;

                //完整功能
                laypage.render({
                     elem: 'page_list'
                    ,count: 100
                    ,theme: '#ff3232'
                    ,limit: 18  //固定每页显示18个
                    ,layout: ['prev', 'page', 'next', 'refresh','count', 'skip']
                    ,jump: function(obj, first){
                        console.log(obj);

                        // 传递参数：当前页以及每页显示条数
                        var data = {"limit": obj.limit, "current": obj.curr};
;
                        console.log(JSON.stringify(data,null, 4));

                        if (!first) {
                            $.ajax({
                                type: "POST",
                                url: ctx + "recipe/searchRecipe",
                                data: {"limit": obj.limit, "current": obj.curr},    //四个空格的缩进
                                dataType: "json",                   //服务端返回的数据格式
                                // contentType: "application/json",    //前端请求的数据格式
                                success: function (result) {
                                    // 查询成功回调函数
                                    console.log(result);
                                },
                                fail: function (result) {
                                    // 查询失败回调函数
                                    console.log(result);
                                }
                            });
                        }
                    }
                });
            });
        },
        fail: function (result) {
            // 查询失败回调函数
            console.log(result);
        }
    });


});