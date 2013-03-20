package de.ms.gallery.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
//TODO change package
public class ImageUploader implements Receiver
{
	private static final long serialVersionUID = -1276759102490466761L;

	private String basePath;
	
	public ImageUploader( String basePath )
	{
		this.basePath = basePath;
	}
	
	@Override
	public OutputStream receiveUpload(String filename, String mimeType)
	{
		// Create upload stream
		FileOutputStream fos = null; // Stream to write to
		try 
		{
			// Open the file for writing.
			File file = new File( basePath+"/"+filename);
			fos = new FileOutputStream(file);
		} 
		catch (final java.io.FileNotFoundException e) 
		{
			new Notification( "Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE ).show(Page.getCurrent());
			
			return null;
		}
		
		return fos; // Return the output stream to write to
	}
}