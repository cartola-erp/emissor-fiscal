-- Este script altera a tributação por conta da reforma tributária do consumo
-- Surgem nesta reforma os tributos CBS, IBS (com as suas modalidades estadual e municipal) e IS (Imposto Seletivo) que também é conhecido como imposto do pecado

-- <IBSCBS>
-- <CST>000</CST>
-- <cClassTrib>000001</cClassTrib>
-- <gIBSCBS>
-- <vBC>100.00</vBC>
-- <gIBSUF>
-- <pIBSUF>0.1000</pIBSUF>
-- <vIBSUF>0.10</vIBSUF>
-- </gIBSUF>
-- <gIBSMun>
-- <pIBSMun>0.0000</pIBSMun>
-- <vIBSMun>0.00</vIBSMun>
-- </gIBSMun>
-- <vIBS>0.10</vIBS>
-- <gCBS>
-- <pCBS>0.9000</pCBS>
-- <vCBS>0.90</vCBS>
-- </gCBS>
-- </gIBSCBS>
-- </IBSCBS>

ALTER TABLE `emissorfiscal`.`trib_fede`  ADD COLUMN `cbs_aliq` DECIMAL(7,6) NOT NULL DEFAULT '0.000000' COMMENT 'A tributação federal apenas define a alíquota da CBS, a base está definida na tributação estadual' AFTER `finalidade`;


ALTER TABLE `emissorfiscal`.`trib_esta`
    ADD COLUMN `ibs_cbs_cst` INT NOT NULL DEFAULT 0 COMMENT 'Código informado no campo CST do campo IBSCBS da NFe' AFTER `is_prod_impor`,
ADD COLUMN `ibs_cbs_clas_trib` INT NOT NULL DEFAULT 0 COMMENT 'Código informado no campo cClassTrib do XML da NFe' AFTER `ibs_cbs_cst`,
ADD COLUMN `ibs_cbs_base` DECIMAL(7,6) NOT NULL DEFAULT '0.000000' COMMENT 'Base de cálculo da CBS e dos IBS estaduais e municipais' AFTER `ibs_cbs_clas_trib`,
ADD COLUMN `ibs_esta_aliq` DECIMAL(7,6) NOT NULL DEFAULT '0.000000' COMMENT 'Alíquota do IBS relativa ao estado (Unidade Federativa)' AFTER `ibs_cbs_base`,
ADD COLUMN `ibs_muni_aliq` DECIMAL(7,6) NOT NULL DEFAULT '0.000000' COMMENT 'Alíquota do IBS relativa ao município' AFTER `ibs_esta_aliq`;
