package de.ms.gallery.view;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

@SuppressWarnings("serial")
public class Collection extends CustomComponent implements SucceededListener
{
	private final GridLayout layout = new GridLayout( 4, 1 );

	private String path;
	
	public Collection( String path )
	{
		this.path = path;
		
		layout.setSpacing(true);
		layout.setMargin(true);
		
        setCompositionRoot( layout );
		
		refreshCollection();
	}
	
	private void refreshCollection()
	{
		layout.removeAllComponents();
		
		File folder = new File(path);
		
		File[] listOfFiles = folder.listFiles();
		
		for( File file: listOfFiles )
		{
			if( file.getName().endsWith("JPG") || file.getName().endsWith("jpg") )
			{
				layout.addComponent( getImageFromFile( file ) );
			}
		}
	}
	
	private Image getImageFromFile( File file )
	{
		Image image = new Image();
		image.setSource(new FileResource(file));
		
		return image;
	}
	
	@Override
	public void uploadSucceeded(SucceededEvent event)
	{
		//TODO possible race conditions with resizer?!? File could not be written when accessed here
		File file = new File( path+"/tbn_"+event.getFilename() ); //TODO put in listOfFiles
		
		layout.addComponent( getImageFromFile( file ) );
	}
}
