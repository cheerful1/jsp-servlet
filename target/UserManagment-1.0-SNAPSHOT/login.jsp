<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/loginstyles.css">
    <script src="js/jquery-3.1.1.min.js"></script>
</head>


<body>
    <div class="container">
        <form action="login" method="post" id="loginForm">
            <label for="uname">账号：</label>
            <input type="text" name = "uname" id="uname" value="${messageModel.object.YHID}" required>
            <br>
            <label for="upwd">口令：</label>
            <input type="password" name="upwd" id="upwd" value="${messageModel.object.YHKL}" required>
            <br>
            <span id="msg" style="color: red;font-size: 12px">${messageModel.msg}</span><br>
            <button type="button" id="loginBtn">登录</button>
            <button type="reset">重置</button>
            <%--        <span style="color: red;font-size: 12px"><%=request.getAttribute("msg")%></span>--%>
            <%--        msg默认是空的字符串--%>
        </form>
    </div>
</body>


<script>

    /**
     * 登录表单验证
     * 1、给登录按钮绑定点击事件
     * 2、获取用户姓名和密码的值
     * 3、判断用户姓名是否为空（如果为空，提示用户，并且return）
     * 4、判断用户密码是否为空（如果为空，提示用户，并且return）
     * 5、如果不为空那么提交表单
     */
    $("#loginBtn").click(function (){
            //2、获取用户姓名和密码的值
            var uname = $("#uname").val();
            var upwd = $("#upwd").val();
            //3、判断用户姓名是否为空
            if(isEmpty(uname)){
                //如果姓名为空那么提示用户，并且return html（）
                $("#msg").html("用户姓名不能为空!");
                return;
            }
            //4、判断密码是否为空
            if(isEmpty(upwd)){
                //如果姓名为空那么提示用户，并且return html（）
                $("#msg").html("用户密码不能为空!");
                return;
            }
            //5、如果不为空那么提交表单
            $("#loginForm").submit();



    });
    // 重置按钮点击事件
    $("button[type='reset']").click(function () {
        // 清空姓名和密码输入框
        $("#uname").val("");
        $("#upwd").val("");
        // 清空错误消息
        $("#msg").html("");
    });

    /**
     * 判断字符串是否为空，
     * 如果为空那么返回true
     * 如果不为空返回false
     * @param str
     * @returns {boolean}
     */
    function isEmpty(str){
        if(str == null|| str.trim()==""){
            return true;
        }else {
            return false;
        }
    }
</script>


</html>