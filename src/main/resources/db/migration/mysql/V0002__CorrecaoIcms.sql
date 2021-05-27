

-- =========================================================================================================================================================
-- =========================================================================================================================================================
-- =========================================================================================================================================================


-- Deixando o campo "mens", vazio, pois será usado como info complementar;
update trib_esta set mens = "";

-- O script abaixo é para atualizar o ICMS na VENDA e TRANSFERENCIA, para os NCMS (73181500, 73181600, 76161000, 73182400, 84814000, 84818099)
-- Da CST 60 --> p/ 00 (que é tributado) 


-- SELECT * FROM trib_esta where ncm_id in (select ncm_id from ncms where nume = 38112190);

update trib_esta SET cod_anp = 740101006 where ncm_id in (select ncm_id from ncms where nume = 38112190);

	

-- RODAR NO EMISSORFISCAL (CORRIGINDO os NCMS que estão na CST 60 para a CST 00)
-- 1 - VENDA
-- SELECT * FROM  emissorfiscal.trib_esta WHERE ncm_id IN ( 
--	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN (73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300 )
-- ) and oper_id = 1 ;

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
-- SELECT * FROM  emissorfiscal.trib_esta WHERE ncm_id IN ( 
--	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN (73181500, 73181600, 76161000, 73182400, 84814000, 84818099, 73182300 )
-- ) and oper_id = 4 ;

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
-- select * from emissorfiscal.trib_esta t where cod_anp != 0 and oper_Id = 1;

update emissorfiscal.trib_esta set cfop = 5656 where cod_anp != 0 and oper_Id = 1;


-- TRANSFERENCIA
-- select * from emissorfiscal.trib_esta t where cod_anp != 0 and oper_Id = 4;

update emissorfiscal.trib_esta set cfop = 5659 where cod_anp != 0 and oper_Id = 4;


-- Atualizando ALIQUOTAS de 12% p/ 13,3%

UPDATE emissorfiscal.trib_esta t 
		SET t.fcp_aliq = 0.000000,
		t.icms_aliq =  0.133000,
		t.icms_aliq_dest = 0.000000,
		t.icms_base = 1.000000,
		t.icms_cst = 0,
		t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000,
		t.mens =  '',
		t.cfop = 5102 
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN(84818021, 90271000, 85432000)
) and t.oper_id = 1;


-- 4 - TRANSFERENCIA
UPDATE emissorfiscal.trib_esta t 
		SET t.fcp_aliq = 0.000000,
		t.icms_aliq =  0.133000,
		t.icms_aliq_dest = 0.000000,
		t.icms_base = 1.000000,
		t.icms_cst = 0,
		t.icms_iva = 1.000000,
		t.icms_st_aliq = 0.000000,
		t.mens =  '',
		t.cfop = 5152 
WHERE t.ncm_id IN ( 
	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN(84818021, 90271000, 85432000)
) and t.oper_id = 4;
