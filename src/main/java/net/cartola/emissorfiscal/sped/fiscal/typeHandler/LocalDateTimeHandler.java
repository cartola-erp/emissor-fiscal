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
import coffeepot.bean.wr.typeHandler.HandlerParseException;
import coffeepot.bean.wr.typeHandler.TypeHandler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Jeandeson O. Merelis
 */
public class LocalDateTimeHandler implements TypeHandler<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "ddMMyyyyHHmmss" );

    @Override
    public LocalDateTime parse( String text ) throws HandlerParseException {
        if (text == null || ( text = text.trim() ).isEmpty()) {
            return null;
        }

        try {
            return LocalDateTime.parse( text, formatter );
        } catch (Exception ex) {
            throw new HandlerParseException( ex );
        }
    }

    @Override
    public String toString( LocalDateTime obj ) {
        if (obj == null) {
            return null;
        }

        return formatter.format( obj );
    }

    @Override
    public void setConfig( String[] params ) {
    }
}
