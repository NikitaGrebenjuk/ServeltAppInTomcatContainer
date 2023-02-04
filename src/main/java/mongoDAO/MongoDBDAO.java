package mongoDAO;

import DAO.Participant;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public class MongoDBDAO {

    private static MongoClient mongoClient = null;
    private static String url = "mongodb+srv://admin:Admin123!@nikesmongocluster.8zv5pfi.mongodb.net/?retryWrites=true&w=majority";
    private MongoClient getMongoClient(){
        if (mongoClient == null){
            try {
                mongoClient = new MongoClient(url);
            }
            catch (MongoException me){
                System.out.println("No MongoClient created: " + me);
            }
        }
        return mongoClient;
    }

    public List<Document> getAllParticipants(){
        List<Document> listOfDocuments = null;
        MongoClient mClient = getMongoClient();
        MongoDatabase mongoDB= mClient.getDatabase("usersDB");
        MongoCollection<Document> mongoCol = mongoDB.getCollection("users");
        Document student = new Document("_id", new ObjectId());
        student.append("name", "Klaus")
                .append("BatchName", "yoga");
        mongoCol.insertOne(student);
        for (Document doc : mongoCol.find()) {
            listOfDocuments.add(doc);
        }
        return listOfDocuments;
    }
    public Document getParticipantByID(int _id){
        mongoClient.getDatabase("usersDB").getCollection("usrs").find(equals("name":))
        return
    }

    public void updateParticipant(int pid, String name, int batchID){ participantDAO.updateParticipant(new Participant(pid,name,batchID));}

    public void addParticipant(Participant participant){
        this.participantDAO.addParticipant(participant);
    }

}
