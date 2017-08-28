package com.shopping.core.business.utils;

import java.math.BigDecimal;

import com.shopping.core.constants.MeasureUnit;


public class DataUtils {
	
	/**
	 * Removes dashes
	 * @param postalCode
	 * @return
	 */
	public static String trimPostalCode(String postalCode) {

		String pc = postalCode.replaceAll("[^a-zA-Z0-9]", "");

		return pc;

	}
	
	
	/**
	 * Get the measure according to the appropriate measure base. If the measure
	 * configured in store is LB and it needs KG then the appropriate
	 * calculation is done
	 * 
	 * @param weight
	 * @param store
	 * @param base
	 * @return
	 */
	public static double getWeight(double weight,String base) {

		double weightConstant = 2.2;
		if (base.equals(MeasureUnit.LB.name())) {
			// pound = kilogram
				double answer = weight * weightConstant;
				BigDecimal w = new BigDecimal(answer);
				return w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			
		} else {// need KG
			
				double answer = weight / weightConstant;
				BigDecimal w = new BigDecimal(answer);
				return w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			
		}
	}
	
	/**
	 * Get the measure according to the appropriate measure base. If the measure
	 * configured in store is IN and it needs CM or vise versa then the
	 * appropriate calculation is done
	 * 
	 * @param weight
	 * @param store
	 * @param base
	 * @return
	 */
	public static double getMeasure(double measure,String base) {

		if (base.equals(MeasureUnit.IN.name())) {
		// centimeter (inch to centimeter)
				double measureConstant = 2.54;

				double answer = measure * measureConstant;
				BigDecimal w = new BigDecimal(answer);
				return w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			
		} else {// need CM
		// in (centimeter to inch)
				double measureConstant = 0.39;

				double answer = measure * measureConstant;
				BigDecimal w = new BigDecimal(answer);
				return w.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			
		}

	}

}
