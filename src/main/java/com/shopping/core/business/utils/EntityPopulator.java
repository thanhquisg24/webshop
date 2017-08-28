/**
 * 
 */
package com.shopping.core.business.utils;

import com.shopping.core.business.exception.ConversionException;



/**
 * @author Umesh A
 *
 */
public interface EntityPopulator<Source,Target>
{

    public Target populateToEntity(Source source, Target target)  throws ConversionException;
    public Target populateToEntity(Source source) throws ConversionException;
}
