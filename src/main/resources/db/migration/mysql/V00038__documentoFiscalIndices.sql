ALTER TABLE `docu_fisc`
ADD INDEX `idx_docu_fisc_nume` (`docu` ASC) VISIBLE,
ADD INDEX `idx_docu_fisc_data_cria` (`criado_por` ASC) INVISIBLE,
ADD INDEX `idx_docu_fisc_emis` (`emissao` ASC) VISIBLE;

ALTER TABLE `docu_fisc` 
DROP INDEX `idx_docu_fisc_data_cria` ,
ADD INDEX `idx_docu_fisc_data_cria` (`cadastro` ASC) INVISIBLE;

