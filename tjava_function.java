public static String RC_SCPB(String dbSchema, String source_name, String currentTableName, String error_code_option, String rule_code,
                             String mainTable, String subTable, String dependencyTable,
                             String mainColumn, String subColumn, String dependencyColumn,
                             String main_query, String sub_query, String dependency_query) {

    String error_code_column = (error_code_option.equals("mandatory")) ? "_dq_error_code_mandatory" : "_dq_error_code_optional";

    // processing the main queries
    String tempMainQuery = "";
    if (mainColumn.contains(",")) {
        String[] mainColumns = mainColumn.split(",");
        String[] tempColumns = new String[mainColumns.length];
        for (int i = 0; i < mainColumns.length; i++) {
            String[] temp_column = mainColumns[i].split("\\.");
            temp_column[temp_column.length - 1] = "`" + temp_column[temp_column.length - 1] + "`";
            String column = String.join(".", temp_column);
            tempColumns[i] = column;
        }
        String[] tempQuery = new String[mainColumns.length];
        for (int i = 0; i < mainColumns.length; i++) {
            tempQuery[i] = main_query.replace("main_column", mainColumns[i]);
        }
        tempMainQuery = String.join(" and ", tempQuery);
    } else {
        String[] temp_column = mainColumn.split("\\.");
        temp_column[temp_column.length - 1] = "`" + temp_column[temp_column.length - 1] + "`";
        mainColumn = String.join(".", temp_column);
        tempMainQuery = main_query.replace("main_column", mainColumn);
    }

    // processing the sub queries (if there are any)
    String tempSubQuery = "";
    if (!subTable.isEmpty()) {
        if (subColumn.contains(",")) {
            String[] subColumns = subColumn.split(",");
            String[] tempColumns = new String[subColumns.length];
            String[] tempQuery = new String[subColumns.length];
            for (int i = 0; i < subColumns.length; i++) {
                String[] temp_column = subColumns[i].split("\\.");
                temp_column[temp_column.length - 1] = "`" + temp_column[temp_column.length - 1] + "`";
                String column = String.join(".", temp_column);
                tempColumns[i] = column;
                tempQuery[i] = sub_query.replace("sub_column", subColumns[i]);
                tempQuery[i] = tempQuery[i].replace("sub_table", subColumns[i].split("\\.")[0]);
            }
            tempSubQuery = String.join(" and ", tempQuery);
        } else {
            String[] temp_column = subColumn.split("\\.");
            temp_column[temp_column.length - 1] = "`" + temp_column[temp_column.length - 1] + "`";
            subColumn = String.join(".", temp_column);
            tempSubQuery = sub_query.replace("sub_column", subColumn);
            tempSubQuery = tempSubQuery.replace("sub_table", subColumn.split("\\.")[0]);
        }
    }

    // processing the dependency queries (if there are any )




    //drafting the condition query 


                             }