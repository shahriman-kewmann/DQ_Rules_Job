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

String main_query = "(main_column = '' or length(main_column) = 0)";
String main_query1 = "(main_column1 = '') or (main_column2 = '')";
String sub_query = "(`_uuid` in (select sub_table.`_uuid` from dbSchema.sub_table where sub_column is not null or sub_table.sub_column != ''))";
String sub_query1 = "(`_uuid` in (select sub_table.`_uuid` from dbSchema.sub_table where sub_column is not null or sub_table.sub_column != ''))";
String dependency_query = "";
String dependency_query1 = "";

String error_code_column = error_code_option = "mandatory" ? "_dq_error_code_mandatory" : "_dq_error_code_optional";


String tempMainQuery = "";
if (mainColumn.contains(",")) {
	String[] mainColumns = mainColumn.split(",");
	String[] tempQuery = new String[mainColumns.length];
	for (int i=0; i < mainColumns.length; i++) {
		tempQuery[i] = main_query;
		tempQuery[i] = tempQuery[i].replace("main_column", mainColumns[i]);
        temp_column[-1] = "`" + temp_column[-1] + "`";
	}
	tempMainQuery = String.join(" and ", tempQuery); 
} else {
	tempMainQuery = main_query;
	tempMainQuery = tempMainQuery.replace("main_column", mainColumn);
}








