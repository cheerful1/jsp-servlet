<%--
  Created by IntelliJ IDEA.
  User: wsj1997
  Date: 2023/12/14
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta content="text/html" charset="UTF-8">
    <!--页面自适应调整大小-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="js/jquery-3.1.1.min.js"></script>
    <title>User Management</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 1500px;
            margin: 20px auto;
            padding: 20px;
            background-color: #f4f4f4;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .header {
            text-align: center;
            margin-bottom: 20px;
            font-size: 15px;
        }

        .section-a {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 20px;
        }

        .centered {
            text-align: center;
        }
        label {
            min-width: 130px;
        }

        input, select {
            width: 200px;
            padding: 8px;
            box-sizing: border-box;
        }

        button {
            padding: 9px;
            background-color: #4c8caf;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #4c8caf;
        }

        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;

        }


    </style>
</head>

<body>
<div class="container">
    <div class="header">
        <h1>用户信息管理页面</h1>
        <h2>当前用户:<%=session.getAttribute("uname")%></h2>
    </div>
    <div class="section-a">
        <label for="userName">用户姓名/用户ID：</label>
        <input type="text" id="userName" placeholder="请输入用户姓名或ID" />

        <label for="userDepartment">用户部门：</label>
        <select id="userDepartment">
            <option value="">请选择部门</option>
            <option value="立案庭">立案庭</option>
            <option value="业务庭">业务庭</option>
            <option value="办公室">办公室</option>
        </select>
        <input type="button" id="btnQuery" onclick="doQuery()" value="查询">
        <input type="button" id="btnAdd" onclick="doAdd()" value="新增">

    </div>

    <!-- 用户信息列表可以在这里添加 -->
    <table id="usertable">
        <!--用于定义表格的表头部分的标签,将表格的主体部分标记为表格的主要数据区域-->
        <thead>
        <tr>
            <th class=centered>序号</th>
            <th>用户姓名</th>
            <th>用户账户</th>
            <th class=centered>用户部门</th>
            <th class=centered>用户性别</th>
            <th class=centered>排序号</th>
            <th class=centered>是否禁用</th>
            <th class=centered>操作 </th>
        </tr>
        </thead>
        <!--        用于定义表格主体部分的标签-->
        <tbody>

        </tbody>
    </table>

</div>



<script>
    /**
     * 点击查询按钮查询事件
     */
    function doQuery(){
        var userName = $("#userName").val().trim();
        var userDepartment = $("#userDepartment").val().trim();
        var encodedUserName = encodeStr(userName);
        var encodeduserDepartment = encodeStr(userDepartment);
        $.ajax({
            type: "POST",
            url: "queryuser",
            data: JSON.stringify({
                encodedUserName: encodedUserName,
                encodeduserDepartment: encodeduserDepartment
            }),
            success: function(response) {
                var rdata =JSON.parse(response);
                if(rdata.code == 1){
                    //这里查询成功就不alert了直接，在B区显示
                    // alert(rdata.msg);
                    displayListInTable(rdata.object);
                }else{
                    alert(rdata.msg);
                }
            },
            error: function(error) {
                alert("查询用户失败，服务器开小差了！");
                // 请求失败时的处理
                console.error("失败: ", error);
            }
        });
    }

    /**
     * 第一页新增按钮点击函数
     */
    function doAdd(){
        // 打开新的页面，注意大小并居中显示
        window.open('add_user.jsp?source=display_users', '新增用户', 'width=1100, height=900, top=200, left=400');
    }

    /**
     * 展示所有用户的信息
     */
    function showUsers(){
        $.ajax({
            type: "POST",
            url: "display_users",
            success: function(response) {
                var rdata =JSON.parse(response);
                if(rdata.code == 1){
                    //这里查询所有用户成功就不alert了直接，在B区显示
                    // alert(rdata.msg);
                    displayListInTable(rdata.object);
                }else{
                    alert(rdata.msg);
                }
            },
            error: function(error) {
                alert("查询用户失败，服务器开小差了！");
                // 请求失败时的处理
                console.error("失败: ", error);
            }
        });
    }


    /**
     * 将全局数组内容显示在表格中的函数
     * @param list
     */
    function displayListInTable(list) {
        // 清空表格的tbody
        $("#usertable tbody").empty();
        let j = 0 ;

        // 遍历全局数组并构建表格行
        for (let i = 0; i < list.length; i++) {
            j= j + 1;
            var rowData = list[i];
            var rowHtml = "<tr><td class=centered>" + j + "</td><td style='max-width: 100px; overflow:hidden;text-overflow: ellipsis;' >" + rowData.yhxm + "</td><td>"
                + rowData.yhid + "</td><td class=centered>"
                + rowData.yhbm + "</td><td class=centered>"
                + rowData.yhxb + "</td><td class=centered>"
                + rowData.pxh + "</td><td class=centered>"
                + rowData.sfjy + "</td>"+"<td>" +
                "<button class='btn-query' data-index='" + i + "' style='margin-left: 20px' >查看</button>" +
                "<button class='btn-edit' data-index='" + i + "' style='margin-left: 20px'>修改</button>" +
                "<button class='btn-delete' data-index='" + i + "' style='margin-left: 20px'>删除</button>" +
                "</td></tr>";
            // 将行添加到表格的tbody中
            $("#usertable tbody").append(rowHtml);
        }


        /**
         * 绑定单条的查看
         */
        $(".btn-query").click(function() {
            let index = $(this).data("index");
            let jsonData  = JSON.stringify(list[index]);
            // alert("查询：" + JSON.stringify(list[index]));
            window.open("add_user.jsp?jsonData=" + encodeURIComponent(jsonData)+ "&mode=view", "_blank");

        });
        /**
         * 绑定编辑
         */
        $(".btn-edit").click(function() {
            var index = $(this).data("index");
            // 编写修改操作的逻辑，可以弹出一个模态框或跳转到编辑页面等
            var updateData = JSON.stringify(list[index]);
            window.open("add_user.jsp?jsonData=" + encodeURIComponent(updateData)+ "&mode=updateusers", "_blank");
        });
        /**
         * 绑定并确认删除
         */
        $(".btn-delete").click(function() {
            var index = $(this).data("index");
            // 编写删除操作的逻辑，可以弹出确认框等
            if (confirm("确认删除？")) {
                let userId = list[index].yhid;
                // 在确认删除后，使用 AJAX 请求执行删除操作
                $.ajax({
                    url: "delete_user",
                    method: "POST",
                    data: { yhid: userId },
                    success: function() {
                        // 删除成功后，从 list 中移除对应项
                        list.splice(index, 1);
                        alert("删除成功！");
                        displayListInTable(data);
                    },
                    error: function() {
                        alert("删除失败，请重试！");
                    }
                });
            }
        });
    }


    /**
     * 页面函数
     */
    $(document).ready(function() {

        doQuery();

    });

    /**
     *  模拟编码方法
     * @param str
     * @returns {string}
     */
    function encodeStr(str) {
        return encodeURIComponent(str);
    }




</script>
</body>
</html>