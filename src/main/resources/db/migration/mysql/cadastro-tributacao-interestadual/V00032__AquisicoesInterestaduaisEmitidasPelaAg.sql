

-- 
-- ==================================================== 33 - AQUISICAO DE MERCADORIAS FORA DO ESTADO PARA COMERCIALIZACAO MG X SP ============================================================== 
-- CST 60

	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 2403, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
				'', regime_tributario, cod_anp, 26, 11, ncm_id, 33, true FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = true and icms_cst = 60);
	
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 2403, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
				'', regime_tributario, cod_anp, 26, 11, ncm_id, 33, false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false and icms_cst = 60);
	
-- CST 00
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 2102, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
				'', regime_tributario, cod_anp, 26, 11, ncm_id, 33, true FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = true and icms_cst = 0);
	
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 2102, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
				'', regime_tributario, cod_anp, 26, 11, ncm_id, 33, false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false and icms_cst = 0);
	
	

-- ==================================================== 33 - AQUISICAO DE MERCADORIAS FORA DO ESTADO PARA COMERCIALIZACAO MG x SP ============================================================== 
