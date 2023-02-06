package mongoDAO;

import org.bson.Document;

import java.util.List;

public class ParticipantMongoService {
    private final MongoDBDAO participantDAO;

    public ParticipantMongoService() {
        this.participantDAO = new MongoDBDAO();
    }

    public List<Document> getAllParticipants() {
        return participantDAO.getAllParticipants();
    }
    public Document getParticipantByID(int pid){
        return participantDAO.getParticipantByID(pid);
    }

    public Document updateParticipant(int pid, String name, int batchID){
        return  participantDAO.updateParticipant(pid,name, batchID);}
    
    public Document addParticipant(Document participant){
        return  this.participantDAO.addParticipant(participant);
    }

    // Other business logic methods
}