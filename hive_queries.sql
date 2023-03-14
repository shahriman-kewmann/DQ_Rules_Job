
-- cannot be blank or null
where `column` is null or `column` = ''

-- cannot be blank
where `column` = ''

-- must match value in code decode table
where `column` not in (select cd_column from code_decode_table)

-- cannot be blank or null and must match value in code decode table
where (`column` is null or `column` = '') and `column` not in (select cd_column from code_decode_table)

-- cannot be blank and must match value in code decode table
where `column` = '' and `column` not in (select cd_column from code_decode_table)

-- cannot be blank or null or 0
where `column` is null or `column` = '' or `column` = 0

-- cannot be null or 0
where `column` is null or `column` = 0

-- Equals value and cannot be blank or null and only allow code decode
WHERE `column` != 'value'
  AND (`column`  = '' or `column` is'NULL')
  AND `column` IN (SELECT cd_column FROM code_decode_table);

--Cannot be blank & value (e.g. OT) and only allow code decode
where (`column` = '' or `column` = 'value') 
  AND `column` IN (SELECT cd_column FROM code_decode_table);

-- cannot be blank if another column has value
where `column1` = '' and (`column2` is not null or `column2` <> '')

-- cannot be blank if multiple other columns has values (same table)
where `column1` = '' and (`column2` is not null or `column3` is not null)

-- cannot be blank if multiple other columns has values (different table)
where table1.`column1` = '' and (table2.`column2` is not null or table2.`column3` is not null)
inner join on table1.`_uuid` = table2.`_uuid`

-- cannot be blank if another column has values (not blank/null or 0)
where `column1` = '' and (`column2` is not null or `column2` != '' or `column2` != 0)

-- cannot be blank if other columns have values (not blank/null or 0)
where `column1` = '' and ((`column2` is not null or `column2` != '' or `column2` != 0) or (`column3` is not null or `column3` != '' or `column3` != 0))

-- cannot be blank if another column is blank/null or 0
where `column1` = '' and (`column2` is null or `column2` = '' or `column2` = 0)

-- cannot be blank if other columns are blank/null or 0
where `column1` = '' and ((`column2` is null or `column2` = '' or `column2` = 0) or (`column3` is null or `column3` = '' or `column3` = 0))

-- Cannot be null/blank or 0 if column 1 > value with Collateral Type
where `column` is null or `column` = '' or `column` = 0 and `column1` > 0; 

-- Code decode if another column = value with Entity Type
where `column` = (select cd_column from code_decode_table); 

-- Cannot be blank and Code Decode if another column = value with Entity Type
where `column` = '' and `column` = 'values'; 

-- Cannot be blank and Code Decode if column 1 = some values and column 2 = some values with Entity Type
where `column` = '' and `column1` = 'values' and `column2` = 'values';

-- Cannot be blank if column 1 or column 2 = value (including null/blank/0)
where `column` = '' and (`column1` is null or `column1` = '' or `column1` = 0 or `column1` = 'values') or (`column2` is null or `column2` = '' or `column2` = 0 or `column2` = 'values'); 

-- Cannot be null/blank or 0 if column 1 > value with Collateral Type
where `column` is null or `column` = '' or `column` = 0 and `column1` > 'value';

-- Code decode if another column = value with Entity Type
where `column` = (select cd_column from code_decode_table); 

-- Cannot be blank and Code Decode if another column = value with Entity Type
where `column1` = '' and `column2` = (select cd_column from code_decode_table);

-- Cannot be blank and Code Decode if column 1 = some values and column 2 = some values with Entity Type
where `column` = '' and `column1` = (select cd_column from code_decode_table) and `column2` = (select cd_column from code_decode_table);

-- Cannot be blank and follow source date format with Entity Type
where `column` = '' and `column` = (CAST(TO_DATE(`source`, 'yyyyMMdd') AS STRING); 

-- Standard date validation rules must follow source date format with Entity Type
where `column` = (CAST(TO_DATE(`source`, 'yyyyMMdd') AS STRING); 

-- Must be in valid date format
where `column` = RLIKE '^[0-9]{4}-[0-9]{2}-[0-9]{2}$';

-- Valid date format with Collateral Type
where `column1` = RLIKE '^[0-9]{4}-[0-9]{2}-[0-9]{2}$';

-- Date cannot be future date from current date, some with Entity Type
WHERE `column` <= from_unixtime(unix_timestamp()) AND CAST(`column` AS TIMESTAMP) IS NOT NULL;

-- Date cannot be future date and must be valid date format
WHERE `column` <= from_unixtime(unix_timestamp()) AND CAST(`column` AS TIMESTAMP) IS NOT NULL;

-- Date cannot be blank and must be valid date format
where `column` = '' and `column` = RLIKE '^[0-9]{4}-[0-9]{2}-[0-9]{2}$';

-- Date cannot be null/blank and must be in valid date format if another column has value
where (`column` is null or `column` = '') and `column` = RLIKE '^[0-9]{4}-[0-9]{2}-[0-9]{2}$') and `column1` is not null; 

-- Date must be greater than another date with Collateral Type
where `column` < `column1`;

-- Date must be minimum date maintained 01/01/1900
where `column` < '1900-01-01';

-- Cannot be negative with Collateral Type
where `column1` >= 0; 

-- Cannot be negative
where `column` >= 0; 

-- Cannot equals to value with Entity Type
where `column` <> `column1`; 

-- Cannot equals to lookup values with Entity Type
where `column` <> `column1`; 

-- Cannot equal to some values with Entity Type
where `column` not in (select `column1` from `table1`); 

-- Equals value if another column = value
where `column` != 'value' and `column1` = 'value';

-- Equals value if another column = value with Entity Type
where `column` != 'value' and `column1` = 'value';

-- Equals value if another column = some values
where `column` != 'value' and `column1` = 'some value';

-- Equals value if another column = some values with Entity Type
where `column` != 'value' and `column1` = 'some value';

-- Equals value if another column equals lookup values
where `column` != 'value' and `column1` = 'lookup value';

-- Equals some values when another column = some values
where `column` != 'some value' and `column1` = 'some value';

-- Equals some values when another column = value
where `column` != 'some value' and `column1` = 'value';

-- Equals some values when column 1 = value 1 and column 2 = value 2
where `column` != 'some value' and (`column1` = 'value 1' and `column2` = 'values 2');

-- Not equals value if another column = some values
where  `column` = 'value' if `column1` = 'some values';

-- Not equals value if another column equals lookup values
where  `column` = 'value' if `column1` = 'lookup values';

-- Cross check column values against lookup values with Entity Type
WHERE `column` = `column1`;

-- Cross check column values against lookup values for multiple columns dependency with Entity Type
WHERE `column` = `column1`
    AND `column` = `column2`;

-- Equals lookup value when column 1 equals lookup and column 2 equals lookup with Entity Type
where `column` != 'lookup value' and (`column1` = 'lookup value' and `column2` = 'lookup values');

-- Not equals value if column 1 = value 1 & column 2 = value 2
where `column` = 'value' and (`column1` = 'value 1' and `column2` = 'values 2');

-- Not equals some values if another column = value
where `column` = 'some values' and `column1` = 'value';

-- Not equals some values when another value = some values
where  `column` = 'some values' and `column1` = 'some values';

-- Not equals some values when another column equals lookup values with Entity Type
where  `column` = 'some values' and `column1` = 'lookup values';

-- Larger than a value when another column = value
where `column` <= 'value' and `column1` = 'value';

-- Column 1 cannot be value + Column 2 with another constraint when another column = value
where `column1` = 'value' and `column2` = 'constraint' and `column` = 'another value';

-- Column 1 and Column 2 cannot be lookup values with Entity Type
where `column1` = 'lookup value' and `column2` = 'lookup value';

-- Cannot be zero with Entity Type
where `column` = 0;

-- Cannot be zero or blank when some columns = values or not equals value with Entity Type
where (`column` = 0 or `column` = '') and `column1` = 'values' and `column2` != 'values';

-- Cannot be zero when another column = some values
where `column` = 0 and `column1` != 'some values';

-- 3 main columns values when another column = some values with Entity Type
where (`column` = 'value' and `column1` != '' or `column2` = 'value') and `column3` = 'some values';

-- Cannot be blank for IDV
where `column` = '' and `clean_zone`.`data_quality_rules_master_list_3`.`individual` = 1; 

-- Cannot be blank and must be valid date format for IDV
where (`column` = '' and `column` = RLIKE '^[0-9]{4}-[0-9]{2}-[0-9]{2}$') and `clean_zone`.`data_quality_rules_master_list_3`.`individual` = 1; 

-- Cannot be blank for ORG
where `column` = '' and `clean_zone`.`data_quality_rules_master_list_3`.`organization` = 1;

-- Cannot be blank or contain some values with Entity Type
where `column` = '' or `column` = 'some values'; 

-- Link to a unique customer/account number
where `column` = `column1`;

-- Mandatory with collateral type
WHERE `column` IS NOT NULL AND `column` != '';

-- Mandatory and cannot be blank with Collateral Type
where 'column' = ''; 

-- Mandatory when another column has value with Collateral Type
where `column1` is null;

-- Mandatory when another column = null/blank or zero
where `column1` is not null or `column1` !='' or `column1`> 0;

-- Mandatory when another column = null/blank or zero with Collateral Type
where `column1` is not null or `column1` !='' or `column1`> 0;

-- Mandatory when column 1 or column 2 = null/blank or zero with Collateral Type
where (`column1` is not null or `column1` !='' or `column1`> 0) or (`column2` is not null or `column2` !='' or `column2`> 0);

-- Mandatory when another column > value
where `column1` <= `column2`;

-- Mandatory when another column > value with collateral type
where `column1` <= `column2`;

-- Date cannot be 00000000 when another column not equals value
where `column` = 00000000 and `column1` != 'value';

-- Equals value or blank when another column = some values with Entity Type
where (`column` != 'value' or `column` != '') and `column1` = 'some value';

-- Regex: Allow specific symbols with Entity Type
WHERE `column` NOT LIKE '%[^@()-''\/&.:,#]%';

-- Regex: Allow specific symbols
WHERE `column` LIKE '%[@()-''\/&.:,#]%'; 

-- Regex: allow specific symbols with Uppercase/lowercase alphabets with Entity Type
WHERE `column` RLIKE '^[A-Za-z@'\-/./()]+$';

-- Regex: Cannot allow special characters except for specific symbols and alphanumeric with Entity Type
WHERE `column` RLIKE '^[a-zA-Z0-9@\\-\\\'\\/\\.,\\(\\)&]+$';

-- Regex: Cannot have symbols or spaces in between
where `column` rlike '^[0-9]+$'; 

-- Regex: digits if another column = value
WHERE (id_type = '0006' AND id_number RLIKE '^[0-9]{12}$');

-- Regex: alphabets
where `column` like '%[a-zA-Z]%';

-- Regex: alphanumerics if another column = value
where `column1` = 'value' and `column` NOT REGEXP '[A-Za-z0-9]+';

-- Regex: Concat some columns must larger than n characters with Entity Type
WHERE CONCAT_WS(' ', `column`, `column1`, `column2`) RLIKE '^.{9,}$';

-- Regex: alphanumeric if else when column 1 = value 1 and column 2 = some values with Entity Type
where `column1` = 'value' and `column2` = 'value' and `column` NOT REGEXP '[A-Za-z0-9]+';

-- Regex: alphanumeric if else-if else when column 1 = value 1 and column 2 = value 2 with Entity Type
where `column1` = 'value' and `column2` = 'value' and `column` NOT REGEXP '[A-Za-z0-9]+';

WHERE `column` = 'value'
  AND (CASE WHEN `column1` = 'value' THEN `column2` RLIKE '^[0-9]{6}[A-Z]{1}$'
        WHEN `column1` = 'value' THEN `column2` RLIKE '^[0-9]{4}[0-9]{2}[0-9]{6}$'
        ELSE LENGTH(`column2`) <= 13 AND `column2` RLIKE '^[A-Za-z0-9]+$'
    END);
    
-- At least one column value should be populated with Entity Type
where `column` is not null;

-- Cannot be blank if another column has value other than some values, otherwise blank
where `column` = '' and `column1` = ('some values');

-- Equals value A if column = value & last digit else value B with Entity Type
WHERE (`column` = 'AMK' OR `column` = 'APR') 
  AND ((substr(`column1`, -1) % 2 = 1 AND `column2` = 'Male') OR (substr(`column1`, -1) % 2 = 0 AND `column2` = 'Female'));

-- Column value cannot be same for different customers
on table1.email = table2.email AND table1.customer_id <> table2.customer_id
where table1.`customer_id` = table2.`customer_id`;

-- Must be a valid year
WHERE `column` BETWEEN 1900 AND YEAR(CURRENT_DATE());

-- Year cannot be larger than current year
WHERE `column` <= YEAR(CURRENT_DATE());

-- Complex email local and domain format with Entity Type
WHERE email RLIKE '^[^@]+@[A-Za-z0-9]([-A-Za-z0-9]*[A-Za-z0-9])?([.][A-Za-z0-9]([-A-Za-z0-9]*[A-Za-z0-9])?)*$';

-- Complex Telephone Number length check with numbers validation
-- new job for this
where (`column` = 'value' and `column1` = 'value') or (`column2` = 'value' and length(`column3`) >= 'value');

-- Postcode regex and numbers check when another column = some values with Entity Type
WHERE (`column` = 'some values')
  AND postcode RLIKE '^[0-9]{5}$'
  AND NOT postcode RLIKE '^([0-9])\\1{4}$';

-- Name specific check with length check and unallowed values with Entity Type
WHERE  
  (
    `column` IS NOT NULL AND
    LENGTH(TRIM(`column`)) >= 'value' AND 
    NOT (`column` RLIKE 'unallowed value')
  );
  
-- Id number cannot be blank and have unallowed values with Entity Type
WHERE 
  (
    `column` = '' AND 
    NOT (`column` RLIKE 'unallowed value')
  );

-- Multiple regex check when column 1 not equals some values and column 2 not equals some values with Entity Type
WHERE 
  (
    LENGTH(TRIM(`column1`)) >= 3 AND 
    NOT (`column1` RLIKE '^[0-9]+$') AND 
    NOT (`column1` RLIKE '^[^a-zA-Z0-9]+$')
  )
  AND 
  (
    `column2` NOT IN ('200', '120', '003', '116') AND 
    `column2` NOT IN ('Blank', 'U001', 'U002', 'U003', 'U00X', '6503', '6504', '6502', '6506', '6999') AND 
    `column2` != 'Blank'
  );

-- Complex status check with multiple conditions defined in another tab/lookup
where `table`.`column` <> `table1`.`column1`;

-- Birthdate specific check with NRIC with Entity Type
WHERE `Birthdate` <> '' -- Birthdate is not blank
AND SUBSTR(`NRIC`, 1, 6) = date_format(`Birthdate`, 'yy-MM-dd') -- replace XXXXXX with the first 6 digits to cross-check against

-- Must have at least n chars when some columns not equal some values with Entity Type
where `column` = 'some values' and length(`column1`) = 'n';  

-- Cannot have more than 1 customers with same values for 2 columns with Entity Type
WHERE (`column1`, `column2`) NOT IN (
  SELECT `column1`, `column2`
  FROM `table1`
  GROUP BY `column1`, `column2`
  HAVING COUNT(*) > 1
);

-- State specific check involving 2 or more state columns must be a value when another column = value
WHERE `column` is null AND `column1` is null
  AND (`column2` LIKE 'value' OR `column2` LIKE 'value');

-- Complex CIF status based on lookup definition criteria with other column dependency






