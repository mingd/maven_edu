war���Ĵ����ʽdemo
===================================
		ͨ��war���Ĵ����ʽ��Ϊ�򵥣�һ���web����ֱ�����У�mvn package �Ϳ��Դ��war����
		��ʵ�ʲ�������Ҫ�������������⣺
		1.���������Ͳ��𻷾�����ֵ��һ��
		2.���𻷾����в��Ի�������ʽ��������Ҫ�������war��

1.���������Ͳ��𻷾���������
-----------------------------------
		������Ҫ����services.properties���������Ҫ�����ļ��ŵ���/WEB-INF/classes Ŀ¼�¡�
		����ʱ�������ļ������ڣ�src/main/resources Ŀ¼�¡����𻷾���Ҫ�õ����ļ����� src/main/production �¡�ͬʱ�޸� pom.xml ���ã��� maven-war-plugin ����м����������ã�
		
		<webResources>
			<resource>
				<directory>src/main/production</directory>
				<targetPath>WEB-INF/classes</targetPath>
				<includes>
					<include>service.properties</include>
				</includes>
			</resource>
		</webResources>
		
		����ʱ������mvn tomcat:run ����ʹ�� src/main/resources Ŀ¼�µ� services.properties ���ճ�������
		���ʱ���mvn package ����� src/main/production Ŀ¼�µ� services.properties ���Ƶ� WEB-INF/classes ���ǵ� src/main/resourcesĿ¼�µ� services.properties
		���war�����

2.���𻷾����в��Ի�������ʽ��������Ҫ�������war��
-----------------------------------
		������Ҫ��weblogic��tomcat����������������ڶ��ߵ�jndi���Ʋ���ϴ󣬴��ʱ��Ҫ���ݲ�ͬ��ƽ̨�޸����á�
		�����ݿ������ڣ�src/main/webapp/WEB-INF/spring/db.xml �С���Ŀ¼�е�db����Ϊ�������������á�
		���𻷾�������λ�ڣ�src/main/production/db.xml ������JNDI���Ƶ�����Ϊ������${jndiName}��Ȼ�����⽨������properties�ļ������ñ����ṩֵ������ֱ�Ϊ��tomcat.properties��weblogic.properties��
		���� pom.xml ��������ֺ͵�һ��������ƣ�����Ҫ����һ�� filtering ��

		<resource>
			<directory>src/main/production</directory>
			<filtering>true</filtering>
			<targetPath>WEB-INF/spring</targetPath>
			<includes>
				<include>**.xml</include>
			</includes>
		</resource>

		���⻹��Ҫ��������propertiesΪprofile��

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

		����ID��������������profile��

		������ɺ������Ҫ��tomcat��������У�mvn package -P tomcat����weblogic��������У�mvn package -P weblogic