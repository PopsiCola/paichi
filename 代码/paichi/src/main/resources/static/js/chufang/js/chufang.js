$(function(){

    // 展示菜谱数据
    recipeList(1);
    // 展示食材列表
    materialsList();

    // 最新 | 最热 按钮
    $("#zuixin").click(function () {

        $("#zuire").removeClass('current');
        $("#zuixin").addClass('current');

        recipeList(1);
    });
    $("#zuire").click(function () {

        $("#zuixin").removeClass('current');
        $("#zuire").addClass('current');

        recipeList(2);
    });
});

// 食材列表
function materialsList() {
    $.ajax({
        type: "POST",
        url: ctx + "materials/queryIngredients",
        dataType: "json",       //服务端返回的数据格式
        success: function (result) {
            // 查询成功回调函数

            var data = result.data;

            // 直接展示的数据，显示前十条
            var materialsHtml = '';

            // 隐藏的数据
            var othersMaerialsHtml = '';

            if (data.length > 10) {
                for (let i = 0; i < 10; i++) {
                    materialsHtml +=
                        '<a href="/">\n' +
                        '\t<span class="yjtip">火热</span>\n' +
                        '\t<strong>'+ data[i].MAINMATERIALS +'</strong>\n' +
                        '\t<em>'+ data[i].MATERIALSNUMBER +'</em>\n' +
                        '</a>\n';
                }

                for (let j = 10; j < data.length; j++) {
                    othersMaerialsHtml +=
                        '<a href="/">\n' +
                        '\t<strong>'+ data[j].MAINMATERIALS +'</strong>\n' +
                        '\t<em>'+ data[j].MATERIALSNUMBER +'</em>\n' +
                        '</a>\n';
                }
                materialsHtml +=
                    '<div class="others">' +
                    othersMaerialsHtml +
                    '</div>\n';
            } else {
                for (let i = 0; i < data.length; i++) {
                    materialsHtml +=
                        '<a href="/">\n' +
                        '\t<span class="yjtip">火热</span>\n' +
                        '\t<strong>'+ data[i].MAINMATERIALS +'</strong>\n' +
                        '\t<em>'+ data[i].MATERIALSNUMBER +'</em>\n' +
                        '</a>\n';
                }
            }

            // 拼接到页面
            $(".clearfix .row2").html(materialsHtml);
        },
        fail: function (result) {
            // 查询失败回调函数
            console.log(result);
        }
    });
}

function recipeList(type) {

    //默认查询所有数据
    $.ajax({
        type: 'POST',
        url: ctx + "recipe/searchRecipe",
        dataType: "JSON",
        data: {"recipeType": parseInt(type), "limit": 18, "current": 1},
        success: function (result) {
            // 查询成功回调函数

            // 分页  每页显示 3 * 6
            layui.use(['laypage', 'layer'], function(){
                var laypage = layui.laypage
                    ,layer = layui.layer;

                //完整功能
                laypage.render({
                    elem: 'page_list'
                    ,count: result.data.recipeCount     // 总条数
                    ,theme: '#ff3232'
                    ,limit: 18  // 固定每页显示18个
                    ,groups: 3  // 连续出现页码的个数
                    ,layout: ['prev', 'page', 'next', 'refresh','count', 'skip']
                    ,jump: function(obj, first){

                        // 首次不执行
                        if (!first) {
                            $.ajax({
                                type: "POST",
                                url: ctx + "recipe/searchRecipe",
                                data: {"limit": obj.limit, "current": obj.curr, "recipeType": parseInt(type)},    //四个空格的缩进
                                dataType: "json",                   //服务端返回的数据格式
                                success: function (result) {
                                    // 查询成功回调函数

                                    var data = result.data;

                                    let newsHeadHtml = recipePageList(data);

                                    $("#listtyle1_list").html(newsHeadHtml);

                                },
                                fail: function (result) {
                                    // 查询失败回调函数
                                    console.log(result);
                                }
                            });
                        } else {
                            // 只有初始化执行
                            let newsHeadHtml = recipePageList(result.data);

                            $("#listtyle1_list").html(newsHeadHtml);

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
}

// 展示菜谱列表  data：recipe集合
function recipePageList(data) {
    var newsHeadHtml = '';
    for (let i = 0; i < data.recipeList.length; i++) {
        newsHeadHtml += '<div class="listtyle1">\n' +
            '<a target="_blank" href="/recipe/zuofa?recipeId='+ data.recipeList[i].RECIPE_ID +'" title="'+ data.recipeList[i].RECIPE_NAME +'" class="big">\n' +
            '<img class="img" alt="'+ data.recipeList[i].RECIPE_NAME +'" src="'+ data.recipeList[i].RECIPE_IMG +'">\n' +
            '<div class="i_w">\n' +
            '<div class="i" style="margin-top: 0px;">\n' +
            '<div class="c1"><strong>'+ data.recipeList[i].RECIPE_NAME +'</strong><span>0 评论  ' + data.recipeList[i].POPULARITY + ' 人气</span><em>'+ data.recipeList[i].USERNAME +'</em></div>\n' +
            '<div class="c2"><ul><li class="li1">'+ data.recipeList[i].STEPNUMBER +'步 / 大概'+ data.recipeList[i].COOK_TIME +'分钟</li><li class="li2">'+ data.recipeList[i].CRAFTNAME +' / '+ data.recipeList[i].TASTENAME +'</li></ul></div></div></div>\n' +
            '<strong class="gx"><span>'+ (data.recipeList[i].EFFECTNAME ==null ? "暂无" : data.recipeList[i].EFFECTNAME) +'</span></strong>' +
            '</a>\n' +
            '</div>';
    }

    return newsHeadHtml;
}