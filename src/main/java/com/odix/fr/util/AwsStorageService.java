package com.odix.fr.util;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class AwsStorageService {
	
	/*********************            https://www.baeldung.com/aws-s3-java           *********************************************
	/******************************************* CREDENTIALS AND INITIATION ******************************************************/
	
	// Create a client connection to access Amazon S3
	// Access Key ID = AKIAJVZZFGXYW75BZ34A
	// Secret Access Key = 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
	AWSCredentials credentials = new BasicAWSCredentials(
			  "AKIAJVZZFGXYW75BZ34A", 
			  "rpS/GX8sv3gWXEW2PXM8qqHnNJR7TyU14RCyuVTW"
			);
	
	// Configure the client
	AmazonS3 s3client = AmazonS3ClientBuilder
			  .standard()
			  .withCredentials(new AWSStaticCredentialsProvider(credentials))
			  .withRegion(Regions.US_EAST_2)
			  .build();
	
	/******************************************** OPERATIONS ON BUCKETS **********************************************************/
	// List All Buckets available in our S3 environment
	public void listAllBuckets() {
		List<Bucket> buckets = s3client.listBuckets();
		for(Bucket bucket : buckets) {
		    System.out.println("AWS Smartgraphe Buckets list : " + bucket.getName());
		}
	}
	
	// Create a new Bucket
	public Bucket createBucket(String bucketName) {
		if(s3client.doesBucketExistV2(bucketName)) {
			System.out.println("Bucket name is not available, Try again with a different Bucket name.");
		    return null;
		}
		return s3client.createBucket(bucketName);
	}
	
	// Delete a Bucket
	public void deleteBucket(String bucketName) {
		try {
		    s3client.deleteBucket(bucketName);
		} 
		catch (AmazonServiceException e) {
			System.out.println(e.getErrorMessage());
		}
	}
	
	/******************************************** OPERATIONS ON OBJECTS **********************************************************/
	
	// List All Objects available in a given Bucket
	public void listAllObjects(String bucketName) {
		ObjectListing objectListing = s3client.listObjects(bucketName);
			for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
			    System.out.println("AWS Smartgraphe Objects list : " + os.getKey());
			}
	}
	

	/**
	 * Uploading an object is a pretty straightforward process. We'll use a putObject() method which accepts 3 parameters:
	 * @param bucketName : The bucket name where we want to upload object
	 * @param objectKey : This is the full path to the file inside the Bucket
	 * @param file: The actual file containing the data to be uploaded
	 */
	// Upload and Object
	public void uploadObject(String bucketName, String objectKey, String file) {
		s3client.putObject(
				  bucketName, 
				  objectKey, // Eg : "Document/hello.txt"
				  new File(file) // Eg : new File("/Users/user/Document/hello.txt")
				);
	}
	
	/**
	 * We can copy an object by calling copyObject() method on our s3client which accepts four parameters:
	 * 
	 * @param oldBucketName : source bucket name
	 * @param oldObjectKey  : object key in source bucket
	 * @param newBucketName : destination bucket name (it can be same as source)
	 * @param newObjectKey  : object key in destination bucket
	 * 
	 * Note: We can use a combination of copyObject() method deleteObject() for performing moving and renaming tasks. 
	 * This will involve copying the object first and then deleting it from its old location.
	 */
	// Copy an Object
	public void copyObject(String oldBucketName, String oldObjectKey, String newBucketName, String newObjectKey) {
		s3client.copyObject(oldBucketName, oldObjectKey, newBucketName, newObjectKey);
	}

	
	/**
	 * To delete an Object, we'll call deleteObject() method on s3client and pass the bucket name and object key
	 * @param bucketName
	 * @param objectKey
	 */
	// Delete an Object
	public void deleteObject(String bucketName,String objectKey) {
		s3client.deleteObject(bucketName,objectKey); // s3client.deleteObject("baeldung-bucket","picture/pic.png");
	}
	
	
	/**
	 * To delete multiple objects at once, we'll first create the DeleteObjectsRequest object and pass the bucket name to its constructor. 
	 * Then we'll pass an array of all the object keys that we want to delete.
	 * Once we have this DeleteObjectsRequest object, we can pass it to deleteObjects() method of our s3client as an argument. 
	 * If successful, then this will delete all objects that we have supplied:
	 * @param bucketName
	 * @param objectKeys
	 */
	// Delete Multiple Objects
	public void deleteMultipleObjects(String bucketName,String objectKeys[]) {	 
		DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(bucketName).withKeys(objectKeys);
		s3client.deleteObjects(delObjReq); // Eg : String objectKeys[] = { "document/hello.txt", "document/pic.png"};
	}
	
	
	
	/**
	 * To download an object, we'll first use the getObject() method on s3client which will return an S3Object object. 
	 * Once we get this, we'll call getObjectContent() on this to get an S3ObjectInputStream object which behaves like a conventional Java InputStream.
	 * @param bucketName
	 * @param objectKey
	 */
	public void downloadObject(String bucketName, String objectKey) {
		//S3Object s3object = s3client.getObject(bucketName, objectKey);
		//S3ObjectInputStream inputStream = s3object.getObjectContent();
		//FileUtils.copyInputStreamToFile(inputStream, new File("/Users/user/Desktop/hello.txt"));
	}

}
