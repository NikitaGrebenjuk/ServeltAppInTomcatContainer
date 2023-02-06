package mongoDAO;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class MongoDBDAO {

    private static MongoClient mongoClient = null;
<<<<<<< HEAD
    private static final String url = "mongodb+srv://admin:Admin123!@nikesmongocluster.8zv5pfi.mongodb.net/?retryWrites=true&w=majority";
    private MongoDatabase getMongoDB(){
=======
    private static String url = "";
    private MongoClient getMongoClient(){
>>>>>>> 2c09684989ac57213dfb29d0450d092ac4863bf5
        if (mongoClient == null){
            try {
                mongoClient = MongoClients.create(url);
            }
            catch (MongoException me){
                System.out.println("No MongoClient created: " + me);
            }
        }
        return mongoClient.getDatabase("devDB");
    }

    public List<Document> getAllParticipants(){
        List<Document> listOfDocuments = new ArrayList<Document>();
        MongoCollection<Document> mongoCol = getMongoDB().getCollection("participants");
        for (Document doc : mongoCol.find()) {
            try {
                listOfDocuments.add(doc);
                System.out.println("doc " + doc.toJson().toString());

            }
            catch (NullPointerException npE){
                System.out.println("Error appeared: " + npE);
            }
        }
        return listOfDocuments;
    }
    public Document getParticipantByID(int _id){
        Document participant = null;
        participant = getMongoDB().getCollection("participants").find(Filters.eq("_id",_id)).first();
        return participant;
    }

    public Document updateParticipant(int pid, String name, int batchID){
        Document participant = null;
        BasicDBObject updateQuerry = new BasicDBObject();
        updateQuerry.put("name",name);
        updateQuerry.put("batchID",batchID);
        BasicDBObject setCommand = new BasicDBObject();
        setCommand.put("$set",updateQuerry);
        UpdateResult updateResult =  getMongoDB().getCollection("participants").updateOne(
                Filters.eq("_id", pid), setCommand);
        System.out.println(updateResult);
        participant =  getMongoDB().getCollection("participants").find(Filters.eq("name",name)).first();
        return participant;
    }

    public Document addParticipant(Document participant){
        getMongoDB().getCollection("participants").insertOne(participant);
        return participant;
    }

}
