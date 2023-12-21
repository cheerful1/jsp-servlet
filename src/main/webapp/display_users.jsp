<%@ page import="com.tdh.usermanagment.entity.TdhUser" %>
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

        .header {
            text-align: center;
            margin-bottom: 20px;
            font-size: 15px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .container {
            max-width: 1500px;
            margin: 20px auto;
            padding: 20px;
            background-color: #f4f4f4;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column; /* 垂直方向布局 */
            justify-content: center; /* 水平居中 */
            align-items: center; /* 垂直居中 */
        }

        .section-a {
            display: flex;
            flex-direction: row; /* 水平方向布局 */
            align-items: center; /* 垂直居中 */
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
        #btnQuery,#btnAdd,#btn-query,#btn-edit,#btn-delete{
            width: 50px;
            padding: 10px;
            background-color: #4577a0;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        #logout-button {
            /*position: absolute;*/
            top: 10px;
            right: 10px;
            padding: 10px;
            background-color: #4577a0;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 50px;
        }
        h2 {
            margin-right: 20px;
        }


    </style>
</head>

<body>
<div class="container">
    <h1>用户信息管理页面</h1>
    <div class="header">
        <h2>当前用户ID:<%= session.getAttribute("user") != null ? ((TdhUser)session.getAttribute("user")).getYHID() : "未知用户" %>
        </h2> <input type="button" id="logout-button" onclick="logout()" value="注销">
    </div>
    <div class="section-a">
        <label for="userName">用户姓名/用户ID：</label>
        <input type="text" id="userName" placeholder="请输入用户姓名或ID" />

        <label for="userDepartment">用户部门：</label>
        <select id="userDepartment">
            <option value="">请选择部门</option>
            <option value="32010001">立案庭</option>
            <option value="32010002">业务庭</option>
            <option value="32010003">办公室</option>
        </select>
        <input type="button" id="btnQuery" onclick="doQuery()" value="查询">
        <input type="button" id="btnAdd" onclick="doAdd()" value="新增">

    </div>

    <table id="usertable">
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
        window.open('add_user.jsp?source=display_users&mode=add', '新增用户', 'width=1100, height=900, top=200, left=400');
    }

    /**
     * 绑定单条的查看
     */
    function seedata(yhid){
        window.open("add_user.jsp?yhid=" + yhid + "&mode=view", "_blank");
    }

    /**
     * 绑定编辑
     */
    function editdata(yhid){
        window.open("add_user.jsp?yhid=" + yhid + "&mode=updateusers", "_blank");
    }


    /**
     * 绑定并确认删除
     */
    function deletedata(yhid){
        //TODO 删除用户id直接获取(完成)
        // 编写删除操作的逻辑，可以弹出确认框等
        if (confirm("确认删除？")) {
            // 在确认删除后，使用 AJAX 请求执行删除操作
            $.ajax({
                url: "delete_user",
                method: "POST",
                data: { yhid: yhid },
                success: function() {
                    alert("删除成功！");
                    location.reload();
                },
                error: function() {
                    alert("删除失败，请重试！");
                }
            });
        }
    }

    /**
     *  模拟编码方法
     * @param str
     * @returns {string}
     */
    function encodeStr(str) {
        return encodeURIComponent(str);
    }

    /**
     * 刷新父页面
     */
    function refreshParent() {
        location.reload();
    }

    /**
     * 注销按钮
     */
    function logout() {
        // 使用Ajax调用Servlet销毁Session
        $.ajax({
            type: "POST",
            url: "LogoutServlet",
            success: function () {
                alert("注销成功！");
                // 注销成功后跳转到登录页面
                window.location.href = "login.jsp";
            },
            error: function () {
                alert("注销失败，请重试！");
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
        let j = 0;
        // 遍历全局数组并构建表格行
        for (let i = 0; i < list.length; i++) {
            j = j + 1;
            var rowData = list[i];
            var rowHtml = "<tr><td class=centered>" + j + "</td><td style='max-width: 100px; overflow:hidden;text-overflow: ellipsis;' >" + rowData.yhxm + "</td><td>"
                + rowData.yhid + "</td><td class=centered>"
                + rowData.yhbm + "</td><td class=centered>"
                + rowData.yhxb + "</td><td class=centered>"
                + rowData.pxh + "</td><td class=centered>"
                + rowData.sfjy + "</td>" + "<td>" +
                "<input type='button' id='btn-query' onclick='seedata("+'\"' + rowData.yhid +'\"'+")' data-index='" + i + "' style='margin-left: 20px' value='查看'>" +
                "<input type='button' id='btn-edit' onclick='editdata("+'\"' + rowData.yhid +'\"'+")'  data-index='" + i + "' style='margin-left: 20px' value='修改'>" +
                "<input type='button' id='btn-delete' onclick='deletedata("+'\"' + rowData.yhid +'\"'+")'  data-index='" + i + "' style='margin-left: 20px' value='删除'>" +
                "</td></tr>";
            // 将行添加到表格的tbody中
            $("#usertable tbody").append(rowHtml);
        }
    }


    /**
     * 页面函数
     */
    $(document).ready(function() {
        doQuery();
    });





</script>
</body>
</html>