pafeOffice前后端分离  </br>
用springboot,thymeleaf,node.js来实现</br>
需要环境：</br>
java环境</br>
maven,gradle环境</br>
node,npm环境</br>
git工具</br>

测试序列号：CA1XB-MF7Y-12ST-PSBP2  </br>
d盘创建lic-fenli文件夹，  将poseal.db和license.lic拷贝进来 (poseal.db是sqlite需要的数据库文件，license.lic是注册生成的文件，如果没有会要求注册)  </br>
d盘创建doc文件夹， 拷贝“准备文件”中的所有doc文件进来  </br>
从pageoffice官网下载pageoffice4.5.0.4.jar,放到d盘。  在该目录下执行命令安装到本地maven中：
mvn install:install-file -DgroupId=com.zhuozhengsoft -DartifactId=pageoffice -Dversion=4.5.0.4 -Dpackaging=jar -Dfile=pageoffice4.5.0.4.jar
  </br>
运行项目java项目：  </br>
访问 http://localhost:8080/fileList   这个页面实现了   列表，打开，保存     签章暂未实现  </br>

进入web1目录下：   </br>
执行node server.js   </br>
访问 http://localhost:8040/  </br>
点击可以打开，成功实现了前后端分离。  </br>




