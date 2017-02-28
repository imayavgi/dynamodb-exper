package uiak.exp.prodcat.model;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument 
public class Reviews {
	private List<Review> reviewEntries;
	
	public Reviews() {
	}

	public Reviews(List<Review> reviewEntries) {
		super();
		this.reviewEntries = reviewEntries;
	}

	@DynamoDBAttribute(attributeName = "reviewEntries")
	public List<Review> getReviewEntries() {
		return reviewEntries;
	}

	public void setReviewEntries(List<Review> reviewEntries) {
		this.reviewEntries = reviewEntries;
	}
	
	public void addEntry(Review revEntry) {
		if(reviewEntries == null) 
			reviewEntries = new ArrayList<Review>();
		
		reviewEntries.add(revEntry);
	}
	
	
}
