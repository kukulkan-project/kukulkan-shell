grammar ${project.basePackage}.${project.name}.${project.name?cap_first} with org.eclipse.xtext.common.Terminals

generate ${project.name} "http://www.infotec.mx/dads/${project.name}/${project.name?cap_first}"

Model:
	greetings+=Greeting*;
	
Greeting:
	'Hello' name=ID '!';