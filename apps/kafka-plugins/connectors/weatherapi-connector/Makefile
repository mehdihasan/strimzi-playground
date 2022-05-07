CONNECT_PLUGIN_PATH=../../../../build-connect/my-plugins
FILE_TO_REMOVE=/weatherapi-connector-*
FILE_TO_ADD=target/*-jar-with-dependencies.jar

all: delete_old_jar maven_build place_jar

delete_old_jar:
	echo "removing old weatherapi-connector"
	[ -f $(CONNECT_PLUGIN_PATH)$(FILE_TO_REMOVE) ] && rm $(CONNECT_PLUGIN_PATH)$(FILE_TO_REMOVE) || echo "$(FILE_TO_REMOVE) does not exist."

maven_build:
	echo "building new weatherapi-connector connector"
	mvn clean package

place_jar:
	echo "place the weatherapi-connector in the connect plugin folder"
	cp $(FILE_TO_ADD) $(CONNECT_PLUGIN_PATH)