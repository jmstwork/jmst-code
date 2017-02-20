echo off
set TERMBASE= ..
set ENV=sit
set spring.profiles.active = oracle
set cloveretl.plugins=D:\opt\founder\batch\termSync\plugins
set properties=D:\opt\founder\batch\termSync\properties
set JAVA_HOME=D:\opt\founder\jdk7u11_32
set CLASSPATH=
FOR %%i IN (%TERMBASE%\lib\*.jar) DO CALL setCP.bat %%i
set CLASSPATH=%CLASSPATH%;%properties%
echo %CLASSPATH%
%JAVA_HOME%\bin\java -DINSTANCE=pro1 -cp "%CLASSPATH%" com.founder.fmdm.MainApp pro1
