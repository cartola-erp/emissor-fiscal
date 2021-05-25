/**
 * Author:  robson.costa
 * Created: 23/06/2020
 */

-- INSERINDO as Operacões que tem no DB AutoGeral, no banco "emissorfiscal";


-- Será preciso mudar o codigo da operacao venda, na procedure de cadastros de tributacoes

ALTER TABLE emissorfiscal.oper MODIFY dscr VARCHAR(250) NOT NULL;


INSERT INTO emissorfiscal.oper (oper_id, dscr) 
(SELECT codigo, descricao FROM autogeral.movimentos_operacoes GROUP BY descricao ORDER BY CODIGO );

