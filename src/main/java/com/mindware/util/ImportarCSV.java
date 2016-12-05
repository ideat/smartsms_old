package com.mindware.util;

import com.csvreader.CsvReader;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;

import java.io.*;
import java.util.Date;



@SuppressWarnings("serial")
public class ImportarCSV implements Upload.Receiver, Upload.SucceededListener{
	private Upload csvUpload;
    private File tempFile;
    
	public ImportarCSV() {
		this.csvUpload = new Upload("Cargar archivo:", this);
        this.csvUpload.addSucceededListener(this);	
        
	}
		
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		/*try {
            File destinationFile = new File("c:/" + event.getFilename());
            FileUtils.moveFile(tempFile, destinationFile);
           
        } catch (IOException e) {
            e.printStackTrace();
        }*/
		
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		try {
            //tempFile = File.createTempFile(filename, "csv");
            tempFile = new File(System.getProperty("java.io.tmpdir") , filename);
            tempFile.deleteOnExit();
            return new FileOutputStream(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
	}
	
	public Integer NumeroColumnasCSV(char separador){
		
		try {
			CsvReader file = new CsvReader(tempFile.getName(),separador);
			Integer i = file.getColumnCount();
			return i;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void cargarContactos(char separador) {
		java.util.Date fecha = new Date();
		try {
			CsvReader file = new CsvReader(tempFile.getName(),separador);
			//file.getHeaders();
			while (file.readRecord()) {
				String nombre = file.get(0);
				String fecha_importacion = fecha.toString();
				String celular = file.get(2);
				String campo1 = file.get(1);
				String campo2 = file.get(3);
				
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
