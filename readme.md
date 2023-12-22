###问题总结
####1、在前端的页面中的最好将一些无用的alert和console.log()去掉，不要输出到前台。
####2、判断一个类是否是工具类，别人能用就是一个公共的方法(能写就写这个)，如果不是，就是个人的一个定制化的方法，最好是private属性放在使用到的类中。
####3、前端jsp最好不要使用windows.open()来传参。因为有长度的限制。
####4、避免冗余的代码，代码要简洁。
####5、工具类方法一定加上判空逻辑。
####6、不要多层的if-else嵌套，能结束的就直接结束掉。
####7、包名要小写，类名的首字母大写。
####8、前端登录逻辑直接判断用户的账户和密码是否和数据库中的一致，不要先判断账户，再判断密码是否一致。