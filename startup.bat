:run
if "%1" == "-debug" goto debug
if "%1" == "-noconsole" goto noconsole
java -Dappdir=.. -cp ../lib/jdom.jar;../lib/log4j.jar;../lib/lti-civil.jar;../lib/fmj.jar;../lib/startup.jar;../lib/windows/jdic.jar;../resources;../lib/windows;../lib/JTattoo.jar; -Djava.library.path="../lib/windows" -Dfile.encoding=utf-8 org.jivesoftware.launcher.Startup
goto end

:noconsole
javaw -Dappdir=.. -cp ../lib/jdom.jar;../lib/log4j.jar;../lib/lti-civil.jar;../lib/fmj.jar;../lib/startup.jar;../lib/windows/jdic.jar;../resources;../lib/windows;../lib/JTattoo.jar; -Djava.library.path="../lib/windows" -Dfile.encoding=utf-8 org.jivesoftware.launcher.Startup
goto end

:debug
start "Spark" "%JAVA_HOME%\bin\java" -Ddebugger=true -Ddebug.mode=true -XX:+HeapDumpOnOutOfMemoryError -Xdebug -Xint -server -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 -Dappdir=.. -cp ../lib/jdom.jar;../lib/log4j.jar;../lib/lti-civil.jar;../lib/fmj.jar;../lib/startup.jar;../lib/windows/jdic.jar;../resources;../lib/windows;../lib/JTattoo.jar; -Dfile.encoding=utf-8 org.jivesoftware.launcher.Startup
goto end
:end
