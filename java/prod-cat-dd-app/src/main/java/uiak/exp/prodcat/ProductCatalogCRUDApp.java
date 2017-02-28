package uiak.exp.prodcat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import uiak.exp.prodcat.model.CatalogItem;
import uiak.exp.prodcat.model.DimensionType;
import uiak.exp.prodcat.model.Review;
import uiak.exp.prodcat.model.ReviewRank;
import uiak.exp.prodcat.model.Reviews;

public class ProductCatalogCRUDApp {

	private String localEndPt = "http://localhost:8000";
	private AmazonDynamoDB client;
	private DynamoDBMapper mapper;

	public static void main(String[] args) throws IOException {
		ProductCatalogCRUDApp app = new ProductCatalogCRUDApp();
		try {
			// app.testReadOperation();
			// app.testWriteOperation();
			// app.testBatchInsertOperation();
			// app.findBooksPricedLessThanSpecifiedValue("400.00");
			app.findBookWithId(401);
			//app.updateOperation(902);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Example complete!");
	}

	public ProductCatalogCRUDApp() {
		client = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localEndPt, "dummy")).build();
		mapper = new DynamoDBMapper(client);
	}

	void testReadOperation() {

		// Retrieve the item.
		CatalogItem itemRetrieved = mapper.load(CatalogItem.class, 201, "Book 101 Title");
		System.out.println("Item retrieved:");
		System.out.println(itemRetrieved);

		itemRetrieved = mapper.load(CatalogItem.class, 102, "Book 102 Title");
		System.out.println("Item retrieved:");
		System.out.println(itemRetrieved);

	}

	void testWriteOperation() {
		DimensionType dimType = new DimensionType();
		dimType.setHeight("8.00");
		dimType.setLength("11.0");
		dimType.setThickness("1.0");

		CatalogItem book = new CatalogItem();
		book.setId(502);
		book.setTitle("Book 502");
		book.setISBN("555-5555555555");
		book.setBookAuthors(new HashSet<String>(Arrays.asList("Imaya", "Karthik", "Chitra", "Kamal")));
		book.setDimensions(dimType);

		DynamoDBMapper mapper = new DynamoDBMapper(client);
		mapper.save(book);

		CatalogItem bookRetrieved = mapper.load(CatalogItem.class, 502, "Book 502");
		System.out.println("Book info: " + "\n" + bookRetrieved);

		bookRetrieved.getDimensions().setHeight("9.0");
		bookRetrieved.getDimensions().setLength("12.0");
		bookRetrieved.getDimensions().setThickness("2.0");

		mapper.save(bookRetrieved);

		bookRetrieved = mapper.load(CatalogItem.class, 502, "Book 502");
		System.out.println("Updated book info: " + "\n" + bookRetrieved);
	}

	void testBatchInsertOperation() {
		CatalogItem book1 = new CatalogItem();
		book1.setId(901);
		book1.setTitle("My book created in batch write");
		book1.setInPublication(true);
		book1.setISBN("902-11-11-1111");
		book1.setPageCount(500);
		book1.setPrice(new BigDecimal("101.30"));
		book1.setProductCategory("Fiction");

		CatalogItem book2 = new CatalogItem();
		book2.setId(902);
		book2.setTitle("Second My book created in batch write");
		book2.setInPublication(true);
		book2.setISBN("902-11-11-2222");
		book2.setPageCount(230);
		book2.setPrice(new BigDecimal("121.30"));
		book2.setProductCategory("No Fiction-Science");

		CatalogItem book3 = new CatalogItem();
		book3.setId(904);
		book3.setTitle("Third My book created in batch write");
		book3.setInPublication(true);
		book3.setISBN("902-11-11-4444");
		book3.setPageCount(500);
		book3.setPrice(new BigDecimal("199.99"));
		book3.setProductCategory("Math Text");

		System.out.println("Adding three books to ProductCatalog table.");
		mapper.batchSave(Arrays.asList(book1, book2, book3));
	}

	void findBooksPricedLessThanSpecifiedValue(String value) throws Exception {

		System.out.println("FindBooksPricedLessThanSpecifiedValue: Scan ProductCatalog.");

		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withN(value));
		eav.put(":val2", new AttributeValue().withS("Math Text"));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("Price < :val1 and ProductCategory = :val2").withExpressionAttributeValues(eav);

		List<CatalogItem> scanResult = mapper.scan(CatalogItem.class, scanExpression);

		for (CatalogItem book : scanResult) {
			System.out.println(book);
		}
	}

	CatalogItem findBookWithId(int id) throws Exception {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withN(id + ""));

		DynamoDBQueryExpression<CatalogItem> queryExpression = new DynamoDBQueryExpression<CatalogItem>()
				.withKeyConditionExpression("Id = :val1").withExpressionAttributeValues(eav);

		List<CatalogItem> betweenReplies = mapper.query(CatalogItem.class, queryExpression);

		for (CatalogItem item : betweenReplies) {
			System.out.format("Id=%s, Title=%s, Reviews=%s", item.getId(), item.getTitle(), item.getReviews());
			return item;
		}
		return null;
	}

	void updateOperation(int id) throws Exception {
		CatalogItem item = findBookWithId(id);
		// Update the item.
		Review rv1 = new Review();
		rv1.setReviewer("Imaya");
		//rv1.setReviewedOn(LocalDateTime.now());
		rv1.setReviewRank(ReviewRank.FOUR);
		rv1.setReviewText("Great book to read during vavation");
		
		
		Reviews entries = item.getReviews();
		if (entries == null ) entries = new Reviews(null);
		entries.addEntry(rv1);
		item.setReviews(entries);
		
		mapper.save(item);
		System.out.println("Item updated:");
	}

}