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
                    $("#username").val(data.userOrder.userName);
                    $("#recipesCount").text("菜谱："+ data.recipes.length +" / 关注：0 / 粉丝：1");

                    // 底部食谱列表
                    var recipesCount = data.recipesCount;
                    var html = '';
                    for (let i = 0; i < recipesCount; i++) {
                        let listtyle1 = i%3 == 0 ? 'listtyle1 ml0' : 'listtyle1';

                        var htmlEffect = '';
                        for (let j = 0; j < data.recipes[i].effects.length; j++) {
                            // 数量大于一个时，在前面加顿号分开
                            if (j>0) {
                                htmlEffect += '、';
                            }
                            htmlEffect += data.recipes[i].effects[j].effectName;
                        }
                        // 判断食谱是否有功效，如果没有功效，则不展示
                        if (htmlEffect.trim() != '') {
                            htmlEffect = '<div class="gx2"><span>'+ htmlEffect +'</span></div>';
                        } else {
                            htmlEffect = '<div class="gx2"><span>暂无功效</span></div>';
                        }

                        html += '<div class="'+ listtyle1 +'">\n' +
                            '                        <div class="img"><a target="_blank"\n' +
                            '                                            th:href="@{/recipe/zuofa?recipeId='+ data.recipes[i].recipeId +'}"><img\n' +
                            '                                src="'+ data.recipes[i].recipeImg +'" width="232" height="232"></a><strong\n' +
                            '                                class="gx"><span>防癌</span></strong></div>\n' +
                            '                        <div class="info2">\n' +
                            '                            <a target="_blank" th:href="@{/recipe/zuofa?recipeId='+ data.recipes[i].recipeId +'}"\n' +
                            '                               class="img"></a>\n' +
                            '                            <div class="info2_c">\n' +
                            '                                <a target="_blank" th:href="@{/recipe/zuofa?recipeId='+ data.recipes[i].recipeId +'}"\n' +
                            '                                   class="link_cp">\n' +
                            '                                    <ul>\n' +
                            '                                        <li class="gy">'+ data.recipes[i].craft.craftName +'</li>\n' +
                            '                                        <li class="kw">'+ data.recipes[i].taste.tasteName +'</li>\n' +
                            '                                        <li class="nd">'+ data.recipes[i].difficulty +'</li>\n' +
                            '                                        <li class="sj">'+ data.recipes[i].cookTime +'</li>\n' +
                            // '                                        <li>>15分钟</li>\n' +
                            '                                    </ul>\n' +
                            '                                </a>\n' +
                            '                                '+ htmlEffect +'\n' +
                            '                            </div>\n' +
                            '                        </div>\n' +
                            '                        <div class="info1">\n' +
                            '                            <h3><a target="_blank" th:href="@{/recipe/zuofa?recipeId='+ data.recipes[i].recipeId +'}">'+ data.recipes[i].recipeName +'</a>\n' +
                            '                            </h3>\n' +
                            '                            <div class="d1"><span>73 评论</span><span>'+ (data.recipes[i].peopleNumber == null ? 0 : data.recipes[i].peopleNumber) +' 人气</span></div>\n' +
                            '                            <a target="_blank" class="author" href="/">'+ data.recipes[i].user.userName +'</a>\n' +
                            '                        </div>\n' +
                            '                    </div>';
                    }
                    // 展示食谱列表
                    $("#listtyle1_list").html(html);

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
