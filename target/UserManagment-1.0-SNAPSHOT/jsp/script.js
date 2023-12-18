var data = [
    {
        "xh":"1",
        "yhxm":"张三",
        "yhzh":"120001001",
        "yhbm":"立案庭",
        "yhxb":"男",
        "pxh":"1",
        "sfjy":"是"
    },

    {
        "xh":"2",
        "yhxm":"李四",
        "yhzh":"120001002",
        "yhbm":"业务庭",
        "yhxb":"男",
        "pxh":"2",
        "sfjy":"否"
    },
    {
        "xh":"3",
        "yhxm":"王五",
        "yhzh":"120001003",
        "yhbm":"办公室",
        "yhxb":"男",
        "pxh":"3",
        "sfjy":"否"
    }
]



/**
 * 将全局数组内容显示在表格中的函数
 * @param list
 */
function displayListInTable(list) {
    // 如果没有传入列表，则使用全局列表
    list = list || data;
    // 清空表格的tbody
    $("#usertable tbody").empty();

    // 遍历全局数组并构建表格行
    for (let i = 0; i < list.length; i++) {
        var rowData = list[i];
        rowData.xh = i + 1 ;
        var rowHtml = "<tr><td class=centered>" + rowData.xh + "</td><td title=''> " + rowData.yhxm + "</td><td>"
            + rowData.yhzh + "</td><td class=centered>"
            + rowData.yhbm + "</td><td class=centered>"
            + rowData.yhxb + "</td><td class=centered>"
            + rowData.pxh + "</td><td class=centered>"
            + rowData.sfjy + "</td>"+"<td>" +
            "<button class='btn-query' data-index='" + i + "' style='margin-left: 40px' >查询</button>" +
            "<button class='btn-edit' data-index='" + i + "' style='margin-left: 20px'>修改</button>" +
            "<button class='btn-delete' data-index='" + i + "' style='margin-left: 20px'>删除</button>" +
            "</td></tr>";
        // 将行添加到表格的tbody中
        $("#usertable tbody").append(rowHtml);
    }
    /**
     * 绑定单条的查询
     */
    $(".btn-query").click(function() {
        let index = $(this).data("index");
        let jsonData  = JSON.stringify(list[index]);
        // alert("查询：" + JSON.stringify(list[index]));
        window.open("add_user.html?jsonData=" + encodeURIComponent(jsonData)+ "&mode=view", "_blank");

    });
    /**
     * 绑定编辑
     */
    $(".btn-edit").click(function() {
        var index = $(this).data("index");
        // 编写修改操作的逻辑，可以弹出一个模态框或跳转到编辑页面等
        var updateData = JSON.stringify(list[index]);
        window.open("add_user.html?jsonData=" + encodeURIComponent(updateData)+ "&mode=updateusers", "_blank");
    });
    /**
     * 绑定并确认删除
     */
    $(".btn-delete").click(function() {
        var index = $(this).data("index");
        // 编写删除操作的逻辑，可以弹出确认框等
        if (confirm("确认删除？")) {
            list.splice(index, 1);
            displayListInTable(data); // 重新显示表格
        }
    });
}


/**
 * 页面函数
 */
$(document).ready(function() {

    /**
     * 第一页点击按钮筛选
     */
    $("#btnQuery").click(function() {
        var userName = $("#userName").val().trim();
        var userDepartment = $("#userDepartment").val().trim();
        var encodedUserName = encodeStr(userName);
        var encodeduserDepartment = encodeStr(userDepartment);
        // 过滤数据
        var filteredData = data.filter(user=> (userName ==="" || user.yhzh === userName ||  user.yhxm === userName) && (userDepartment === "" || user.yhbm === userDepartment));
        // 更新表格显示
        displayListInTable(filteredData);

        alert("查询参数：\n用户姓名/用户ID: " + encodedUserName + "\n用户部门: " + encodeduserDepartment);
    });

    /**
     * 第一页新增按钮点击事件
     */
    $("#btnAdd").click(function() {
        // 打开新的页面，注意大小并居中显示
            window.open('add_user.html?source=display_users', '新增用户', 'width=1100, height=900, top=200, left=400');
    });
    displayListInTable();


});

/**
 *  模拟编码方法
 * @param str
 * @returns {string}
 */
function encodeStr(str) {
    return encodeURIComponent(str);
}

/**
 * 功能：添加用户到表格
 * @param map 传入父级窗口map
 */
function addUsers(map){
    var jsonData = JSON.parse(map);
    data.push(jsonData);
    displayListInTable(data);
}

/**
 * 功能：更新表格
 * @param map 传入父级窗口map
 */
function updateUsers(map){
    //用于将 JSON 字符串解析成 JavaScript 对象
    var jsonData = JSON.parse(map);
    //根据用户账户来筛选
    const userAccount = jsonData["yhzh"];
    //找到所在的下标
    var index = data.findIndex(user => user["yhzh"] === userAccount);
    //为-1,表示失败了
    if (index !== -1) {
        //更新那一条数据
        data[index] = jsonData;
    }
    displayListInTable(data);
}
