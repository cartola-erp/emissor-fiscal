
-- SELECIONANDO os ids dos ncms, que SÃO MONOFÀSICOS
SELECT n.ncm_id, n.nume FROM ncms n INNER JOIN NCMS_MONOFASICOS m ON (n.nume = m.NCM and n.exce = m.EXCECAO );


-- Criando a view para ajudar nos inserts
CREATE VIEW vw_ncm_id_mono AS SELECT n.ncm_id, n.nume FROM ncms n INNER JOIN NCMS_MONOFASICOS m ON (n.nume = m.NCM and n.exce = m.EXCECAO );


-- --------------------------------------------------------- 	1 - VENDA 					---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',1, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	2 - VENDA INTERESTADUAL (JURIDICA) 	---------------------------------------------------------	
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',2, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	3 - VENDA INTERESTADUAL (FISICA)	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',3, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	4 - TRANSFERENCIA MONOFASICA 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',4, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	6 - DEVOLUCAO PARA FORNECEDOR 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',6, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	7 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL 					---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',7, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	10 - REMESSA DE MERCADORIA PARA ANALISE EM GARANTIA 	-------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',10, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	11 - REMESSA DE MERCADORIA PARA ANALISE EM GARANTIA (INTERESTADUAL) 	---------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',11, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	13 - TRANSFERENCIA DE SALDO DEVEDOR ART.98 DO RICMS 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',13, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	14 - TRANSFERENCIA DE SALDO CREDOR  ART.98 DO RICMS 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',14, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	16 - OUTRAS ENTRADAS NAO ESPECIFICADAS 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL', 16, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	21 - REMESSA P/ CONSERTO 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',21, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	24 - MOVIMENTO JA REGISTRA EM ECF 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',24, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	25 -  MOVIMENTO JA REGISTRA EM ECF (INTERESTADUAL) 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',25, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------


-- --------------------------------------------------------- 	29 - OUTRAS SAIDAS NAO ESPECIFICADAS 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',29, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	30 - OUTRAS SAIDAS (SIMPLES REMESSA) 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',30, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	39 - DEVOLUCAO PARA FORNECEDOR (TRIBUTADA) 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',39, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	40 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL (TRIBUTADA) 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',40, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	41 - TRANSFERENCIA (SAIDA) - GARANTIA 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',41, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	46 - REMESSA VINCULADA A VENDA DE ENTREGA FUTURA 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',46, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	48 -  REMESSA EM BONIFICACAO, DOACAO OU BRINDE 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',48, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	50 -  REMESSA EM CONSIGNACAO", 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',50, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	59 - VENDA DE MERCADORIA SUJEITA A ST, COM CONTRIB. SUBSTIT. 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',59, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	83 - DISTRIBUICAO GRATUITA DE ITEM DE ESTOQUE 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',83, ncm_id FROM vw_ncm_id_mono;
				
-- --------------------------------------------------------- 	84 - REMESSA P/ CONSERTO ATIVO/CONSUMO - INTERESTADUAL 	-------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, '', 0.000000, 0.000000, 4,'NORMAL',84, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
			



-- CSTS Diferente da 04 (monofasico), PORÉM sem TRIBUTACAO

-- ========================================================================================================================================================
-- ============================================================== CST 08 - Tributacao Zerada ==============================================================	
-- ========================================================================================================================================================

-- --------------------------------------------------------- 	23 – DEVOLUCAO MATERIAL CONSUMO 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',23, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	26 – TRANSFERÊNCIA DE BEM DO ATIVO IMOBILIZADO 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',26, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	28 – DEVOLUCAO MERC. DESTINADA A USO E CONSUMO EM OP. C/ST 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',28, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------


-- --------------------------------------------------------- 	45 - VENDA DE PRODUTO PARA ENTREGA FUTURA 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',45, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------


-- --------------------------------------------------------- 	67 - TRANSFERENCIA DE MATERIAL DE USO OU CONSUMO 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',67, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------


-- --------------------------------------------------------- 	79 | LANCAMENTO EFETUADO A TITULO DE BAIXA DE ESTOQUE 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',79, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- ========================================================================================================================================================


-- ========================================================================================================================================================
-- ============================================================== CST 70 - Tributacao Zerada ==============================================================	
-- ========================================================================================================================================================
-- --------------------------------------------------------- 18 - COMPRA DE BEM PARA ATIVO IMOBILIZADO	---------------------------------------------------------
-- Entradas emitida pela AG
-- --------------------------------------------------------- 	8 - DEVOLUCAO DO CLIENTE 				---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 70, 'CONSUMO', 0.000000,0.000000, 70, '', 0.000000, 0.000000, 70,'NORMAL',8, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- --------------------------------------------------------- 	9 - DEVOLUCAO DO CLIENTE INTERESTADUAL 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 70, 'CONSUMO', 0.000000,0.000000, 70, '', 0.000000, 0.000000, 70,'NORMAL',9, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------


INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 70, 'CONSUMO', 0.000000,0.000000, 70, '', 0.000000, 0.000000, 70,'NORMAL',18, ncm_id FROM vw_ncm_id_mono;		



-- ------------------------------------------------ 33 - AQUISICAO DE MERCADORIAS FORA DO ESTADO PARA COMERCIALIZACAO ---------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 70, 'CONSUMO', 0.000000,0.000000, 70, '', 0.000000, 0.000000, 70,'NORMAL',33, ncm_id FROM vw_ncm_id_mono;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------

-- ========================================================================================================================================================



-- ========================================================================================================================================================
-- ============================================================== CST 98 - Tributacao Zerada ==============================================================	
-- ========================================================================================================================================================
-- --------------------------------------------------------- 	34 - AQUISICAO DE MERCADORIAS DENTRO DO ESTADO PARA CONSUMO 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 98, 'CONSUMO', 0.000000,0.000000, 98, '', 0.000000, 0.000000, 98,'NORMAL',34, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
-- ========================================================================================================================================================


-- ========================================================================================================================================================
-- ============================================================== CST 99 - Tributacao Zerada ==============================================================	
-- ========================================================================================================================================================
-- ------------------------------------------------------------ 12 - VENDA DE IMOBILIZADO  ----------------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 99, 'CONSUMO', 0.000000,0.000000, 99, '', 0.000000, 0.000000, 99,'NORMAL',12, ncm_id FROM vw_ncm_id_mono;	
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
-- ========================================================================================================================================================


DROP VIEW vw_ncm_id_mono;

