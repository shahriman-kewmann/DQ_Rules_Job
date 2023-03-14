# 1 main table  
# multiple main table 

# 1 maincolumn 
# 1 maincolumn and 1 subcolumn 
# 1 maincolumn and multiple subcolumn 
# multiple maincolumn and 1 subcolumn 
# multiple maincolumn and multiple subcolumn 

def RC_SCPB(dbSchema, currentTableName, error_code_option, rule_code,
            mainTable, subTable, dependencyTable, 
            mainColumn, subColumn, dependencyColumn, 
            main_query, sub_query, dependency_query):
    
    error_code_column = "_dq_error_code_mandatory" if (error_code_option == "mandatory") else "_dq_error_code_optional"
    
    # processsing the main queries
    tempMainQuery = "";
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
    tempSubQuery = "";
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
            tempSubQuery = (" and ").join(tempQuery)
        else:
            temp_column = subColumn.split(".")
            temp_column[-1] = "`"+temp_column[-1]+"`"
            subColumn = ".".join(temp_column)
            tempSubQuery = sub_query
            tempSubQuery = tempSubQuery.replace("sub_column", subColumn)
    
    # processing the dependency queries (if there are any)
    tempDependencyQuery = "";
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

def RC_MCPB(dbSchema, currentTableName, error_code_option, rule_code,
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
            tempQuery[i] = tempQuery[i].replace("main_column"+i, mainColumns[i])
        tempMainQuery = (" and ").join(tempQuery)
    else:
        temp_column = mainColumn.split(".")
        temp_column[-1] = "`"+temp_column[-1]+"`"
        mainColumn = ".".join(temp_column)
        tempMainQuery = main_query
        tempMainQuery = tempMainQuery.replace("main_column", mainColumn)
    
    # processing the sub queries (if there are any)
    tempSubQuery = "";
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
            tempSubQuery = (" and ").join(tempQuery)
        else:
            temp_column = subColumn.split(".")
            temp_column[-1] = "`"+temp_column[-1]+"`"
            subColumn = ".".join(temp_column)
            tempSubQuery = sub_query
            tempSubQuery = tempSubQuery.replace("sub_column", subColumn)
    
    
    # drafting the conditon query
    mainQuery = tempMainQuery
    subQuery = tempSubQuery
    
    conditionQuery = "("+mainQuery+")" if (subQuery != "" or dependencyQuery != "") else mainQuery
    if (len(subQuery) > 0): 
        conditionQuery += " and ("+subQuery+")" if "," in subColumn else " and "+subQuery 

    updateQuery = "update "+dbSchema+"."+currentTableName+" set `"+error_code_column+"` ='"+rule_code+"' where "+conditionQuery
    return updateQuery

# def single_lookup_default():
#     return output