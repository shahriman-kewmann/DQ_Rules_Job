String dbSchema = "dummy_zone";
String currentTableName = "dummy_table";

String mainColumn = "flmbgc";
String subColumn = "faxtdt,ftttts";

String mainTable = "dummy_table_main,dummy_table_main2";
String subTable = "dummy_table_sub";

String dependencyTable = "dummy_dep_table";
String dependencyColumn = "dummy_dep_column";

String main_query = "(`main_column` is null)";
String sub_query = "(select `sub_column` from "+dbSchema+".subtable where `sub_column` is not null)";
String dependency_query = "(`dependency_column` = '')";

String tempSubQuery = "";
if (subTable != null || subTable.length() != 0) {
	if (subColumn.contains(",")) {
		String[] subColumns = subColumn.split(",");
        String[] tempQuery = new String[subColumns.length]; 
		for (int i=0; i < subColumns.length; i++) {
            tempQuery[i] = sub_query;
			tempQuery[i] = tempQuery[i].replace("sub_column", subColumns[i]);
		}
        tempSubQuery = String.join(" and ", tempQuery); 
	} else {
		tempSubQuery = sub_query;
        tempSubQuery = tempSubQuery.replace("sub_column", subColumn);
	}
} 

String tempMainQuery = "";
if (mainColumn.contains(",")) {
	String[] mainColumns = mainColumn.split(",");
	String[] tempQuery = new String[mainColumns.length];
	for (int i=0; i < mainColumns.length; i++) {
		tempQuery[i] = main_query;
		tempQuery[i] = tempQuery[i].replace("main_column", mainColumns[i]);
	}
	tempMainQuery = String.join(" and ", tempQuery); 
} else {
	tempMainQuery = main_query;
	tempMainQuery = tempMainQuery.replace("main_column", mainColumn);
}

String tempDependencyQuery = "";
if (dependencyTable != null || dependencyTable.length() != 0) {
	if (dependencyColumn.contains(",")) {
		String[] dependencyColumns = dependencyColumn.split(",");
		String[] tempQuery = new String[dependencyColumns.length];
		for (int i=0; i < dependencyColumns.length; i++) {
			tempQuery[i] = dependency_query;
			tempQuery[i] = tempQuery[i].replace("dependency_column", dependencyColumns[i]);
		}
		tempDependencyQuery = String.join(" and ", tempQuery); 
	} else {
		tempDependencyQuery = dependency_query;
		tempDependencyQuery = tempDependencyQuery.replace("dependency_column", dependencyColumn);
	}
}

String mainQuery = "("+tempMainQuery+")";
String subQuery = "("+tempSubQuery+")";
String dependencyQuery = "("+tempDependencyQuery+")";


String conditionQuery = mainQuery;

if (subQuery != null || subQuery != "") {
	conditionQuery += " and "+subQuery + "";
} 

if (dependencyQuery != null || dependencyQuery != "") {
	conditionQuery += " and "+dependencyQuery + "";
} 

String updateQuery = "update "+dbSchema+"."+currentTableName+" where "+conditionQuery + "";
System.out.println("updateQuery: "+updateQuery); 


// Testing on this using github
