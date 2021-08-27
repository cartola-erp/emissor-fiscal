

-- Selecionando os ids dos ncms, que NÃO são monofasicos
SELECT n.ncm_id FROM ncms n WHERE n.ncm_id NOT IN(
	SELECT n.ncm_id FROM ncms n INNER JOIN NCMS_MONOFASICOS m ON (n.nume = m.NCM and n.exce = m.EXCECAO ));

-- Criando a view para ajudar nos inserts
CREATE VIEW vw_ncm_id_tribu AS SELECT n.ncm_id FROM ncms n WHERE n.ncm_id NOT IN(
								SELECT n.ncm_id FROM ncms n INNER JOIN NCMS_MONOFASICOS m ON (n.nume = m.NCM and n.exce = m.EXCECAO ));


-- ========================================================================================================================================================
-- ============================================================== -- CST 01 - TRIBUTADO ==============================================================	
-- ========================================================================================================================================================
-- --------------------------------------------------------- 		1 - VENDA 					---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, '', 0.016500, 1.000000, 1, 'NORMAL', 1, ncm_id FROM vw_ncm_id_tribu;

-- --------------------------------------------------------- 2 - VENDA INTERESTADUAL (JURIDICA) ---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, '', 0.016500, 1.000000, 1, 'NORMAL', 2, ncm_id FROM vw_ncm_id_tribu;

-- --------------------------------------------------------- 3 - VENDA INTERESTADUAL (FISICA)	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, '', 0.016500, 1.000000, 1, 'NORMAL', 3, ncm_id FROM vw_ncm_id_tribu;

-- --------------------------------------------------------- 6 - DEVOLUCAO PARA FORNECEDOR 		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, '', 0.016500, 1.000000, 1, 'NORMAL', 6, ncm_id FROM vw_ncm_id_tribu;

-- --------------------------------------------------------- 7 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL ---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, '', 0.016500, 1.000000, 1, 'NORMAL', 7, ncm_id FROM vw_ncm_id_tribu;


-- --------------------------------------------------------- 39 - DEVOLUCAO PARA FORNECEDOR (TRIBUTADA)	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, '', 0.016500, 1.000000, 1, 'NORMAL', 39, ncm_id FROM vw_ncm_id_tribu;

-- --------------------------------------------------------- 40 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL (TRIBUTADA) ---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, '', 0.016500, 1.000000, 1, 'NORMAL', 40, ncm_id FROM vw_ncm_id_tribu;

-- --------------------------------------------------------- 46 - REMESSA VINCULADA A VENDA DE ENTREGA FUTURA ---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, '', 0.016500, 1.000000, 1, 'NORMAL', 46, ncm_id FROM vw_ncm_id_tribu;

-- --------------------------------------------------------- 59 - VENDA DE MERCADORIA SUJEITA A ST, COM CONTRIB. SUBSTIT. ---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, '', 0.016500, 1.000000, 1, 'NORMAL', 59, ncm_id FROM vw_ncm_id_tribu;

-- --------------------------------------------------------- 63 | VENDA DE SUCATA | NCM = 85481010| EX = 0 (CST 01) ---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
			VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000,0.000000, 1, '', 0.016500, 1.000000, 1,'NORMAL',63, 9902);	

-- --------------------------------------------------------- 83 - DISTRIBUICAO GRATUITA DE ITEM DE ESTOQUE ---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, '', 0.016500, 1.000000, 1, 'NORMAL', 83, ncm_id FROM vw_ncm_id_tribu;

-- ========================================================================================================================================================

-- CST 01 - ZERADO (Sem impostos, pois é VENDA do SAT que virou NOTA)
-- --------------------------------------------------------- 24 - MOVIMENTO JA REGISTRA EM ECF			 	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, '', 0.000000, 0.000000, 1, 'NORMAL', 24, ncm_id FROM vw_ncm_id_tribu;


-- ========================================================================================================================================================
-- ============================================================== CST 08 - Tributacao Zerada ==============================================================	
-- ========================================================================================================================================================
-- --------------------------------------------------------- 4 - TRANSFERENCIA			---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',4, ncm_id FROM vw_ncm_id_tribu;	

-- --------------------------------------------------------- 21 - REMESSA P/ CONSERTO			 ---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',21, ncm_id FROM vw_ncm_id_tribu;	

-- --------------------------------------------------------- 26 – TRANSFERÊNCIA DE BEM DO ATIVO IMOBILIZADO			 ---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',26, ncm_id FROM vw_ncm_id_tribu;	


-- --------------------------------------------------------- 12 -  VENDA DE IMOBILIZADO			 ---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',12, ncm_id FROM vw_ncm_id_tribu;	

-- --------------------------------------------------------- 13 - TRANSFERENCIA DE SALDO DEVEDOR ART.98 DO RICMS	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',13, ncm_id FROM vw_ncm_id_tribu;	

-- --------------------------------------------------------- 14 - TRANSFERENCIA DE SALDO CREDOR  ART.98 DO RICMS	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',14, ncm_id FROM vw_ncm_id_tribu;	

-- --------------------------------------------------------- 25 -  MOVIMENTO JA REGISTRA EM ECF (INTERESTADUAL)		---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',25, ncm_id FROM vw_ncm_id_tribu;	
	
-- --------------------------------------------------------- 41 - TRANSFERENCIA (SAIDA) - GARANTIA			---------------------------------------------------------	
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',41, ncm_id FROM vw_ncm_id_tribu;	

-- --------------------------------------------------------- 45 - VENDA DE PRODUTO PARA ENTREGA FUTURA			---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',45, ncm_id FROM vw_ncm_id_tribu;	

-- --------------------------------------------------------- 48 - REMESSA EM BONIFICACAO, DOACAO OU BRINDE			---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',48, ncm_id FROM vw_ncm_id_tribu;	

-- --------------------------------------------------------- 50 - REMESSA EM CONSIGNACAO			---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',50, ncm_id FROM vw_ncm_id_tribu;	

-- --------------------------------------------------------- 67 - TRANSFERENCIA DE MATERIAL DE USO OU CONSUMO			---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',67, ncm_id FROM vw_ncm_id_tribu;	
				
-- --------------------------------------------------------- 79 | LANCAMENTO EFETUADO A TITULO DE BAIXA DE ESTOQUE			---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, '', 0.000000, 0.000000, 8,'NORMAL',79, ncm_id FROM vw_ncm_id_tribu;					
				
-- ========================================================================================================================================================



-- ========================================================================================================================================================
-- ============================================================== CST 49 - Tributacao Zerada ==============================================================	
-- ========================================================================================================================================================
-- --------------------------------------------------------- 10 - OUTRAS SAIDA NAO ESPECIFICADAS	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 49, 'CONSUMO', 0.000000,0.000000, 49, '', 0.000000, 0.000000, 49,'NORMAL', 10, ncm_id FROM vw_ncm_id_tribu;	

-- --------------------------------------------------------- 29 - OUTRAS SAIDAS NAO ESPECIFICADAS	---------------------------------------------------------//
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 49, 'CONSUMO', 0.000000,0.000000, 49, '', 0.000000, 0.000000, 49,'NORMAL', 29, ncm_id FROM vw_ncm_id_tribu;	
		
-- --------------------------------------------------------- 30 - OUTRAS SAIDAS (SIMPLES REMESSA)	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 49, 'CONSUMO', 0.000000,0.000000, 49, '', 0.000000, 0.000000, 49,'NORMAL', 30, ncm_id FROM vw_ncm_id_tribu;	
-- ========================================================================================================================================================
		

-- ========================================================================================================================================================
-- ============================================================== CST 50 - TRIBUTADO ==============================================================	
-- ========================================================================================================================================================
-- --------------------------------------------------------- 8 - DEVOLUCAO DO CLIENTE	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 50, 'CONSUMO', 0.000000, 0.000000, 50, '', 0.016500, 1.000000, 50, 'NORMAL', 8, ncm_id FROM vw_ncm_id_tribu;

-- --------------------------------------------------------- 9 - DEVOLUCAO DO CLIENTE INTERESTADUAL	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.076000, 1.000000, 50, 'CONSUMO', 0.000000, 0.000000, 50, '', 0.016500, 1.000000, 50, 'NORMAL', 9, ncm_id FROM vw_ncm_id_tribu;

-- Emitida pelo fornecedor (Vamos Dar Entrada)
-- --------------------------------- 81 - COMPRA DE SUCATA PARA COMERCIALIZACAO (CONTRIBUINTE)  NCM = 85481010| EX = 0 ------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
	VALUES (0.076000, 1.000000, 50, 'CONSUMO', 0.000000,0.000000, 1, '', 0.016500, 1.000000, 50,'NORMAL', 81, 9902);	

-- ========================================================================================================================================================


-- ========================================================================================================================================================
-- ============================================================== CST 98 - Tributacao Zerada ==============================================================	
-- ========================================================================================================================================================
-- --------------------------------------------------------- 16 - OUTRAS ENTRADAS NAO ESPECIFICADAS	---------------------------------------------------------

INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 98, 'CONSUMO', 0.000000,0.000000, 98, '', 0.000000, 0.000000, 98,'NORMAL',16, ncm_id FROM vw_ncm_id_tribu;	

-- --------------------------------------------------------- 34 - OAQUISICAO DE MERCADORIAS DENTRO DO ESTADO PARA CONSUMO	---------------------------------------------------------
INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
				SELECT 0.000000, 0.000000, 98, 'CONSUMO', 0.000000,0.000000, 98, '', 0.000000, 0.000000, 98,'NORMAL',34, ncm_id FROM vw_ncm_id_tribu;	

-- Entrada emitida pela AG
-- --------------------------------- 73 | COMPRA DE SUCATA PARA COMERCIALIZACAO (NAO CONTRIBUINTE) | NCM = 85481010| EX = 0 ---------------------------------
	INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) 
		VALUES (0.000000, 0.000000, 98, 'CONSUMO', 0.000000,0.000000, 98, '', 0.000000, 0.000000, 98,'NORMAL',73, 9902);	
-- ========================================================================================================================================================


DROP VIEW vw_ncm_id_tribu;