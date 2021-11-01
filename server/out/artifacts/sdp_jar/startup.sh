java -Dserver.port=9999 -Dspring.datasource.url="jdbc:h2:$PWD/sdp;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1;MODE=MYSQL;DATABASE_TO_LOWER=TRUE" -jar sdp.jar
