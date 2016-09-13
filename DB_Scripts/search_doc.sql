create table search_doc
(doc_no int(10) references doc_info(doc_no),
keyword varchar(30) not null);