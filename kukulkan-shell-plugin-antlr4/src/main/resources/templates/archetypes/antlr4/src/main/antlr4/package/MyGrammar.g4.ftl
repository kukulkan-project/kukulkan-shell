grammar ${project.grammarName};

start
:
	'hello' 'KUKULKAN'
;

WS
:
	[ \t\r\n]+ -> skip
;