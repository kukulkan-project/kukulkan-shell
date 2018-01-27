grammar ${grammar.name};

start
:
	'hello' 'KUKULKAN'
;

WS
:
	[ \t\r\n]+ -> skip
;