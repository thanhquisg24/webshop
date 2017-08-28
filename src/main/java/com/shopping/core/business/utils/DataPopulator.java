/**
 * 
 */
package com.shopping.core.business.utils;

import com.shopping.core.business.exception.ConversionException;



/**
 * @author Umesh A
 *
 */
public interface DataPopulator<Source,Target>
{


    public Target populate(Source source,Target target) throws ConversionException;
    public Target populate(Source source) throws ConversionException;

   
}
