package kr.brainyx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class AmazonFileUpload
{

	public static String AWS_BUCKETNAME = "botalks";
	public static String AWS_ACCESS_KEY = "AKIAJ7WTW2HZIPRMYVYA";
	public static String AWS_SECRET_KEY = "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";

	static AWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY,
			AWS_SECRET_KEY);
	static AmazonS3 s3 = new AmazonS3Client(credentials);

	public static Map<String, Object> s3Upload(File file, String folder)
			throws IOException
	{

		Map<String, Object> map = new HashMap<String, Object>();

		String file_type = file.getName().substring(
				file.getName().lastIndexOf("."), file.getName().length()); // 원본
																			// 파일의
																			// 확장자
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssSSS"); // 리네임
																				// 할
																				// 시분초
																				// 이름
		String file_name = dateFormat.format(calendar.getTime()) + file_type; // 완성된
																				// 파일명

		file_name = folder + file_name;

		try
		{
			// 파일 업로드 부분 파일 이름과 경로를 동시에 넣어줌.
			PutObjectRequest putObjectRequest = new PutObjectRequest(
					AWS_BUCKETNAME, file_name, file);
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // URL
																				// 접근시
																				// 권한
																				// 읽을수
																				// 있도록
																				// 설정.
			s3.putObject(putObjectRequest);
			System.out.println("Uploadinf OK");

			// 파일 다운로드 다운로드 경로와 파일이름 동시 필요.
			System.out.println("Downloading an object");
			S3Object object = s3.getObject(new GetObjectRequest(AWS_BUCKETNAME,
					file_name));

			System.out.println("Content-Type: "
					+ object.getObjectMetadata().getContentType());
			// displayTextInputStream(object.getObjectContent());
			object.close();

		}
		catch (AmazonServiceException ase)
		{
			System.out
					.println("Caught an AmazonServiceException, which means your request made it "
							+ "to Amazon S3, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		}
		catch (AmazonClientException ace)
		{
			System.out
					.println("Caught an AmazonClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with S3, "
							+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

		String filePath = "http://s3-" + s3.getBucketLocation(AWS_BUCKETNAME)
				+ ".amazonaws.com/" + AWS_BUCKETNAME + "/" + file_name;

		System.out.println(" filePath : " + filePath);

		map.put("filePath", filePath);
		map.put("fileName", file_name);
		return map;

	}

	/**
	 * shop만
	 * 
	 * @param file
	 * @param folder
	 * @param no
	 * @return
	 */
	public static Map<String, Object> s3Upload_shop(File file, String folder,
			int no, String idx)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		String file_name = "No" + no + "/photo" + idx;
		file_name = folder + file_name;

		try
		{
			// 파일 업로드 부분 파일 이름과 경로를 동시에 넣어줌.
			PutObjectRequest putObjectRequest = new PutObjectRequest(
					AWS_BUCKETNAME, file_name, file);
			putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // URL
																				// 접근시
																				// 권한
																				// 읽을수
																				// 있도록
																				// 설정.
			s3.putObject(putObjectRequest);
			System.out.println("Uploadinf OK");

			// 파일 다운로드 다운로드 경로와 파일이름 동시 필요.
			System.out.println("Downloading an object");
			S3Object object = s3.getObject(new GetObjectRequest(AWS_BUCKETNAME,
					file_name));

			System.out.println("Content-Type: "
					+ object.getObjectMetadata().getContentType());
			// displayTextInputStream(object.getObjectContent());

		}
		catch (AmazonServiceException ase)
		{
			System.out
					.println("Caught an AmazonServiceException, which means your request made it "
							+ "to Amazon S3, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		}
		catch (AmazonClientException ace)
		{
			System.out
					.println("Caught an AmazonClientException, which means the client encountered "
							+ "a serious internal problem while trying to communicate with S3, "
							+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

		String filePath = "http://s3-" + s3.getBucketLocation(AWS_BUCKETNAME)
				+ ".amazonaws.com/" + AWS_BUCKETNAME + "/" + file_name;

		System.out.println(" filePath : " + filePath);
		map.put("filePath", filePath);
		map.put("fileName", file_name);
		return map;

	}

	public static File s3Download(String filePath, String fileName)
	{
		File file = null;

		try
		{
			URL url = new URL(filePath);
			System.out.println(url);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();

			if (is != null)
			{

				Writer writer = new StringWriter();
				char[] buffer = new char[1024];
				try
				{
					Reader reader = new BufferedReader(new InputStreamReader(
							is, "UTF-8"));
					int n;
					while ((n = reader.read(buffer)) != -1)
					{
						writer.write(buffer, 0, n);
					}
				}
				finally
				{
					is.close();
				}

				System.out.println("connected");
				System.out.println(writer.toString());

			}
			else
			{
				System.out.println("no-connection");
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return file;
	}

	public static void s3Delete(String filePath)
	{
		System.out.println("filePath : " + filePath);
		String realName = filePath.substring(filePath.lastIndexOf("/") + 1,
				filePath.length());

		DeleteObjectsRequest deleterequest = new DeleteObjectsRequest(
				AWS_BUCKETNAME).withKeys(realName);// n.withKeys( fileName );
		s3.deleteObjects(deleterequest);

	}

}
