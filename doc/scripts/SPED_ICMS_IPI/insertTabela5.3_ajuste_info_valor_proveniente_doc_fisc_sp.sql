-- Abrir o "CMD do MYSQL", onde se encontra o arquivo abaixo

LOAD DATA LOCAL INFILE 'Tabela_5.3_ajuste_info_valor_proveniente_doc_fisc_sp.txt'
INTO TABLE tbl_53_ajust_info_vlr_doc_fisc  
 CHARACTER SET latin1
FIELDS TERMINATED BY '|'
OPTIONALLY ENCLOSED BY '\"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(cod_ajust_bene_ince, dscr, @DT_INI, @DT_FIM)
set data_inicio = STR_TO_DATE(@DT_INI,'%d%m%Y'), data_fim = STR_TO_DATE(@DT_FIM,'%d%m%Y');


