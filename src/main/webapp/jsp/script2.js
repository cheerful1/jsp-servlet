
var parammap = window.opener;

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
        $("#user_id").val(jsonData.yhzh);
        $("#user_name").val(jsonData.yhxm);
        $("#user_department").val(jsonData.yhbm);
        $("#user_gender").val(jsonData.yhxb);
        $("#user_pxh").val(jsonData.pxh);
        $("#user_disable").prop("checked",jsonData.sfjy === "是");

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
            "xh": $("#user_id").val(),
            "yhxm": $("#user_name").val(),
            "yhkl":$("#user_pass").val(),
            "cfkl":$("#user_repass").val(),
            "yhzh": $("#user_id").val(),
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
                $(this).addClass("required_input");
            }else{
                $(this).removeClass("required_input");
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
        if(flag){
            //更改日期的入库格式为：yyyyMMdd -> 使用正则表达式替换字符串中的连字符 "-"
            userData.csrq = userData.csrq.replace(/-/g, "");
            //拼接字符串
            let outputString = "";
            for (let key in userData) {
                if (userData.hasOwnProperty(key)) {
                    outputString += key + ": " + userData[key] + "\n";
                }
            }
            alert(outputString);


            /**
             * 判断使用哪种方式保存。
             */
            if (mode === 'updateusers'){
                let jsonData  = JSON.stringify(userData);
                parammap.updateUsers(jsonData);
                window.close()
            }else {
                let jsonData  = JSON.stringify(userData);
                parammap.addUsers(jsonData);
                window.close()
            }
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

