-- Abrir o "CMD do MYSQL", onde se encontra o arquivo abaixo


LOAD DATA LOCAL INFILE 'Tabela_5.1.1_ajuste_apuracao_icms_sp.txt'
INTO TABLE tbl_5_1_1_ajust_apura_icms_sp   
 CHARACTER SET latin1
FIELDS TERMINATED BY '|'
OPTIONALLY ENCLOSED BY '\"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(cod_ajust, dscr, @DT_INI, @DT_FIM)
set data_inicio = STR_TO_DATE(@DT_INI,'%d%m%Y'), data_fim = STR_TO_DATE(@DT_FIM,'%d%m%Y');


