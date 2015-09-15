package com.nms.catalog.utils;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericUtil {
	private static final Logger logger = LoggerFactory.getLogger(GenericUtil.class);
	
	public static MediaType getMediaType(HttpHeaders httpHeaders) {
		MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
		if (httpHeaders == null) {
			return mediaType;
		} else {
			List<MediaType> acceptableMediaTypes = httpHeaders
					.getAcceptableMediaTypes();
			List<MediaType> mTypes = new ArrayList<MediaType>();
			if (acceptableMediaTypes == null || acceptableMediaTypes.isEmpty()) {
				return mediaType;
			} else {
				// comes here if there are more than one acceptable media type
				for (MediaType mType : acceptableMediaTypes) {
					// If one of them is a JSON tpy return it.
					if (mType == MediaType.APPLICATION_JSON_TYPE) {
						if(logger.isDebugEnabled()){
							logger.debug("Found one of the JSON Type in the acceptable media types so considering it.");
						}
						
						return mType;
					} else {
						mTypes.add(MediaType.valueOf(mType.toString()
								.split(";")[0]));
					}
				}

				// comes here if both there are no JSON types..
				if (mTypes.contains(MediaType.APPLICATION_XML_TYPE)) {
					if(logger.isDebugEnabled()){
						logger.debug("Found one of the XML Type in the acceptable media types so considering it.");
					}
					
					return MediaType.APPLICATION_XML_TYPE;
				}
			}
		}

		if(logger.isDebugEnabled()){
			logger.debug("None of the JSON or XML types found in the acceptable media types."
					+ " so returning the default JSON_TYPE.");
		}
		return mediaType;
	}	

}
