String createCleanQuery = "";
String setPkQuery = "";
System.out.println("raw table exist? :"+(boolean)globalMap.get("rawTableExist"));
if ((boolean)globalMap.get("rawTableExist") == true) {
	createCleanQuery = "create table if not exists " + context.hiveCleanZoneDb_DEV + "." + (String)globalMap.get("tableName") + " like " + context.hiveRawZoneDb_DEV + "." + (String)globalMap.get("tableName");
} else {
	createCleanQuery = "show tables in " + context.hiveRawZoneDb_DEV;
}

globalMap.put("createCleanQuery", createCleanQuery);
System.out.println("\n"+(String)globalMap.get("createCleanQuery")+"\n");

String connectionUrl2 = String.format("jdbc:hive2://%s:%s/%s;principal=%s;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2;ssl=true;sslTrustStore=%s;trustStorePassword=%s", 
context.getStringValue("hiveHost"), 
context.getStringValue("hivePort"), 
context.getStringValue("hiveRawZoneDb"), 
context.getStringValue("hivePrincipal"),
context.getStringValue("hiveSslTrustStorePath"),
context.getStringValue("hiveSslTrustStorePassword"));
java.sql.Connection con2 = java.sql.DriverManager.getConnection(connectionUrl2, "", "");
java.sql.Statement stmt2 = con2.createStatement();
String showSql1 = "show tables in " + context.hiveRawZoneDb_DEV;
java.sql.ResultSet rs2 = stmt2.executeQuery(showSql1);
java.sql.ResultSetMetaData rsmd2 = rs2.getMetaData();
java.util.List<String> cleanTables = new java.util.ArrayList<String>();

while (rs2.next()) {
	String tabname1 = rs2.getString(1);
	cleanTables.add(tabname1);
}

boolean cleanTableExist = cleanTables.contains((String)globalMap.get("tableName"));
globalMap.put("cleanTableExist", cleanTableExist);
System.out.println(globalMap.get("cleanTableExist"));

rs2.close();
stmt2.close();
con2.close();

if (((boolean)globalMap.get("rawTableExist") == true) and ((boolean)globalMap.get("cleanTableExist") == false)) {
	setPkQuery = "ALTER TABLE " + context.hiveCleanZoneDb_DEV + "." + (String)globalMap.get("tableName") + " ADD CONSTRAINT pk_ PRIMARY KEY (`_uuid`) DISABLE NOVALIDATE";
} else { 
	setPkQuery = "show tables in " + context.hiveCleanZoneDb_DEV;
}

globalMap.put("setPkQuery", setPkQuery);
System.out.println((String)globalMap.get("setPkQuery")+"\n");








 