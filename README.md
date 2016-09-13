# multi-doc-summ


DB_Scripts contain all the script file related to create schemas for backend.

you should run the scripts in the following way.

source <filename.sql> to load the filename on command line of sql, to load the file.

1. Source createDB.sql --- this will create the database named multdocsumm.
2. Use SHOW DATABASES to check from the databases list if multdocsumm exists
3. Now load doc_info.sql followed by search_doc.sql
4. Lastly stopsTable.sql


This branch should never be merged with Master branch as it will lead to lose of files & conflicts. Use it for DB related issue.
In future releases it may be moved to a new repository
