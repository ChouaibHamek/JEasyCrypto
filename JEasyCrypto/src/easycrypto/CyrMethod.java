package easycrypto;

import java.text.BreakIterator;
import java.util.Locale;

import easycrypto.EasyCryptoAPI.Result;
import easycrypto.EasyCryptoAPI.ResultCode;

class CyrMethod extends IterableMethod implements CryptoMethod {
	private static final int STEPS_NUMBER = 2;
	private static final String ENCRYPT_STEP_ONE_INTRO = "Step one: the latin offset (0x0020) is removed from the data. Result: ";
	private static final String ENCRYPT_STEP_TWO_INTRO = "Step two: the cyrilic offset (0x0400) is added to the data. Result: ";
	private static final String DECRYPT_STEP_ONE_INTRO = "Step one: the cyrilic offset (0x0400) is removed from the data. Result: ";
	private static final String DECRYPT_STEP_TWO_INTRO = "Step two: the latin offset (0x0020) is added to the data. Result: ";
	
	private static final int CLEAR_TEXT_UNICODE_START_VALUE = 0x0020; // Basic latin, Range: 0020— 007F
	private static final int CRYPTED_TEXT_UNICODE_START_VALUE = 0x0400; // Cyrillic, Range: 0400— 04FF

	public CyrMethod() {
		super(STEPS_NUMBER);
		initializeResponses();
	}

	@Override
	protected void initializeResponses() {
		addToEncryptResponse(1, ENCRYPT_STEP_ONE_INTRO);
		addToEncryptResponse(2, ENCRYPT_STEP_TWO_INTRO);

		addToDecryptResponse(1, DECRYPT_STEP_ONE_INTRO);
		addToDecryptResponse(2, DECRYPT_STEP_TWO_INTRO);
	}

	@Override
	public boolean requiresKey() {
		return false;
	}
	
	@Override
	public Result encrypt(String toEncrypt, int step) {
		String result = new String();

        BreakIterator breakIterator = BreakIterator.getCharacterInstance(Locale.ENGLISH);
        breakIterator.setText(toEncrypt);

        String tmp;
        int start = breakIterator.first();
        for (int end = breakIterator.next(); end != BreakIterator.DONE; start = end, end = breakIterator.next()) {
        		tmp = toEncrypt.substring(start, end);
				int valueOfChar = tmp.codePointAt(0); 
				int charPos = valueOfChar - CLEAR_TEXT_UNICODE_START_VALUE;
				addToEncryptResponse(1, Integer.toString(charPos) + " ");
        		int newValue = CRYPTED_TEXT_UNICODE_START_VALUE + charPos;
        		addToEncryptResponse(2, String.copyValueOf(Character.toChars(newValue))); 
        }
		return new Result(ResultCode.ESuccess, getEncryptResponse(step));
	}

	@Override
	public Result decrypt(String toDecrypt, int step) {
		String result = new String();

        BreakIterator breakIterator = BreakIterator.getCharacterInstance(Locale.ENGLISH);
        breakIterator.setText(toDecrypt);

        String tmp;
        int start = breakIterator.first();
        for (int end = breakIterator.next(); end != BreakIterator.DONE; start = end, end = breakIterator.next()) {
        		tmp = toDecrypt.substring(start, end);
				int valueOfChar = tmp.codePointAt(0); 
				int charPos = valueOfChar - CRYPTED_TEXT_UNICODE_START_VALUE;
				addToDecryptResponse(1, Integer.toString(charPos) + " ");
				int newValue = CLEAR_TEXT_UNICODE_START_VALUE + charPos;
				addToDecryptResponse(2, String.copyValueOf(Character.toChars(newValue)));
        }
		return new Result(ResultCode.ESuccess, getDecryptResponse(step));
	}

	@Override
	public String method() {
		return "cyr (max-step=" + STEPS_NUMBER +")";
	}
	
	//empty methods for interface
	
	@Override
	public Result encrypt(final String toEncrypt, final String key) {
		return new Result(ResultCode.EError, "Error: Wrong method accessed!");
	}
			
	@Override
	public Result decrypt(final String toDecrypt, final String key) {
		return new Result(ResultCode.EError, "Error: Wrong method accessed!");
	}

}
