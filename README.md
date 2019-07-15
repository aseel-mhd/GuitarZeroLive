# GuitarZeroLive
Simplified version of the Guitar Hero Live game. This game allows players to simulate playing lead, rhythm, and bass guitars on a veriety of rock music tracks. 

Can be played using either the keyboard or a plastic guitar.


# Compile Instructions


LINUX

CLASSPATH=jinput-2.0.9.jar:.
export CLASSPATH
javac *.java
java -Djava.library.path=. Main


WINDOWS

set CLASSPATH=jinput-2.0.9.jar;.
javac *.java
java -Djava.library.path=. Main
