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

import java.io.*;
import java.util.zip.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class backupofStudentAnswerExtractor{ 
//public static String STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/compressed Answers";
//public static String STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/decompressed Answers/";
    
    public static String STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES="C:/umlassessment/studentsanswers";
    public static String STR_NAME_OF_FOLDER_TO_STORE_EXTRACTED_FILES="C:/umlassessment/decompressedanswers/";
    
//	public static String STR_NAME_OF_REPORT_FILE="C:/SoftwareDev/0001_A_Brunel Electronic Assessment Software/Students Answers/studentsextractorReport.txt";
    
    public static String STR_NAME_OF_REPORT_FILE="C:/umlassessment/studentsanswers/studentsextractorReport.txt";
    
    private static BufferedWriter bufferedWriter = null;
//new BufferedWriter(new FileWriter(STR_NAME_OF_REPORT_FILE));
    
    public static String STR_NAME_OF_FILE_TO_EXTRACT="";
    public static String STR_NAME_TO_APPEND_TO_THE_EXTRACTED_FILE="";
    
    public static String STR_DESIRED_FILE_EXTENSION=".wmf";
    public static String STR_FILE_EXTENSION_TO_BE_EXCLUDED="default.dfPackage.wmf";
    
    public static String STR_ZIP_FILE_EXTENSION=".zip";
    
    
    public static void main(String[] args) throws Exception {
    /*
    */    
     //extractStudentsAnswers(String strBaseFolderPath);
     //   
     
    }
    
    
    public static void main_1(String[] args) throws Exception {
        try{
            File baseDir1=new File(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES);
            File[] 	listZippedFiles=baseDir1.listFiles();
            String strTextToWrite= "";
            for(int i=0;i<listZippedFiles.length;i++){
//String strZipFileName=listZippedFiles[i].getName() + "\n";
                String strZipFileName=listZippedFiles[i].getAbsolutePath();
//getAbsolutePath()
                System.out.println(strZipFileName);
//writeToReportFile(strZipFileName);
                extractStudentAnswers(strZipFileName);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
//extractStudentAnswers();
//extractStudentAnswers(STR_NAME_OF_FOLDER_CONTAINING_ZIPFILES);
        try{
            bufferedWriter.close();
        }catch(Exception ex){
            ex.printStackTrace();
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
}
