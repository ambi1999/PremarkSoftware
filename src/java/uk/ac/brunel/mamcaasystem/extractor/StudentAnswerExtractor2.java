package uk.ac.brunel.mamcaasystem.extractor;
/*
 * Project: MAMEAssSys (Brunel Electronic Assessment Software)
 *
 * Resource Name: StudentAnswerExtractor.java
 *
 * Description: This is a java file which is being used to extract the images files from the zipped borland project
 *
 * Author: Ambikesh Jayal
 *
 * Creation date: 2006-12-01
 *
 * Last Modified date: 2006-12-01
 *
 * Version: 1.0
 *
 * Revisions: 0
 *
 * Copyright Info: Copyright 2006-2008 by Ambikesh
 * Copying or reproduction without prior
 * written approval is prohibited. All rights
 * reserved.
 */

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import java.awt.Color;
import java.io.*;
import java.util.zip.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

public class StudentAnswerExtractor2 {

//public static String STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/12345/";
//public static String STR_NAME_OF_FOLDER_CONTAINING_OUTPUT_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/output/";  

//public static String STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/compressed Answers";
//public static String STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/decompressed Answers/";

public static String STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/classtest3Answers";
public static String STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/classtest3AnswersCompressed/";

public static String STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/classtest3Answers";
public static String STR_NAME_OF_FOLDER_CONTAINING_OUTPUT_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/classtest3AnswersCompressed";

    //public static String STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES="C:/umlasC:\SoftwareDev\0001_A_Brunel Electronic Assessment Software\Students Answers\classtest3Answerssessment/studentsanswers";
    //public static String STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES="C:/umlassessment/decompressedanswers/";
    
//	public static String STR_NAME_OF_REPORT_FILE="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/studentsextractorReport.txt";
    
    //public static String STR_NAME_OF_REPORT_FILE="C:/umlassessment/studentsanswers/studentsextractorReport.txt";
    public static String STR_NAME_OF_REPORT_FILE=STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES+"studentsextractorReport.txt";
    
    private static BufferedWriter bufferedWriter = null;
//new BufferedWriter(new FileWriter(STR_NAME_OF_REPORT_FILE));
    
    public static String STR_NAME_OF_FILE_TO_EXTRACT="";
    public static String STR_NAME_TO_APPEND_TO_THE_EXTRACTED_FILE="";
    
    public static String STR_DESIRED_FILE_EXTENSION=".wmf";
    public static String STR_FILE_EXTENSION_TO_BE_EXCLUDED="default.dfPackage.wmf";
    
    public static String STR_ZIP_FILE_EXTENSION=".zip";
    
    
    public static void main_old(String[] args) throws Exception {
    /*
    */    
     //extractStudentsAnswers(String strBaseFolderPath);
     //   
	 Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
            PdfWriter.getInstance(document, new FileOutputStream(STR_NAME_OF_FOLDER_CONTAINING_OUTPUT_FILES+"rawdata.pdf"));
			//writeRawImageDataToPDF(Document document, byte imext[]);

		}catch(Exception ex){
			ex.printStackTrace();
		}
     
    }
    
    
    public static void main(String[] args) throws Exception {
        try{
            //File baseDir1=new File(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES);
            File baseDir1=new File(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES);
            File[] listZippedFiles=baseDir1.listFiles();
            String strTextToWrite="";
            for(int i=0;i<listZippedFiles.length;i++){
            //String strZipFileName=listZippedFiles[i].getName() + "\n";
                String strZipFileName=listZippedFiles[i].getAbsolutePath();
            //getAbsolutePath()
                System.out.println("strZipFileName: ["+strZipFileName+"]");
            //writeToReportFile(strZipFileName);
                extractStudentAnswers(strZipFileName);
                //writeImagesToPDF();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        try{
        File extractedFilesDir=new File(STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES);
        File[] listExtractedFiles=extractedFilesDir.listFiles();
        for(int i=0;i<listExtractedFiles.length;i++){
            //String strZipFileName=listZippedFiles[i].getName() + "\n";
                String strZipFileName=listExtractedFiles[i].getAbsolutePath();
            //getAbsolutePath()
                System.out.println("strZipFileName: ["+strZipFileName+"]");
            //writeToReportFile(strZipFileName);
                //extractStudentAnswers(strZipFileName);
                writeImagesToPDF();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
//extractStudentAnswers();
//extractStudentAnswers(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES);
        try{
            bufferedWriter.close();
            
            //writing to the pdf file
            //writeRawImageDataToPDF();
            //writeImagesToPDF();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static void extractStudentAnswers(String strZipFilesSourceFolder, Document document) throws Exception{
        
        System.out.println("Starting the Extraction Process ");
        String strAdditionalPrefix= strZipFilesSourceFolder.substring(strZipFilesSourceFolder.lastIndexOf('\\')+1);
        System.out.println("***** strAdditionalPrefix: ["+strAdditionalPrefix+"]");
        String strTextToWrite= "";
        try{
//File baseDir1=new File(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES);
            File baseDir1=new File(strZipFilesSourceFolder);
            File[] 	listZippedFiles=baseDir1.listFiles();
            
            for(int i=0;i<listZippedFiles.length;i++){
                ZipFile zipFile=new ZipFile(listZippedFiles[i]);
                String strZipFileName=zipFile.getName();
                System.out.println("zipFile.getName(): ["+zipFile.getName()+"]");
//Enumeration<? extends ZipEntry> enumZipEntries=zipFile.entries();
                Enumeration enumZipEntries=zipFile.entries();
                int count=0;
                while(enumZipEntries.hasMoreElements()){
//ZipEntry zipEntry=enumZipEntries.nextElement();
                    ZipEntry zipEntry=(ZipEntry)enumZipEntries.nextElement();
                    String strZipEntryName=zipEntry.getName();
                    System.out.println("*********strZipEntryName: ["+strZipEntryName+"]");
                    
//recursive call, if student has double zipped a file
                    if(strZipEntryName.endsWith(STR_ZIP_FILE_EXTENSION)){
//extractStudentAnswers();
                        strTextToWrite="*********  An inside zip file found for the student answer: [" + strZipFileName + "]  \n";
                        writeToReportFile(strTextToWrite);
                    }
                    
                    if(strZipEntryName!=null && strZipEntryName.endsWith(STR_DESIRED_FILE_EXTENSION)){
                        if(!strZipEntryName.endsWith(STR_FILE_EXTENSION_TO_BE_EXCLUDED)){
                            count=count+1;
                            byte[] b =zipEntry.getExtra();
                            
                            String strSuffix=strZipFileName.substring(strZipFileName.lastIndexOf('\\')+1,strZipFileName.lastIndexOf('.'));
//String strActualFileName=strZipEntryName.substring(strZipEntryName.lastIndexOf('/')+1,strZipEntryName.lastIndexOf('.'));
                            String strActualFileName=strZipEntryName.substring(strZipEntryName.lastIndexOf('/')+1,strZipEntryName.indexOf('.'));
                            System.out.println("*********strActualFileName: ["+strActualFileName+"]");
//String strFileName=	strSuffix+"-"+count + STR_DESIRED_FILE_EXTENSION;
                            String strDesiredFileName=	strAdditionalPrefix+"----"+strSuffix+"-"+strActualFileName+"-"+count + STR_DESIRED_FILE_EXTENSION;
                            System.out.println("***********strDesiredFileName: ["+strDesiredFileName+"]");
                            InputStream inputStream = zipFile.getInputStream( zipEntry );
                            createCloneFile(inputStream,strDesiredFileName,STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES);
                        }
                    }
                }
                
                if(count == 0){
//strTextToWrite="*********  NO IMAGE FILE DETECTED except the default one has been found for the student answer: [" + strZipFileName + "]  \n";
                    strTextToWrite="*********  NO IMAGE FILE DETECTED for the student answer: [" + strZipFileName + "]  \n";
                    writeToReportFile(strTextToWrite);
                    
                }
                
                if(count != 0){
                    strTextToWrite="The number of image files found for the for the student answer: [" + strZipFileName + "]  is ["+count+"]   \n";
                    writeToReportFile(strTextToWrite);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            strTextToWrite="%%%%%%%%%%  EXCEPTION WITH THE FILE : [" + strZipFilesSourceFolder + "]  \n";
            writeToReportFile(strTextToWrite);
        }
    }
    
	 public static void extractStudentAnswers(String strZipFilesSourceFolder) throws Exception{
        
        System.out.println("Starting the Extraction Process ");
        String strAdditionalPrefix= strZipFilesSourceFolder.substring(strZipFilesSourceFolder.lastIndexOf('\\')+1);
        System.out.println("***** strAdditionalPrefix: ["+strAdditionalPrefix+"]");
        String strTextToWrite= "";
        try{
//File baseDir1=new File(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES);
            File baseDir1=new File(strZipFilesSourceFolder);
            File[] 	listZippedFiles=baseDir1.listFiles();
            
            for(int i=0;i<listZippedFiles.length;i++){
                ZipFile zipFile=new ZipFile(listZippedFiles[i]);
                String strZipFileName=zipFile.getName();
                System.out.println("zipFile.getName(): ["+zipFile.getName()+"]");
//Enumeration<? extends ZipEntry> enumZipEntries=zipFile.entries();
                Enumeration enumZipEntries=zipFile.entries();
                int count=0;
                while(enumZipEntries.hasMoreElements()){
//ZipEntry zipEntry=enumZipEntries.nextElement();
                    ZipEntry zipEntry=(ZipEntry)enumZipEntries.nextElement();
                    String strZipEntryName=zipEntry.getName();
                    System.out.println("*********strZipEntryName: ["+strZipEntryName+"]");
                    
//recursive call, if student has double zipped a file
                    if(strZipEntryName.endsWith(STR_ZIP_FILE_EXTENSION)){
//extractStudentAnswers();
                        strTextToWrite="*********  An inside zip file found for the student answer: [" + strZipFileName + "]  \n";
                        writeToReportFile(strTextToWrite);
                    }
                    
                    if(strZipEntryName!=null && strZipEntryName.endsWith(STR_DESIRED_FILE_EXTENSION)){
                        if(!strZipEntryName.endsWith(STR_FILE_EXTENSION_TO_BE_EXCLUDED)){
                            count=count+1;
                            byte[] b =zipEntry.getExtra();
                            
                            String strSuffix=strZipFileName.substring(strZipFileName.lastIndexOf('\\')+1,strZipFileName.lastIndexOf('.'));
//String strActualFileName=strZipEntryName.substring(strZipEntryName.lastIndexOf('/')+1,strZipEntryName.lastIndexOf('.'));
                            String strActualFileName=strZipEntryName.substring(strZipEntryName.lastIndexOf('/')+1,strZipEntryName.indexOf('.'));
                            System.out.println("*********strActualFileName: ["+strActualFileName+"]");
//String strFileName=	strSuffix+"-"+count + STR_DESIRED_FILE_EXTENSION;
                            String strDesiredFileName=	strAdditionalPrefix+"----"+strSuffix+"-"+strActualFileName+"-"+count + STR_DESIRED_FILE_EXTENSION;
                            System.out.println("***********strDesiredFileName: ["+strDesiredFileName+"]");
                            InputStream inputStream = zipFile.getInputStream( zipEntry );
                            createCloneFile(inputStream,strDesiredFileName,STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES);
                        }
                    }
                }
                
                if(count == 0){
//strTextToWrite="*********  NO IMAGE FILE DETECTED except the default one has been found for the student answer: [" + strZipFileName + "]  \n";
                    strTextToWrite="*********  NO IMAGE FILE DETECTED for the student answer: [" + strZipFileName + "]  \n";
                    writeToReportFile(strTextToWrite);
                    
                }
                
                if(count != 0){
                    strTextToWrite="The number of image files found for the for the student answer: [" + strZipFileName + "]  is ["+count+"]   \n";
                    writeToReportFile(strTextToWrite);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            strTextToWrite="%%%%%%%%%%  EXCEPTION WITH THE FILE : [" + strZipFilesSourceFolder + "]  \n";
            writeToReportFile(strTextToWrite);
        }
    }

    public static void extractStudentAnswers() throws Exception{
        System.out.println("Starting the Extraction Process ");
        File baseDir1=new File(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES);
        File[] 	listZippedFiles=baseDir1.listFiles();
        for(int i=0;i<listZippedFiles.length;i++){
            ZipFile zipFile=new ZipFile(listZippedFiles[i]);
            String strZipFileName=zipFile.getName();
            System.out.println("zipFile.getName(): ["+zipFile.getName()+"]");
//Enumeration<? extends ZipEntry> enumZipEntries=zipFile.entries();
            Enumeration enumZipEntries=zipFile.entries();
            int count=0;
            while(enumZipEntries.hasMoreElements()){
                ZipEntry zipEntry=(ZipEntry)enumZipEntries.nextElement();
                String strZipEntryName=zipEntry.getName();
                System.out.println("*********strZipEntryName: ["+strZipEntryName+"]");
                if(strZipEntryName!=null && strZipEntryName.endsWith(STR_DESIRED_FILE_EXTENSION)){
                    if(!strZipEntryName.endsWith(STR_FILE_EXTENSION_TO_BE_EXCLUDED)){
                        count=count+1;
                        byte[] b =zipEntry.getExtra();
                        
                        String strSuffix=strZipFileName.substring(strZipFileName.lastIndexOf('\\')+1,strZipFileName.lastIndexOf('.'));
//String strActualFileName=strZipEntryName.substring(strZipEntryName.lastIndexOf('/')+1,strZipEntryName.lastIndexOf('.'));
                        String strActualFileName=strZipEntryName.substring(strZipEntryName.lastIndexOf('/')+1,strZipEntryName.indexOf('.'));
                        System.out.println("*********strActualFileName: ["+strActualFileName+"]");
//String strFileName=	strSuffix+"-"+count + STR_DESIRED_FILE_EXTENSION;
                        String strDesiredFileName=	strSuffix+"-"+strActualFileName+"-"+count + STR_DESIRED_FILE_EXTENSION;
                        System.out.println("***********strDesiredFileName: ["+strDesiredFileName+"]");
                        InputStream inputStream = zipFile.getInputStream( zipEntry );
                        createCloneFile(inputStream,strDesiredFileName,STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES);
                    }
                }
            }
        }
    }
    
    public static void createCloneFile(InputStream inputStream, String strNameOfFileToBeCopiedTo, String strNameOfFolderToBeCopiedTo) throws Exception{
        File targetFile=new File(strNameOfFolderToBeCopiedTo+strNameOfFileToBeCopiedTo);
        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
        int b=-1;
        while((b=inputStream.read())!=-1){
            fileOutputStream.write(b);
        }
    }
    
    public static void writeToReportFile(String strTextToWrite){
	//BufferedWriter bufferedWriter = null;
        try{
            if(bufferedWriter==null){
                bufferedWriter = new BufferedWriter(new FileWriter(STR_NAME_OF_REPORT_FILE, true));
            }
            bufferedWriter.write(strTextToWrite);
            bufferedWriter.write("\n");
	//bufferedWriter.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

public static void writeRawImageDataToPDF(Document document, byte imext[]){
	    System.out.println("bytes[] / raw image");
        // step 1: creation of a document-object
        //Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
            //PdfWriter.getInstance(document, new FileOutputStream(STR_NAME_OF_FOLDER_CONTAINING_OUTPUT_FILES+"rawdata.pdf"));
            
            // step 3: we open the document
            document.open();
            
			/*
            // step 4: we add content (example by Paulo Soares)
            
            // creation a jpeg passed as an array of bytes to the Image
            //RandomAccessFile rf = new RandomAccessFile("otsoe.jpg", "r");
		    RandomAccessFile rf = new RandomAccessFile(STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES+"MY Statechart Q33.dfState.wmf", "r");
           //RandomAccessFile rf = new RandomAccessFile(STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES+"my 12345 answer.doc", "r");
            int size = (int)rf.length();
            byte imext[] = new byte[size];
					
            rf.readFully(imext);
            rf.close();
			*/
            Image img1 = Image.getInstance(imext);
            img1.setAbsolutePosition(50, 500);
            document.add(img1);
            
			/*
            // creation of an image of 100 x 100 pixels (x 3 bytes for the Red, Green and Blue value)
            byte data[] = new byte[100*100*3];
            for (int k = 0; k < 100; ++k) {
                for (int j = 0; j < 300; j += 3) {
                    data[k * 300 + j] = (byte)(255 * Math.sin(j * .5 * Math.PI / 300));
                    data[k * 300 + j + 1] = (byte)(256 - j * 256 / 300);
                    data[k * 300 + j + 2] = (byte)(255 * Math.cos(k * .5 * Math.PI / 100));
                }
            }
            Image img2 = Image.getInstance(100, 100, 3, 8, data);
            img2.setAbsolutePosition(200, 200);
            document.add(img2);
			*/
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        
        // step 5: we close the document
        document.close();
}

public static void writeImagesToPDF(){
    System.out.println("************* Entering writeImagesToPDF");
     try{
         // step 1: creation of a document-object
       //Document document = new Document();
   
       // creation of the document with a certain size and certain margins
        //Document document = new Document(PageSize.A4, 50, 50, 50, 50);
         Document document = new Document(PageSize.A2, 50, 50, 50, 50);
        
        // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
            //PdfWriter.getInstance(document, new FileOutputStream(STR_NAME_OF_FOLDER_CONTAINING_OUTPUT_FILES+"/2222rawdata.pdf"));
            //RandomAccessFile rf = new RandomAccessFile(STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES+"MY Statechart Q33.dfState.wmf", "r");
            
            PdfWriter.getInstance(document, new FileOutputStream(STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES+"ct3results.pdf"));
            
            System.out.println("***** [" + STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES+"ct3results.pdf" +"]");
            
            // step 3: we open the document
            document.open();
            //document.newPage();
            
            File baseDir1=new File(STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES);
            File[] 	listFiles=baseDir1.listFiles();
            String strTextToWrite= "";
            int totalPageCount=listFiles.length+1;
            
             //HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
            //HeaderFooter headerToppage = new HeaderFooter(new Phrase("Student Answers  "), false);
            
            //HeaderFooter footerToppage = new HeaderFooter(new Phrase("Page " ), new Phrase(" of " + totalPageCount +  "."));
            //header.setPageNumber(i);
            
            //document.setHeader(headerToppage);
            
            document.add(new Paragraph("%%%%  Student Answers for the Class Test 3. "));
            
            //document.setFooter(footerToppage);
            
            
            //document.newPage();
            
            for(int i=0;i<listFiles.length;i++){
                //String strZipFileName=listZippedFiles[i].getName() + "\n";
                String strFileName=listFiles[i].getAbsolutePath();
//getAbsolutePath()
                System.out.println("strFileName: ["+strFileName+"]");
//writeToReportFile(strZipFileName);
                
                 //HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
            HeaderFooter header = new HeaderFooter(new Phrase("&&&&&&&&Answer for " + strFileName), false);
            
            HeaderFooter footer = new HeaderFooter(new Phrase("Page " ), new Phrase(" of " + totalPageCount +  "."));
            //header.setPageNumber(i);
            
            document.setHeader(header);
            document.setFooter(footer);
            document.newPage();
            
                if(strFileName!=null && strFileName.endsWith(STR_DESIRED_FILE_EXTENSION)){   

        // step 1: creation of a document-object
        //Document document = new Document();
        
        //try {
            // step 4: we add content (example by Paulo Soares)
            
            // creation a jpeg passed as an array of bytes to the Image
            //RandomAccessFile rf = new RandomAccessFile("otsoe.jpg", "r");
            //RandomAccessFile rf = new RandomAccessFile(STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES+"MY Statechart Q33.dfState.wmf", "r");
            RandomAccessFile rf = new RandomAccessFile(strFileName, "r");
            //RandomAccessFile rf = new RandomAccessFile(STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES+"MY Statechart Q33.dfState.wmf", "r");
           //RandomAccessFile rf = new RandomAccessFile(STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES+"my 12345 answer.doc", "r");
            int size = (int)rf.length();
            byte imext[] = new byte[size];
            rf.readFully(imext);
            //rf.close();
            
            //document.newPage();
            
           
            Image img1 = Image.getInstance(imext);
            //img1.setAbsolutePosition(50, 500);
            String strText="i= ["+i+"]  \n Name of the Diagram File: ["+strFileName+"]";
            //document.add(new Paragraph(strText));
           
            rf.close();
            //HeaderFooter headerFooter=new HeaderFooter();
            //headerFooter.setPageNumber(i);
            //document.setHeader(headerFooter);
            document.add(img1);
            
            }else{
            //Paragraph p = new Paragraph("NOT AN IMAGE FILE. ",FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, new Color(0, 255, 255)));
               Paragraph p = new Paragraph("NOT AN IMAGE FILE. ",
               FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, Color.RED));
            document.add(p);
            }
            }
            
        // step 5: we close the document
        document.close();

        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
System.out.println("************* Exiting writeImagesToPDF");        
}


public static void writeRawImageDataToPDF(){
    System.out.println("bytes[] / raw image");
        try {
        // step 1: creation of a document-object
        Document document = new Document();
        
            // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
            PdfWriter.getInstance(document, new FileOutputStream(STR_NAME_OF_FOLDER_CONTAINING_OUTPUT_FILES+"rawdata.pdf"));
            
            // step 3: we open the document
            document.open();
            
            // step 4: we add content (example by Paulo Soares)
            
            // creation a jpeg passed as an array of bytes to the Image
            //RandomAccessFile rf = new RandomAccessFile("otsoe.jpg", "r");
		    RandomAccessFile rf = new RandomAccessFile(STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES+"MY Statechart Q33.dfState.wmf", "r");
           //RandomAccessFile rf = new RandomAccessFile(STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES+"my 12345 answer.doc", "r");
            int size = (int)rf.length();
            byte imext[] = new byte[size];
            rf.readFully(imext);
            rf.close();
            Image img1 = Image.getInstance(imext);
            img1.setAbsolutePosition(50, 500);
            document.add(img1);
            
            // creation of an image of 100 x 100 pixels (x 3 bytes for the Red, Green and Blue value)
            byte data[] = new byte[100*100*3];
            for (int k = 0; k < 100; ++k) {
                for (int j = 0; j < 300; j += 3) {
                    data[k * 300 + j] = (byte)(255 * Math.sin(j * .5 * Math.PI / 300));
                    data[k * 300 + j + 1] = (byte)(256 - j * 256 / 300);
                    data[k * 300 + j + 2] = (byte)(255 * Math.cos(k * .5 * Math.PI / 100));
                }
            }
            Image img2 = Image.getInstance(100, 100, 3, 8, data);
            img2.setAbsolutePosition(200, 200);
            document.add(img2);
            
            // step 5: we close the document
            document.close();
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        
        
}

}