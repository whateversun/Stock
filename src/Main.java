public class Main {
	public static void main(String[] args) throws Exception { 
		Stock[] haha = getCompanies();
		System.out.println(haha[0].getSymbol());
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
