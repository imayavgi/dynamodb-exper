package uiak.exp.prodcat.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Review {
	private String reviewer;
	private String reviewedOn;
	private String reviewText;
	private ReviewRank reviewRank;
	
	public Review() {
	}
	
	@DynamoDBAttribute(attributeName = "reviewer")
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
	@DynamoDBAttribute(attributeName = "reviewedOn")
	public String getReviewedOn() {
		return reviewedOn;
	}
	public void setReviewedOn(String reviewedOn) {
		this.reviewedOn = reviewedOn;
	}
	
	@DynamoDBAttribute(attributeName = "reviewText")
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	
	@DynamoDBAttribute(attributeName = "rank")
	public ReviewRank getReviewRank() {
		return reviewRank;
	}
	public void setReviewRank(ReviewRank reviewRank) {
		this.reviewRank = reviewRank;
	}
	
}
