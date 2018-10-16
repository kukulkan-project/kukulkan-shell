<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<artifactId>${appName}-app-starters-build</artifactId>
	<version>2.0.1.RELEASE</version>
	<packaging>pom</packaging>

	<parent>
		<groupId>org.springframework.cloud.stream.app</groupId>
		<artifactId>app-starters-build</artifactId>
		<version>2.0.1.RELEASE</version>
		<relativePath/>
	</parent>

	<modules>
		<module>spring-cloud-starter-stream-processor-${appName}</module>
		<module>${appName}-app-dependencies</module>
	</modules>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud.stream.app</groupId>
				<artifactId>${appName}-app-dependencies</artifactId>
				<version>2.0.1.RELEASE</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>
<profiles>
		<profile>
			<id>spring</id>
			<repositories>
				<repository>
					<id>spring-snapshots</id>
					<name>Spring Snapshots</name>
					<url>http://repo.spring.io/libs-snapshot-local</url>
					<snapshots>
						<enabled>true</enabled>
					</snapshots>
				</repository>
				<repository>
					<id>spring-milestones</id>
					<name>Spring Milestones</name>
					<url>http://repo.spring.io/libs-milestone-local</url>
					<snapshots>
						<enabled>false</enabled>
					</snapshots>
				</repository>
				<repository>
					<id>spring-releases</id>
					<name>Spring Releases</name>
					<url>http://repo.spring.io/release</url>
					<snapshots>
						<enabled>false</enabled>
					</snapshots>
				</repository>
				<repository>
					<id>spring-libs-release</id>
					<name>Spring Libs Release</name>
					<url>http://repo.spring.io/libs-release</url>
					<snapshots>
						<enabled>false</enabled>
					</snapshots>
				</repository>
				<repository>
					<snapshots>
						<enabled>false</enabled>
					</snapshots>
					<id>spring-milestone-release</id>
					<name>Spring Milestone Release</name>
					<url>http://repo.spring.io/libs-milestone</url>
				</repository>
			</repositories>
			<pluginRepositories>
<pluginRepository><id>spring-releases></id><name>Spring Releases</name><url>http://repo.spring.io/libs-release</url></pluginRepository>
				<pluginRepository>
					<id>spring-snapshots</id>
					<name>Spring Snapshots</name>
					<url>http://repo.spring.io/libs-snapshot-local</url>
					<snapshots>
						<enabled>true</enabled>
					</snapshots>
				</pluginRepository>
				<pluginRepository>
					<id>spring-milestones</id>
					<name>Spring Milestones</name>
					<url>http://repo.spring.io/libs-milestone-local</url>
					<snapshots>
						<enabled>false</enabled>
					</snapshots>
				</pluginRepository>
			</pluginRepositories>
		</profile>
	</profiles>

</project>
