import java.util.Arrays;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class CreateUserTable {

    public static void main(String[] args) throws Exception {

    	AmazonDynamoDBClient client = new AmazonDynamoDBClient()
                .withRegion(Regions.US_WEST_2);

        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "User";

        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                Arrays.asList(
                    new KeySchemaElement("username", KeyType.HASH),  //Partition key
                    new KeySchemaElement("total asset", KeyType.RANGE)), //Sort key
                    Arrays.asList(
                        new AttributeDefinition("total asset", ScalarAttributeType.N),
                        new AttributeDefinition("username", ScalarAttributeType.S)), 
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }

    }
}
