public class UUID {
    private String uuid;

    public UUID(int value, int maxUuidLength) throws Exception {
        this.verifyUuid(value, maxUuidLength);
        this.uuid = 
    }

    private void verifyUuid(int uuid, int maxUuidLength) throws Exception {
        if(Integer.toString(uuid).length() > maxUuidLength) {
            System.out.println("UUID overflows: plus que "+maxUuidLength+" caractères.\nLe programme va maintenant se fermer.");
            throw new Exception("UUID OVERFLOW");   //sera attrape par le gros try catch de meta_callByName
        }
    }


}
