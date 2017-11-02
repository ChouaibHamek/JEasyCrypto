package easycrypto;

import easycrypto.EasyCryptoAPI.Result;
import easycrypto.EasyCryptoAPI.ResultCode;

class Rot13Method extends IterableMethod implements CryptoMethod {
	
	private static final int STEPS_NUMBER = 1;
	private static final String ENCRYPT_STEP_ONE_INTRO = "Result: ";
	private static final String DECRYPT_STEP_ONE_INTRO = "Result: ";

	public Rot13Method() {
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
	public Result encrypt(final String toEncrypt, final int step){
		String res="";
		char c='a';
		for (int i=0;i<toEncrypt.length();i++){
			c=toEncrypt.charAt(i);
			if (Character.isLowerCase(c)){
				c=((c<(int)'n') ? (c+=13) : (c-=13));
			} else{
				c=((c<(int)'N') ? (c+=13) : (c-=13));
			}
			addToEncryptResponse(1, "" + c);
		}
		return new Result(ResultCode.ESuccess,getEncryptResponse(step));
	}
	
	@Override
	public Result decrypt(final String toDecrypt, final int step){
		String res="";
		char c='a';
		for (int i=0;i<toDecrypt.length();i++){
			c=toDecrypt.charAt(i);
			if (Character.isLowerCase(c)){
				c=((c>=(int)'n') ? (c-=13) : (c+=13));
			} else{
				c=((c>=(int)'N') ? (c-=13) : (c+=13));
			}
			addToDecryptResponse(1, "" + c);
		}
		return new Result(ResultCode.ESuccess,getDecryptResponse(step));
	}
	
	@Override
	public String method(){
		return "rot13 (max-step=" + STEPS_NUMBER +")";
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
