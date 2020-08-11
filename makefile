# 314825191	
# elesawm
compile: bin
	javac -cp biuoop-1.4.jar:src -d bin src/*/*.java src/Ass5Game.java
run:	
	java -cp biuoop-1.4.jar:bin Ass5Game
jar:
	jar cvfm ass5game.jar MANIFEST.MF -C bin .
bin:
	mkdir bin