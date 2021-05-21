
-- O script abaixo é para atualizar o ICMS na VENDA e TRANSFERENCIA, para os NCMS (73181500, 73181600, 76161000, 73182400, 84814000, 84818099)
-- Da CST 60 --> p/ 00 (que é tributado) 


-- RODAR NO EMISSORFISCAL (CORRIGINDO os NCMS que estão na CST 60 para a CST 00)
-- 1 - VENDA
SELECT * FROM  emissorfiscal.trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN (73181500, 73181600, 76161000, 73182400, 84814000, 84818099 )
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
	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN(73181500, 73181600, 76161000, 73182400, 84814000, 84818099 )
) and t.oper_id = 1 AND  t.icms_cst = 60;





-- 4 - TRANSFERENCIA
SELECT * FROM  emissorfiscal.trib_esta WHERE ncm_id IN ( 
	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN (73181500, 73181600, 76161000, 73182400, 84814000, 84818099 )
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
	SELECT ncm_id FROM emissorfiscal.ncms WHERE nume IN(73181500, 73181600, 76161000, 73182400, 84814000, 84818099 )
) and t.oper_id = 4 AND  t.icms_cst = 60;

