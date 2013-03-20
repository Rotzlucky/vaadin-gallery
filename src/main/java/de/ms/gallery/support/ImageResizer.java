package de.ms.gallery.support;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import com.vaadin.server.AbstractClientConnector;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

@SuppressWarnings("serial")
public class ImageResizer extends AbstractClientConnector implements SucceededListener
{
	private String basePath;
	
	public ImageResizer( String basePath )
	{
		this.basePath = basePath;
	}

	@Override
	public void uploadSucceeded(SucceededEvent event)
	{
		try
		{
			BufferedImage image = ImageIO.read( new File(basePath+"/"+event.getFilename() ) );
			
			makeThumb( image, event.getFilename() );
			resize( image, event.getFilename() );
			
		}
		catch( IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void makeThumb( BufferedImage img, String fileName )
	{
		BufferedImage thumb = Scalr.resize( img, 200 );
		
		try 
		{
			ImageIO.write(thumb, "jpeg", new File(basePath+"/thumbs/tbn_"+fileName));
		}
		catch( IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void resize( BufferedImage img, String fileName )
	{
		BufferedImage thumb = Scalr.resize( img, 800 );
		
		try 
		{
			ImageIO.write(thumb, "jpeg", new File(basePath+"/img/"+fileName));
		}
		catch( IOException e)
		{
			e.printStackTrace();
		}
	}
}
