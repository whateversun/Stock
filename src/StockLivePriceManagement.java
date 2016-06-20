import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

public class StockLivePriceManagement {

    public static void main(String[] args) throws Exception {
    	Timer timer = new Timer ();
    	TimerTask minuteTask = new TimerTask () {
    	    public void run() {
    	    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        		Date date = new Date();
    	        manageStockItem(dateFormat.format(date));
    	    }
    	};

    	// schedule the task to run starting now and then every minute...
    	timer.schedule (minuteTask, 0l, 1000*30);
    }
    public static void manageStockItem(String time){
    	AmazonDynamoDBClient client = new AmazonDynamoDBClient()
                .withRegion(Regions.US_WEST_2);

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("Stock");
        Stock[] company = getCompanies();
        double[] stockPrice = getPrice(company);
        for(int i = 0; i < 9; i++){
        	try {
               System.out.println("Adding a new item...");
               PutItemOutcome outcome = table.putItem(new Item()
                   .withPrimaryKey("price", stockPrice[i], "company", company[i].getSymbol())
                   .withString("time", time));

               System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

        	} catch (Exception e) {
               System.err.println("Unable to add item: " + stockPrice[i] + " " + company[i].getSymbol());
               System.err.println(e.getMessage());
        	}
        }
    }
    
	public static Stock[] getCompanies(){
		Stock[] companies = new Stock[9];
		Companies[] companySymbol = Companies.values();
		for(int i = 0; i < 9; i++){
			companies[i] = StockFetcher.getStock(companySymbol[i].toString());
		}
		
		return companies;
	}
	
	public static double[] getPrice(Stock[] companyNames){
		double[] price = new double[9];
		for(int i = 0; i < 9; i++){
			price[i] = companyNames[i].getPrice();
		}
		
		return price;
	}
}