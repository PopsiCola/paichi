$(function(){

    // 展示菜谱数据
    recipeList();
    // 展示食材列表
    materialsList();

    // 最新 | 最热 按钮
    $("#zuixin").click(function () {

        $("#zuire").removeClass('current');
        $("#zuixin").addClass('current');

        recipeList();
    });
    $("#zuire").click(function () {

        $("#zuixin").removeClass('current');
        $("#zuire").addClass('current');

        recipeList();
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
                        '<a onclick="chooseTerm(this.id, 1)" id="'+ data[i].MAINMATERIALS +'">' +
                        '\t<span class="yjtip">火热</span>\n' +
                        '\t<strong>'+ data[i].MAINMATERIALS +'</strong>\n' +
                        '\t<em>'+ data[i].MATERIALSNUMBER +'</em>\n' +
                        '</a>\n';
                }

                for (let j = 10; j < data.length; j++) {
                    othersMaerialsHtml +=
                        '<a onclick="chooseTerm(this.id, 1)" id="'+ data[j].MAINMATERIALS +'">' +
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
                        '<a onclick="chooseTerm(this.id, 1)" id="'+ data[i].MAINMATERIALS +'">' +
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

// 删除筛选条件并更新列表
function removeTerm(id) {
    // 移除选中的样式
    let removeCSS = $("#" + id + "").text();
    $("#"+ removeCSS +"").removeClass();
    // 删除筛选条件列表
    let element = document.getElementById(id);
    element.parentElement.removeChild(element);
    // 查询条件列表数量，列表中没有条件时，移除列表样式
    if ($("#termList a").size() == 0) {
        $("#termList").removeClass();
    }
    // 更新列表
    recipeList();

}

// 添加筛选条件。type：筛选类型 1：食材筛选。2：难度筛选。3：工艺筛选。4：口味筛选。5：时间筛选。
function chooseTerm(data, type) {
    // 新添加的筛选条件
    var termHtml = '';

    switch (type) {
        case 1:
            // 获取条件列表，一类条件只能有一个，需要删除上一个
            var removeId = $("#materialTerm").text();
            // 查看是否已存在食材筛选条件，有则删除
            if ($("#materialTerm").text().length > 0) {
                var element = document.getElementById('materialTerm');
                element.parentElement.removeChild(element);
            }

            termHtml = '<a href="JavaScript:void(0);" onclick="removeTerm(this.id);" id="materialTerm">' + data + '</a>';
            break;
        case 2:
            // 获取条件列表，一类条件只能有一个，需要删除上一个
            var removeId = $("#diffcultTerm").text();
            // 查看是否已存在难度筛选条件，有则删除
            if ($("#diffcultTerm").text().length > 0) {
                var element = document.getElementById('diffcultTerm');
                element.parentElement.removeChild(element);
            }

            termHtml = '<a href="JavaScript:void(0);" onclick="removeTerm(this.id);" id="diffcultTerm">' + data + '</a>';
            break;
        case 3:
            // 获取条件列表，一类条件只能有一个，需要删除上一个
            var removeId = $("#craftTerm").text();
            // 查看是否已存在工艺筛选条件，有则删除
            if ($("#craftTerm").text().length > 0) {
                var element = document.getElementById('craftTerm');
                element.parentElement.removeChild(element);
            }

            termHtml = '<a href="JavaScript:void(0);" onclick="removeTerm(this.id);" id="craftTerm">' + data + '</a>';
            break;
        case 4:
            // 获取条件列表，一类条件只能有一个，需要删除上一个
            var removeId = $("#tasteTerm").text();
            // 查看是否已存在口味筛选条件，有则删除
            if ($("#tasteTerm").text().length > 0) {
                var element = document.getElementById('tasteTerm');
                element.parentElement.removeChild(element);
            }

            termHtml = '<a href="JavaScript:void(0);" onclick="removeTerm(this.id);" id="tasteTerm">' + data + '</a>';
            break;
        default:
            // 删除"<"符号
            data = data.replace('<', '');
            // 获取条件列表，一类条件只能有一个，需要删除上一个
            var removeId = $("#cookTimeTerm").text();
            // 查看是否已存在时间筛选条件，有则删除
            if ($("#cookTimeTerm").text().length > 0) {
                var element = document.getElementById('cookTimeTerm');
                element.parentElement.removeChild(element);
            }

            termHtml = '<a href="JavaScript:void(0);" onclick="removeTerm(this.id);" id="cookTimeTerm">' + data + '</a>';
            break;
    }

    // 删除之前存在的样式，并添加新选择的样式
    $("#"+ removeId +"").removeClass();
    $("#"+ data +"").addClass('chosed');

    var termList = $("#termList").html();

    $("#termList").addClass("chosedbox clearfix");
    $("#termList").html(termList + termHtml);

    // 每次选择筛选条件后都要根据条件重新查询食谱
    recipeList();
}

// 菜谱列表  type：筛选类型，1 最新，2 最热。data：筛选条件
function recipeList() {
    var type = $("#zuixin").attr('class');
    type = type == 'current'? 1 : 2;
    // 筛选条件 id 以及 text，根据条件请求食谱数据
    var aElmentLen = $("#termList a").size();

    var data = {};
    for (let i = 0; i < aElmentLen; i++) {
        let id = $("#termList a").eq(i).attr('id');
        let text = $("#termList a").eq(i).text();
        data[id] = text;
    }

    data['recipeType'] = parseInt(type);
    data['limit'] = 18;
    data['current'] = 1;

    //默认查询所有数据
    $.ajax({
        type: 'POST',
        url: ctx + "recipe/searchRecipe",
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
                    ,count: result.data.recipeCount     // 总条数
                    ,theme: '#ff3232'
                    ,limit: 18  // 固定每页显示18个
                    ,groups: 3  // 连续出现页码的个数
                    ,layout: ['prev', 'page', 'next', 'refresh','count', 'skip']
                    ,jump: function(obj, first){
                        data['limit'] = obj.limit;
                        data['current'] = obj.curr;

                        // 首次不执行
                        if (!first) {
                            $.ajax({
                                type: "POST",
                                url: ctx + "recipe/searchRecipe",
                                data: data,    //四个空格的缩进
                                dataType: "json",                   //服务端返回的数据格式
                                success: function (result) {
                                    // 查询成功回调函数

                                    var data = result.data;
                                    let newsHeadHtml = recipePageList(data);
                                    $("#listtyle1_list").html(newsHeadHtml);
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
                            let newsHeadHtml = recipePageList(result.data);

                            $("#listtyle1_list").html(newsHeadHtml);
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
            '<div class="c2"><ul><li class="li1">'+ data.recipeList[i].STEPNUMBER +'步 / '+ (data.recipeList[i].COOK_TIME != null ? '大概'+ data.recipeList[i].COOK_TIME +'分钟' : '未知') + '</li><li class="li2">'+ data.recipeList[i].CRAFTNAME +' / '+ data.recipeList[i].TASTENAME +'</li></ul></div></div></div>\n' +
            '<strong class="gx"><span>'+ (data.recipeList[i].EFFECTNAME ==null ? "暂无" : data.recipeList[i].EFFECTNAME) +'</span></strong>' +
            '</a>\n' +
            '</div>';
    }

    return newsHeadHtml;
}