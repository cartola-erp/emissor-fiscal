
-- ========== OBSERVAÇÕES IMPORTANTES ========== 
	-- Que Rodar a CorrigeCestTributacaoEstadual, que está no emissor-fiscal
	-- Tenho que fazer um update para atualizar, o COD ANP
	-- Tenho que cadastrar as tributacoes paras os NCMS que tem mais de uma EXCECAO
	-- Tambem terei que atualizar o PIS/COFINS, (pois quando cadastrei usavamos menos ncms)
		-- E na operação de movimenta ja registra ECF, zerar os impostos
	
-- Deixando o campo "mens", vazio, pois será usado como info complementar;
select * from trib_esta;
update trib_esta set mens = "";


-- 67 | TRANSFERENCIA DE MATERIAL DE USO OU CONSUMO
INSERT INTO `trib_esta` (ncm_id, cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
	mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, oper_id ) 
	(SELECT ncm_id, 0, 5557, 0.000000, 'CONSUMO', 0.000000, 0.000000, 0.000000, 41, 0.000000, 0.000000, 
			'Nota Fiscal emitida conforme o artigo 7 inciso XV do Regulamento do ICMS de SP', "NORMAL" , 0,  26, 26, 67
	FROM trib_esta WHERE oper_id = 1);	

-- 