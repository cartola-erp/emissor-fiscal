	

	-- IGNORAR O DECIMAL
	
	-- 73 | COMPRA DE SUCATA PARA COMERCIALIZACAO | NCM = 85481010| EX = 0 (ENTRADA)
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id ) 
		VALUES (199900, 1102, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 40, 0.000000, 0.000000, 'Trib Esta Add. - 26/03 - VIA SCRIPT', 'NORMAL', 0, 26,  26, 9902, 73);	

	-- VER ESSA PARTE ABAIXO
	-- 63 | VENDA DE SUCATA (SAIDA) (dentro do estado)
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id ) 
		VALUES (199900, 5102, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 40, 0.000000, 0.000000, 'Trib Esta Add. - 02/12 - VIA SCRIPT', 'NORMAL', 0, 26,  26, 9902, 63);	


	-- 81 - COMPRA DE SUCATA PARA COMERCIALIZACAO (CONTRIBUINTE)
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id ) 
		VALUES (199900, 1102, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 40, 0.000000, 0.000000, 'Trib Esta Add. - 02/12 - VIA SCRIPT', 'NORMAL', 0, 26,  26, 9902, 81);	
