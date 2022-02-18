
-- Esse é para cadastrar as tributações dos outros ncms que tem mais de uma excecao
-- Para as OPERAÇÕES (1,4, 46, 63, 73, 81, 83), que não foi cadastrados para todos o NCMS (mas o ICMS é igual para qualquer EXCECAO)


DROP TABLE IF EXISTS trib_esta_para_clonar;
	
 CREATE TABLE IF NOT EXISTS `trib_esta_para_clonar` (
  `trib_esta_clone_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trib_esta_id` bigint(20) NOT NULL ,
  `cest` int(11) DEFAULT NULL,
  `fcp_aliq` decimal(7,6) NOT NULL DEFAULT '0.000000',
  `finalidade` enum('COMERCIALIZACAO','BRINDE','DOACAO','PATRIMONIO','CONSUMO') NOT NULL DEFAULT 'CONSUMO',
  `icms_aliq` decimal(7,6) NOT NULL DEFAULT '0.000000',
  `icms_aliq_dest` decimal(7,6) NOT NULL DEFAULT '0.000000',
  `icms_base` decimal(7,6) NOT NULL DEFAULT '0.000000',
  `icms_cst` int(11) NOT NULL,
  `icms_iva` decimal(7,6) NOT NULL DEFAULT '0.000000',
  `icms_st_aliq` decimal(7,6) NOT NULL DEFAULT '0.000000',
  `mens` varchar(255) DEFAULT NULL,
  `regime_tributario` enum('SIMPLES','SIMPLES_EXCESSO','NORMAL') DEFAULT NULL,
  `esta_dest_id` bigint(20) NOT NULL,
  `esta_orig_id` bigint(20) NOT NULL,
  `ncm` int(11) NOT NULL COMMENT "Tirei a FK de NCM (ncm_id), e coloquei junto nessa tabela",
  `exce` int(11) NOT NULL COMMENT "Inclusive a exceção do ncm (que no caso sempre será zero, já que só tem dessas excecoes)",
  `oper_id` bigint(20) NOT NULL,
  `cfop` int(11) NOT NULL,
  `cod_anp` int(11) NOT NULL,
  `is_prod_impor` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (trib_esta_clone_id),
  UNIQUE KEY `unk_trib_esta_oper_ncm_fina_regi_trib_uf_prod_impor` (`oper_id`,`ncm`, `exce`,`finalidade`,`regime_tributario`,`esta_orig_id`,`esta_dest_id`,`is_prod_impor`),
  KEY `fnk_trib_esta_clone_dest_id` (`esta_dest_id`),
  KEY `fnk_trib_esta_clone_orig_id` (`esta_orig_id`),
  CONSTRAINT `fnk_trib_esta_clone_dest_id` FOREIGN KEY (`esta_dest_id`) REFERENCES `esta` (`esta_id`),
  CONSTRAINT `fnk_trib_esta_clone_oper_id` FOREIGN KEY (`oper_id`) REFERENCES `oper` (`oper_id`),
  CONSTRAINT `fnk_trib_esta_clone_orig_id` FOREIGN KEY (`esta_orig_id`) REFERENCES `esta` (`esta_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
	   

-- Buscando as tributacoes dos ncms que tem mais de uma excecao nas OPERAÇÕES (1,4, 46, 63, 73, 81, 83) (porém abaixo só estão na exececao 0)
SElECT t.trib_esta_id, t.cest, t.fcp_aliq, t.finalidade, t.icms_aliq, t.icms_aliq_dest, t.icms_base, t.icms_cst, t.icms_iva,
		t.icms_st_aliq, t.mens, t.regime_tributario, t.esta_dest_id, t.esta_orig_id, n.nume, n.exce, t.oper_id, t.cfop, t.cod_anp, t.is_prod_impor 
FROM trib_esta t INNER JOIN ncms n ON (n.ncm_id = t.ncm_id) 
WHERE t.ncm_id IN (							-- Todas as tributacoes (venda) dos ID's abaixo
			(SELECT ncm_id FROM ncms WHERE nume IN 							-- Todos os IDs dos ncms abaixo
					(SELECT nume FROM ncms WHERE exce >0) AND exce = 0)) 					-- todos NCMS que tem mais de uma EXCECAO
AND oper_id IN (1,4, 46, 63, 73, 81, 83) ;
    
-- oper_id IN (1,4, 46, 63, 73, 81, 83) 	- (São as operações que não foi cadastrado o ICMS para todos os NCMS)

	
-- Inserindo a tributação dos ncms que tem mais de uma execao, na tabela auxiliar "trib_esta_para_clonar"
INSERT INTO trib_esta_para_clonar (trib_esta_id, cest, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva,
									icms_st_aliq, mens, regime_tributario, esta_dest_id, esta_orig_id, ncm, exce, oper_id, cfop, cod_anp, is_prod_impor  ) 
SElECT t.trib_esta_id, t.cest, t.fcp_aliq, t.finalidade, t.icms_aliq, t.icms_aliq_dest, t.icms_base, t.icms_cst, t.icms_iva,
			t.icms_st_aliq, t.mens, t.regime_tributario, t.esta_dest_id, t.esta_orig_id, n.nume, n.exce, t.oper_id, t.cfop, t.cod_anp, t.is_prod_impor  
FROM trib_esta t INNER JOIN ncms n ON (n.ncm_id = t.ncm_id) 
WHERE t.ncm_id IN (	(SELECT ncm_id FROM ncms WHERE nume IN 	(SELECT nume FROM ncms WHERE exce >0)	
														AND exce = 0)) 					
AND oper_id IN (1,4, 46, 63, 73, 81, 83) ;
            
			
SET @max_id_antes_clonagem  =  (SELECT MAX(trib_esta_Id) FROM trib_esta);

-- Select com as tributações, para os NCMS que tem mais de uma excecao
SELECT  tc.cest, tc.fcp_aliq, tc.finalidade, tc.icms_aliq, tc.icms_aliq_dest, tc.icms_base, tc.icms_cst, tc.icms_iva,
		tc.icms_st_aliq, tc.mens, tc.regime_tributario, tc.esta_dest_id, tc.esta_orig_id, n.ncm_id, tc.oper_id, tc.cfop, tc.cod_anp, tc.is_prod_impor 
FROM ncms n INNER JOIN trib_esta_para_clonar tc ON (tc.ncm = n.nume) AND n.exce != 0;


-- INSERINDO em trib_esta, as tributações para os NCMs que tem mais de uma excecao
INSERT INTO trib_esta (cest, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva,
				icms_st_aliq, mens, regime_tributario, esta_dest_id, esta_orig_id, ncm_id, oper_id, cfop, cod_anp, is_prod_impor  ) 
SELECT  tc.cest, tc.fcp_aliq, tc.finalidade, tc.icms_aliq, tc.icms_aliq_dest, tc.icms_base, tc.icms_cst, tc.icms_iva,
			tc.icms_st_aliq, tc.mens, tc.regime_tributario, tc.esta_dest_id, tc.esta_orig_id, n.ncm_id, tc.oper_id, tc.cfop, tc.cod_anp, tc.is_prod_impor 
FROM ncms n INNER JOIN trib_esta_para_clonar tc ON (tc.ncm = n.nume) AND n.exce != 0;	

-- tributacoes que tem ncm com mais de uma execao
SELECT t.ncm_id FROM trib_esta t INNER JOIN ncms n ON (n.ncm_id = t.ncm_id) WHERE n.exce > 0;

-- Buscando as tributações que foram inseridas
SELECT * FROM trib_esta WHERE trib_esta_id > @max_id_antes_clonagem;
	



DROP TABLE IF EXISTS trib_esta_para_clonar;


