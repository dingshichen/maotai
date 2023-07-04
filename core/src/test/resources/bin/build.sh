#!/bin/zsh

if [ $# -lt 1 ]
then
  echo "Usage: $0 [mysql]"
  exit
fi

LOCATION=./antlr-4.12.0-complete.jar
case "$1" in
  "mysql") echo "Building MySQL parser"
    java -jar $LOCATION -Dlanguage=Java -listener -visitor -o ../code -package parsers ../MySQL/lang/MySqlLexer.g4 ../MySQL/lang/MySqlParser.g4
  ;;
  "pg") echo "Building PostgreSQL parser"
    java -jar $LOCATION -Dlanguage=Java -listener -visitor -o ../code -package parsers ../PostgreSQL/lang/PostgreSQLLexer.g4 ../PostgreSQL/lang/PostgreSQLParser.g4
  ;;
 *) echo "Unknown parser type $1"
 exit 1
 ;;
esac
