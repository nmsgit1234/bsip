package com.nms.util.db;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;

public class RestrictionConstants
{

	public static String EQUAL = "equal";

	public static String ORDER_BY_ASCENDING = "OrderByAsc";

	public static String ORDER_BY_DESCENDING = "OrderByDesc";

	public static String GROUP_BY = "GroupBy";
	
	
        public static Criterion getCriterion(String key,String value)
        {
                Criterion crit = null;

                if (key != null && key.equalsIgnoreCase(EQUAL))
                {
                  crit = Restrictions.eq(key,value);
                }
                return crit;
        }


}
