慕课商城后端项目代码
V1  项目初始化
maven pom文件初始化
项目结构包初始化
mybatis-generator配置    生成数据对象&时间戳优化
mybatis-plugin配置    dao层和xml文件切换
mybatis-pagehelper配置    分页插件

用户模块：
    登录 验证 注册 忘记密码 重置密码
    横纵向越权 高复用响应对象封装  MD5 Guava session

分类管理模块
    设计和封装无限层级的树状数据结构 递归算法设计思想
    处理复杂对象排重
    重写hashCOde和equals注意事项
例子 /manage/category/get_category.do   获取品类子节点(平级)
name/categoryId
9900/100061
	9901/100062											9902/100063
	9921/100064  					9922/100065			9931/100066
	9951/100067 9952/100068 		9961/100069

100064:
    100067,100064,100068
100062：
    100062
    100064,100065
    100067,100068,100069

云服务器部署

云服务器 申请，配置

域名 申请，配置

源配置
    备份
    下载配置
    生成缓存

线上环境
    JDK tomcat maven vsftpd nginx mysql git iptables 文件服务器

自动化发布脚本

线上验证







