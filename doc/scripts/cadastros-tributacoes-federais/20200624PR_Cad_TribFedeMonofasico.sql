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
    DECLARE CUR_NCMS_MONO_EXCE_0 CURSOR FOR SELECT n.ncm_id FROM ncms n WHERE n.nume IN (SELECT m.ncm FROM NCMS_MONOFASICOS m) GROUP BY nume HAVING COUNT(nume) = 1; 
		
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
		
		-- FALTA A GABRIELA CONFIRMAR
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',8, ID_NCM);
		SELECT "TRIB_FEDE | 8 - DEVOLUCAO DO CLIENTE", ROW_COUNT();
		
		-- FALTA A GABRIELA CONFIRMAR
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',9, ID_NCM);
		SELECT "TRIB_FEDE | 9 - DEVOLUCAO DO CLIENTE INTERESTADUAL", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',4, ID_NCM);	
		SELECT "TRIB_FEDE | 4 - TRANSFERENCIA MONOFASICA", ROW_COUNT();

		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',24, ID_NCM);	
		SELECT "TRIB_FEDE | 24 - MOVIMENTO JA REGISTRA EM ECF", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',39, ID_NCM);	
		SELECT "TRIB_FEDE | 39 - DEVOLUCAO PARA FORNECEDOR (TRIBUTADA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',40, ID_NCM);	
		SELECT "TRIB_FEDE | 40 - DEVOLUCAO PARA FORNECEDOR INTERESTADUAL (TRIBUTADA)", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',45, ID_NCM);	
		SELECT "TRIB_FEDE | 45 - VENDA DE PRODUTO PARA ENTREGA FUTURA", ROW_COUNT();
		
		INSERT INTO `trib_fede` (cofins_aliq, cofins_base, cofins_cst, finalidade, ipi_aliq, ipi_base, ipi_cst, mens, pis_aliq, pis_base, pis_cst, regime_tributario, oper_id, ncm_id) VALUES (0.000000, 0.000000, 4, 'CONSUMO', 0.000000,0.000000, 4, 'Tributacao MONOFÀSICO Cadastrada - 24/06 - VIA SCRIPT', 0.000000, 0.000000, 4,'NORMAL',46, ID_NCM);	
		SELECT "TRIB_FEDE | 46 - REMESSA VINCULADA A VENDA DE ENTREGA FUTURA", ROW_COUNT();
		
		
	END LOOP insertNcmMono;	
	
	CLOSE CUR_NCMS_MONO_EXCE_0;
	
END $$
DELIMITER ;

CALL emissorfiscal.PR_CADASTRA_TRIBUTACOES_FEDERAIS_MONO();

DROP PROCEDURE IF EXISTS emissorfiscal.`PR_CADASTRA_TRIBUTACOES_FEDERAIS_MONO`;