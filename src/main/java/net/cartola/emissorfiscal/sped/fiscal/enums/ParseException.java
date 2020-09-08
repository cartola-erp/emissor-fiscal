/*
 * Copyright 2013 - Jeandeson O. Merelis
 */
package net.cartola.emissorfiscal.sped.fiscal.enums;

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


/**
 * 08/09/2020
 * @author Jeandeson O. Merelis
 */
public class ParseException extends RuntimeException {

    private static final long serialVersionUID = 5457819261404871322L;

    /**
     * Creates a new instance of <code>ParseException</code> without detail
     * message.
     */
    public ParseException() {
    }

    /**
     * Constructs an instance of <code>ParseException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ParseException( String msg ) {
        super( msg );
    }
}
