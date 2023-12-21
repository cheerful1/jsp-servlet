<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/loginstyles.css">
    <script src="js/jquery-3.1.1.min.js"></script>
</head>

<%--登录的主要页面--%>
<body>
    <div class="container">
        <form  action="login" method="post" id="loginForm" >
            <label for="uname">账号：</label>
            <input type="text" name = "uname" id="uname" value="${messageModel.object.YHID}" required>
            <input type="checkbox" name="rememberUsername" id="rememberUsername" checked>记住账号
            <br>
            <br>
            <label for="upwd">口令：</label>
            <input type="password" name="upwd" id="upwd" required>
            <input type="checkbox" name="rememberPassword" id="rememberPassword"  value="${messageModel.object.YHKL}" checked>记住口令
            <br>
            <br>
            <span id="msg" style="color: red;font-size: 12px">${messageModel.msg}</span><br>
            <button type="submit" id="loginBtn">登录</button>
            <button type="reset">重置</button>
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
            // 从表单获取数据
            var username = document.getElementById("uname").value;
            var password = document.getElementById("upwd").value;


            if (document.getElementById("rememberUsername").checked) {
                setCookie("rememberedUsername", username, 365);
            } else {
                // If not checked, remove the cookie
                deleteCookie("rememberedUsername");
            }

            //检查时候选上了
            if (document.getElementById("rememberPassword").checked) {
                setCookie("rememberedPassword", password, 365);
            } else {
                // If not checked, remove the cookie
                deleteCookie("rememberedPassword");
            }

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

    window.onload = function () {
        var rememberedUsername = getCookie("rememberedUsername");
        var rememberedPassword = getCookie("rememberedPassword");

        if (rememberedUsername) {
            document.getElementById("uname").value = rememberedUsername;
            document.getElementById("rememberUsername").checked = true;
        }

        if (rememberedPassword) {
            document.getElementById("upwd").value = rememberedPassword;
            document.getElementById("rememberPassword").checked = true;
        }
    };



    /**
     * 设置 cookie的时间
     * @param {string} name - cookie 的名称
     * @param {string} value - cookie 的值
     * @param {number} days - cookie 的过期时间（天数），可选参数，如果不提供则为 session cookie
     */
    function setCookie(name, value, days) {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    }

    /**
     * 获取指定名称的 cookie 值
     * @param {string} name - 要获取的 cookie 的名称
     * @returns {string|null} - 如果找到指定名称的 cookie，则返回其值；否则返回 null
     */
    function getCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(";");
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) === " ") c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    }

    /**
     * 删除指定名称的 cookie
     * @param {string} name - 要删除的 cookie 的名称
     */
    function deleteCookie(name) {
        document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    }
</script>


</html>