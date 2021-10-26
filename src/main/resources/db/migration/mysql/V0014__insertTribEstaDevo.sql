
-- =========================================== 6 - DEVOLUCAO PARA FORNECEDOR ===================================================
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(10, 5101, 1102, 5202, "COMERCIALIZACAO", "NORMAL", "", 6);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(10, 5102, 1102, 5202, "COMERCIALIZACAO", "NORMAL", "", 6);

-- Destacamos o ICMS na Devolução (Veio na compra mas não podemos nos creditar)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(10, 5401, 1403, 5411, "COMERCIALIZACAO", "NORMAL", "", 6);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(10, 5402, 1403, 5411, "COMERCIALIZACAO", "NORMAL", "", 6);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(10, 5403, 1403, 5411, "COMERCIALIZACAO", "NORMAL", "", 6);
					
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(60, 5405, 1403, 5411, "COMERCIALIZACAO", "NORMAL", "", 6);
-- Óleo
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(60, 5652, 1652, 5661, "COMERCIALIZACAO", "NORMAL", "", 6);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(60, 5655, 1652, 5661, "COMERCIALIZACAO", "NORMAL", "", 6);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(60, 5656, 1652, 5661, "COMERCIALIZACAO", "NORMAL", "", 6);
-- ===============================================================================================================================
					
-- ================================ 39 - DEVOLUCAO PARA FORNECEDOR (TRIBUTADA) ===================================================								

INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(10, 5101, 1102, 5202, "COMERCIALIZACAO", "NORMAL", "", 39);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(10, 5102, 1102, 5202, "COMERCIALIZACAO", "NORMAL", "", 39);

-- Destacamos o ICMS na Devolução (Veio na compra mas não podemos nos creditar)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(10, 5401, 1403, 5411, "COMERCIALIZACAO", "NORMAL", "", 39);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(10, 5402, 1403, 5411, "COMERCIALIZACAO", "NORMAL", "", 39);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(10, 5403, 1403, 5411, "COMERCIALIZACAO", "NORMAL", "", 39);
					
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(60, 5405, 1403, 5411, "COMERCIALIZACAO", "NORMAL", "", 39);					
-- Óleo
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(60, 5652, 1652, 5661, "COMERCIALIZACAO", "NORMAL", "", 39);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(60, 5655, 1652, 5661, "COMERCIALIZACAO", "NORMAL", "", 39);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(60, 5656, 1652, 5661, "COMERCIALIZACAO", "NORMAL", "", 39);
-- ===============================================================================================================================		

-- ================================ 7 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL ===================================================								

-- ===============================================================================================================================		


-- ================================ 40 -  DEVOLUCAO PARA FORNECEDOR INTERESTADUAL (TRIBUTADA) ===================================================								



-- ===============================================================================================================================		


-- ====================================== 23 - DEVOLUCAO MATERIAL CONSUMO =======================================================	
-- ICMS_CST == null -> Deverá ser usado a mesma que vier na NF do fornecedor							
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(null, 5101, 1556, 5556, "COMERCIALIZACAO", "NORMAL", "", 23);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(null, 5102, 1556, 5556, "COMERCIALIZACAO", "NORMAL", "", 23);

-- Destacamos o ICMS na Devolução (Veio na compra mas não podemos nos creditar)
-- Verificar se nessas cfop abaixo (5401, 5402 e 5403 temos que mencionar o ICMS na devolução, caso sim acredito que o ICMS CST tenha que ser != 90)
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(90, 5401, 1407, 5413, "COMERCIALIZACAO", "NORMAL", "", 23);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(90, 5402, 1407, 5413, "COMERCIALIZACAO", "NORMAL", "", 23);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(90, 5403, 1407, 5413, "COMERCIALIZACAO", "NORMAL", "", 23);
					
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(90, 5405, 1407, 5413, "COMERCIALIZACAO", "NORMAL", "", 23);					
-- Óleo
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(90, 5652, 1653, 5662, "COMERCIALIZACAO", "NORMAL", "", 23);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(90, 5655, 1653, 5662, "COMERCIALIZACAO", "NORMAL", "", 23);
INSERT INTO trib_esta_devo (icms_cst, cfop_venda, cfop_entr, cfop_nota_devo, finalidade, regime_tributario , mens, oper_id) 
					VALUES	(90, 5656, 1653, 5662, "COMERCIALIZACAO", "NORMAL", "", 23);
-- ===============================================================================================================================		


-- ============================== 28 - DEVOLUCAO MERC. DESTINADA A USO E CONSUMO EM OP. C/ST ========================================



-- ==================================================================================================================================		