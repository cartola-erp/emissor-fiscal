

DROP TABLE IF EXISTS ncms_validados_consulcamp;

CREATE TABLE IF NOT EXISTS `ncms_validados_consulcamp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ncm_id` int(11) DEFAULT NULL,
  `ncm` int(11) DEFAULT NULL,
  `descricao` VARCHAR(1024),
  `cfop` int(11) NOT NULL,
  `cfop_transferencia` int(11) NOT NULL,
  `cst` int ,
  `cod_anp` int(11) NOT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Naum consegui fazer funcionar o LOAD DATA INFILE com o FLYWAY
-- LOAD DATA LOCAL INFILE 'NCMs - Validado pela Consulcamp.csv'
-- 	INTO TABLE ncms_validados_consulcamp CHARACTER SET latin1 
-- FIELDS TERMINATED BY ';' 
-- OPTIONALLY ENCLOSED BY '\"'
-- LINES TERMINATED BY '\r\n' 
-- IGNORE 2 LINES 
-- (ncm, cfop, @cst_icms, @cfop_transf)
-- SET cst = @cst_icms,
-- cfop_transferencia = @cfop_transf;	



--
-- Dumping data for table `ncms_validados_consulcamp`
--
INSERT INTO `ncms_validados_consulcamp` VALUES 
(1,NULL,27079990,NULL,5102,5152,0,0),
(2,NULL,27101249,NULL,5102,5152,0,0),
(3,NULL,28061010,NULL,5102,5152,0,0),
(4,NULL,28272090,NULL,5102,5152,0,0),
(5,NULL,29161990,NULL,5102,5152,0,0),
(6,NULL,29420000,NULL,5102,5152,0,0),
(7,NULL,32082011,NULL,5102,5152,0,0),
(8,NULL,32082020,NULL,5102,5152,0,0),
(9,NULL,34012090,NULL,5102,5152,0,0),
(10,NULL,38151900,NULL,5102,5152,0,0),
(11,NULL,38249939,NULL,5102,5152,0,0),
(12,NULL,38249989,NULL,5102,5152,0,0),
(13,NULL,39073011,NULL,5102,5152,0,0),
(14,NULL,39206299,NULL,5102,5152,0,0),
(15,NULL,39233090,NULL,5102,5152,0,0),
(16,NULL,39269069,NULL,5102,5152,0,0),
(17,NULL,40092290,NULL,5405,5409,60,0),
(18,NULL,40094290,NULL,5405,5409,60,0),
(19,NULL,40103500,NULL,5405,5409,60,0),
(20,NULL,48237000,NULL,5102,5152,0,0),
(21,NULL,56041000,NULL,5102,5152,0,0),
(22,NULL,68151020,NULL,5102,5152,0,0),
(23,NULL,70109011,NULL,5102,5152,0,0),
(24,NULL,72132000,NULL,5102,5152,0,0),
(25,NULL,73042931,NULL,5102,5152,0,0),
(26,NULL,73043110,NULL,5102,5152,0,0),
(27,NULL,74199990,NULL,5102,5152,0,0),
(28,NULL,82075090,NULL,5102,5152,0,0),
(29,NULL,83024900,NULL,5102,5152,0,0),
(30,NULL,84099114,NULL,5405,5409,60,0),
(31,NULL,84129080,NULL,5102,5152,0,0),
(32,NULL,84129090,NULL,5102,5152,0,0),
(33,NULL,84148090,NULL,5102,5152,0,0),
(34,NULL,84195021,NULL,5405,5409,60,0),
(35,NULL,84199039,NULL,5102,5152,0,0),
(36,NULL,84669100,NULL,5102,5152,0,0),
(37,NULL,84672992,NULL,5102,5152,0,0),
(38,NULL,84796000,NULL,5102,5152,0,0),
(39,NULL,84818097,NULL,5102,5152,0,0),
(40,NULL,84829910,NULL,5405,5409,60,0),
(41,NULL,84841000,NULL,5405,5409,60,0),
(42,NULL,85013210,NULL,5102,5152,0,0),
(43,NULL,85013310,NULL,5102,5152,0,0),
(44,NULL,85030090,NULL,5102,5152,0,0),
(45,NULL,85059080,NULL,5102,5152,0,0),
(46,NULL,85059090,NULL,5102,5152,0,0),
(47,NULL,85111000,NULL,5405,5409,60,0),
(48,NULL,85112010,NULL,5405,5409,60,0),
(49,NULL,85333910,NULL,5102,5152,0,0),
(50,NULL,85334011,NULL,5102,5152,0,0),
(51,NULL,85334099,NULL,5102,5152,0,0),
(52,NULL,85371090,NULL,5102,5152,0,0),
(53,NULL,85439010,NULL,5102,5152,0,0),
(54,NULL,85472090,NULL,5102,5152,0,0),
(55,NULL,87042390,NULL,5102,5152,0,0),
(56,NULL,87084080,NULL,5405,5409,60,0),
(57,NULL,87089200,NULL,5405,5409,60,0),
(58,NULL,87164000,NULL,5102,5152,0,0),
(59,NULL,87169010,NULL,5405,5409,60,0),
(60,NULL,89020090,NULL,5102,5152,0,0),
(61,NULL,90259090,NULL,5102,5152,0,0),
(62,NULL,90268000,NULL,5102,5152,0,0),
(63,NULL,90291090,NULL,5405,5409,60,0),
(64,NULL,90312090,NULL,5102,5152,0,0),
(65,NULL,90328923,NULL,5405,5409,60,0),
(66,NULL,90328981,NULL,5405,5409,60,0),
(67,NULL,90328990,NULL,5405,5409,60,0),
(68,NULL,25049000,NULL,5102,5152,0,0),
(69,NULL,28539019,NULL,5102,5152,0,0);


-- colocando os ids dos NCMS para facilitar nos inserts
UPDATE ncms_validados_consulcamp c INNER JOIN ncms n ON (c.ncm = n.nume AND n.exce = 0) 
SET c.ncm_id = n.ncm_id;

SELECT c.*, n.ncm_id, n.nume FROM ncms_validados_consulcamp c INNER JOIN ncms n ON (c.ncm = n.nume AND n.exce = 0);

-- Deletando ncms que já estavam previamente cadastrados 
DELETE FROM trib_esta  where oper_id IN(1,4) and ncm_Id IN (select ncm_id from ncms_validados_consulcamp where ncm_id is not null) ;

-- 	============================================================================================================================================================
-- 	======================= cadastrando tributacoes estaduais para venda/transferencia (produtos importados e nacionais) - TRIBUTADOS ==========================
--  ============================================================================================================================================================

	-- VENDA - cst 00 - importado
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
	(select '199900', c.cfop, 0.000000,	'CONSUMO', 0.180000, 0.000000,	1.000000,	cst,	1.000000,	0.000000,	'',	'NORMAL',	0,	26,	26,	c.ncm_id, 1,	
		true FROM ncms_validados_consulcamp c where c.ncm_id is not null and c.cst = 0);
		
	-- VENDA - cst 00 - nacional
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
	(select '199900', c.cfop, 0.000000,	'CONSUMO', 0.180000, 0.000000,	1.000000,	cst,	1.000000,	0.000000,	'',	'NORMAL',	0,	26,	26,	c.ncm_id, 1,	
		false FROM ncms_validados_consulcamp c where c.ncm_id is not null and c.cst = 0);
		

	-- TRANSFERENCIA - cst 00 - importado
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
	(select '199900', c.cfop_transferencia, 0.000000,	'CONSUMO', 0.180000, 0.000000,	1.000000,	cst,	1.000000,	0.000000,	'',	'NORMAL',	0,	26,	26,	c.ncm_id, 4,	
		true FROM ncms_validados_consulcamp c where c.ncm_id is not null and c.cst = 0);
		
	-- TRANSFERENCIA - cst 00 - nacional
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
	(select '199900', c.cfop_transferencia, 0.000000,	'CONSUMO', 0.180000, 0.000000,	1.000000,	cst,	1.000000,	0.000000,	'',	'NORMAL',	0,	26,	26,	c.ncm_id, 4,	
		false FROM ncms_validados_consulcamp c where c.ncm_id is not null and c.cst = 0);
--  ============================================================================================================================================================


	
-- 	============================================================================================================================================================
--  ======================= cadastrando tributacoes estaduais para venda/transferencia (produtos importados e nacionais) - TRIBUTADOS  =========================
--  ============================================================================================================================================================
	-- VENDA - cst 60 - importado
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
	(select '199900', c.cfop, 0.000000,	'CONSUMO', 0.000000, 0.000000,	1.000000,	cst,	1.000000,	0.000000,	'',	'NORMAL',	0,	26,	26,	c.ncm_id, 1,	
		true FROM ncms_validados_consulcamp c where c.ncm_id is not null and c.cst = 60);
	
	-- VENDA - cst 60 - nacional
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
	(select '199900', c.cfop, 0.000000,	'CONSUMO', 0.000000, 0.000000,	1.000000,	cst,	1.000000,	0.000000,	'',	'NORMAL',	0,	26,	26,	c.ncm_id, 1,	
		false FROM ncms_validados_consulcamp c where c.ncm_id is not null and c.cst = 60);

	-- TRANSFERENCIA - cst 60 - importado
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
	(select '199900', c.cfop_transferencia, 0.000000,	'CONSUMO', 0.000000, 0.000000,	1.000000,	cst,	1.000000,	0.000000,	'',	'NORMAL',	0,	26,	26,	c.ncm_id, 4,	
		true FROM ncms_validados_consulcamp c where c.ncm_id is not null and c.cst = 60);

	-- TRANSFERENCIA - cst 60 - nacional
	INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, mens, regime_tributario, 
		cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
	(select '199900', c.cfop_transferencia, 0.000000,	'CONSUMO', 0.000000, 0.000000,	1.000000,	cst,	1.000000,	0.000000,	'',	'NORMAL',	0,	26,	26,	c.ncm_id, 4,	
		false FROM ncms_validados_consulcamp c where c.ncm_id is not null and c.cst = 60);
--  ============================================================================================================================================================

-- ================================================================ OBS IMPORTANTE =====================================================================
-- =========================== Aqui está sendo DELETADO, as tributações cadastradas anteriormente para os PRODUTOS IMPORTADOS ==========================
-- == MOTIVO -> Caso seja rodar pelo FLYWAY, elas serão cadastradas no SCRIPT -> V00010__insertTribEstaSaidaDentroEstadoProdutoImportado.sql ===========
-- == SE FOR EXECUTADA MANUALMENTE NO DB basta não deletar elas
DELETE FROM trib_esta  where oper_id IN(1,4) and ncm_Id IN (select ncm_id from ncms_validados_consulcamp where ncm_id is not null) AND is_prod_impor;


-- consultando as tributacoes cadastradas
SELECT * FROM trib_esta  where oper_id IN(1,4) and ncm_Id IN (select ncm_id from ncms_validados_consulcamp where ncm_id is not null) ;

SELECT * FROM trib_esta  where oper_id IN(1,4) and ncm_Id IN (select ncm_id from ncms_validados_consulcamp where ncm_id is not null) 
and icms_cst = 0;


SELECT * FROM trib_esta  where oper_id IN(1,4) and ncm_Id IN (select ncm_id from ncms_validados_consulcamp where ncm_id is not null) 
and icms_cst = 60;


DROP TABLE IF EXISTS ncms_validados_consulcamp;