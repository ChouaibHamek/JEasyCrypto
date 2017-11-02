package easycrypto;

import java.util.ArrayList;

public abstract class IterableMethod {
    ArrayList<StringBuilder> encryptResultList;
    ArrayList<StringBuilder> decryptResultList;

    public IterableMethod(int size) {
        encryptResultList = new ArrayList<StringBuilder>();
        for(int i = 0; i < size; i++) {
            encryptResultList.add(new StringBuilder());
        }

        decryptResultList = new ArrayList<StringBuilder>();
        for(int i = 0; i < size; i++) {
            decryptResultList.add(new StringBuilder());
        }
    }

    protected void addToEncryptResponse(int index, String text) {
        encryptResultList.get(index - 1).append(text);
    }

    protected String getEncryptResponse(int index) {
        return encryptResultList.get(index - 1).toString();
    }

    protected void addToDecryptResponse(int index, String text) {
        decryptResultList.get(index - 1).append(text);
    }

    protected String getDecryptResponse(int index) {
        return decryptResultList.get(index - 1).toString();
    }


    protected abstract void initializeResponses();
}