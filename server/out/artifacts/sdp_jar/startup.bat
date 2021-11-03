@set H2_DATABASE=%cd%/sdp

@set SDP_PORT=9999

@title SDP H2(%H2_DATABASE%)
@echo ------------------------------------------------------------------------
@echo Welcome to SDP's world via the following URL:
@echo.
@echo     http://localhost:%SDP_PORT%
@echo.
@echo Browse http://localhost:%SDP_PORT%/db to view the database.
@echo The running output stored into %cd%/sdp.log
@echo Access https://github.com/BrookYuGit/sdp to get the lastest source code.
@echo ------------------------------------------------------------------------
@java -Dfile.encoding=utf8 -Dserver.port=%SDP_PORT% -Dspring.datasource.url="jdbc:h2:%H2_DATABASE%;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1;MODE=MYSQL;DATABASE_TO_LOWER=TRUE" -jar sdp.jar > sdp.log
pause