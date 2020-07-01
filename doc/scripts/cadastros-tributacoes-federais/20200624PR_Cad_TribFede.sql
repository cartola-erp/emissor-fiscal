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

	
    DECLARE CUR_NCMS_JA_USADOS CURSOR FOR SELECT n.ncm_id FROM ncms n WHERE n.nume IN (SELECT v.ncm FROM TODOS_NCMS_USADOS v WHERE v.NCM 
		NOT IN(SELECT m.NCM FROM ncms_monofasicos m));

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
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 50, 'CONSUMO', 0.000000, 0.000000, 50, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 50, 'NORMAL', 8, ID_NCM);
		SELECT "TRIB_FEDE | 8 - DEVOLUCAO DO CLIENTE", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 50, 'CONSUMO', 0.000000, 0.000000, 50, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 50, 'NORMAL', 9, ID_NCM);
		SELECT "TRIB_FEDE | 9 - DEVOLUCAO DO CLIENTE INTERESTADUAL", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 8, 'CONSUMO', 0.000000,0.000000, 8, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 8,'NORMAL',4, ID_NCM);	
		SELECT "TRIB_FEDE | 4 - TRANSFERENCIA", ROW_COUNT();

		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 24, ID_NCM);
		SELECT "TRIB_FEDE | 24 - MOVIMENTO JA REGISTRA EM ECF", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 39, ID_NCM);
		SELECT "TRIB_FEDE | 39 - DEVOLUCAO PARA FORNECEDOR (TRIBUTADA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 29/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 40, ID_NCM);
		SELECT "TRIB_FEDE | 40 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL (TRIBUTADA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.076000, 1.000000, 1, 'CONSUMO', 0.000000, 0.000000, 1, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.016500, 1.000000, 1, 'NORMAL', 45, ID_NCM);
		SELECT "TRIB_FEDE | 45 - VENDA DE PRODUTO PARA ENTREGA FUTURA", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 49, 'CONSUMO', 0.000000,0.000000, 49, 'Tributacao Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 49,'NORMAL', 46, ID_NCM);	
		SELECT "TRIB_FEDE | 46 - REMESSA VINCULADA A VENDA DE ENTREGA FUTURA", ROW_COUNT();
				
	END LOOP updateValor;	
	
	CLOSE CUR_NCMS_JA_USADOS;

END $$
DELIMITER ;

CALL emissorfiscal.PR_CADASTRA_TRIBUTACOES_FEDERAIS();

DROP PROCEDURE IF EXISTS emissorfiscal.`PR_CADASTRA_TRIBUTACOES_FEDERAIS`;