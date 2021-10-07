-- ==================== is_prod_impor == TRUE

-- ESSES que são transferência de saldo, terei que montar alguma lógica, para, para ir "calcular" somente o VALOR ICMS que está sendo transferido/creditado
-- E além disso acrescentar a quantidade 1, no "item"

	-- 13 | TRANSFERENCIA DE SALDO DEVEDOR ART.98 DO RICMS
		INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		VALUES (199900, 5605, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
		'TRANSFERENCIA DE SALDO DEVEVDOR DO ICMS DE R$ %s - APURACAO MES DE %s', 'NORMAL', 0, 26,  26, 11848, 13, true);	

	-- 14 | TRANSFERENCIA DE SALDO CREDOR  ART.98 DO RICMS 
		INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		VALUES (199900, 5602, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
		'TRANSFERENCIA DE SALDO CREDOR DO ICMS DE R$ %s - APURACAO MES DE %s', 'NORMAL', 0, 26,  26, 11848, 14, true);	
-- ================================================================================================================================================================
	
	-- 1 | VENDA 	(Cadastrando VENDA e TRANSFERENCIA para CASO o PRODUTO SEJA IMPORTADO)
-- ================================================================================================================================================================
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
	(select cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, '', regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, 1, true FROM trib_esta where oper_id = 1 and is_prod_impor = false);
	
	
	-- 4 | TRANSFERENCIA
		
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
	(select cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, '', regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, 4, true FROM trib_esta where oper_id = 4 and is_prod_impor = false);
	
-- ================================================================================================================================================================
-- ================================================================================================================================================================
-- ================================================================================================================================================================



	--  12 – VENDA DE IMOBILIZADO (CST 41 - CFOP - 5551, para qualquer ncm)
-- -----------------------------------------------------------------------------------------------------------------------------------------------------
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 5551, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 40, 0.000000, 0.000000, 
				'', regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, 12, true FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = true);
	
	-- 12 – VENDA DE IMOBILIZADO  Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS)
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
		(select 199900, 5551, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 40, 0.000000, 0.000000, 
				'', "NORMAL", 0, 26, 26, ncm_id, 12, true FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 12 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = true));
-- ---------------------------------------------- Para Quando o produto for importado (dentro do estado é a mesma tributacao -------------------------------	


	--  26 – TRANSFERÊNCIA DE BEM DO ATIVO IMOBILIZADO (CST 41 - CFOP - 5552, para qualquer ncm)
-- -----------------------------------------------------------------------------------------------------------------------------------------------------
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 5552, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 41, 0.000000, 0.000000, 
				'Não incidencia do ICMS nos termos do artigo 7 inciso XIV do Decreto nr 45490/2000 (RICMS)', 
				regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, 26, true FROM trib_esta where oper_id = 1 and is_prod_impor = true);
	
	-- 26 – TRANSFERÊNCIA DE BEM DO ATIVO IMOBILIZADO Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS)
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
		(select 199900, 5552, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 41, 0.000000, 0.000000, 
				'Não incidencia do ICMS nos termos do artigo 7 inciso XIV do Decreto nr 45490/2000 (RICMS)',
				"NORMAL", 0, 26, 26, ncm_id, 26, true FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 26 and is_prod_impor = true));
-- ---------------------------------------------- Para Quando o produto for importado (dentro do estado é a mesma tributacao -------------------------------	
	


	-- 34 | AQUISICAO DE MERCADORIAS DENTRO DO ESTADO PARA CONSUMO | (É uma entrada que temos que emitir quando o fornecedor for por ex.: MEI ) 
	-- ICMS → CST = 90 | CFOP = 1407 (Para qualquer ncm nessa operação) NCM = 94019090| EX = 0 (ENTRADA)
-- -----------------------------------------------------------------------------------------------------------------------------------------------------
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 1407, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
				'', regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, 34, true FROM trib_esta where oper_id = 1 and is_prod_impor = true);
	
	-- 34 | AQUISICAO DE MERCADORIAS DENTRO DO ESTADO PARA CONSUMO | Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS)
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
		(select 199900, 1407, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
				'', "NORMAL", 0, 26, 26, ncm_id, 34, true FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 34 and is_prod_impor = true));

-- -----------------------------------------------------------------------------------------------------------------------------------------------------


	-- 45 | VENDA DE PRODUTO PARA  ENTREGA FUTURA (CST 90 | CFOP - 5922, para qualquer ncm)
-- -----------------------------------------------------------------------------------------------------------------------------------------------------
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
				'', regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, 45, true FROM trib_esta where oper_id = 1 and is_prod_impor = true);
	
	-- 45 | VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS)
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
		(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
				'', "NORMAL", 0, 26, 26, ncm_id, 45, true FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and is_prod_impor = true));

-- ---------------------------------------------- Para Quando o produto for importado (dentro do estado é a mesma tributacao -------------------------------	
	
	


-- =========================================================================================================================================================


	-- 46 | REMESSA VINCULADA A VENDA DE ENTREGA FUTURA (O que muda é a CFOP, mas a tributação é a mesma lógica da OPERACAO 01 - VENDA)
	-- A CFOP -> 5117 (É a mesma para as CSTs 60 e 00, nessa operação)
-- -----------------------------------------------------------------------------------------------------------------------------------------------------
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 5117, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
				'', regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, 46, true FROM trib_esta where oper_id = 1 and is_prod_impor = true);
-- -----------------------------------------------------------------------------------------------------------------------------------------------------


	
	-- 63 | VENDA DE SUCATA (SAIDA) (dentro do estado) ( OBS: É para somente UM NCM)
-- -----------------------------------------------------------------------------------------------------------------------------------------------------

	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		VALUES (199900, 5102, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 40, 0.000000, 0.000000, '', 'NORMAL', 0, 26,  26, 9902, 63, true);	
-- -----------------------------------------------------------------------------------------------------------------------------------------------------


	
	-- 67 | TRANSFERENCIA DE MATERIAL DE USO OU CONSUMO (CST 41 | zerado)
-- -----------------------------------------------------------------------------------------------------------------------------------------------------
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 5557, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 41, 0.000000, 0.000000, 
				'NOTA FISCAL EMITIDA CONFORME O ARTIGO 7 INCISO XV DO REGULAMENTO DO ICMS DE SP', regime_tributario, 
				cod_anp, esta_dest_id, esta_orig_id, ncm_id, 67, true FROM trib_esta where oper_id = 1 and is_prod_impor = true);
	
	-- 67 | TRANSFERENCIA DE MATERIAL DE USO OU CONSUMO (CST 41 | zerado) Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS)
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
		(select 199900, 5557, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 41, 0.000000, 0.000000, 
				'NOTA FISCAL EMITIDA CONFORME O ARTIGO 7 INCISO XV DO REGULAMENTO DO ICMS DE SP', "NORMAL", 
				0, 26, 26, ncm_id, 67, true FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 67 and is_prod_impor = true));
-- -----------------------------------------------------------------------------------------------------------------------------------------------------


	-- 73 | COMPRA DE SUCATA PARA COMERCIALIZACAO | NCM = 85481010| EX = 0 (ENTRADA)
-- -----------------------------------------------------------------------------------------------------------------------------------------------------
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		VALUES (199900, 1102, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 40, 0.000000, 0.000000, '', 'NORMAL', 0, 26,  26, 9902, 73, true);	
-- -----------------------------------------------------------------------------------------------------------------------------------------------------


	-- 79 | LANCAMENTO EFETUADO A TITULO DE BAIXA DE ESTOQUE (CST 90 | zerado)
-- -----------------------------------------------------------------------------------------------------------------------------------------------------
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 5927, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
				'NOTA FISCAL EMITIDA NOS TERMOS DO ARTIGO 125 INCISO VI E PARAGRAFO OITVAVO DO RICMS/2000.', regime_tributario, 
				cod_anp, esta_dest_id, esta_orig_id, ncm_id, 79, true FROM trib_esta where oper_id = 1 and is_prod_impor = true);
	
	-- 79 | LANCAMENTO EFETUADO A TITULO DE BAIXA DE ESTOQUE (CST 90 | zerado) Cadastrando para os outros ncms (que PROVAVELMENTE não SÃO de AUTOPEÇAS)
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
		(select 199900, 5927, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
				'NOTA FISCAL EMITIDA NOS TERMOS DO ARTIGO 125 INCISO VI E PARAGRAFO OITVAVO DO RICMS/2000.', "NORMAL", 
				0, 26, 26, ncm_id, 79, true FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 79 and is_prod_impor = true));
-- -----------------------------------------------------------------------------------------------------------------------------------------------------



	-- 81 - COMPRA DE SUCATA PARA COMERCIALIZACAO (CONTRIBUINTE) ( OBS: É para somente UM NCM)
-- -----------------------------------------------------------------------------------------------------------------------------------------------------
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		VALUES (199900, 1102, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 40, 0.000000, 0.000000, '', 'NORMAL', 0, 26,  26, 9902, 81, true);	
-- -----------------------------------------------------------------------------------------------------------------------------------------------------


	-- 83 | DISTRIBUICAO GRATUITA DE ITEM DE ESTOQUE (O que muda é a CFOP, mas a tributação é a mesma lógica da OPERACAO 01 - VENDA) 
	-- A CFOP -> 5949 (É a mesma para as CSTs 60 e 00, nessa operação)
-- -----------------------------------------------------------------------------------------------------------------------------------------------------
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
							mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
		(select cest, 5949, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
				'', regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, 83, true FROM trib_esta where oper_id = 1 and is_prod_impor = true);
-- -----------------------------------------------------------------------------------------------------------------------------------------------------
