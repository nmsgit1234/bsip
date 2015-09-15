set DERBY_HOME=E:\Inst\db-derby-10.2.1.6-bin

set CLASSPATH=%DERBY_HOME%\lib\derby.jar;%DERBY_HOME%\lib\derbyclient.jar;%DERBY_HOME%\lib\derbyLocale_de_DE.jar;%DERBY_HOME%\lib\derbyLocale_es.jar;%DERBY_HOME%\lib\derbynet.jar;%DERBY_HOME%\lib\derbyrun.jar;%DERBY_HOME%\lib\derbytools.jar;%CLASSPATH%

set DB_PATH=jdbc:derby://localhost:1527/catalogdb;create=true

java -Dij.protocol=jdbc:derby: -Dij.database=%DB_PATH% -Dij.user=nshaikh -Dij.password=nshaikh org.apache.derby.tools.ij