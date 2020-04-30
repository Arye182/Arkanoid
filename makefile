# 200778769
# amsalea3

compile: bin
	find src -name "*.java" > sources.txt
	javac -cp biuoop-1.4.jar -d bin @sources.txt

run:
	java -cp biuoop-1.4.jar:bin:resources game.Ass7Game

jar:
	jar cvfm ass7game.jar manifest.mf -C bin geometry -C bin collidable -C bin game -C bin sprites . -C resources .

bin:
	mkdir bin