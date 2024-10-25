

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x PA ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.17, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 14 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE n�o S�O de AUTOPE�AS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.17, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 14,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 14 and is_prod_impor = true));

-- Importados = false (Aliq interestadual = 7.000000000000001%
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x PA ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108 , 0.0, finalidade, 0.07 ,0.17, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 14, 26, ncm_id,3, false FROM trib_esta WHERE oper_id = 1 AND is_prod_impor = false); 

-- 3 | VENDA INTERESTADUAL (FISICA) Cadastrando para os outros ncms (que PROVAVELMENTE n�o S�O de AUTOPE�AS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT 199900, 6108 , 0.0, "CONSUMO",0.07, 0.17, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
 0, 14, 26, ncm_id,3, false FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 AND esta_dest_id = 14 AND is_prod_impor = false));