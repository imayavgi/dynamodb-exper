package uiak.exp.prodcat.model;

import java.math.BigDecimal;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

@DynamoDBTable(tableName = "ProductCatalog")
public class CatalogItem {
	private Integer id;
	private String title;
	private String ISBN;
	private Set<String> bookAuthors;
	private DimensionType dimensionType;
	private BigDecimal price;
	private int pageCount;
	private String productCategory;
	private boolean inPublication;
	
	/*
	 * reviews : {
	 * 		reviewEntries : [
	 * 							{
	 * 								reviewer: "Imaya",
	 * 							},
	 * 							{
	 * 							}
	 * 						]
	 * 	
	 * }
	 */
	private Reviews reviews;

	// Partition key
	@DynamoDBHashKey(attributeName = "Id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@DynamoDBRangeKey(attributeName = "Title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@DynamoDBAttribute(attributeName = "ISBN")
	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	@DynamoDBAttribute(attributeName = "Authors")
	public Set<String> getBookAuthors() {
		return bookAuthors;
	}

	public void setBookAuthors(Set<String> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	@DynamoDBTypeConverted(converter = DimensionTypeConverter.class)
	@DynamoDBAttribute(attributeName = "Dimensions")
	public DimensionType getDimensions() {
		return dimensionType;
	}

	@DynamoDBAttribute(attributeName = "Dimensions")
	public void setDimensions(DimensionType dimensionType) {
		this.dimensionType = dimensionType;
	}

	@DynamoDBAttribute(attributeName = "Price")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@DynamoDBAttribute(attributeName = "PageCount")
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	@DynamoDBAttribute(attributeName = "ProductCategory")
	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@DynamoDBAttribute(attributeName = "InPublication")
	public boolean getInPublication() {
		return inPublication;
	}

	public void setInPublication(boolean inPublication) {
		this.inPublication = inPublication;
	}

	@DynamoDBAttribute(attributeName = "Reviews")
	public Reviews getReviews() {
		return reviews;
	}

	public void setReviews(Reviews reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		/*
		 * return "Book [ISBN=" + ISBN + ", bookAuthors=" + bookAuthors +
		 * ", dimensionType= " + dimensionType.getHeight() + " X " +
		 * dimensionType.getLength() + " X " + dimensionType.getThickness() +
		 * ", Id=" + id + ", Title=" + title + "]" + ", price=" + price +
		 * ", product category=" + productCategory;
		 */
		return "Book [ISBN=" + ISBN + ", bookAuthors=" + bookAuthors +  ", Id=" + id + ", Title="
				+ title + "]" + ", price=" + price;
	}

}