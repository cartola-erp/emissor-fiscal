DROP TABLE IF EXISTS ncms_validados_consulcamp;

CREATE TABLE `ncms_validados_consulcamp` (
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


LOAD DATA LOCAL INFILE 'NCMs - Validado pela Consulcamp.csv'
	INTO TABLE ncms_validados_consulcamp CHARACTER SET latin1 
FIELDS TERMINATED BY ';' 
OPTIONALLY ENCLOSED BY '\"'
LINES TERMINATED BY '\r\n' 
IGNORE 2 LINES 
(ncm, cfop, @cst_icms, @cfop_transf)
SET cst = @cst_icms,
cfop_transferencia = @cfop_transf;	


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


-- consultando as tributacoes cadastradas

SELECT * FROM trib_esta  where oper_id IN(1,4) and ncm_Id IN (select ncm_id from ncms_validados_consulcamp where ncm_id is not null) ;

SELECT * FROM trib_esta  where oper_id IN(1,4) and ncm_Id IN (select ncm_id from ncms_validados_consulcamp where ncm_id is not null) 
and icms_cst = 0;


SELECT * FROM trib_esta  where oper_id IN(1,4) and ncm_Id IN (select ncm_id from ncms_validados_consulcamp where ncm_id is not null) 
and icms_cst = 60;


DROP TABLE IF EXISTS ncms_validados_consulcamp;