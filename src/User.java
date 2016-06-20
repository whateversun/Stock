import java.util.Collections;
import java.util.Map;

public class User{
	private String name;
	private int totalAsset;
	private Map<String, Integer> ownedStocks; //String: company name  Integer: amount of stocks owned
	
	public User(String name, int totalAsset, Map<String, Integer> ownedStocks){
		this.name = name;
		this.totalAsset = totalAsset;
		this.ownedStocks = ownedStocks;
	}
	
	public User(String name, int totalAsset){
		this.name = name;
		this.totalAsset = totalAsset;
		this.ownedStocks = Collections.emptyMap();
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getTotalAsset(){
		return this.totalAsset;
	}
	
	public Map<String, Integer> getOwnedStocks(){
		return this.ownedStocks;
	}
	
	public synchronized boolean sell(int price, int amount, String company){
		this.totalAsset = this.totalAsset + price * amount;
		if(amount <= this.ownedStocks.get(company)){
			this.ownedStocks.put(company, this.ownedStocks.get(company) - amount);
			return true;
		}else{
			return false;
		}
	}
	
	public synchronized boolean buy(int price, int amount, String company){
		this.totalAsset = this.totalAsset - price * amount;
		if(this.totalAsset >= price * amount){
			this.ownedStocks.put(company, this.ownedStocks.get(company) + amount);
			return true;
		}else{
			return false;
		}
	}
	
	
}
