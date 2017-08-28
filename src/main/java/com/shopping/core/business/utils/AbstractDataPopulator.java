/**
 * 
 */
package com.shopping.core.business.utils;


import com.shopping.core.business.exception.ConversionException;




/**
 * @author Umesh A
 *
 */
public abstract class AbstractDataPopulator<Source,Target> implements DataPopulator<Source, Target>
{


	@Override
	public Target populate(Source source) throws ConversionException{
	   return populate(source,createTarget());
	}
	
	protected abstract Target createTarget();

   

}
