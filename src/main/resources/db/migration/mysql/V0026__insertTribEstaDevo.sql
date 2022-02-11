
-- =========================================== 6 - DEVOLUCAO PARA FORNECEDOR ===================================================
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 5101, 1102, 5202, "COMERCIALIZACAO", "NORMAL", false , "", 6);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 5102, 1102, 5202, "COMERCIALIZACAO", "NORMAL", false , "", 6);

-- Destacamos o ICMS na Devolução (Veio na compra mas não podemos nos creditar)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 5401, 1403, 5411, "COMERCIALIZACAO", "NORMAL", false , "", 6);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 5402, 1403, 5411, "COMERCIALIZACAO", "NORMAL", false , "", 6);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 5403, 1403, 5411, "COMERCIALIZACAO", "NORMAL", false , "", 6);
					
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5405, 1403, 5411, "COMERCIALIZACAO", "NORMAL", false , "", 6);
-- Óleo
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5652, 1652, 5661, "COMERCIALIZACAO", "NORMAL", false , "", 6);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5655, 1652, 5661, "COMERCIALIZACAO", "NORMAL", false , "", 6);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5656, 1652, 5661, "COMERCIALIZACAO", "NORMAL", false , "", 6);
-- ===============================================================================================================================
					
-- ================================ 39 - DEVOLUCAO PARA FORNECEDOR (TRIBUTADA) ===================================================								

INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 5101, 1102, 5202, "COMERCIALIZACAO", "NORMAL", false , "", 39);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 5102, 1102, 5202, "COMERCIALIZACAO", "NORMAL", false , "", 39);

-- Destacamos o ICMS na Devolução (Veio na compra mas não podemos nos creditar)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 5401, 1403, 5411, "COMERCIALIZACAO", "NORMAL", false , "", 39);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 5402, 1403, 5411, "COMERCIALIZACAO", "NORMAL", false , "", 39);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 5403, 1403, 5411, "COMERCIALIZACAO", "NORMAL", false , "", 39);
					
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5405, 1403, 5411, "COMERCIALIZACAO", "NORMAL", false , "", 39);					
-- Óleo
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5652, 1652, 5661, "COMERCIALIZACAO", "NORMAL", false , "", 39);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5655, 1652, 5661, "COMERCIALIZACAO", "NORMAL", false , "", 39);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5656, 1652, 5661, "COMERCIALIZACAO", "NORMAL", false , "", 39);
-- ===============================================================================================================================		

-- ================================ 7 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL ===================================================								
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 6101, 2102, 6202, "COMERCIALIZACAO", "NORMAL", false , "", 7);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 6102, 2102, 6202, "COMERCIALIZACAO", "NORMAL", false , "", 7);

-- Destacamos o ICMS na Devolução (Veio na compra mas não podemos nos creditar)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 6401, 2403, 6411, "COMERCIALIZACAO", "NORMAL", false , "", 7);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 6402, 2403, 6411, "COMERCIALIZACAO", "NORMAL", false , "", 7);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 6403, 2403, 6411, "COMERCIALIZACAO", "NORMAL", false , "", 7);
					
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6405, 2403, 6411, "COMERCIALIZACAO", "NORMAL", false , "", 7);
-- Óleo
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6652, 2652, 6661, "COMERCIALIZACAO", "NORMAL", false , "", 7);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6655, 2652, 6661, "COMERCIALIZACAO", "NORMAL", false , "", 7);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6656, 2652, 6661, "COMERCIALIZACAO", "NORMAL", false , "", 7);
-- ===============================================================================================================================		


-- ================================ 40 -  DEVOLUCAO PARA FORNECEDOR INTERESTADUAL (TRIBUTADA) ===================================================								

INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 6101, 2102, 6202, "COMERCIALIZACAO", "NORMAL", false , "", 40);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 6102, 2102, 6202, "COMERCIALIZACAO", "NORMAL", false , "", 40);

-- Destacamos o ICMS na Devolução (Veio na compra mas não podemos nos creditar)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 6401, 2403, 6411, "COMERCIALIZACAO", "NORMAL", false , "", 40);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 6402, 2403, 6411, "COMERCIALIZACAO", "NORMAL", false , "", 40);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(10, 6403, 2403, 6411, "COMERCIALIZACAO", "NORMAL", false , "", 40);
					
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6405, 2403, 6411, "COMERCIALIZACAO", "NORMAL", false , "", 40);					
-- Óleo
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6652, 2652, 6661, "COMERCIALIZACAO", "NORMAL", false , "", 40);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6655, 2652, 6661, "COMERCIALIZACAO", "NORMAL", false , "", 40);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6656, 2652, 6661, "COMERCIALIZACAO", "NORMAL", false , "", 40);

-- ===============================================================================================================================		


-- ====================================== 23 - DEVOLUCAO MATERIAL CONSUMO =======================================================	
-- ICMS_CST == null -> Deverá ser usado a mesma que vier na NF do fornecedor							
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(null, 5101, 1556, 5556, "COMERCIALIZACAO", "NORMAL", true , "", 23);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(null, 5102, 1556, 5556, "COMERCIALIZACAO", "NORMAL", true , "", 23);
-- as cst acima devem ser "null" ! ? (Ou alguma outra forma que indique que eu tenha que pegar a do fornecedor )

-- Destacamos o ICMS na Devolução (Veio na compra mas não podemos nos creditar)
-- Verificar se nessas cfop abaixo (5401, 5402 e 5403 temos que mencionar o ICMS na devolução, caso sim acredito que o ICMS CST tenha que ser != 90)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5401, 1407, 5413, "COMERCIALIZACAO", "NORMAL", false , "", 23);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5402, 1407, 5413, "COMERCIALIZACAO", "NORMAL", false , "", 23);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5403, 1407, 5413, "COMERCIALIZACAO", "NORMAL", false , "", 23);
					
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5405, 1407, 5413, "COMERCIALIZACAO", "NORMAL", false , "", 23);					
-- Óleo
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5652, 1653, 5662, "COMERCIALIZACAO", "NORMAL", false , "", 23);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5655, 1653, 5662, "COMERCIALIZACAO", "NORMAL", false , "", 23);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5656, 1653, 5662, "COMERCIALIZACAO", "NORMAL", false , "", 23);
-- ===============================================================================================================================		


-- ============================== 28 - DEVOLUCAO MERC. DESTINADA A USO E CONSUMO EM OP. C/ST ========================================
-- ICMS_CST == null -> Deverá ser usado a mesma que vier na NF do fornecedor							
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(null, 5101, 1556, 5556, "COMERCIALIZACAO", "NORMAL", true , "", 28);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(null, 5102, 1556, 5556, "COMERCIALIZACAO", "NORMAL", true , "", 28);
-- as cst acima devem ser "null" ! ? (Ou alguma outra forma que indique que eu tenha que pegar a do fornecedor )

-- Destacamos o ICMS na Devolução (Veio na compra mas não podemos nos creditar)
-- Verificar se nessas cfop abaixo (5401, 5402 e 5403 temos que mencionar o ICMS na devolução, caso sim acredito que o ICMS CST tenha que ser != 90)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5401, 1407, 5413, "COMERCIALIZACAO", "NORMAL", false , "", 28);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5402, 1407, 5413, "COMERCIALIZACAO", "NORMAL", false , "", 28);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5403, 1407, 5413, "COMERCIALIZACAO", "NORMAL", false , "", 28);
					
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5405, 1407, 5413, "COMERCIALIZACAO", "NORMAL", false , "", 28);					
-- Óleo
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5652, 1653, 5662, "COMERCIALIZACAO", "NORMAL", false , "", 28);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5655, 1653, 5662, "COMERCIALIZACAO", "NORMAL", false , "", 28);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(90, 5656, 1653, 5662, "COMERCIALIZACAO", "NORMAL", false , "", 28);
-- ==================================================================================================================================		


-- =========================================== 21 - REMESSA P/ CONSERTO (ATIVO IMOBILIZADO/USO E CONSUMO) ===================================================
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(41, 5102, 1556, 5915, "COMERCIALIZACAO", "NORMAL", false , "", 21);
-- ===============================================================================================================================

-- =========================================== 84 - REMESSA P/ CONSERTO (ATIVO/CONSUMO) - INTERESTADUAL ===================================================
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(41, 6102, 2556, 6915, "COMERCIALIZACAO", "NORMAL", false , "", 84);
-- ===============================================================================================================================
	

-- ==================================================================================================================================
-- ============================================ REMESSAS EM GARANTIA - OUTRAS SAIDAS ================================================ 
-- ==================================================================================================================================

-- ==================================== 10 - REMESSA DE MERCADORIA PARA ANALISE EM GARANTIA =========================================
-- ICMS_CST == null -> Deverá ser usado a mesma que vier na NF do fornecedor							
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(null, 0, 0, 5949, "COMERCIALIZACAO", "NORMAL", true , "", 10);
				
-- PARA os casos em que o fornecedor é do simples nacional (NÃO usa a mesma cst do fornecedor)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 5101, 1102, 5949, "COMERCIALIZACAO", "NORMAL", false , "", 10);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 5102, 1102, 5949, "COMERCIALIZACAO", "NORMAL", false , "", 10);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5401, 1403, 5949, "COMERCIALIZACAO", "NORMAL", false , "", 10);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5403, 1403, 5949, "COMERCIALIZACAO", "NORMAL", false , "", 10);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5405, 1403, 5949, "COMERCIALIZACAO", "NORMAL", false , "", 10);						
		
-- ==================================================================================================================================		

-- ============================================ 29 - OUTRAS SAIDAS NAO ESPECIFICADAS ================================================
-- ICMS_CST == null -> Deverá ser usado a mesma que vier na NF do fornecedor							
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(null, 0, 0, 5949, "COMERCIALIZACAO", "NORMAL", true , "", 29);

-- PARA os casos em que o fornecedor é do simples nacional (NÃO usa a mesma cst do fornecedor)			
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 5101, 1102, 5949, "COMERCIALIZACAO", "NORMAL", false , "", 29);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 5102, 1102, 5949, "COMERCIALIZACAO", "NORMAL", false , "", 29);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5401, 1403, 5949, "COMERCIALIZACAO", "NORMAL", false , "", 29);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5403, 1403, 5949, "COMERCIALIZACAO", "NORMAL", false , "", 29);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5405, 1403, 5949, "COMERCIALIZACAO", "NORMAL", false , "", 29);			
-- ==================================================================================================================================		


-- ============================== 11 - REMESSA DE MERCADORIA PARA ANALISE EM GARANTIA (INTERESTADUAL) ===================================
-- ICMS_CST == null -> Deverá ser usado a mesma que vier na NF do fornecedor							
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(null, 0, 0, 6949, "COMERCIALIZACAO", "NORMAL", true , "", 11);

-- PARA os casos em que o fornecedor é do simples nacional (NÃO usa a mesma cst do fornecedor)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 6101, 2102, 6949, "COMERCIALIZACAO", "NORMAL", false , "", 11);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 6102, 2102, 6949, "COMERCIALIZACAO", "NORMAL", false , "", 11);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6401, 2403, 6949, "COMERCIALIZACAO", "NORMAL", false , "", 11);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6403, 2403, 6949, "COMERCIALIZACAO", "NORMAL", false , "", 11);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6405, 2403, 6949, "COMERCIALIZACAO", "NORMAL", false , "", 11);			
-- ======================================================================================================================================		

-- =============================================== 30 - OUTRAS SAIDAS (SIMPLES REMESSA) ================================================
-- ICMS_CST == null -> Deverá ser usado a mesma que vier na NF do fornecedor							
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(null, 0, 0, 6949, "COMERCIALIZACAO", "NORMAL", true , "", 30);

-- PARA os casos em que o fornecedor é do simples nacional (NÃO usa a mesma cst do fornecedor)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 6101, 2102, 6949, "COMERCIALIZACAO", "NORMAL", false , "", 30);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 6102, 2102, 6949, "COMERCIALIZACAO", "NORMAL", false , "", 30);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6401, 2403, 6949, "COMERCIALIZACAO", "NORMAL", false , "", 30);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6403, 2403, 6949, "COMERCIALIZACAO", "NORMAL", false , "", 30);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6405, 2403, 6949, "COMERCIALIZACAO", "NORMAL", false , "", 11);
-- ======================================================================================================================================		


-- ==================================================================================================================================
-- ========================================== DEVOLUCAO DO CLIENTE - EMITIDAS PELA AG =============================================== 
-- ==================================================================================================================================
-- ================================================ 8 - DEVOLUCAO DO CLIENTE ==========================================================
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 5101, 1102, 1102, "COMERCIALIZACAO", "NORMAL", false , "", 8);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 5102, 1202, 1202, "COMERCIALIZACAO", "NORMAL", false , "", 8);

-- Destacamos o ICMS na Devolução (Veio na compra mas não podemos nos creditar)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5401, 1403, 1403, "COMERCIALIZACAO", "NORMAL", false , "", 8);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5403, 1403, 1403, "COMERCIALIZACAO", "NORMAL", false , "", 8);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5405, 1411, 1411, "COMERCIALIZACAO", "NORMAL", false , "", 8);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5656, 1662, 1662, "COMERCIALIZACAO", "NORMAL", false , "", 8);

-- devolucao de venda futura
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(00, 5117, 1202, 1202, "COMERCIALIZACAO", "NORMAL", false , "", 8);

INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5117, 1411, 1411, "COMERCIALIZACAO", "NORMAL", false , "", 8);

-- Devolucao de Sat que Virou NFE (encima da operação "movimento já registra em ECF")					
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(00, 5929, 1202, 1202, "COMERCIALIZACAO", "NORMAL", false , "", 8);

INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 5929, 1411, 1411, "COMERCIALIZACAO", "NORMAL", false , "", 8);
-- =========================================== 9 - DEVOLUCAO DO CLIENTE INTERESTADUAL ===================================================
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 6101, 2102, 2102, "COMERCIALIZACAO", "NORMAL", false , "", 9);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(0, 6102, 2202, 2202, "COMERCIALIZACAO", "NORMAL", false , "", 9);

INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6401, 2403, 2403, "COMERCIALIZACAO", "NORMAL", false , "", 9);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6403, 2403, 2403, "COMERCIALIZACAO", "NORMAL", false , "", 9);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6405, 2411, 2411, "COMERCIALIZACAO", "NORMAL", false , "", 9);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , usa_mesm_cst_forn, mens, oper_id) 
					VALUES	(60, 6656, 2662, 2662, "COMERCIALIZACAO", "NORMAL", false , "", 9);
-- ======================================================================================================================================		

