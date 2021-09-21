

-- =========================================================================================================================================================
-- =========================================================================================================================================================
-- =========================================================================================================================================================


-- Deixando o campo "mens", vazio, pois será usado como info complementar;
-- update trib_esta set mens = "";

-- O script abaixo é para atualizar o ICMS na VENDA e TRANSFERENCIA, para os NCMS (73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300, 73182200, 39269090, 76169900, 74152900, 73269090)
-- Da CST 60 --> p/ 00 (que é tributado) 


-- SELECT * FROM trib_esta where ncm_id in (select ncm_id from ncms where nume = 38112190);

update trib_esta SET cod_anp = 740101006 where ncm_id in (select ncm_id from ncms where nume = 38112190);

	

	
-- =========================================================================================================================================================
-- ###############################	RODAR NO EMISSORFISCAL (CORRIGINDO os NCMS que estão na CST 60 para a CST 00)	########################################
-- =========================================================================================================================================================
-- 1 - VENDA
SELECT * FROM  trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN (73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300, 73182200, 39269090, 76169900, 74152900, 73269090 )
) and oper_id = 1 ;

UPDATE trib_esta t 
		SET t.fcp_aliq = 0.000000, 		t.icms_aliq =  0.180000, 
		t.icms_aliq_dest = 0.000000, 	t.icms_base = 1.000000,
		t.icms_cst = 0, 				t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000, 		t.mens =  '',
		t.cfop = 5102 
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN(73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300, 73182200, 39269090, 76169900, 74152900, 73269090 )
) and t.oper_id = 1 AND  t.icms_cst = 60;


-- 4 - TRANSFERENCIA
SELECT * FROM  trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN (73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300, 73182200, 39269090, 76169900, 74152900, 73269090 )
) and oper_id = 4 ;

UPDATE trib_esta t 
		SET t.fcp_aliq = 0.000000, 		t.icms_aliq =  0.180000,
		t.icms_aliq_dest = 0.000000, 	t.icms_base = 1.000000,
		t.icms_cst = 0, 				t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000, 		t.mens =  '',
		t.cfop = 5152 
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN(73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300, 73182200, 39269090, 76169900, 74152900, 73269090 )
) and t.oper_id = 4 AND  t.icms_cst = 60;

-- ############### Aqui é para quando a CFOP não será mudado (ou seja, PERMANECE a que já estava. São p/ OPERACOES que a CFOP p/ as CSTs 00 e 60 são as mesmas) ######
-- 46 - REMESSA VINCULADA A VENDA DE ENTREGA FUTURA
-- 83 - DISTRIBUICAO GRATUITA DE ITEM DE ESTOQUE
SELECT * FROM  trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN (73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300, 73182200, 39269090, 76169900, 74152900, 73269090 )
) and oper_id  IN (46, 83) ;

UPDATE trib_esta t 
		SET t.fcp_aliq = 0.000000, 		t.icms_aliq =  0.180000,
		t.icms_aliq_dest = 0.000000, 	t.icms_base = 1.000000,
		t.icms_cst = 0, 				t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000, 		t.mens =  '' 
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN(73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300, 73182200, 39269090, 76169900, 74152900, 73269090 )
) and t.oper_id IN (46, 83) AND  t.icms_cst = 60;

-- =========================================================================================================================================================



-- =========================================================================================================================================================
-- ###############################	RODAR NO (CORRIGINDO os NCMS que estão na CST 00 para a CST 60)	########################################
-- =========================================================================================================================================================
-- 1 - VENDA
SELECT * FROM trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN (87089100)
) and oper_id = 1 ;

UPDATE trib_esta t 
		SET t.fcp_aliq = 0.000000, 		t.icms_aliq =  0.000000,
		t.icms_aliq_dest = 0.000000, 	t.icms_base = 1.000000,
		t.icms_cst = 60, 				t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000, 		t.mens =  '',
		t.cfop = 5405 
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN(87089100)
) and t.oper_id = 1 AND  t.icms_cst = 0;


-- 4 - TRANSFERENCIA
SELECT * FROM  trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN (87089100 )
) and oper_id = 4 ;

UPDATE trib_esta t 
		SET t.fcp_aliq = 0.000000,		t.icms_aliq =  0.000000,
		t.icms_aliq_dest = 0.000000,	t.icms_base = 1.000000,
		t.icms_cst = 60,				t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000,		t.mens =  '',
		t.cfop = 5409	
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN(87089100 )
) and t.oper_id = 4 AND  t.icms_cst = 0;

-- ############### Aqui é para quando a CFOP não será mudado (ou seja, PERMANECE a que já estava. São p/ OPERACOES que a CFOP p/ as CSTs 00 e 60 são as mesmas) ######
-- 46 - REMESSA VINCULADA A VENDA DE ENTREGA FUTURA
-- 83 - DISTRIBUICAO GRATUITA DE ITEM DE ESTOQUE
SELECT * FROM  trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN (87089100 )
) and oper_id IN (46, 83);

UPDATE trib_esta t 
		SET t.fcp_aliq = 0.000000,		t.icms_aliq =  0.000000,
		t.icms_aliq_dest = 0.000000,	t.icms_base = 1.000000,
		t.icms_cst = 60,				t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000,		t.mens =  '' 
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN(87089100 )
) and t.oper_id IN (46,83) AND  t.icms_cst = 0;


-- =========================================================================================================================================================




-- =========================================================================================================================================================
-- ########################################################	 	ATUALIZANDO a CFOP de OLEO		############################################################
-- =========================================================================================================================================================
-- VENDA
select * from trib_esta t where cod_anp != 0 and oper_Id = 1;

UPDATE trib_esta t 
		SET t.fcp_aliq = 0.000000, 		t.icms_aliq =  0.000000,
		t.icms_aliq_dest = 0.000000, 	t.icms_base = 1.000000,
		t.icms_cst = 60, 				t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000, 		t.mens =  '',
		t.cfop = 5656 
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN(27101931, 27101932, 27101992, 27129000, 34031900, 38112190, 27101991)
) and t.oper_id = 1;


-- TRANSFERENCIA
select * from trib_esta t where cod_anp != 0 and oper_Id = 4;

UPDATE trib_esta t 
		SET t.fcp_aliq = 0.000000,		t.icms_aliq =  0.000000,
		t.icms_aliq_dest = 0.000000,	t.icms_base = 1.000000,
		t.icms_cst = 60,				t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000,		t.mens =  '',
		t.cfop = 5659	
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN(27101931, 27101932, 27101992, 27129000, 34031900, 38112190, 27101991 )
) and t.oper_id = 4;

-- 
UPDATE trib_esta SET cod_anp = 620101008 WHERE ncm_id IN (SELECT ncm_id FROM ncms WHERE nume IN(27101931));
UPDATE trib_esta SET cod_anp = 620501002 WHERE ncm_id IN (SELECT ncm_id FROM ncms WHERE nume IN(27101932));
UPDATE trib_esta SET cod_anp = 620504001 WHERE ncm_id IN (SELECT ncm_id FROM ncms WHERE nume IN(27101992));
UPDATE trib_esta SET cod_anp = 650101001 WHERE ncm_id IN (SELECT ncm_id FROM ncms WHERE nume IN(27129000));
UPDATE trib_esta SET cod_anp = 620503001 WHERE ncm_id IN (SELECT ncm_id FROM ncms WHERE nume IN(34031900));
UPDATE trib_esta SET cod_anp = 740101006 WHERE ncm_id IN (SELECT ncm_id FROM ncms WHERE nume IN(38112190));
UPDATE trib_esta SET cod_anp = 650101001 WHERE ncm_id IN (SELECT ncm_id FROM ncms WHERE nume IN(27101991));
UPDATE trib_esta SET cod_anp = 650101001 WHERE ncm_id IN (SELECT ncm_id FROM ncms WHERE nume IN(27101991));


-- =========================================================================================================================================================



-- =========================================================================================================================================================
-- ##################################################	 Atualizando ALIQUOTAS de 12% p/ 13,3%		###########################################################
-- =========================================================================================================================================================
-- 1 - VENDA
SELECT * FROM  trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN (84818021, 90271000, 85432000)
) and oper_id = 1 ;

UPDATE trib_esta t 
		SET t.fcp_aliq = 0.000000,		t.icms_aliq =  0.133000,
		t.icms_aliq_dest = 0.000000,	t.icms_base = 1.000000,
		t.icms_cst = 0,					t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000,		t.mens =  '',
		t.cfop = 5102 					
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN(84818021, 90271000, 85432000)
) and t.oper_id = 1;



SELECT * FROM  trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN (84818021, 90271000, 85432000 )
) and oper_id = 4 ;

-- 4 - TRANSFERENCIA
UPDATE trib_esta t 
		SET t.fcp_aliq = 0.000000,		t.icms_aliq =  0.133000,
		t.icms_aliq_dest = 0.000000,	t.icms_base = 1.000000,
		t.icms_cst = 0,					t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000,		t.mens =  '',
		t.cfop = 5152 
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN(84818021, 90271000, 85432000)
) and t.oper_id = 4;

-- ############### Aqui é para quando a CFOP não será mudado (ou seja, PERMANECE a que já estava. São p/ OPERACOES que a CFOP p/ as CSTs 00 e 60 são as mesmas) ######
-- 46 - REMESSA VINCULADA A VENDA DE ENTREGA FUTURA
-- 83 - DISTRIBUICAO GRATUITA DE ITEM DE ESTOQUE
SELECT * FROM  trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN (84818021, 90271000, 85432000 )
) and oper_id IN (46, 83) ;

UPDATE trib_esta t 
		SET t.fcp_aliq = 0.000000,		t.icms_aliq =  0.133000,
		t.icms_aliq_dest = 0.000000,	t.icms_base = 1.000000,
		t.icms_cst = 0,					t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000,		t.mens =  ''
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM ncms WHERE nume IN(84818021, 90271000, 85432000)
) and t.oper_id IN (46, 83);
-- =========================================================================================================================================================
