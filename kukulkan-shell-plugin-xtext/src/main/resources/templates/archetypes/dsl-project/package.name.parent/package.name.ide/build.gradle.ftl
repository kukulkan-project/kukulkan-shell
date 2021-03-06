plugins {
	id 'com.github.johnrengelman.shadow' version '2.0.0'
}

dependencies {
	compile project(':${project.basePackage}.${project.name}')
<#noparse>	compile "org.eclipse.xtext:org.eclipse.xtext.ide:${xtextVersion}"
	compile "org.eclipse.xtext:org.eclipse.xtext.xbase.ide:${xtextVersion}"</#noparse>
}

apply plugin: 'application'
apply plugin: 'com.github.johnrengelman.shadow'
mainClassName = "org.eclipse.xtext.ide.server.ServerLauncher"

shadowJar {
	from(project.convention.getPlugin(JavaPluginConvention).sourceSets.main.output)
	configurations = [project.configurations.runtime]
	exclude('META-INF/INDEX.LIST', 'META-INF/*.SF', 'META-INF/*.DSA', 'META-INF/*.RSA','schema/*',
		'.options', '.api_description', '*.profile', '*.html', 'about.*', 'about_files/*',
		'plugin.xml', 'modeling32.png', 'systembundle.properties', 'profile.list')
	classifier = 'ls'
	append('plugin.properties')
}
