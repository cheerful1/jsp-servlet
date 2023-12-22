<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/styles2.css">
    <script src="js/jquery-3.1.1.min.js"></script>
    <title>Add Users</title>
</head>
<style>
    #btn-return,#btn-save{
        width: 50px;
        padding: 10px;
        background-color: #4577a0;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
</style>
<body>
<div class="container">
    <div class="header">
        <h1>用户信息</h1>
    </div>

        <form id="userForm"  novalidate>
            <table align="center" >
                <tr>
                    <th><label for="user_id" class="required">用户ID：</label></th>
                    <th><input type="text" id="user_id" name="user_id" required></th>
                    <th><label for="user_name" class="required">用户姓名：</label></th>
                    <th>
                        <input type="text" id="user_name" name="user_name" required></th>
                    </th>
                </tr>
                <tr>
                    <th><label for="user_pass" class="required">用户口令：</label></th>
                    <th><input type="password" id="user_pass" name="user_pass" required></th>
                    <th><label for="user_repass" class="required">重复口令：</label></th>
                    <th><input type="password" id="user_repass"  required ></th>
                </tr>
                <tr>
                    <th><label for="user_department" class="required">用户部门：</label></th>
                    <th>
                        <select id="user_department" name="user_department" required>
                            <option value="">请选择部门</option>
                            <option value="32010001">立案庭</option>
                            <option value="32010002">业务庭</option>
                            <option value="32010003">办公室</option>
                        </select>
                    </th>
                    <th><label for="user_gender">用户性别：</label></th>
                    <th>
                        <select  id="user_gender" name="user_gender">
                            <option value="">请选择性别</option>
                            <option value="09_00003-1">男</option>
                            <option value="09_00003-2">女</option>
                            <option value="09_00003-255">其他</option>
                        </select>
                    </th>
                </tr>
                <tr>
                    <th><label for="user_birthday">出生日期：</label></th>
                    <th><input type="date" id="user_birthday" name="user_birthday" placeholder="请输入出生日期"></th>
                    <th><label for="user_pxh">排序号：</label></th>
                    <th><input type="number" id="user_pxh"  name="user_pxh" placeholder="请输入排序号令"></th>
                </tr>
                <tr>
                    <th><label for="user_disable">是否禁用：</label></th>
                    <th colspan="1">
                        <input type="checkbox" id="user_disable" name="user_disable" value="是">
                    </th>
                    <th></th>
                    <th></th>
                </tr>
                <tr>
                    <th colspan="4">
                        <div class="form-row">
                            <input type="button" id="btn-save" onclick="doSave()" value="保存">
                            <button id="btn-return">返回</button>
                         </div>
                    </th>
                </tr>
            </table>
        </form>



</div>
<script>
    /**
     * 从上一页绑定的按钮中传参，判断时插入数据还是查询数据
     * @type {URLSearchParams}
     */
    const urlParams = new URLSearchParams(window.location.search);
    const jsonDataParam = urlParams.get('jsonData');
    const mode = urlParams.get('mode');
    const YHID = urlParams.get('yhid');



    /**
     * 发送 AJAX 请求的通用函数
     * @param {string} url - 请求的URL
     * @param {string} method - 请求方法
     * @param {Object} data - 请求数据
     * @param {string} successMsg - 成功时的提示消息
     * @param {string} errorMsg - 失败时的提示消息
     */
    function sendAjaxRequest(url, method, data, successMsg, errorMsg) {
        $.ajax({
            url: url,
            method: method,
            data: data,
            success: function (response) {
                try {
                    var rdata = JSON.parse(response);
                    if (rdata.code === 1) {
                        alert(successMsg);
                        window.opener.location.reload();
                        window.close();
                    } else {
                        alert(rdata.msg);
                    }
                } catch (error) {
                    console.error('JSON 解析错误:', error);
                }
            },
            error: function (error) {
                alert(errorMsg);
                console.error('失败:', error);
            }
        });
    }

    /**
     * 根据id来查询数据,发送ajax请求，返回user对象
     * @param {number} yhid - 要查询的用户ID
     */
    function selectsingedataRequest(yhid){
        var user = null;
        $.ajax({
            url: "selectbyid",
            method: "POST",
            data: { yhid: yhid },
            success: function(response) {
                var rdata =JSON.parse(response);
                if(rdata.code == 1){
                    user = rdata.object;
                    $("#user_id").val(user.yhid);
                    $("#user_name").val(user.yhxm);
                    $("#user_pass").val(user.yhkl);
                    $("#user_repass").val(user.yhkl);
                    $("#user_birthday").val(user.csrq);
                    $("#user_department").val(user.yhbm);
                    $("#user_gender").val(user.yhxb);
                    $("#user_pxh").val(user.pxh);
                    $("#user_disable").prop("checked",user.sfjy === "是");
                }else{
                    alert(rdata.msg);
                }
            },
            error: function() {
                alert("查看失败，服务器开小差了！");
            }
        });
    }


    /**
     * 点击保存按钮，发送保存请求
     */
    function doSave(){

        var params = $("#userForm").serialize();


        /**
         * 判断必填的字段
         * @type {*|jQuery|HTMLElement}
         */
        let form = $('#userForm');
        let flag = true;
        form.find('[required]').each(function (){
            if($(this).val() ===''){
                flag = false;
                $(this).addClass("required-field");
            }else{
                $(this).removeClass("required-field");
            }
         });

         /**
         * 检查密码
         */
        if (!flag){
            alert("请检查所有必填项！");
            return;
        }
        /**
         * 验证两次口令是否一致
         */
        let password = $("#user_pass").val();
        let confirmPassword =$("#user_repass").val();
        if (password !== confirmPassword){
            alert("用户口令和确认用户口令不一致！");
            flag = false;
            $("#user_pass, #user_repass").addClass("highlight");
            return;
        }else {
            $("#user_pass, #user_repass").removeClass("highlight");
        }

        //校验没有问题，发送ajax请求
        if (flag && mode !== "updateusers") {
            // 添加用户
            sendAjaxRequest('add_users', 'POST', params, '添加用户成功', '添加用户失败，服务器开小差了！');
        } else {
            // 更新用户
            sendAjaxRequest('update_user', 'POST', params, '更新用户成功', '更新用户失败，服务器开小差了！');
        }
    }


    /**
     * 页面展示函数，放在回调函数里面
     */
    function displaysingledata() {
        selectsingedataRequest(YHID);
        if (mode === 'view'){
            //view模式是查询
            if (mode === 'view') {
                // 禁用全局的输入
                $("input, select").attr("disabled", true);
                // 隐藏保存按钮
                $("#btn-save").hide();
            }
        }
        //updateusers模式是更新用户的数据
        if(mode === 'updateusers'){
            $("#user_id").attr("readonly",true)
        }
    }



    $(document).ready(function () {
        if (mode !=='add'){
            displaysingledata();
        }
        $("#btn-return").click(function () {
            window.close();
        });
    });



</script>
</body>

</html>

