/**
 * Contains a header used by Pro and Client Reporter.
 */
abstract class EntityReporter extends Reporter {
    /**
     * @param e an entity e
     * @return the header for the report of the entity
     */
    protected static String getReportHeader(Entity e) {
        return "NAME: "+e.getName()+"\n" +
                "NUMBER: "+e.getUuid()+"\n" +
                "ADRESS: "+e.getAddress()+"\n" +
                "CITY: "+e.getCity()+"\n" +
                "PROVINCE: "+e.getProvince() + "\n" +
                "POSTAL CODE: "+ e.getPostalCode() + "\n";
    }
}
