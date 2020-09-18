package uk.ac.brunel.mamcaasystem.extractor;
import java.io.*;
import java.util.zip.*;
import java.util.*;

public class StudentAnswerExtractor_old 
{
	public static String STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/compressed Answers";
	public static String STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/decompressed Answers/";

	public static String STR_NAME_OF_FILE_TO_EXTRACT="";
	public static String STR_NAME_TO_APPEND_TO_THE_EXTRACTED_FILE="";

	public static String STR_DESIRED_FILE_EXTENSION=".wmf";
	public static String STR_FILE_EXTENSION_TO_BE_EXCLUDED="default.dfPackage.wmf";

	public static void main(String[] args) throws Exception
	{
		System.out.println("Hello World!");
		//File[] 	listFiles() 
		File baseDir1=new File(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES);
		File[] 	listZippedFiles=baseDir1.listFiles();
		for(int i=0;i<listZippedFiles.length;i++){
		//File zipFile=listZippedFiles[i];
		ZipFile zipFile=new ZipFile(listZippedFiles[i]);
		String strZipFileName=zipFile.getName();
		System.out.println("zipFile.getName(): ["+zipFile.getName()+"]");
		//System.out.println("zipFile.getCanonicalPath(): ["+zipFile.getCanonicalPath()+"]");

		//Enumeration<? extends ZipEntry> 	entries() 
		Enumeration<? extends ZipEntry> enumZipEntries=zipFile.entries();
		int count=0;
		while(enumZipEntries.hasMoreElements()){
		
		//ZipEntry ZipEntry=(ZipEntry)enumZipEntries.nextElement();
		ZipEntry zipEntry=enumZipEntries.nextElement();
		String strZipEntryName=zipEntry.getName();
		if(strZipEntryName!=null && strZipEntryName.endsWith(STR_DESIRED_FILE_EXTENSION)){
		if(!strZipEntryName.endsWith(STR_FILE_EXTENSION_TO_BE_EXCLUDED)){

		//System.out.println("zipEntry.getName(): ["+zipEntry.getName()+"]");
		count=count+1;
		byte[] b =zipEntry.getExtra();
		
		String strSuffix=strZipFileName.substring(strZipFileName.lastIndexOf('\\')+1,strZipFileName.lastIndexOf('.'));
		//String strFileName=	strSuffix+"-"+strZipEntryName.substring(strZipEntryName.lastIndexOf('/')+1);
		String strFileName=	strSuffix+"-"+count + STR_DESIRED_FILE_EXTENSION;
		System.out.println("strFileName: ["+strFileName+"]");
		
		//InputStreamReader inputStreamReader=new InputStreamReader(zipFile.getInputStream(zipEntry));
		InputStream inputStream = zipFile.getInputStream( zipEntry );
		createCloneFile(inputStream,strFileName,STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES);

		}
		}
		//System.out.println("zipEntry.getName(): ["+zipEntry.getName()+"]");
		//System.out.println("zipEntry.isDirectory(): ["+zipEntry.isDirectory()+"]");
		}

		}
	}

	public static void createCloneFile(InputStream inputStream, String strNameOfFileToBeCopiedTo, String strNameOfFolderToBeCopiedTo) throws Exception{
 File targetFile=new File(strNameOfFolderToBeCopiedTo+strNameOfFileToBeCopiedTo);
 FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
 int b=-1;
 //while((b=inputStreamReader.read())!=-1){
while((b=inputStream.read())!=-1){
	 //System.out.println("b: ["+b+"]");
fileOutputStream.write(b);
 }
 }


 public static void createCloneFile_old(InputStreamReader inputStreamReader, String strNameOfFileToBeCopiedTo, String strNameOfFolderToBeCopiedTo) throws Exception{
//	public static void createCloneFile(byte[] b, String strNameOfFileToBeCopiedTo, String strNameOfFolderToBeCopiedTo) throws Exception{
 //BufferedReader zipReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(zipEntry)));
 //BufferedReader zipReader = new BufferedReader(inputStreamReader);
 //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
 File targetFile=new File(strNameOfFolderToBeCopiedTo+strNameOfFileToBeCopiedTo);
 FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
 int b=-1;
 while((b=inputStreamReader.read())!=-1){
	 //System.out.println("b: ["+b+"]");
fileOutputStream.write(b);
 }
 }
}
