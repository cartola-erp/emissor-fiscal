

-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x AC ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 1, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,1, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x AL ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 2, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,2, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x AM ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 3, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,3, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x AP ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 4, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,4, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x BA ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 5, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,5, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x CE ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 6, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,6, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x DF ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 7, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,7, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x ES ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 8, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,8, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x GO ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 9, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,9, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x MA ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 10, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,10, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x MG ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 11, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,11, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x MS ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 12, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,12, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x MT ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 13, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,13, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x PA ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 14, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,14, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x PB ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 15, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,15, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x PE ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 16, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,16, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x PI ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 17, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,17, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x PR ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 18, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,18, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x RJ ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 19, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,19, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x RN ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 20, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,20, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x RO ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 21, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,21, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x RR ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 22, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,22, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x RS ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 23, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,23, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x SC ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 24, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,24, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x SE ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 25, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,25, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------


-- PARA PRODUTOS IMPORTADOS == false
-- ==================================================== 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA  SP x TO ============================================================== 

INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor ) 
(select cest, 5922, fcp_aliq, finalidade, 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', regime_tributario, cod_anp, 27, esta_orig_id, ncm_id, 45,false FROM trib_esta where oper_id = 1 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false);

-- 45 - VENDA DE PRODUTO PARA  ENTREGA FUTURA Cadastrando para os outros ncms (que PROVAVELMENTE nao SAO de AUTOPECAS) 
INSERT INTO `trib_esta` (cest, cfop, fcp_aliq, finalidade, icms_aliq, icms_aliq_dest, icms_base, icms_cst, icms_iva, icms_st_aliq, 
mens, regime_tributario, cod_anp, esta_dest_id, esta_orig_id, ncm_id, oper_id, is_prod_impor )
(select 199900, 5922, 0, "CONSUMO", 0.000000, 0.000000, 0.000000, 90, 0.000000, 0.000000, 
 '', "NORMAL", 0,27, 26, ncm_id, 45,false FROM ncms where ncm_id not in (select ncm_id from trib_esta where oper_id = 45 and esta_orig_id = 26 and esta_dest_id = 26 and is_prod_impor = false));
-- ---------------------------------------------- Para Quando o produto for importado dentro do estado eh a mesma tributacao -------------------------------
