buildscript {
	repositories {
		jcenter()
	}
	dependencies {
		classpath 'org.xtext:xtext-gradle-plugin:1.0.18'
	}
}

subprojects {
	ext.xtextVersion = '2.13.0'
	repositories {
		jcenter()
	}
<#noparse>
	apply plugin: 'java'
	apply plugin: 'org.xtext.xtend'
	apply from: "${rootDir}/gradle/source-layout.gradle"
	apply from: "${rootDir}/gradle/maven-deployment.gradle"
	apply plugin: 'eclipse'
	apply plugin: 'idea'
</#noparse>
	group = '${project.basePackage}.${project.name}'
	version = '${project.version}-SNAPSHOT'
	
	sourceCompatibility = '1.8'
	targetCompatibility = '1.8'
	
	configurations.all {
		exclude group: 'asm'
	}
}

defaultTasks 'generateXtext'
