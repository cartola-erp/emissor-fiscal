	
	-- Entrada emitida pela AG
	-- 73 | COMPRA DE SUCATA PARA COMERCIALIZACAO (NAO CONTRIBUINTE) | NCM = 85481010| EX = 0
	INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
		VALUES (0.000000, 0.000000, 98, 'CONSUMO', 0.000000,0.000000, 98, 'Tributacao Cadastrada - 01/12 - VIA SCRIPT', 0.000000, 0.000000, 98,'NORMAL',73, 9902);	



	-- 63 | VENDA DE SUCATA | NCM = 85481010| EX = 0 (CST 01)
	INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
		VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000,0.000000, 1, 'Tributacao Cadastrada - 01/12 - VIA SCRIPT', 0.016500, 1.000000, 1,'NORMAL',63, 9902);	


	-- Emitida pelo fornecedor (Vamos Dar Entrada)
	-- 81 - COMPRA DE SUCATA PARA COMERCIALIZACAO (CONTRIBUINTE)
	INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
		VALUES (0.076000, 1.000000, 50, 'CONSUMO', 0.000000,0.000000, 1, 'Tributacao Cadastrada - 01/12 - VIA SCRIPT', 0.016500, 1.000000, 50,'NORMAL', 81, 9902);	
