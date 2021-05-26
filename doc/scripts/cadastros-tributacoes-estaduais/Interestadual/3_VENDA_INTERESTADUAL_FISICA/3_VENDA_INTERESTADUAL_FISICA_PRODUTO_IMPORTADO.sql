

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x AC ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.17, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 1 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.17, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 1,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 1 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x AL ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.01, finalidade, 0.04, 0.17, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 2 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.01, "CONSUMO", 0.04,0.17, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 2,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 2 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x AM ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 3 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 3,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 3 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x AP ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 4 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 4,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 4 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x BA ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 5 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 5,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 5 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x CE ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 6 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 6,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 6 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x DF ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 7 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 7,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 7 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x ES ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.17, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 8 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.17, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 8,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 8 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x GO ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.17, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 9 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.17, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 9,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 9 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x MA ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 10 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 10,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 10 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x MG ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 11 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 11,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 11 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x MS ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.17, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 12 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.17, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 12,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 12 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x MT ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.17, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 13 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.17, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 13,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 13 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x PA ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.17, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 14 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.17, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 14,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 14 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x PB ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 15 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 15,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 15 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x PE ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 16 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 16,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 16 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x PI ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 17 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 17,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 17 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x PR ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 18 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 18,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 18 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x RJ ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.02, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 19 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.02, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 19,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 19 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x RN ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 20 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 20,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 20 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x RO ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.175, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 21 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.175, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 21,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 21 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x RR ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.17, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 22 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.17, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 22,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 22 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x RS ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.175, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 23 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.175, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 23,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 23 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x SC ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.17, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 24 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.17, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 24,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 24 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x SE ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 25 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 25,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 25 and is_prod_impor = true)); 

-- PARA PRODUTOS IMPORTADOS (Aliq 4%) 
-- ==================================================== 3 | VENDA INTERESTADUAL (FISICA)  SP x TO ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
 (SELECT cest, 6108, 0.0, finalidade, 0.04, 0.18, 1.000000, 00, 1.000000, 0.000000, '', regime_tributario, 
 cod_anp, 27 , 26, ncm_id, 3, true FROM trib_esta WHERE oper_id = 1 and is_prod_impor = true); 

-- Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS) 
 INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
 cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id,  is_prod_impor) 
	(SELECT 199900,6108,0.0, "CONSUMO", 0.04,0.18, 1.000000, 00, 1.000000, 0.000000, '', "NORMAL", 
	0, 27,26, ncm_id,3, true FROM ncms WHERE ncm_id NOT IN (SELECT ncm_id FROM trib_esta WHERE oper_id = 3 and esta_orig_id = 26 and esta_dest_id = 27 and is_prod_impor = true));