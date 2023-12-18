<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: wsj1997
  Date: 2023/12/14
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
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
<body>
<div class="container">
    <div class="header">
        <h1>用户信息</h1>
    </div>

    <form id="form" onsubmit="return validateForm()" id="userForm" novalidate>
        <table align="center" >
            <tr>
                <th><label for="user_id" class="required">用户ID：</label></th>
                <th><input type="text" id="user_id" required></th>
                <th><label for="user_name" class="required">用户姓名：</label></th>
                <th>
                    <input type="text" id="user_name" required></th>
                </th>
            </tr>
            <tr>
                <th><label for="user_pass" class="required">用户口令：</label></th>
                <th><input type="password" id="user_pass"  required></th>
                <th><label for="user_repass" class="required">重复口令：</label></th>
                <th><input type="password" id="user_repass"    required ></th>
            </tr>
            <tr>
                <th><label for="user_department" class="required">用户部门：</label></th>
                <th>
                    <select id="user_department"  required>
                        <option value="">请选择部门</option>
                        <option value="立案庭">立案庭</option>
                        <option value="业务庭">业务庭</option>
                        <option value="办公室">办公室</option>
                    </select>
                </th>
                <th><label for="user_gender">用户性别：</label></th>
                <th>
                    <select  id="user_gender">
                        <option value="">请选择性别</option>
                        <option value="男">男</option>
                        <option value="女">女</option>
                        <option value="其他">其他</option>
                    </select>
                </th>
            </tr>
            <tr>
                <th><label for="user_birthday">出生日期：</label></th>
                <th><input type="date" id="user_birthday" placeholder="请输入出生日期"></th>
                <th><label for="user_pxh">排序号：</label></th>
                <th><input type="text" id="user_pxh" placeholder="请输入排序号令"></th>
            </tr>
            <tr>
                <th><label for="user_disable">是否禁用：</label></th>
                <th colspan="1">
                    <input type="checkbox" id="user_disable" value="是">
                </th>
                <th></th>
                <th></th>
            </tr>
            <tr>
                <th colspan="4">
                    <div class="form-row">
                        <input type="button" id="btn-save" onclick="doSqve()" value="保存"></input>
                        <input type="button" id="btn-return" value="返回"></input>
                    </div>
                </th>
            </tr>
        </table>
    </form>



</div>
<script>


    function doSqve(){
        var params = $("#form").serialize();
    }

    $(document).ready(function() {
        /**
         * 从上一页绑定的按钮中传参，判断时插入数据还是查询数据
         * @type {URLSearchParams}
         */
        const urlParams = new URLSearchParams(window.location.search);
        const jsonDataParam = urlParams.get('jsonData');
        const mode = urlParams.get('mode');

        /**
         * 查询的回显和页面格式的调整
         */
        if (jsonDataParam) {
            // 解码
            let jsonData = JSON.parse(decodeURIComponent(jsonDataParam));
            // 假设 jsonData.csrq 是一个以 "yyyyMMdd" 格式表示的日期字符串
            let originalDateStr = jsonData.csrq;
            // 使用解构赋值和模板字符串进行日期格式化
            let [year, month, day] = [
                originalDateStr.substring(0, 4),
                originalDateStr.substring(4, 6),
                originalDateStr.substring(6, 8)
            ];

            // 将日期格式化为 "yyyy-MM-dd" 格式
            let formattedDate = year + '-' + month + '-' + day;

            $("#user_id").val(jsonData.yhid);
            $("#user_name").val(jsonData.yhxm);
            $("#user_pass").val(jsonData.yhkl);
            $("#user_repass").val(jsonData.yhkl);
            $("#user_department").val(jsonData.yhbm);
            $("#user_gender").val(jsonData.yhxb);
            $("#user_birthday").val(formattedDate);
            $("#user_pxh").val(jsonData.pxh);
            $("#user_disable").prop("checked", jsonData.sfjy === "是");

            //view模式是查询
            if (mode === 'view') {
                // 禁用全局的输入
                $("input, select").attr("disabled", true);
                // 隐藏保存按钮
                $("#btn-save").hide();
            }
            //updateusers模式是更新用户的数据
            if(mode === 'updateusers'){
                $("#user_id").attr("disabled",true)
            }
        }


        /**
         *  点击保存
         */
        $("#btn-save").click(function(event){
            //防止页面刷新
            event.preventDefault();

            /**
             * 取表单中的所有的元素
             * @type {{xh: null, yhxb: (*|jQuery), pxh: (*|jQuery), csrq: (*|jQuery), yhbm: (*|jQuery), yhzh: (*|jQuery), sfjy: (string), yhkl: (*|jQuery), cfkl: (*|jQuery), yhxm: (*|jQuery)}}
             */

            var userData = {
                //这里没有使用赋值，在addUsers的函数中，将其设为根据list的长度自增。
                "yhid": $("#user_id").val(),
                "yhxm": $("#user_name").val(),
                "yhkl":$("#user_pass").val(),
                "cfkl":$("#user_repass").val(),
                "yhbm": $("#user_department").val(),
                "yhxb": $("#user_gender").val(),
                "pxh": $("#user_pxh").val(),
                "csrq":$("#user_birthday").val(),
                "sfjy": $("#user_disable").prop("checked") ? "是" : "否"
            };

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
            if (userData.yhkl !== userData.cfkl){
                alert("用户口令和确认用户口令不一致！");
                flag = false;
                $("#user_pass, #user_repass").addClass("highlight");
                return;
            }else {
                $("#user_pass, #user_repass").removeClass("highlight");
            }

            /**
             * 填写无误进行提交
             */
            if(flag && mode !=="updateusers"){
                //更改日期的入库格式为：yyyyMMdd -> 使用正则表达式替换字符串中的连字符 "-"
                userData.csrq = userData.csrq.replace(/-/g, "");
                //重复口令这个字段，校验完成就不需要传了
                delete userData.cfkl;

                // 使用 AJAX 发送 JSON 数据到后端
                $.ajax({
                    url: 'add_users',
                    method: 'POST',
                    data: JSON.stringify(userData),
                    success: function(response) {
                        var rdata =JSON.parse(response);
                        if(rdata.code == 1){
                            alert(rdata.msg);
                            window.close()
                        }else{
                            alert(rdata.msg);
                        }
                    },
                    error: function(error) {
                        alert("添加用户失败，服务器开小差了！");
                        console.error('失败:', error);
                    }
                });

            }else{
                //更改日期的入库格式为：yyyyMMdd -> 使用正则表达式替换字符串中的连字符 "-"
                userData.csrq = userData.csrq.replace(/-/g, "");
                //重复口令这个字段，校验完成就不需要传了
                delete userData.cfkl;

                // 使用 AJAX 发送 JSON 数据到后端
                $.ajax({
                    url: 'update_user',
                    method: 'POST',
                    contentType: 'application/json;charset=utf-8',
                    data: JSON.stringify(userData),
                    success: function(response) {
                        var rdata =JSON.parse(response);
                        if(rdata.code == 1){
                            alert(rdata.msg);
                            window.close()
                        }else{
                            alert(rdata.msg);
                        }
                    },
                    error: function(error) {
                        alert("更新用户失败，服务器开小差了！");
                        console.error('失败:', error);
                    }
                });

            }
        });


        /**
         * 返回按钮
         */
        $(document).ready(function() {
            // 当按钮被点击时触发
            $("#btn-return").click(function() {
                // 关闭当前窗口或标签页
                window.close();
            });
        });
    });



</script>
</body>

</html>

