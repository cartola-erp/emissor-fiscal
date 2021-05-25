/**
 * Author:  robson.costa
 * Created: 24/06/2020
 */


DROP PROCEDURE IF EXISTS emissorfiscal.`PR_CADASTRA_TRIBUTACOES_FEDERAIS`;

DELIMITER $$ 

CREATE DEFINER=`root`@`localhost` PROCEDURE emissorfiscal.`PR_CADASTRA_TRIBUTACOES_FEDERAIS`() 
BEGIN
    
    DECLARE terminou INTEGER DEFAULT 0;
    DECLARE ID_NCM INT;
	
	-- "Selecione, todos os IDs de NCMS que estejam nos NCMS USADOS e NÃO SEJA (NOT IN) MONOFASICO
	DECLARE CUR_NCMS_JA_USADOS CURSOR FOR SELECT distinct(n.ncm_id) FROM ncms n INNER JOIN TODOS_NCMS_USADOS v ON (n.NUME = v.NCM AND n.EXCE = v.EXCECAO) 
	WHERE v.CODIGO NOT IN (select t.codigo from todos_ncms_usados t INNER JOIN ncms_monofasicos m ON (t.NCM = m.NCM AND t.EXCECAO = m.EXCECAO));
	
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET terminou = 1;
	
    OPEN CUR_NCMS_JA_USADOS;
	
	updateValor : LOOP 
		FETCH CUR_NCMS_JA_USADOS INTO ID_NCM;
		IF terminou = 1 THEN 
			LEAVE updateValor;
		END IF;
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 1, ID_NCM);
		SELECT "TRIB_FEDE | 1 - VENDA", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 2, ID_NCM);
		SELECT "TRIB_FEDE | 2 - VENDA INTERESTADUAL (JURIDICA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 3, ID_NCM);
		SELECT "TRIB_FEDE | 3 - VENDA INTERESTADUAL (FISICA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 6, ID_NCM);
		SELECT "TRIB_FEDE | 6 - DEVOLUCAO PARA FORNECEDOR", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 7, ID_NCM);
		SELECT "TRIB_FEDE | 7 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 1, 'NORMAL', 24, ID_NCM);
		SELECT "TRIB_FEDE | 24 - MOVIMENTO JA REGISTRA EM ECF", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 39, ID_NCM);
		SELECT "TRIB_FEDE | 39 - DEVOLUCAO PARA FORNECEDOR (TRIBUTADA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 40, ID_NCM);
		SELECT "TRIB_FEDE | 40 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL (TRIBUTADA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 46, ID_NCM);		
		SELECT "TRIB_FEDE | 46 - REMESSA VINCULADA A VENDA DE ENTREGA FUTURA", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 59, ID_NCM);
		SELECT "TRIB_FEDE | 59 - VENDA DE MERCADORIA SUJEITA A ST, COM CONTRIB. SUBSTIT.", ROW_COUNT();
		
		-- CST 08 
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',4, ID_NCM);	
		SELECT "TRIB_FEDE | 4 - TRANSFERENCIA", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',21, ID_NCM);	
		SELECT "TRIB_FEDE | 21 - REMESSA P/ CONSERTO", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',12, ID_NCM);	
		SELECT "TRIB_FEDE | 12 -  VENDA DE IMOBILIZADO", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',13, ID_NCM);	
		SELECT "TRIB_FEDE | 13 - TRANSFERENCIA DE SALDO DEVEDOR ART.98 DO RICMS", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',14, ID_NCM);	
		SELECT "TRIB_FEDE | 14 - TRANSFERENCIA DE SALDO CREDOR  ART.98 DO RICMS", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',25, ID_NCM);	
		SELECT "TRIB_FEDE | 25 -  MOVIMENTO JA REGISTRA EM ECF (INTERESTADUAL)", ROW_COUNT();
			
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',41, ID_NCM);	
		SELECT "TRIB_FEDE | 41 - TRANSFERENCIA (SAIDA) - GARANTIA", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',45, ID_NCM);	
		SELECT "TRIB_FEDE | 45 - VENDA DE PRODUTO PARA ENTREGA FUTURA", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',48, ID_NCM);	
		SELECT "TRIB_FEDE | 48 - REMESSA EM BONIFICACAO, DOACAO OU BRINDE", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',50, ID_NCM);	
		SELECT "TRIB_FEDE | 50 - REMESSA EM CONSIGNACAO", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',67, ID_NCM);	
		SELECT "TRIB_FEDE | 67 - TRANSFERENCIA DE MATERIAL DE USO OU CONSUMO", ROW_COUNT();
		
		-- CST 49
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 49, 'CONSUMO', 0.000000,0.000000, 49, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 49,'NORMAL', 10, ID_NCM);	
		SELECT "TRIB_FEDE | 10 - OUTRAS SAIDA NAO ESPECIFICADAS", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 49, 'CONSUMO', 0.000000,0.000000, 49, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 49,'NORMAL', 29, ID_NCM);	
		SELECT "TRIB_FEDE | 29 - OUTRAS SAIDAS NAO ESPECIFICADAS", ROW_COUNT();
				
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 49, 'CONSUMO', 0.000000,0.000000, 49, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 49,'NORMAL', 30, ID_NCM);	
		SELECT "TRIB_FEDE | 30 - OUTRAS SAIDAS (SIMPLES REMESSA)", ROW_COUNT();
		
		-- CST 50
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 50, 'CONSUMO', 0.000000, 0.000000, 50, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 50, 'NORMAL', 8, ID_NCM);
		SELECT "TRIB_FEDE | 8 - DEVOLUCAO DO CLIENTE", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 50, 'CONSUMO', 0.000000, 0.000000, 50, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 50, 'NORMAL', 9, ID_NCM);
		SELECT "TRIB_FEDE | 9 - DEVOLUCAO DO CLIENTE INTERESTADUAL", ROW_COUNT();
		
		-- CST 98
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 98, 'CONSUMO', 0.000000,0.000000, 98, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 98,'NORMAL',16, ID_NCM);	
		SELECT "TRIB_FEDE | 16 - OUTRAS ENTRADAS NAO ESPECIFICADAS", ROW_COUNT();
		
		
	END LOOP updateValor;	
	
	CLOSE CUR_NCMS_JA_USADOS;

END $$
DELIMITER ;

CALL emissorfiscal.PR_CADASTRA_TRIBUTACOES_FEDERAIS();

DROP PROCEDURE IF EXISTS emissorfiscal.`PR_CADASTRA_TRIBUTACOES_FEDERAIS`;