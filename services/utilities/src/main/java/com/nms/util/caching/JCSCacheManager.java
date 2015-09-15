package com.nms.util.caching;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jcs.JCS;

import com.nms.util.log.CommonLogger;

public class JCSCacheManager
{

	private static Log log = LogFactory.getLog(JCSCacheManager.class);

	private static JCS bsiCache;
	private static JCSCacheManager instance;
    private static int NUM_ROWS_TO_CACHE=2000;


/*	static {


		try
		{
			String recCount = JSPUtil.getEnvSetting(JSPConsts.ENV_JCS_CACHE_REC_COUNT);
			if (recCount != null && recCount.length() > 0)
			{
				NUM_ROWS_TO_CACHE = Integer.parseInt(recCount);
			}
		}
		catch(Exception ex)
		{
			CommonLogger.logError(log,"Couldnot retrive the JCS record count from the environment",ex);
		}

		CommonLogger.logDebug(log,"The record count limitation for the JCS cache is " + NUM_ROWS_TO_CACHE);


	}

*/


	private JCSCacheManager(){

		CommonLogger.logDebug(log,"In JCSCacheManager:JCSCacheManager()");
		try{
			bsiCache = JCS.getInstance("bsiCache");

		}
		catch(Exception ex){

			CommonLogger.logError(log,"Error occured while getting an instance of JCS cache : ",ex);

		}
	}


	public static JCSCacheManager getInstance(){

		CommonLogger.logDebug(log,"In JCSCacheManager:getInstance()");

        if (instance == null)
        {
                instance = new JCSCacheManager();
        }
        return instance;

	}


	public static Object getFromBsiCache(String key){
		CommonLogger.logDebug(log,"In JCSCacheManager:getFromBsiCache() \n key=" + key);

		Object result = null;
		try
		{
			result = (Object)bsiCache.get(key);
		}
		catch(Exception ex)
		{
			CommonLogger.logError(log,"Error occured while retrieving the result from cache : ",ex);
		}
		return result;
	}

	public static void putInBsiCache(String key,Object obj){

		CommonLogger.logDebug(log,"In JCSCacheManager:putInBsiCache() \n key=" + key + ", value=" + obj.toString());

		try{
                  bsiCache.put(key, obj);
		}
		catch(Exception ex){
                  CommonLogger.logError(log,"Error occured while putting the result into cache : ",ex);
		}
	}


	public static void removeFromBsiCache(String key){

		CommonLogger.logDebug(log,"In JCSCacheManager:removeFromBsiCache() \n key=" + key);

		try{

			if (key != null)
			{
				bsiCache.remove(key);
			}

		}
		catch(Exception ex){
            CommonLogger.logError(log,"Error occured while putting the result into cache : ",ex);

		}

	}
}
