import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

public class StockTrading {

    public static void main(String[] args) throws Exception {
    	Timer timer = new Timer ();
    	TimerTask minuteTask = new TimerTask () {
    	    public void run() {
    	        manageUserItem();
    	    }
    	};

    	// schedule the task to run starting now and then every minute...
    	timer.schedule (minuteTask, 0l, 1000*30);
    }
    public static void manageUserItem(){
    	AmazonDynamoDBClient client = new AmazonDynamoDBClient()
                .withRegion(Regions.US_WEST_2);

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("User");
        
        ArrayList<User> usernames = users();
        
        for(int i = 0; i < usernames.size(); i++){
        	try {
            	System.out.println("Adding a new item...");
            	PutItemOutcome outcome = table.putItem(new Item()
                    .withPrimaryKey("username", usernames.get(i).getName(), "total asset", usernames.get(i).getTotalAsset())
            		.withMap("owned stocks", usernames.get(i).getOwnedStocks()));

                System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

            } catch (Exception e) {
                System.err.println("Unable to add item: " + usernames.get(i).getName() + " " + usernames.get(i).getTotalAsset());
                System.err.println(e.getMessage());
            }
            
            System.out.println(usernames.get(i).getName() + " " + usernames.get(i).getTotalAsset() + " " + usernames.get(i).getOwnedStocks());
        }
        
    }
    
    public static ArrayList<User> users(){
    	Map<String, Integer> ownedStocks1 = new HashMap<String, Integer>();
        ownedStocks1.put("FB", 5);
        ownedStocks1.put("MSFT", 7);
        User john = new User("john", 8000, ownedStocks1);
        
        Map<String, Integer> ownedStocks2 = new HashMap<String, Integer>();
        ownedStocks2.put("PG", 6);
        ownedStocks2.put("GOOGL", 2);
        User paul = new User("paul", 8000, ownedStocks2);
        
        ArrayList<User> user = new ArrayList<User>();
        user.add(john);
        user.add(paul);
        
        return user;
    }

}