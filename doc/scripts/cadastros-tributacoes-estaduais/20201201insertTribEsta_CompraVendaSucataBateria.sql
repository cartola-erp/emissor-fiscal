	
	-- 73 | COMPRA DE SUCATA PARA COMERCIALIZACAO | NCM = 85481010| EX = 0
	INSERT INTO `trib_esta` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, 
				pis_cst, regime_tributario, oper_id, ncm_id) 
		VALUES (0.000000, 0.000000, 98, 'CONSUMO', 0.000000,0.000000, 98, 'Tributacao Cadastrada - 01/12 - VIA SCRIPT', 0.000000, 0.000000, 98,'NORMAL',73, 9902);	

	-- IGNORAR O DECIMAL
	
	-- 73 | COMPRA DE SUCATA PARA COMERCIALIZACAO | NCM = 85481010| EX = 0 (ENTRADA)
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, esta_dest_id, esta_orig_id, ncm_id, oper_id ) 
		VALUES (null, 1102, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 40, 0.000000, 0.000000, 'Trib Esta Add. - 02/12 - VIA SCRIPT', 'NORMAL', 26,  26, 9902, 73);	

	-- VER ESSA PARTE ABAIXO
	-- 63 | VENDA DE SUCATA (SAIDA) (dentro do estado)
--	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
--							mens, regime_tributario, esta_dest_id, esta_orig_id, ncm_id, oper_id, ) 
--		VALUES (null, 5102, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 40, 0.000000, 0.000000, 'Trib Esta Add. - 02/12 - VIA SCRIPT', 'NORMAL', 26,  26, 9902, 73);	
--	
--	-- 63 | VENDA DE SUCATA (SAIDA) (fora do estado)
--	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
--							mens, regime_tributario, esta_dest_id, esta_orig_id, ncm_id, oper_id, ) 
--		VALUES (null, 5102, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 40, 0.000000, 0.000000, 'Trib Esta Add. - 02/12 - VIA SCRIPT', 'NORMAL', 26,  26, 9902, 73);	

	
	
	cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
	mens, regime_tributario, esta_dest_id, esta_orig_id, ncm_id, oper_id, 