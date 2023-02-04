package mongoDAO;

import DAO.JDBCParticipantDAO;
import DAO.Participant;
import DAO.ParticipantDAO;
import org.bson.Document;

import java.util.List;

public class ParticipantMongoService {
    private MongoDBDAO participantDAO;

    public ParticipantMongoService() {
        this.participantDAO = new MongoDBDAO();
    }

    public List<Document> getAllParticipants() {
        return participantDAO.getAllParticipants();
    }
    public Participant getParticipantByID(int pid){
        return participantDAO.getParticipantByID(pid);
    }

    public void updateParticipant(int pid, String name, int batchID){ participantDAO.updateParticipant(new Participant(pid,name,batchID));}
    
    public void addParticipant(Participant participant){
    	this.participantDAO.addParticipant(participant);
    }

    // Other business logic methods
}