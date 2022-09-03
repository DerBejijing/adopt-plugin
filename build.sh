#!/bin/bash

SRCDIR=./src
PLUGIN_YML="plugin.yml"
JARFILE=Adopt
REPOSITORY=https://github.com/derbejijing/adopt-plugin
SERVER_JAR_DOWNLOAD="https://cdn.getbukkit.org/spigot/spigot-1.16.4.jar"
MAIN=io.github.derbejijing.adopt.Main

CLASSPATH_JAR="server.jar"
MAINPATH=$(echo $MAIN | tr "." "/")
MAINPATH="${MAINPATH}.class"

COLOR_GREEN='\033[0;32m'
COLOR_LIGHT_BLUE='\033[1;34m'
COLOR_YELLOW='\033[1;33m'
COLOR_RESET='\033[0m'

if ! [[ -f "$CLASSPATH_JAR" ]]; then
	curl $SERVER_JAR_DOWNLOAD -o $CLASSPATH_JAR
fi

cd $SRCDIR

find . -name "*.java" > sources.txt

echo -e "${COLOR_YELLOW}Compiling classes..."
javac -cp ../${CLASSPATH_JAR} @sources.txt
echo -e "${COLOR_GREEN}Done compiling!${COLOR_RESET}"


echo -e "${COLOR_YELLOW}\nBuilding jar file:"
jar -cfe ${JARFILE}_tmp.jar $MAIN $MAINPATH

echo -e "Classes... ["
find . -name "*.class" > sources.txt
while read line; do
	echo -e ${COLOR_LIGHT_BLUE}$line
	jar -uf ${JARFILE}_tmp.jar $line
done < sources.txt
echo -e "${COLOR_YELLOW}] ...Classes${COLOR_RESET}"


echo -e "${COLOR_YELLOW}\nPlugin YML... ["
echo -e ${COLOR_LIGHT_BLUE}${PLUGIN_YML}
jar -uf ${JARFILE}_tmp.jar $PLUGIN_YML
echo -e "${COLOR_YELLOW}] ...Plugin YML"
echo -e "${COLOR_GREEN}\nDone packing!"


echo -e "${COLOR_YELLOW}\nCleaning..."
rm sources.txt
rm -rf *.class
find . -name '*.class' -delete
chmod +x ${JARFILE}_tmp.jar
mv ${JARFILE}_tmp.jar ../${JARFILE}.jar
echo -e "${COLOR_GREEN}Done cleaning"

echo -e "\nSuccessfully built jar file \"${JARFILE}.jar\"!${COLOR_RESET}"
