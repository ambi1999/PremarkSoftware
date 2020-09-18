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

public class CourseworkOrganiser {

//public static String STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/12345/";
//public static String STR_NAME_OF_FOLDER_CONTAINING_OUTPUT_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/output/";  

//public static String STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/compressed Answers";
//public static String STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/decompressed Answers/";

//public static String STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/classtest3Answers";
//public static String STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/classtest3AnswersCompressed/";

public static String STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES="C:\\SoftwareDev\\0001_A_Brunel Electronic Assessment Software\\Students Answers\\classtest3Answers";
public static String STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES="C:\\SoftwareDev\\0001_A_Brunel Electronic Assessment Software\\Students Answers\\classtest3AnswersCompressed\\";

//public static String STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/classtest3Answers";
//public static String STR_NAME_OF_FOLDER_CONTAINING_OUTPUT_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/classtest3AnswersCompressed";

    //public static String STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES="C:/umlasC:\SoftwareDev\0001_A_Brunel Electronic Assessment Software\Students Answers\classtest3Answerssessment/studentsanswers";
    //public static String STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES="C:/umlassessment/decompressedanswers/";
    
//	public static String STR_NAME_OF_REPORT_FILE="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/studentsextractorReport.txt";
    
    //public static String STR_NAME_OF_REPORT_FILE="C:/umlassessment/studentsanswers/studentsextractorReport.txt";
    //public static String STR_NAME_OF_REPORT_FILE=STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES+"studentsextractorReport.txt";

    //for Windows
    /*
    public static String STR_NAME_OF_REPORT_FOLDER="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/classtest3Reports/";
    public static String STR_NAME_OF_REPORT_FILE=STR_NAME_OF_REPORT_FOLDER+"studentsextractorReport.txt";
    public static String STR_NAME_OF_REPORT_FILE_CONTAINING_ERROR=STR_NAME_OF_REPORT_FOLDER+"studentsextractorReportcontainingerror.txt";
    public static String STR_NAME_OF_STUDENTWISE_REPORT_FILE=STR_NAME_OF_REPORT_FOLDER+"studentswiseextractorReport.txt";
    */

    //for Linux
    public static String STR_NAME_OF_REPORT_FOLDER="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/classtest3Reports/";
    public static String STR_NAME_OF_REPORT_FILE=STR_NAME_OF_REPORT_FOLDER+"studentsextractorReport.txt";
    public static String STR_NAME_OF_REPORT_FILE_CONTAINING_ERROR=STR_NAME_OF_REPORT_FOLDER+"studentsextractorReportcontainingerror.txt";
    public static String STR_NAME_OF_STUDENTWISE_REPORT_FILE=STR_NAME_OF_REPORT_FOLDER+"studentswiseextractorReport.txt";
    
    
    private static BufferedWriter bufferedWriter = null;
    private static BufferedWriter bufferedWriter_studentwisereport = null;
    private static BufferedWriter bufferedWriter_ErrorReport = null;
//new BufferedWriter(new FileWriter(STR_NAME_OF_REPORT_FILE));
    
    public static String STR_NAME_OF_FILE_TO_EXTRACT="";
    public static String STR_NAME_TO_APPEND_TO_THE_EXTRACTED_FILE="";
    
    public static String STR_DESIRED_FILE_EXTENSION=".wmf";
    public static String STR_FILE_EXTENSION_TO_BE_EXCLUDED="default.dfPackage.wmf";
    public static String STR_DESIRED_FILE_WORD="act";
    
    
    public static String STR_ZIP_FILE_EXTENSION=".zip";
    
    public static Map mapExtractionStatus=new LinkedHashMap();
    
    public static Map mapExtractedFileSizeAndNames=new TreeMap();
    
    public static void main(String[] args) throws Exception {
    writeImagesToPDFInOrganisedManner();
    }
    
    public static void main_backup(String[] args) throws Exception {
        try{
            //File baseDir1=new File(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES);
            File baseDir1=new File(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES);
            File[] listZippedFiles=baseDir1.listFiles();
            String strTextToWrite="";
            System.out.println("*************  listZippedFiles.length: ["+listZippedFiles.length+"]");
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
        
        
        generateStudentwiseReport(mapExtractionStatus);
        
        
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
            if(bufferedWriter!=null){
            bufferedWriter.close();
            }
            if(bufferedWriter_studentwisereport!=null){
            bufferedWriter_studentwisereport.close();
            }
            
            if(bufferedWriter_ErrorReport!=null){
            bufferedWriter_ErrorReport.close();
            }
            //writing to the pdf file
            //writeRawImageDataToPDF();
            //writeImagesToPDF();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
	 public static void extractStudentAnswers(String strZipFilesSourceFolder) throws Exception{
        
        System.out.println("Starting the Extraction Process ");
        String strAdditionalPrefix= strZipFilesSourceFolder.substring(strZipFilesSourceFolder.lastIndexOf('\\')+1);
        System.out.println("***** strAdditionalPrefix: ["+strAdditionalPrefix+"]");
        Vector vecMessages=new Vector();
        
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
                    
                    if(strZipEntryName==null){
                        strZipEntryName="";
                    }
                    strZipEntryName=strZipEntryName.trim();
                    
//recursive call, if student has double zipped a file
                    if(strZipEntryName.endsWith(STR_ZIP_FILE_EXTENSION)){
//extractStudentAnswers();
                        strTextToWrite="*********  Inside zip file Found: [" + strZipFileName.replace(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES,"") + "]  \n";
                        writeToReportFile(strTextToWrite);
                        writeToErrorReportFile(strTextToWrite);
                        vecMessages.add(strTextToWrite);
                    }
                    
                    if(strZipEntryName!=null && strZipEntryName.endsWith(STR_DESIRED_FILE_EXTENSION)){
                        //if(!strZipEntryName.endsWith(STR_FILE_EXTENSION_TO_BE_EXCLUDED)){
                        if(true){
                            if(strZipEntryName.toLowerCase().contains(STR_DESIRED_FILE_WORD.toLowerCase())){
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
                            strTextToWrite="£££££££££ EXTRACTING THE IMAGE FILE : [" + strZipEntryName.replace(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES,"") + "]  \n";
                            //writeToReportFile(strTextToWrite);
                            vecMessages.add(strTextToWrite);
                        }else{
                        strTextToWrite="*********  NOT EXTRACTING THE IMAGE FILE BECAUSE IT DOES NOT CONTAIN DESIRED WORD: [" + strZipEntryName.replace(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES,"") + "]  \n";
                        writeToReportFile(strTextToWrite);
                        vecMessages.add(strTextToWrite);
                        }
                            
                        }
                    }//end of top if condition
                }
                
                if(count == 0){
//strTextToWrite="*********  NO IMAGE FILE DETECTED except the default one has been found for the student answer: [" + strZipFileName + "]  \n";
                    strTextToWrite="*********  NO IMAGE FILE DETECTED for the student answer: [" + strZipFileName.replace(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES,"") + "]  \n";
                    writeToReportFile(strTextToWrite);
                    writeToErrorReportFile(strTextToWrite);
                    vecMessages.add(strTextToWrite);
                    
                }
                
                if(count != 0){
                    //strTextToWrite="Number Image files found for the for the student answer: [" + strZipFileName + "]  is ["+count+"]   \n";
                    strTextToWrite="Num Image Files : [" + strZipFileName.replace(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES,"") + "]  is ["+count+"]   \n";
                    writeToReportFile(strTextToWrite);
                    vecMessages.add(strTextToWrite);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            strTextToWrite="%%%%%%%%%%  EXCEPTION WITH THE FILE : [" + strZipFilesSourceFolder.replace(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES,"") + "]  \n";
            writeToReportFile(strTextToWrite);
            writeToErrorReportFile(strTextToWrite);
            vecMessages.add(strTextToWrite);
        }
        
        mapExtractionStatus.put(strAdditionalPrefix,vecMessages);
        System.out.println("Finishing the Extraction Process ");
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
                //bufferedWriter = new BufferedWriter(new FileWriter(STR_NAME_OF_REPORT_FILE, true));
                bufferedWriter = new BufferedWriter(new FileWriter(STR_NAME_OF_REPORT_FILE, false));
            }
            bufferedWriter.write(strTextToWrite);
            bufferedWriter.write("\n");
	//bufferedWriter.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
    public static void writeToStudentwiseReport(String strTextToWrite){
	//BufferedWriter bufferedWriter = null;
        try{
            if(bufferedWriter_studentwisereport==null){
                //bufferedWriter = new BufferedWriter(new FileWriter(STR_NAME_OF_REPORT_FILE, true));
                bufferedWriter_studentwisereport = new BufferedWriter(new FileWriter(STR_NAME_OF_STUDENTWISE_REPORT_FILE, false));
            }
            bufferedWriter_studentwisereport.write(strTextToWrite);
            bufferedWriter_studentwisereport.write("\n");
	//bufferedWriter.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void writeToErrorReportFile(String strTextToWrite){
	//BufferedWriter bufferedWriter = null;
        try{
            if(bufferedWriter_ErrorReport==null){
                //bufferedWriter_ErrorReport = new BufferedWriter(new FileWriter(STR_NAME_OF_REPORT_FILE_CONTAINING_ERROR, true));
                    bufferedWriter_ErrorReport = new BufferedWriter(new FileWriter(STR_NAME_OF_REPORT_FILE_CONTAINING_ERROR, false));
            }
            bufferedWriter_ErrorReport.write(strTextToWrite);
            bufferedWriter_ErrorReport.write("\n");
	//bufferedWriter.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
   
public static void writeImagesToPDF(){
    System.out.println("************* Entering writeImagesToPDF");
     try{
         // step 1: creation of a document-object
       //Document document = new Document();
   
       // creation of the document with a certain size and certain margins
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
         //Document document = new Document(PageSize.A2, 50, 50, 50, 50);
        
        // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
            //PdfWriter.getInstance(document, new FileOutputStream(STR_NAME_OF_FOLDER_CONTAINING_OUTPUT_FILES+"/2222rawdata.pdf"));
            //RandomAccessFile rf = new RandomAccessFile(STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES+"MY Statechart Q33.dfState.wmf", "r");
            
            //PdfWriter.getInstance(document, new FileOutputStream(STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES+"ct3results.pdf"));
            PdfWriter.getInstance(document, new FileOutputStream(STR_NAME_OF_REPORT_FOLDER+"ct3results.pdf"));
            
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
               Paragraph p1 = new Paragraph("Student Answers for the Class Test 3.  ",
               FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, Color.BLUE));
               p1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p1);
            
            //document.setFooter(footerToppage);
            
            
            //document.newPage();
            
            for(int i=0;i<listFiles.length;i++){
                //String strZipFileName=listZippedFiles[i].getName() + "\n";
                String strFileName=listFiles[i].getAbsolutePath();
                
//getAbsolutePath()
                //System.out.println("strFileName: ["+strFileName+"]");
//writeToReportFile(strZipFileName);
                
                 //HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
            String strHeader= "Answer for " + strFileName.substring(strFileName.lastIndexOf('\\')+1);  
  System.out.println("strFileName: ["+strFileName+"]");
    System.out.println("strHeader: ["+strHeader+"]");
                    //strZipFilesSourceFolder.substring(strZipFilesSourceFolder.lastIndexOf('\\')+1);    
            HeaderFooter header = new HeaderFooter(new Phrase(strHeader), false);
            
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
            
            if(size>0){
            byte imext[] = new byte[size];
            rf.readFully(imext);
            //rf.close();
            
            //document.newPage();
            
           
            Image img1 = Image.getInstance(imext);
            img1.setAlignment(Image.ALIGN_CENTER);
            img1.scalePercent(50);
            //img1.setAbsolutePosition(50, 500);
            String strText="i= ["+i+"]  \n Name of the Diagram File: ["+strFileName+"]";
            //document.add(new Paragraph(strText));
           
            rf.close();
            //HeaderFooter headerFooter=new HeaderFooter();
            //headerFooter.setPageNumber(i);
            //document.setHeader(headerFooter);
            document.add(img1);
            }else{
                Paragraph p = new Paragraph("IMAGE FILE IS EMPTY ",
               FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, Color.RED));
                p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);
            }
            }else{
            //Paragraph p = new Paragraph("NOT AN IMAGE FILE. ",FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, new Color(0, 255, 255)));
               Paragraph p = new Paragraph("NOT AN IMAGE FILE. ",
               FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, Color.RED));
               p.setAlignment(Paragraph.ALIGN_CENTER);
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

public static void generateStudentwiseReport(Map mapExtractionStatus){
    Iterator itr1=mapExtractionStatus.keySet().iterator();
    while(itr1.hasNext()){
        Object key=itr1.next();
        //Object value=mapExtractionStatus.get(key);
        Vector vecMessages=(Vector)mapExtractionStatus.get(key);
        String strCompleteMessage="";
        for(int i=0;i<vecMessages.size();i++){
            String strMessage=(String)vecMessages.get(i);
            strCompleteMessage=strCompleteMessage+ "\n " +strMessage;
            
        }
        //writeToStudentwiseReport(key.toString() + ": \n\n" + strCompleteMessage);
        writeToStudentwiseReport(key.toString() + ": \t\t\t\t" + strCompleteMessage);
        }  
          
    }

//methods for plagiarism detection
public static Map getImagesOrganisedBySize(File[] listFiles){
    //this map contains a treemap, the keys are the file size, 
    //the values is a vector containing all the files that have size equal to the key value.
    Map mapFilesOrganiesdBySize=new TreeMap();
    for(int i=0;i<listFiles.length;i++){
        long sizeofFile=listFiles[i].length();
        if(mapFilesOrganiesdBySize.containsKey(sizeofFile)){
            //yes, there is already a key present with this size
            //get the vector against this key and add the listFiles[i] to it
            //Object objectValue=(Vector)mapFilesOrganiesdBySize.get(sizeofFile);
            Vector vecFiles=(Vector)mapFilesOrganiesdBySize.get(sizeofFile);
            vecFiles.add(listFiles[i]);
        }else{
        // the key is not there, create a vetor, add the listFiles[i] to it, and then put it in the map against the key value sizeofFile
            Vector vecFiles=new Vector();
            vecFiles.add(listFiles[i]);
            mapFilesOrganiesdBySize.put(sizeofFile,vecFiles);
        }
    }
    
    System.out.println("mapFilesOrganiesdBySize: ["+mapFilesOrganiesdBySize+"]");
    
    System.out.println("mapFilesOrganiesdBySize.size(): ["+mapFilesOrganiesdBySize.size()+"]");
    
    Iterator itr1=mapFilesOrganiesdBySize.keySet().iterator();
            while(itr1.hasNext()){
                Object keyValue=itr1.next();
                Vector vecFiles=(Vector)mapFilesOrganiesdBySize.get(keyValue);
                System.out.println("Cluster File Size: ["+keyValue+" bytes] \t Num of Coursework: ["+vecFiles.size()+"]");
            }
    System.out.println("List of probablePgariarismLevel1Document");
    
        Iterator itr2=mapFilesOrganiesdBySize.keySet().iterator();
            while(itr2.hasNext()){
                Object keyValue=itr2.next();
                Vector vecFiles=(Vector)mapFilesOrganiesdBySize.get(keyValue);
                if(vecFiles.size()>1){
                System.out.println("*******  Cluster File Size: ["+keyValue+" bytes] \t Num of Coursework: ["+vecFiles.size()+"]");
                System.out.println("Names of Files in this cluster");
                for(int i=0;i<vecFiles.size();i++){
                    String strNameOfFile=((File)vecFiles.get(i)).getName();
                    System.out.println(strNameOfFile.replace(STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES,""));
                }
                
                }
            }
        
    return mapFilesOrganiesdBySize;
}

//public static void writeImagesToPDF(Map mapFilesOrganiesdBySize){
public static void writeImagesToPDFInOrganisedManner(){
    System.out.println("************* Entering writeImagesToPDF(Map mapFilesOrganiesdBySize)");
     try{
         // step 1: creation of a document-object
       //Document document = new Document();
   
       // creation of the document with a certain size and certain margins
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        Document probablePgariarismLevel1Document = new Document(PageSize.A4, 50, 50, 50, 50);
         //Document document = new Document(PageSize.A2, 50, 50, 50, 50);
        
        // step 2:
            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            
            //PdfWriter.getInstance(document, new FileOutputStream(STR_NAME_OF_FOLDER_CONTAINING_OUTPUT_FILES+"/2222rawdata.pdf"));
            //RandomAccessFile rf = new RandomAccessFile(STR_NAME_OF_FOLDER_CONTAINING_SOURCE_FILES+"MY Statechart Q33.dfState.wmf", "r");
            
            //PdfWriter.getInstance(document, new FileOutputStream(STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES+"ct3results.pdf"));
            PdfWriter.getInstance(document, new FileOutputStream(STR_NAME_OF_REPORT_FOLDER+"ct3results.pdf"));
            PdfWriter.getInstance(probablePgariarismLevel1Document, new FileOutputStream(STR_NAME_OF_REPORT_FOLDER+"probablePgariarismLevel1Document.pdf"));
            
            
            // step 3: we open the document
            document.open();
            probablePgariarismLevel1Document.open();
            //document.newPage();
            
            File baseDir1=new File(STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES);
            File[] listFiles=baseDir1.listFiles();
            String strTextToWrite= "";
            int totalPageCount=listFiles.length+1;
            
            Map mapFilesOrganiesdBySize=getImagesOrganisedBySize(listFiles);
            
             //HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
            //HeaderFooter headerToppage = new HeaderFooter(new Phrase("Student Answers  "), false);
            
            //HeaderFooter footerToppage = new HeaderFooter(new Phrase("Page " ), new Phrase(" of " + totalPageCount +  "."));
            //header.setPageNumber(i);
            
            //document.setHeader(headerToppage);
                    /*
               Paragraph p1 = new Paragraph("Student Answers for the Class Test 3.  ",
               FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, Color.BLUE));
               p1.setAlignment(Paragraph.ALIGN_CENTER);
               */
       
            String strParagraphText="The files have been arranged in clusters, each cluster representing a particular file size. " + 
                    "All the files in a single cluster have exactly the same size to the last byte. The clusters have been arranged in increasing order of file size.";
               Paragraph p1 = new Paragraph(strParagraphText,
               FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, Color.BLUE));
               p1.setAlignment(Paragraph.ALIGN_CENTER);
               
               
                document.add(p1);
                
               //Paragraph pProbablePgariarismLevel1 = new Paragraph("probablePgariarismLevel1 Student Answers for the Class Test 3.  ",
               String strProbablePgariarismLevel1="High Plagiarism Probability Cases Level 1  \n"+" This file contains only those clusters that have more than one file in them. " +
                       "All the files in a single cluster have exactly the same size to the last byte. So the key idea is that if two answers have exactly the" +
                       "same size to the last bite, then the probability of plagiarism is high. ";
               Paragraph pProbablePgariarismLevel1 = new Paragraph("\n" + strProbablePgariarismLevel1,
               FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLDITALIC, Color.BLUE));
               pProbablePgariarismLevel1.setAlignment(Paragraph.ALIGN_CENTER);
                probablePgariarismLevel1Document.add(pProbablePgariarismLevel1);
                
                
            
            //document.setFooter(footerToppage);
            
            //increase the number of pages by the number of cluster because while starting each new cluser a page will be displayed with the 
            // siuze that the cluster is representing and the number of coursework in the respective cluster    
            totalPageCount=mapFilesOrganiesdBySize.size();
            
            //document.newPage();
            Iterator itr1=mapFilesOrganiesdBySize.keySet().iterator();
            while(itr1.hasNext()){
                Object keyValue=itr1.next();
                Vector vetFiles=(Vector)mapFilesOrganiesdBySize.get(keyValue);
                String strAdditionalText="";
                strAdditionalText="This belongs to the cluster with File size: ["+keyValue+" bytes]";
                //writeTextInNewPageToPDF(Document document, String strText, int totalPageCount, String strAdditionalText){
                String strText="Starting a New Cluster. The cluster details are as follows";
                strText= strText + "\n" + "File Size : ["+keyValue+" bytes]";
                strText=strText + "\n" + "Num files in the cluster: ["+vetFiles.size()+"]";
                strAdditionalText="File Size: ["+keyValue+" bytes]";
                writeTextInNewPageToPDF(document, strText, totalPageCount, strAdditionalText);
                if(vetFiles.size()>1){
                writeTextInNewPageToPDF(probablePgariarismLevel1Document, strText, totalPageCount, strAdditionalText);    
                }
                for(int i=0;i<vetFiles.size();i++){
                    File file=(File)vetFiles.get(i);
                    writeFiletoPDF(document, file, totalPageCount, strAdditionalText);
                    //do not write to the probablePgariarismLevel1Document for the first cluster for now that is for i=0
                    //because it has 23 images and all are empty and we want to deliver bit clener pdf file at the moment to save lecturer's time
                    
                    if(vetFiles.size()>1){
                        writeFiletoPDF(probablePgariarismLevel1Document, file, totalPageCount, strAdditionalText);
                    }
                    
                }
            }
            
            /*
            for(int i=0;i<listFiles.length;i++){
                //String strZipFileName=listZippedFiles[i].getName() + "\n";
                String strFileName=listFiles[i].getAbsolutePath();
                
//getAbsolutePath()
                //System.out.println("strFileName: ["+strFileName+"]");
//writeToReportFile(strZipFileName);
                
                 //HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
            String strHeader= "Answer for " + strFileName.substring(strFileName.lastIndexOf('\\')+1);  
  System.out.println("strFileName: ["+strFileName+"]");
    System.out.println("strHeader: ["+strHeader+"]");
                    //strZipFilesSourceFolder.substring(strZipFilesSourceFolder.lastIndexOf('\\')+1);    
            HeaderFooter header = new HeaderFooter(new Phrase(strHeader), false);
            
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
            
            if(size>0){
            byte imext[] = new byte[size];
            rf.readFully(imext);
            //rf.close();
            
            //document.newPage();
            
           
            Image img1 = Image.getInstance(imext);
            img1.setAlignment(Image.ALIGN_CENTER);
            img1.scalePercent(50);
            //img1.setAbsolutePosition(50, 500);
            String strText="i= ["+i+"]  \n Name of the Diagram File: ["+strFileName+"]";
            //document.add(new Paragraph(strText));
           
            rf.close();
            //HeaderFooter headerFooter=new HeaderFooter();
            //headerFooter.setPageNumber(i);
            //document.setHeader(headerFooter);
            document.add(img1);
            }else{
                Paragraph p = new Paragraph("IMAGE FILE IS EMPTY ",
               FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, Color.RED));
                p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);
            }
            }else{
            //Paragraph p = new Paragraph("NOT AN IMAGE FILE. ",FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, new Color(0, 255, 255)));
               Paragraph p = new Paragraph("NOT AN IMAGE FILE. ",
               FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, Color.RED));
               p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);
            }
            }
        */    
        // step 5: we close the document
        document.close();
        probablePgariarismLevel1Document.close();

        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        System.out.println("************* Exiting writeImagesToPDF(Map mapFilesOrganiesdBySize)");
}

public static void writeFiletoPDF(Document document, File file, int totalPageCount, String strAdditionalText){
    try{
    //for(int i=0;i<listFiles.length;i++){
                //String strZipFileName=listZippedFiles[i].getName() + "\n";
                //String strFileName=listFiles[i].getAbsolutePath();
                String strFileName=file.getAbsolutePath();
                
                
//getAbsolutePath()
                //System.out.println("strFileName: ["+strFileName+"]");
//writeToReportFile(strZipFileName);
                
                 //HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
                String strHeader=strAdditionalText + "\n";
            strHeader= strHeader + "Answer for " + strFileName.substring(strFileName.lastIndexOf('\\')+1);  
  System.out.println("strFileName: ["+strFileName+"]");
    System.out.println("strHeader: ["+strHeader+"]");
                    //strZipFilesSourceFolder.substring(strZipFilesSourceFolder.lastIndexOf('\\')+1);    
            HeaderFooter header = new HeaderFooter(new Phrase(strHeader), false);
            
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
            
            if(size>0){
            byte imext[] = new byte[size];
            rf.readFully(imext);
            //rf.close();
            
            //document.newPage();
            
           
            Image img1 = Image.getInstance(imext);
            img1.setAlignment(Image.ALIGN_CENTER);
            img1.scalePercent(50);
            //img1.setAbsolutePosition(50, 500);
            //String strText="i= ["+i+"]  \n Name of the Diagram File: ["+strFileName+"]";
            //document.add(new Paragraph(strText));
           
            rf.close();
            //HeaderFooter headerFooter=new HeaderFooter();
            //headerFooter.setPageNumber(i);
            //document.setHeader(headerFooter);
            document.add(img1);
            }else{
                Paragraph p = new Paragraph("IMAGE FILE IS EMPTY ",
               FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, Color.RED));
                p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);
            }
            }else{
            //Paragraph p = new Paragraph("NOT AN IMAGE FILE. ",FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, new Color(0, 255, 255)));
               Paragraph p = new Paragraph("NOT AN IMAGE FILE. ",
               FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, Color.RED));
               p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);
            }
            //}
    }catch(Exception ex){
        ex.printStackTrace();
    }
}

public static void writeTextInNewPageToPDF(Document document, String strText, int totalPageCount, String strAdditionalText){
    try{
            String strHeader=strAdditionalText + "\n";
            
            HeaderFooter header = new HeaderFooter(new Phrase(strHeader), false);
            
            HeaderFooter footer = new HeaderFooter(new Phrase("Page " ), new Phrase(" of " + totalPageCount +  "."));
            //header.setPageNumber(i);
            
            document.setHeader(header);
            document.setFooter(footer);
            document.newPage();
            
            //Paragraph p = new Paragraph("NOT AN IMAGE FILE. ",FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, new Color(0, 255, 255)));
               Paragraph p = new Paragraph(strText,
               FontFactory.getFont(FontFactory.HELVETICA, 32, Font.BOLDITALIC, Color.GREEN));
               p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);
    }catch(Exception ex){
        ex.printStackTrace();
    }
}

}