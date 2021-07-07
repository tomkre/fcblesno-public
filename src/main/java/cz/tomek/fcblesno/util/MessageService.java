package cz.tomek.fcblesno.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Message service.
 * 
 * @author tomek
 *
 */
@Service
public class MessageService {

	private static MessageSourceAccessor msa;
	
	@Autowired
	public void initMessageSourceAccessor(MessageSource messageSource) {
		msa = new MessageSourceAccessor(messageSource);
	}
	
	
	public static String getMessage(String key) {
		return msa.getMessage(key);
	}
	
}
