# 1 main table  
# multiple main table 

# 1 maincolumn 
# 1 maincolumn and 1 subcolumn 
# 1 maincolumn and multiple subcolumn 
# multiple maincolumn and 1 subcolumn 
# multiple maincolumn and multiple subcolumn 

def RC_SCPB(dbSchema, source_name, currentTableName, error_code_option, rule_code,
            mainTable, subTable, dependencyTable, 
            mainColumn, subColumn, dependencyColumn, 
            main_query, sub_query, dependency_query):
    
    error_code_column = "_dq_error_code_mandatory" if (error_code_option == "mandatory") else "_dq_error_code_optional"
    
    # processsing the main queries
    tempMainQuery = ""
    if ("," in mainColumn):
        mainColumns = mainColumn.split(",")
        tempColumns = []
        for column in mainColumns:
            temp_column = column.split(".")
            temp_column[-1] = "`"+temp_column[-1]+"`"
            column = ".".join(temp_column)
            tempColumns.append(column)
        tempQuery = []
        for i in range(len(mainColumns)):
            tempQuery.append(main_query)
            tempQuery[i] = tempQuery[i].replace("main_column", mainColumns[i])
        tempMainQuery = (" and ").join(tempQuery)
    else:
        temp_column = mainColumn.split(".")
        temp_column[-1] = "`"+temp_column[-1]+"`"
        mainColumn = ".".join(temp_column)
        tempMainQuery = main_query
        tempMainQuery = tempMainQuery.replace("main_column", mainColumn)
    
    # processing the sub queries (if there are any)
    tempSubQuery = ""
    if len(subTable) > 0:    
        if ("," in subColumn):
            subColumns = subColumn.split(",")
            tempColumns = []
            for column in subColumns:
                temp_column = column.split(".")
                temp_column[-1] = "`"+temp_column[-1]+"`"
                column = ".".join(temp_column)
                tempColumns.append(column)
            tempQuery = []
            for i in range(len(subColumns)):
                tempQuery.append(sub_query)
                tempQuery[i] = tempQuery[i].replace("sub_column", subColumns[i])
                tempQuery[i] = tempQuery[i].replace("sub_table", subColumns[i].split(".")[0])
            tempSubQuery = (" and ").join(tempQuery)
        else:
            temp_column = subColumn.split(".")
            temp_column[-1] = "`"+temp_column[-1]+"`"
            subColumn = ".".join(temp_column)
            tempSubQuery = sub_query
            tempSubQuery = tempSubQuery.replace("sub_column", subColumn)
            tempSubQuery = tempSubQuery.replace("sub_table", subColumn.split(".")[0])
    
    # processing the dependency queries (if there are any)
    tempDependencyQuery = ""
    if len(dependencyTable) > 0:
        if ("," in dependencyColumn):
            dependencyColumns = dependencyColumn.split(",")
            tempColumns = []
            for column in dependencyColumns:
                temp_column = column.split(".")
                temp_column[-1] = "`"+temp_column[-1]+"`"
                column = ".".join(temp_column)
                tempColumns.append(column)
            tempQuery = []
            for i in range(len(dependencyColumns)):
                tempQuery.append(dependency_query)
                tempQuery[i] = tempQuery[i].replace("dependency_column", dependencyColumns[i])
            tempDependencyQuery = (" and ").join(tempQuery)
        else:
            temp_column = dependencyColumn.split(".")
            temp_column[-1] = "`"+temp_column[-1]+"`"
            dependencyColumn = ".".join(temp_column)
            tempDependencyQuery = dependency_query
            tempDependencyQuery = tempDependencyQuery.replace("dependency_column", dependencyColumn)
        tempDependencyQuery = tempDependencyQuery.replace("source_name", source_name)
        if "," in mainColumn:
            tempDependencyQuery = tempDependencyQuery.replace("main_column", mainColumn.split(",")[0])
        else:
            tempDependencyQuery = tempDependencyQuery.replace("main_column", mainColumn)
    
    # drafting the condition query
    mainQuery = tempMainQuery
    subQuery = tempSubQuery
    dependencyQuery = tempDependencyQuery
    conditionQuery = "("+mainQuery+")" if (subQuery != "" or dependencyQuery != "") else mainQuery
    if (len(subQuery) > 0): 
        conditionQuery += " and ("+subQuery+")" if "," in subColumn else " and "+subQuery 
    if (len(dependencyQuery) > 0): 
        conditionQuery += " and ("+dependencyQuery+")" if "," in dependencyColumn else " and "+dependencyQuery 

    updateQuery = "update "+dbSchema+"."+currentTableName+" set `"+error_code_column+"` ='"+rule_code+"' where "+conditionQuery
    return updateQuery

def RC_MCPB(dbSchema, source_name, currentTableName, error_code_option, rule_code,
            mainTable, subTable, dependencyTable, 
            mainColumn, subColumn, dependencyColumn, 
            main_query, sub_query, dependency_query):
    error_code_column = "_dq_error_code_mandatory" if (error_code_option == "mandatory") else "_dq_error_code_optional"
    
    # processsing the main queries
    tempMainQuery = ""
    if ("," in mainColumn):
        mainColumns = mainColumn.split(",")
        tempColumns = []
        for column in mainColumns:
            temp_column = column.split(".")
            temp_column[-1] = "`"+temp_column[-1]+"`"
            column = ".".join(temp_column)
            tempColumns.append(column)
        tempMainQuery = main_query
        for i in range(len(mainColumns)):
            tempMainQuery = tempMainQuery.replace("main_column"+str(i+1), mainColumns[i])
    else:
        temp_column = mainColumn.split(".")
        temp_column[-1] = "`"+temp_column[-1]+"`"
        mainColumn = ".".join(temp_column)
        tempMainQuery = main_query
        tempMainQuery = tempMainQuery.replace("main_column", mainColumn)
    
    # processing the sub queries (if there are any)
    tempSubQuery = ""
    if len(subTable) > 0:
        if ("," in subColumn):
            subColumns = subColumn.split(",")
            tempColumns = []
            for column in subColumns:
                temp_column = column.split(".")
                temp_column[-1] = "`"+temp_column[-1]+"`"
                column = ".".join(temp_column)
                tempColumns.append(column)
            tempSubQuery = sub_query
            for i in range(len(subColumns)):
                tempSubQuery = tempSubQuery.replace("sub_column"+str(i+1), subColumns[i])
                tempSubQuery = tempSubQuery.replace("sub_table", subColumns[i].split(".")[0])
        else:
            temp_column = subColumn.split(".")
            temp_column[-1] = "`"+temp_column[-1]+"`"
            subColumn = ".".join(temp_column)
            tempSubQuery = sub_query
            tempSubQuery = tempSubQuery.replace("sub_column", subColumn)
            tempSubQuery = tempSubQuery.replace("sub_table", subColumn.split(".")[0])
    print("tempSubQuery",tempSubQuery)
    
    # processing the dependency queries (if there are any)
    tempDependencyQuery = ""
    if len(dependencyTable) > 0:
        if ("," in dependencyColumn):
            dependencyColumns = dependencyColumn.split(",")
            tempColumns = []
            for column in dependencyColumns:
                temp_column = column.split(".")
                temp_column[-1] = "`"+temp_column[-1]+"`"
                column = ".".join(temp_column)
                tempColumns.append(column)
            tempDependencyQuery = dependency_query
            for i in range(len(dependencyColumns)):
                tempDependencyQuery = tempDependencyQuery.replace("dependency_column"+str(i+1), dependencyColumns[i])
                tempDependencyQuery = tempDependencyQuery.replace("dependency_table", dependencyColumns[i].split(".")[0])
        else:
            temp_column = dependencyColumn.split(".")
            temp_column[-1] = "`"+temp_column[-1]+"`"
            dependencyColumn = ".".join(temp_column)
            tempDependencyQuery = dependency_query
            tempDependencyQuery = tempDependencyQuery.replace("dependency_column", dependencyColumn)
            tempDependencyQuery = tempDependencyQuery.replace("dependency_table", dependencyColumn.split(".")[0])
    print("tempDependencyQuery",tempDependencyQuery)
    
    # drafting the conditon query
    mainQuery = tempMainQuery
    subQuery = tempSubQuery
    dependencyQuery = tempDependencyQuery
    
    conditionQuery = "("+mainQuery+")" if (subQuery != "" or dependencyQuery != "") else mainQuery
    if (len(subQuery) > 0): 
        conditionQuery += " and ("+subQuery+")" if "," in subColumn else " and "+subQuery
    if (len(dependencyQuery) > 0): 
        conditionQuery += " and ("+dependencyQuery+")" if "," in dependencyColumn else " and "+dependencyQuery

    updateQuery = "update "+dbSchema+"."+currentTableName+" set `"+error_code_column+"` ='"+rule_code+"' where "+conditionQuery
    return updateQuery
