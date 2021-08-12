public class Patients  {

    private Long id;
    private String nom;
    private String prenom;
    private String patrynome;
    private int age;
    private date datebirthday;	

}

public class PatientsMapper {

    private final Connection connection;

    public PatientsMapper(Connection connection) {
        this.connection  = connection;
    }


    public Patients findById(int id) throws SQLException,RecordNotFoundException{
        PreparedStatement statement = connection.prepareStatement("SELECT ID, NOM, PRENOM,PATRYNOME,AGE,DATEBIRTHDAY FROM PATIENTS WHERE ID = ?");
        statement.setInt(1,id);
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Patients patients = new Patients();
                patients.setIdPatients(resultSet.getInt(1));
                patients.setNom(resultSet.getString(2));
                patients.setPrenom(resultSet.getString(3));
		patients.setPatrynome(resultSet.getString(4));
		patients.setAge (resultSet.getString(5));
		patients.setDatebirthday(resultSet.getString(6));
                return patients;
            }
        }

        throw new RecordNotFoundException(id);
    }

    public void insert(Patients patients) {

    }


    class IdentityMap { 
    
        private Map<Long, Patients> patientsMap = new HashMap();

        public static void addPatients(Patients patients) {
            instance.patientsMap.put(patients.getId(), patients);
        }

        public static Patients getPatients(Long id) {
            return instance.patientsMap.get(id);
        }
    }

}