package easycrypto;

import easycrypto.EasyCryptoAPI.Result;
import easycrypto.EasyCryptoAPI.ResultCode;

class MatrixMethod extends IterableMethod implements CryptoMethod {
	private static final int STEPS_NUMBER = 1;
	private static final String ENCRYPT_STEP_ONE_INTRO = "Result: ";
	private static final String DECRYPT_STEP_ONE_INTRO = "Result: ";

	public MatrixMethod() {
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
		String toStoreTo = new String();
		int matrixWidth = (int) Math.floor(Math.sqrt(toEncrypt.length()));

		String toRotate;
		String tmp = "";
		int extraCount = (int) Math.abs(Math.pow(matrixWidth, 2) - toEncrypt.length());
		if (extraCount > 0) {
			tmp = toEncrypt.substring(0,extraCount);
			tmp = new StringBuilder(tmp).reverse().toString();
			toRotate = toEncrypt.substring(extraCount);
		} else {
			toRotate = toEncrypt;
		}
		for (int outer = 0; outer < matrixWidth; outer++) {
			addToEncryptResponse(1, toRotate.substring(outer,outer+1));
			for (int inner = outer+matrixWidth; inner < toRotate.length(); inner += matrixWidth) {
				addToEncryptResponse(1, toRotate.substring(inner,inner+1));
			}
		}
		if (tmp.length() > 0) {
			addToEncryptResponse(1, tmp);
		}
		return new Result(ResultCode.ESuccess, getEncryptResponse(step));
	}
	
	@Override
	public Result decrypt(final String toDecrypt, final int step) {
		String toStoreTo = new String();
		int matrixWidth = (int)Math.floor(Math.sqrt(toDecrypt.length()));

		String toRotate;
		String tmp = "";
		int extraCount = (int)Math.abs(Math.pow(matrixWidth, 2) - toDecrypt.length());
		if (extraCount > 0) {
			tmp = toDecrypt.substring(toDecrypt.length()-extraCount,(toDecrypt.length()-extraCount)+extraCount);
			tmp = new StringBuilder(tmp).reverse().toString();
			toRotate = toDecrypt.substring(0, toDecrypt.length()-extraCount);
		} else {
			toRotate = toDecrypt;
		}
		for (int outer = 0; outer < matrixWidth; outer++) {
			addToDecryptResponse(1, toRotate.substring(outer,outer+1));
			for (int inner = outer+matrixWidth; inner < toRotate.length(); inner += matrixWidth) {
				addToDecryptResponse(1, toRotate.substring(inner,inner+1));
			}
		}
		if (tmp.length() > 0) {
			addToDecryptResponse(1, toStoreTo);
		}
		return new Result(ResultCode.ESuccess, getDecryptResponse(step));
	}

	@Override
	public String method() {
		return "matrix (max-step=" + STEPS_NUMBER +")";
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
