-- Abrir o "CMD do MYSQL", onde se encontra o arquivo abaixo

LOAD DATA LOCAL INFILE 'TabelaIBPTaxSP20.1.A.csv'
INTO TABLE trib_olho_imposto 
 CHARACTER SET latin1
FIELDS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '\"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(ncm,exce,tabela,descricao_ibpt,aliq_fede_nacional,aliq_fede_importado,aliq_esta,aliq_municipal,@var_vigenciaInico,@var_vigenciaTermino,chave,versao, fonte)
set vigencia_inicio = STR_TO_DATE(@var_vigencia_inicio,'%d-%m-%y'), vigencia_termino = STR_TO_DATE(@var_vigencia_termino,'%d-%m-%y');


update trib_olho_imposto set exce='0' where exce='';
update trib_olho_imposto set aliq_fede_nacional=aliq_fede_nacional/100, aliq_fede_importado=aliq_fede_importado/100, aliq_esta=aliq_esta/100, aliq_municipal=aliq_municipal/100;

select * from trib_olho_imposto where ncm=94043000\G 

create table classes_fiscais_excluir (
codigo int(5) unsigned,
classe_fiscal char(8),
exceto int(2),
primary key(codigo));

insert into classes_fiscais_excluir (
select n.ncm_id, n.nume, b.ncm ibpt
  from ncms n left join trib_olho_imposto b on n.nume=b.ncm and n.exce=b.exce
where b.ncm is null);

-- delete from compras_tributacao_cofins where `CLASSE_FISCAL_CODIGO` in (select codigo from classes_fiscais_excluir);
-- delete from compras_tributacao_pis where `CLASSE_FISCAL_CODIGO` in (select codigo from classes_fiscais_excluir);
delete from ncms where ncm_id in (select codigo from classes_fiscais_excluir);

/*insert into CLASSES_FISCAIS (CLASSE_FISCAL, EXCETO, DESCRICAO, ALIQUOTA_FEDERAL_NACIONAL, ALIQUOTA_FEDERAL_IMPORTADO, ALIQUOTA_ESTADUAL, ALIQUOTA_MUNICIPAL, CADASTRO)
 (SELECT codigo, exce, descricao_ibpt, aliq_fede_nacional, aliq_fede_importado, aliq_esta,aliq_municipal, now() from trib_olho_imposto)
ON DUPLICATE KEY UPDATE
DESCRICAO=descricao_ibpt,
ALIQUOTA_FEDERAL_NACIONAL=aliq_fede_nacional,
ALIQUOTA_FEDERAL_IMPORTADO=aliq_fede_importado,
ALIQUOTA_ESTADUAL=aliq_esta,
ALIQUOTA_MUNICIPAL=aliq_municipal;
*/

-- drop table if exists trib_olho_imposto;
drop table if exists classes_fiscais_excluir;
