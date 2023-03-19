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


//JAVA
//RC_SCPB
//processsing the main queries 
String tempMainQuery = "";
if (mainColumn.contains(",")) {
    String[] mainColumns = mainColumn.split(",");
    String[] tempColumns = new String[mainColumns.length];
    for (column : mainColumns) {
        String[] temp_column = column("\\.");
        temp_column[temp_column.length - 1] = "`" + temp_column[temp_column.length - 1] + "`";
        column = String.join(".",temp_column);
        tempColumns.add(column);
    }
    String[] tempQuery = new String[mainColumns.length];
    for (int i = 0; i < mainColumns.length; i++) {
        tempQuery.add(main_query);
        tempQuery.set(i, tempQuery.get(i).replace("main_column", mainColumns[i]));       
    }
    tempMainQuery = String.join(" and ", tempQuery);
} else {
    String[] temp_column = mainColumn.split("\\.");
    temp_column[temp_column.length - 1] = "`" + temp_column[temp_column.length - 1] + "`";
    mainColumn = String.join(".", temp_column);
    tempMainQuery = main_query;
    tempMainQuery = tempMainQuery.replace("main_column", mainColumn);
}

//processing the sub queries (if there are any)
String tempSubQuery = "";
if (subTable.length() > 0) {    
    if (subColumn.contains(",")) {
        String[] subColumns = subColumn.split(",");
        String[] tempColumns = new String[subColumns.length];
        for (String column : subColumns) {
            String[] temp_column = column.split("\\.");
            temp_column[temp_column.length - 1] = "`" + temp_column[temp_column.length - 1] + "`";
            column = String.join(".", temp_column);
            tempColumns.add(column);
        }
        String[] tempQuery = new String[subColumns.length];
        for (int i = 0; i < subColumns.length; i++) {
            tempQuery.add(sub_query);
            tempQuery.set(i, tempQuery.get(i).replace("sub_column", subColumns[i]));
            tempQuery.set(i, tempQuery.get(i).replace("sub_table", subColumns[i].split("\\.")[0]));
        }
        tempSubQuery = String.join(" and ", tempQuery);
    } else {
        String[] temp_column = subColumn.split("\\.");
        temp_column[temp_column.length - 1] = "`" + temp_column[temp_column.length - 1] + "`";
        subColumn = String.join(".", temp_column);
        tempSubQuery = sub_query;
        tempSubQuery = tempSubQuery.replace("sub_column", subColumn);
        tempSubQuery = tempSubQuery.replace("sub_table", subColumn.split("\\.")[0]);
    }
}

//processing the dependency queries (if there are any)
String tempDependencyQuery = "";
if (dependencyTable.length() > 0) {
    if (dependencyColumn.contains(",")) {
        String[] dependencyColumns = dependencyColumn.split(",");
        String[] tempColumns = new String[dependencyColumns.length];
        for (String column : dependencyColumns) {
            String[] temp_column = column.split("\\.");
            temp_column[temp_column.length - 1] = "`" + temp_column[temp_column.length - 1] + "`";
            column = String.join(".", temp_column);
            tempColumns.add(column);
        }
        String[] tempQuery = new String[dependencyColumns.length];
        for (int i = 0; i < dependencyColumns.length; i++) {
            tempQuery.add(dependency_query);
            tempQuery.set(i, tempQuery.get(i).replace("dependency_column", dependencyColumns[i]));
        }
        tempDependencyQuery = String.join(" and ", tempQuery);
    } else {
        String[] temp_column = dependencyColumn.split("\\.");
        temp_column[temp_column.length - 1] = "`" + temp_column[temp_column.length - 1] + "`";
        dependencyColumn = String.join(".", temp_column);
        tempDependencyQuery = dependency_query;
        tempDependencyQuery = tempDependencyQuery.replace("dependency_column", dependencyColumn);
    }
    tempDependencyQuery = tempDependencyQuery.replace("source_name", source_name);
    if (mainColumn.contains(",")) {
        tempDependencyQuery = tempDependencyQuery.replace("main_column", mainColumn.split(",")[0]);
    } else {
        tempDependencyQuery = tempDependencyQuery.replace("main_column", mainColumn);
    }
}

//drafting the condition query
String mainQuery = "("+tempMainQuery+")";
String subQuery = "("+tempSubQuery+")";
String dependencyQuery = "("+tempDependencyQuery+")";


String conditionQuery = mainQuery;
if (!subQuery.isEmpty() || !dependencyQuery.isEmpty()) {
    conditionQuery = conditionQuery + " and ";
}
if (!subQuery.isEmpty()) {
    conditionQuery = conditionQuery + "(" + subQuery + ")";
    if (!subColumn.contains(",")) {
        conditionQuery = conditionQuery + " and ";
    }
}
if (!dependencyQuery.isEmpty()) {
    conditionQuery = conditionQuery + "(" + dependencyQuery + ")";
    if (!dependencyColumn.contains(",")) {
        conditionQuery = conditionQuery + " and ";
    }
}

String updateQuery = "update " + dbSchema + "." + currentTableName + " set `" + error_code_column + "` ='" + rule_code + "' where " + conditionQuery;
System.out.println("updateQuery: "+updateQuery);

