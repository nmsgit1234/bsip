set CLASSPATH=H:\nshaikh\JavaDB\lib\derby.jar;H:\nshaikh\JavaDB\lib\derbyclient.jar;H:\nshaikh\JavaDB\lib\derbyLocale_de_DE.jar;H:\nshaikh\JavaDB\lib\derbyLocale_es.jar;H:\nshaikh\JavaDB\lib\derbynet.jar;H:\nshaikh\JavaDB\lib\derbyrun.jar;H:\nshaikh\JavaDB\lib\derbytools.jar;%CLASSPATH%

set DB_PATH=H:/nshaikh/personal/Projects/NMS/apps/BSI/BSI-war/setup/db/databases/testdb

java -Dij.protocol=jdbc:derby: -Dij.database=%DB_PATH% org.apache.derby.tools.ij