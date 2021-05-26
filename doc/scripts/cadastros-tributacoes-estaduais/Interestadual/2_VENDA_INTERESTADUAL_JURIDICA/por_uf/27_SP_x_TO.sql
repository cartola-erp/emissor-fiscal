

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 2 | VENDA INTERESTADUAL (JURIDICA) SP x TO ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6102, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 27 , 26, ncm_id, 2, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6102,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 27,26, ncm_id,2, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 2 and esta_orig_id = 26 and esta_dest_id = 27 and is_prod_impor = true));

-- Importados = false (Aliq interestadual = 7.000000000000001%
-- ==================================================== 2 | VENDA INTERESTADUAL (JURIDICA)  SP x TO ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6102 , 0.0, finalidade, 0.07 ,0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 27, 26, ncm_id,2, false FROM trib_esta WHERE oper_id = 1 AND is_prod_impor = false); 

-- 2 | VENDA INTERESTADUAL (JURIDICA) Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT 199900, 6102 , 0.0, "CONSUMO",0.07, 0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
 0, 27, 26, ncm_id,2, false FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 2 and esta_orig_id = 26 AND esta_dest_id = 27 AND is_prod_impor = false));