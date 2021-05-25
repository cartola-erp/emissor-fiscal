
-- =========================================================================================================================================================
-- =======================================  ESSE bloco, abaixo é para ser corrigido no DB "autogeral.produtos_dbf" =========================================
-- =========================================================================================================================================================
-- FINALIDADES


-- 2 - SUBSTITUIÇÃO TRIBUTÁRIA
-- 1 - NORMAL

-- 
-- Todos os produtos que tiverem a classe fiscal abaixa e forem TRIBUTADOS como "normal" serão atualizados a TRIBUTACAO para "ST" (Substituição Tributária) (CST 60 - Base E Aliq ZERADA)
-- 

SELECT * FROM autogeral.produtos_dbf WHERE classe_fiscal IN (40091100, 90262090) AND tributacao_codigo = 1;

update autogeral.produtos_dbf set tributacao_codigo = 2 WHERE classe_fiscal IN (40091100, 90262090) AND tributacao_codigo = 1;


-- PS: Os ncms ACIMA no emissorfiscal, já estão como ST, e os abaixo tbm.
-- MAS os logo abaixo tem que ser na 

-- 
-- Todos os produtos que tiverem os NCMs e que estiverem cadastrados como ST (tributacao_codigo = 2) serão atualizados a TRIBUTACAO do ICMS para "NORMAL" (CST 00)
-- 

SELECT * FROM autogeral.produtos_dbf WHERE classe_fiscal IN (73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300 ) AND tributacao_codigo = 2 ;

update autogeral.produtos_dbf set tributacao_codigo = 1 WHERE classe_fiscal IN (73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300 ) AND tributacao_codigo = 2 ;

-- =========================================================================================================================================================
-- =========================================================================================================================================================
-- =========================================================================================================================================================


-- Deixando o campo "mens", vazio, pois será usado como info complementar;
select * from trib_esta;
update trib_esta set mens = "";

-- O script abaixo é para atualizar o ICMS na VENDA e TRANSFERENCIA, para os NCMS (73181500, 73181600, 76161000, 73182400, 84814000, 84818099)
-- Da CST 60 --> p/ 00 (que é tributado) 


SELECT * FROM trib_esta where ncm_id in (select ncm_id from ncms where nume = 38112190);

update trib_esta SET cod_anp = 740101006 where ncm_id in (select ncm_id from ncms where nume = 38112190);

	

-- RODAR NO EMISSORFISCAL (CORRIGINDO os NCMS que estão na CST 60 para a CST 00)
-- 1 - VENDA
SELECT * FROM  emissorfiscal.trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN (73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300 )
) and oper_id = 1 ;

UPDATE emissorfiscal.trib_esta t 
		SET t.fcp_aliq = 0.000000,
		t.icms_aliq =  0.180000,
		t.icms_aliq_dest = 0.000000,
		t.icms_base = 1.000000,
		t.icms_cst = 0,
		t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000,
		t.mens =  '',
		t.cfop = 5102 
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN(73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300 )
) and t.oper_id = 1 AND  t.icms_cst = 60;


-- 4 - TRANSFERENCIA
SELECT * FROM  emissorfiscal.trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN (73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300 )
) and oper_id = 4 ;

UPDATE emissorfiscal.trib_esta t 
		SET t.fcp_aliq = 0.000000,
		t.icms_aliq =  0.180000,
		t.icms_aliq_dest = 0.000000,
		t.icms_base = 1.000000,
		t.icms_cst = 0,
		t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000,
		t.mens =  '',
		t.cfop = 5152 
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN(73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300 )
) and t.oper_id = 4 AND  t.icms_cst = 60;






-- ATUALIZANDO CFOP de OLEO

-- VENDA
select * from emissorfiscal.trib_esta t where cod_anp != 0 and oper_Id = 1;

update emissorfiscal.trib_esta set cfop = 5656 where cod_anp != 0 and oper_Id = 1;


-- TRANSFERENCIA
select * from emissorfiscal.trib_esta t where cod_anp != 0 and oper_Id = 4;

update emissorfiscal.trib_esta set cfop = 5659 where cod_anp != 0 and oper_Id = 4;
