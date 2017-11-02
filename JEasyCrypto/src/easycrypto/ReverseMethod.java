package easycrypto;

import easycrypto.EasyCryptoAPI.Result;
import easycrypto.EasyCryptoAPI.ResultCode;

class ReverseMethod extends IterableMethod implements CryptoMethod {

	private static final int STEPS_NUMBER = 1;
	private static final String ENCRYPT_STEP_ONE_INTRO = "Result: ";
	private static final String DECRYPT_STEP_ONE_INTRO = "Result: ";

	public ReverseMethod() {
		super(STEPS_NUMBER);
		initializeResponses();
	}

	@Override
	protected void initializeResponses() {
		addToEncryptResponse(1, ENCRYPT_STEP_ONE_INTRO);
		addToDecryptResponse(1, DECRYPT_STEP_ONE_INTRO);
	}

	@Override
	public boolean requiresKey() {
		return false;
	}
	@Override
	public Result encrypt(final String toEncrypt, final int step) {
		addToEncryptResponse(1, new StringBuilder(toEncrypt).reverse().toString());
		return new Result(ResultCode.ESuccess, getEncryptResponse(step));
	}
	
	
	@Override
	public Result decrypt(final String toDecrypt, final int step) {
		addToDecryptResponse(1, new StringBuilder(toDecrypt).reverse().toString());
		return new Result(ResultCode.ESuccess, getDecryptResponse(step));
	}

	@Override
	public String method() {
		return "reverse (max-step=" + STEPS_NUMBER +")";
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
