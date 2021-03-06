<#noparse>dependencies {
	testCompile "junit:junit:4.12"
	testCompile "org.eclipse.xtext:org.eclipse.xtext.testing:${xtextVersion}"
	testCompile "org.eclipse.xtext:org.eclipse.xtext.xbase.testing:${xtextVersion}"
	compile "org.eclipse.xtext:org.eclipse.xtext:${xtextVersion}"
	compile "org.eclipse.xtext:org.eclipse.xtext.xbase:${xtextVersion}"
}
configurations {
	mwe2 {
		extendsFrom compile
	}
}

dependencies {
	mwe2 "org.eclipse.emf:org.eclipse.emf.mwe2.launch:2.9.1.201705291010"
	mwe2 "org.eclipse.xtext:org.eclipse.xtext.common.types:${xtextVersion}"
	mwe2 "org.eclipse.xtext:org.eclipse.xtext.xtext.generator:${xtextVersion}"
	mwe2 "org.eclipse.xtext:xtext-antlr-generator:[2.1.1, 3)"
}
</#noparse>
task generateXtextLanguage(type: JavaExec) {
	main = 'org.eclipse.emf.mwe2.launch.runtime.Mwe2Launcher'
	classpath = configurations.mwe2
	inputs.file "src/main/java/${project.basePackage?replace(".", "/")}/${project.name}/Generate${project.name?cap_first}.mwe2"
	inputs.file "src/main/java/${project.basePackage?replace(".", "/")}/${project.name}/${project.name?cap_first}.xtext"
	outputs.dir "src/main/xtext-gen"
	args += "src/main/java/${project.basePackage?replace(".", "/")}/${project.name}/Generate${project.name?cap_first}.mwe2"
	args += "-p"
	args += "rootPath=/<#noparse>${projectDir}</#noparse>/.."
}

generateXtext.dependsOn(generateXtextLanguage)
clean.dependsOn(cleanGenerateXtextLanguage)
eclipse.classpath.plusConfigurations += [configurations.mwe2]
