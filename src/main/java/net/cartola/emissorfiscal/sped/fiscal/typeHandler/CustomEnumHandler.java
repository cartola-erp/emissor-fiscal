/*
 * Copyright 2013 - Jeandeson O. Merelis
 */
package net.cartola.emissorfiscal.sped.fiscal.typeHandler;

/*
 * #%L
 * coffeepot-br-sped-fiscal
 * %%
 * Copyright (C) 2013 Jeandeson O. Merelis
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
/*
 * #%L
 * coffeepot-br-sped-fiscal
 * %%
 * Copyright (C) 2013 Jeandeson O. Merelis
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import coffeepot.bean.wr.typeHandler.DefaultEnumHandler;
import coffeepot.bean.wr.typeHandler.HandlerParseException;
import net.cartola.emissorfiscal.sped.fiscal.enums.EnumCodificado;
import net.cartola.emissorfiscal.sped.fiscal.enums.EnumParser;

/**
 * 08/09/2020
 * 
 * @author Jeandeson O. Merelis
 */
public class CustomEnumHandler extends DefaultEnumHandler {
	

    @Override
    public Enum parse(String text) throws HandlerParseException {
        if (text == null || "".equals(text)) {
            return null;
        }

        if (EnumCodificado.class.isAssignableFrom( type )) {
            try{
               return EnumParser.parseEnumCodificado( type, text );
            }catch(Exception ex){
               throw new HandlerParseException( ex );
            }
        }

        return super.parse(text);
    }

    @Override
    public String toString(Enum obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof EnumCodificado) {
            return ((EnumCodificado) obj).getCodigo();
        }

        return super.toString(obj);
    }
    
}
