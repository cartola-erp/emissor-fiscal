CREATE TABLE `trib_mens` (
`trib_mens_id` int unsigned NOT NULL AUTO_INCREMENT,
`mens` varchar(255) DEFAULT NULL,
`cria_em` datetime DEFAULT NULL,
`cria_por` char(32) DEFAULT NULL,
`alte_em` datetime DEFAULT NULL,
`alte_por` char(32) DEFAULT NULL,
PRIMARY KEY (`trib_mens_id`)
);

CREATE TABLE `trib_esta_mens` (
`trib_esta_mens_id` int unsigned NOT NULL AUTO_INCREMENT,
`trib_esta_id` bigint NOT NULL,
`trib_mens_id` int unsigned NOT NULL,
`cria_em` datetime NOT NULL,
`cria_por` char(32) DEFAULT NULL,
PRIMARY KEY (`trib_esta_mens_id`),
KEY `fk_trib_esta_mens_trib_esta_idx` (`trib_esta_id`),
KEY `fk_trib_esta_mens_mens_idx` (`trib_mens_id`),
CONSTRAINT `fk_trib_esta_mens_mens` FOREIGN KEY (`trib_mens_id`) REFERENCES `trib_mens` (`trib_mens_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
CONSTRAINT `fk_trib_esta_mens_trib_esta` FOREIGN KEY (`trib_esta_id`) REFERENCES `trib_esta` (`trib_esta_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) COMMENT = 'Tabela de vínculo, apenas vinculando o tributação estadual as mensagens que a tributação utiliza';

CREATE TABLE `trib_fede_mens` (
`trib_fede_mens_id` int unsigned NOT NULL AUTO_INCREMENT,
`trib_fede_id` bigint NOT NULL,
`trib_mens_id` int unsigned NOT NULL,
`cria_em` datetime NOT NULL,
`cria_por` char(32) DEFAULT NULL,
PRIMARY KEY (`trib_fede_mens_id`),
KEY `fk_trib_fede_mens_trib_fede_idx` (`trib_fede_id`),
KEY `fk_trib_fede_mens_mens_idx` (`trib_mens_id`),
CONSTRAINT `fk_trib_fede_mens_mens` FOREIGN KEY (`trib_mens_id`) REFERENCES `trib_mens` (`trib_mens_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
CONSTRAINT `fk_trib_fede_mens_trib_fede` FOREIGN KEY (`trib_fede_id`) REFERENCES `trib_fede` (`trib_fede_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) COMMENT = 'Tabela de vínculo, apenas vinculando o tributação federal as mensagens que a tributação utiliza';

