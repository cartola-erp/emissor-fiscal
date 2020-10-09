/**
 * Author:  robson.costa
 * Created: 24/06/2020
 */


-- PS: PARA os NCMS, que tem várias EXCECOES, Eu

DROP PROCEDURE IF EXISTS emissorfiscal.`PR_CADASTRA_TRIBUTACOES_FEDERAIS_MONO`;

DELIMITER $$ 

CREATE DEFINER=`root`@`localhost` PROCEDURE emissorfiscal.`PR_CADASTRA_TRIBUTACOES_FEDERAIS_MONO`() 
BEGIN
    
    DECLARE terminou INTEGER DEFAULT 0;
    DECLARE ID_NCM INT;
    
	-- "Selecione todos os IDs de NCMS que SÃO monofásicos
	DECLARE CUR_NCMS_MONO_EXCE_0 CURSOR FOR SELECT n.ncm_id FROM ncms n INNER JOIN ncms_monofasicos m ON (n.NUME = m.NCM AND n.EXCE = m.EXCECAO);
	
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET terminou = 1;
	
    OPEN CUR_NCMS_MONO_EXCE_0;
	
	insertNcmMono : LOOP 
		FETCH CUR_NCMS_MONO_EXCE_0 INTO ID_NCM;
		IF terminou = 1 THEN 
			LEAVE insertNcmMono;
		END IF;
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',1, ID_NCM);
		SELECT "TRIB_FEDE | 1 - VENDA MONOFASICA", ROW_COUNT();
			
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',2, ID_NCM);
		SELECT "TRIB_FEDE | 2 - VENDA INTERESTADUAL (JURIDICA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',3, ID_NCM);
		SELECT "TRIB_FEDE | 3 - VENDA INTERESTADUAL (FISICA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',6, ID_NCM);
		SELECT "TRIB_FEDE | 6 - DEVOLUCAO PARA FORNECEDOR	", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',7, ID_NCM);
		SELECT "TRIB_FEDE | 7 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',8, ID_NCM);
		SELECT "TRIB_FEDE | 8 - DEVOLUCAO DO CLIENTE", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',9, ID_NCM);
		SELECT "TRIB_FEDE | 9 - DEVOLUCAO DO CLIENTE INTERESTADUAL", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',10, ID_NCM);
		SELECT "TRIB_FEDE | 10 - OUTRAS SAIDA NAO ESPECIFICADAS", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',12, ID_NCM);
		SELECT "TRIB_FEDE | 12 - VENDA DE IMOBILIZADO", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',13, ID_NCM);
		SELECT "TRIB_FEDE | 13 - TRANSFERENCIA DE SALDO DEVEDOR ART.98 DO RICMS ", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',14, ID_NCM);
		SELECT "TRIB_FEDE | 14 - TRANSFERENCIA DE SALDO CREDOR  ART.98 DO RICMS", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',4, ID_NCM);	
		SELECT "TRIB_FEDE | 4 - TRANSFERENCIA MONOFASICA", ROW_COUNT();

		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',21, ID_NCM);
		SELECT "TRIB_FEDE | 21 - REMESSA P/ CONSERTO", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',24, ID_NCM);	
		SELECT "TRIB_FEDE | 24 - MOVIMENTO JA REGISTRA EM ECF", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',25, ID_NCM);	
		SELECT "TRIB_FEDE | 25 -  MOVIMENTO JA REGISTRA EM ECF (INTERESTADUAL)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',29, ID_NCM);
		SELECT "TRIB_FEDE | 29 - OUTRAS SAIDAS NAO ESPECIFICADAS", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',30, ID_NCM);
		SELECT "TRIB_FEDE | 30 - OUTRAS SAIDAS (SIMPLES REMESSA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',39, ID_NCM);	
		SELECT "TRIB_FEDE | 39 - DEVOLUCAO PARA FORNECEDOR (TRIBUTADA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',40, ID_NCM);	
		SELECT "TRIB_FEDE | 40 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL (TRIBUTADA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',41, ID_NCM);	
		SELECT "TRIB_FEDE | 41 - TRANSFERENCIA (SAIDA) - GARANTIA", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',45, ID_NCM);	
		SELECT "TRIB_FEDE | 45 - VENDA DE PRODUTO PARA ENTREGA FUTURA", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',46, ID_NCM);	
		SELECT "TRIB_FEDE | 46 - REMESSA VINCULADA A VENDA DE ENTREGA FUTURA", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',48, ID_NCM);	
		SELECT "TRIB_FEDE | 48 -  REMESSA EM BONIFICACAO, DOACAO OU BRINDE", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',50, ID_NCM);	
		SELECT "TRIB_FEDE | 50 -  REMESSA EM CONSIGNACAO", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',59, ID_NCM);	
		SELECT "TRIB_FEDE | 59 - VENDA DE MERCADORIA SUJEITA A ST, COM CONTRIB. SUBSTIT.", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',67, ID_NCM);	
		SELECT "TRIB_FEDE | 67 - VENDA DE MERCADORIA SUJEITA A ST, COM CONTRIB. SUBSTIT.", ROW_COUNT();
		
	END LOOP insertNcmMono;	
	
	CLOSE CUR_NCMS_MONO_EXCE_0;
	
END $$
DELIMITER ;

CALL emissorfiscal.PR_CADASTRA_TRIBUTACOES_FEDERAIS_MONO();

DROP PROCEDURE IF EXISTS emissorfiscal.`PR_CADASTRA_TRIBUTACOES_FEDERAIS_MONO`;