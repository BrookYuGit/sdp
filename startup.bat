@set H2_DATABASE=%cd%/sdp

@set SDP_PORT=9999

@title SDP H2(%H2_DATABASE%)
@echo Please wait...
@java -Dfile.encoding=utf8 -Dserver.port=%SDP_PORT% -Dspring.datasource.url="jdbc:h2:%H2_DATABASE%;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1;MODE=MYSQL;DATABASE_TO_LOWER=TRUE" -jar server\sdp\target\sdp-1.7.0.jar 1> sdp.log
@echo ---------------------------------
@echo Server down!
@echo ---------------------------------
pause
