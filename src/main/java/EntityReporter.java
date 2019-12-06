public class EntityReporter extends Reporter {
    protected static String getReportHeader(Entity e) {
        return "NAME: "+e.getName()+"\n" +
                "NUMBER: "+e.getUuid()+"\n" +
                "ADRESS: "+e.getAddress()+"\n" +
                "CITY: "+e.getCity()+"\n" +
                "PROVINCE: "+e.getProvince() + "\n" +
                "POSTAL CODE: "+ e.getPostalCode() + "\n";
    }
}
