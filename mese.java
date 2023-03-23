String dbSchema = "dummy_zone";
String currentSourceName = "DSN";

String currentTableName = "dummy_mis006";
String error_code_option = "mandatory";
String rule_code = "RC_dummy_1";

String mainTable = "dummy_mis006";
String subTable = "dummy_tcs_jfacif";
String dependencyTable = "";

String mainColumn = "dummy_mis006.M_STCODE,dummy_mis006.M_POSTCODE";
String subColumn = "dummy_tcs_jfacif.POSTCODE";
String dependencyColumn = "";

String main_intra_op = "and";
String main_inter_op = "or";
String sub_intra_op = "and";
String sub_inter_op = "or";
String dep_intra_op = "and";

String main_query = "(main_column = '' or length(main_column) = 0)";
String sub_query = "(`_uuid` in (select sub_table.`_uuid` from dbSchema.sub_table where sub_column is not null or sub_table.sub_column != ''))";
String dependency_query = "";

String error_code_column = error_code_option.equals("mandatory") ? "_dq_error_code_mandatory" : "_dq_error_code_optional";
System.out.println(error_code_column);

String tempMainQuery = "";
if (mainColumn.contains(",")) {
	String[] mainColumns = mainColumn.split(",");
	String[] tempColumns = new String[mainColumns.length];
	for (int i=0; i < mainColumns.length; i++) {
		String temp_column = mainColumns[i];
		String[] temp_columns = temp_column.split("\\.");
		temp_columns[temp_columns.length-1] = "`"+temp_columns[temp_columns.length-1]+"`";
		temp_column = String.join(".", temp_columns);
		tempColumns[i] = temp_column;
	}

	String[] tempQuery = new String[mainColumns.length];
	for (int i=0; i < mainColumns.length; i++) {
		tempQuery[i] = main_query;
		tempQuery[i] = tempQuery[i].replace("main_column", mainColumns[i]);
	}
	tempMainQuery = String.join(" "+main_intra_op+" ", tempQuery); 
} else {
	String[] temp_columns = mainColumn.split("\\.");
	temp_columns[temp_columns.length-1] = "`"+temp_columns[temp_columns.length-1]+"`";
	mainColumn = String.join(".", temp_columns);
	tempMainQuery = main_query;
	tempMainQuery = tempMainQuery.replace("main_column", mainColumn);
}

String tempSubQuery = "";
if (subTable.length() > 0) {
	if (subColumn.contains(",")) {
		String[] subColumns = subColumn.split(",");
		String[] tempColumns = new String[subColumns.length];
		for (int i=0; i < subColumns.length; i++) {
			String[] temp_columns = subColumns[i].split("\\.");
			temp_columns[temp_columns.length-1] = "`"+temp_columns[temp_columns.length-1]+"`";
			subColumns[i] = String.join(".", temp_columns);
			tempColumns[i] = subColumns[i];
		}

		String[] tempQuery = new String[subColumns.length];
		for (int i=0; i < subColumns.length; i++) {
			tempQuery[i] = sub_query;
			tempQuery[i] = tempQuery[i].replace("sub_column", subColumns[i]);
			tempQuery[i] = tempQuery[i].replace("sub_table", subColumns[i].split("\\.")[0]);
		}
		tempSubQuery = String.join(" "+sub_intra_op+" ", tempQuery); 
	} else {
		String[] temp_columns = subColumn.split("\\.");
		temp_columns[temp_columns.length-1] = "`"+temp_columns[temp_columns.length-1]+"`";
		subColumn = String.join(".", temp_columns);
		tempSubQuery = sub_query;
		tempSubQuery = tempSubQuery.replace("sub_column", subColumn);
		tempSubQuery = tempSubQuery.replace("sub_table", subColumn.split("\\.")[0]);
	}
}

String tempDependencyQuery = "";
if (dependencyTable.length() > 0) {
	if (dependencyColumn.contains(",")) {
		String[] dependencyColumns = dependencyColumn.split(",");
		String[] tempColumns = new String[dependencyColumns.length];
		for (int i=0; i < dependencyColumns.length; i++) {
			String[] temp_columns = dependencyColumns[i].split("\\.");
			temp_columns[temp_columns.length-1] = "`"+temp_columns[temp_columns.length-1]+"`";
			dependencyColumns[i] = String.join(".", temp_columns);
			tempColumns[i] = dependencyColumns[i];
		}

		String[] tempQuery = new String[dependencyColumns.length];
		for (int i=0; i < dependencyColumns.length; i++) {
			tempQuery[i] = dependency_query;
			tempQuery[i] = tempQuery[i].replace("dependency_column", dependencyColumns[i]);
			tempQuery[i] = tempQuery[i].replace("dependency_table", dependencyColumns[i].split("\\.")[0]);
		}
		tempDependencyQuery = String.join(" and ", tempQuery); 
	} else {
		String[] temp_columns = dependencyColumn.split(".");
		temp_columns[temp_columns.length-1] = "`"+temp_columns[temp_columns.length-1]+"`";
		dependencyColumn = String.join(".", temp_columns);
		tempDependencyQuery = dependency_query;
		tempDependencyQuery = tempDependencyQuery.replace("dependency_column", dependencyColumn);
		tempDependencyQuery = tempDependencyQuery.replace("dependency_table", dependencyColumn.split("\\.")[0]);
	}
	if (mainColumn.contains(",")) {
		for (int i=0; i < mainColumns.length; i++) {
			String temp_column = mainColumns[i];
			String[] temp_columns = temp_column.split("\\.");
			temp_columns[temp_columns.length-1] = "`"+temp_columns[temp_columns.length-1]+"`";
			temp_column = String.join(".", temp_columns);
			tempColumns[i] = temp_column;
			tempDependencyQuery = tempDependencyQuery.replace("main_column", tempColumns[i]);
		}
	} else {
		tempDependencyQuery = tempDependencyQuery.replace("main_column", mainColumn);
	}
}

String mainQuery = tempMainQuery;
String subQuery = tempSubQuery;
String dependencyQuery = tempDependencyQuery;
//String conditionQuery = mainQuery;
String conditionQuery = ((subQuery != "") || dependencyQuery != "") ? "("+mainQuery+")" : mainQuery;

if (subQuery != null || subQuery != "") {
	conditionQuery += subColumn.contains(",") ? " "+main_inter_op+" ("+subQuery+")" : " "+main_inter_op+" "+subQuery;
} 

if (dependencyQuery != null || dependencyQuery != "") {
	conditionQuery += dependencyColumn.contains(",") ? " "+sub_inter_op+" ("+dependencyQuery+")" : " "+sub_inter_op+" "+dependencyQuery;
} 

String updateQuery = "update "+dbSchema+"."+currentTableName+" set `"+error_code_column+"` = '"+rule_code+"' where "+conditionQuery;
System.out.println("updateQuery: "+updateQuery);
