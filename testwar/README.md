war包的打包方式demo
===================================
		通常war包的打包方式最为简单，一般的web工程直接运行：mvn package 就可以打成war包。
		在实际操作中主要会遇到如下问题：
		1.开发环境和部署环境配置值不一致
		2.部署环境中有测试环境和正式环境，需要打成两种war包

1.开发环境和部署环境的问题解决
-----------------------------------
		假设需要配置services.properties，打包后需要将此文件放到：/WEB-INF/classes 目录下。
		开发时该配置文件存在于：src/main/resources 目录下。部署环境需要用到的文件置于 src/main/production 下。同时修改 pom.xml 配置，在 maven-war-plugin 插件中加入如下配置：
		
		<webResources>
			<resource>
				<directory>src/main/production</directory>
				<targetPath>WEB-INF/classes</targetPath>
				<includes>
					<include>service.properties</include>
				</includes>
			</resource>
		</webResources>
		
		开发时启动：mvn tomcat:run 将会使用 src/main/resources 目录下的 services.properties ，照常开发。
		打包时命令：mvn package 将会把 src/main/production 目录下的 services.properties 复制到 WEB-INF/classes 覆盖掉 src/main/resources目录下的 services.properties
		完成war打包。

2.部署环境中有测试环境和正式环境，需要打成两种war包
-----------------------------------
		假设需要给weblogic和tomcat两种容器打包。由于二者的jndi名称差异较大，打包时需要根据不同的平台修改配置。
		主数据库配置于：src/main/webapp/WEB-INF/spring/db.xml 中。该目录中的db配置为开发环境的配置。
		部署环境的配置位于：src/main/production/db.xml 。其中JNDI名称的配置为变量：${jndiName}。然后另外建立两个properties文件，给该变量提供值。这里分别为：tomcat.properties、weblogic.properties。
		配置 pom.xml ，插件部分和第一种情况类似，但需要多配一个 filtering ：

		<resource>
			<directory>src/main/production</directory>
			<filtering>true</filtering>
			<targetPath>WEB-INF/spring</targetPath>
			<includes>
				<include>**.xml</include>
			</includes>
		</resource>

		另外还需要配置两个properties为profile：

		<profile>
			<id>weblogic</id>
			<build>
				<filters>
					<filter>src/main/production/weblogic.properties</filter>
				</filters>
			</build>
		</profile>
		<profile>
			<id>tomcat</id>
			<build>
				<filters>
					<filter>src/main/production/tomcat.properties</filter>
				</filters>
			</build>
		</profile>

		其中ID将用于区别两个profile。

		配置完成后，如果需要给tomcat打包则运行：mvn package -P tomcat，给weblogic打包则运行：mvn package -P weblogic