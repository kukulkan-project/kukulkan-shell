<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-cloud-starter-stream-processor-${appName}</artifactId>
	<packaging>jar</packaging>
	<name>spring-cloud-starter-stream-processor-${appName}</name>
	<description>Spring Cloud Stream processor-${appName} core</description>

	<parent>
		<groupId>org.springframework.cloud.stream.app</groupId>
		<artifactId>${appName}-app-starters-build</artifactId>
		<version>2.0.1.RELEASE</version>
	</parent>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-app-starter-doc-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.springframework.cloud.stream.app.plugin</groupId>
				<artifactId>spring-cloud-stream-app-maven-plugin</artifactId>
				<configuration>
					<generatedProjectHome>${session.executionRootDirectory}/apps</generatedProjectHome>
					<generatedProjectVersion>${project.version}</generatedProjectVersion>
					<bom>
						<name>scs-bom</name>
						<groupId>org.springframework.cloud.stream.app</groupId>
						<artifactId>${appName}-app-dependencies</artifactId>
						<version>${project.version}</version>
					</bom>
					<generatedApps>
						<${appName}-source />
						<${appName}-processor />
						<${appName}-sink />
					</generatedApps>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>
