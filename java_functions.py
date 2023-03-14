# 1 main table 
# multiple main table 

# 1 maincolumn 
# 1 maincolumn and 1 subcolumn 
# 1 maincolumn and multiple subcolumn 
# multiple maincolumn and 1 subcolumn 
# multiple maincolumn and multiple subcolumn 



def single_default(dbSchema, currentTableName, error_code_option, rule_code,
                   mainTable, subTable, dependencyTable, 
                   mainColumn, subColumn, dependencyColumn, 
                   main_query, sub_query, dependency_query):
    error_code_column = "_dq_error_code_mandatory" if (error_code_option == "mandatory") else "_dq_error_code_optional"

    tempSubQuery = "";
    if len(subTable) > 0:
        if ("," in subColumn):
            subColumns = subColumn.split(",")
            tempQuery = []
            for i in range(len(subColumns)):
                tempQuery.append(sub_query)
                tempQuery[i] = tempQuery[i].replace("sub_column", subColumns[i])
            tempSubQuery = (" and ").join(tempQuery)
        else:
            tempSubQuery = sub_query
            tempSubQuery = tempSubQuery.replace("sub_column", subColumn)
    
    tempMainQuery = "";
    if ("," in mainColumn):
        mainColumns = mainColumn.split(",")
        tempQuery = []
        for i in range(len(mainColumns)):
            tempQuery.append(main_query)
            tempQuery[i] = tempQuery[i].replace("main_column", mainColumns[i])
        tempMainQuery = (" and ").join(tempQuery)
    else:
        tempMainQuery = main_query
        tempMainQuery = tempMainQuery.replace("sub_column", subColumn)
        
    tempDependencyQuery = "";
    if len(dependencyTable) > 0:
        if ("," in dependencyColumn):
            dependencyColumns = dependencyColumn.split(",")
            tempQuery = []
            for i in range(len(dependencyColumns)):
                tempQuery.append(dependency_query)
                tempQuery[i] = tempQuery[i].replace("dependency_column", dependencyColumns[i])
            tempDependencyQuery = (" and ").join(tempQuery)
        else:
            tempDependencyQuery = dependency_query
            tempDependencyQuery = tempDependencyQuery.replace("dependency_column", dependencyColumn)
    
    mainQuery = tempMainQuery
    subQuery = tempSubQuery
    dependencyQuery = tempDependencyQuery

    conditionQuery = "("+mainQuery+")"
    
    print(subQuery)
    print(dependencyQuery)

    if (len(subQuery) > 0):
        conditionQuery += " and ("+subQuery + ")"
        
    if (len(dependencyQuery) > 0):
        conditionQuery += " and ("+dependencyQuery + ")"

    updateQuery = "update "+dbSchema+"."+currentTableName+" set `"+error_code_column+"` ='"+rule_code+"' where "+conditionQuery
    return updateQuery

def single_multiple_default():
    return output

def single_lookup_default():
    return output