package de.ms.gallery.factories;

import java.io.File;
import java.util.ArrayList;

import com.vaadin.ui.Upload;

import de.ms.gallery.support.ImageResizer;
import de.ms.gallery.support.ImageUploader;
import de.ms.gallery.view.Collection;
import de.ms.gallery.view.CollectionContainer;
import de.ms.gallery.view.Overview.CreateCollectionEvent;
import de.ms.gallery.view.Overview.CreateCollectionListener;

@SuppressWarnings("serial")
public class CollectionFactory
{
	private final String path = System.getProperty("user.home") + "/uploads";

	private Upload upload;
	private Collection collection;
	
	private ImageUploader uploader;
	private ImageResizer resizer;
	
	File[] content;
	
	public CollectionFactory( File[] content )
	{
		this.content = content;
	}
	
	public ArrayList<CollectionContainer> buildCollections()
	{
		
		ArrayList<CollectionContainer> collections = new ArrayList<CollectionContainer>();
		
		for( File file: content )
		{
			if( file.isDirectory() )
			{
				collections.add( buildCollectionContainer(file) );
			}
		}
		
		return collections;
	}

	public CollectionContainer createCollection( String name )
	{
		File file = new File( path + "/" + name );
		file.mkdir();
		
		new File(path + "/" + name + "/img").mkdir();
		new File(path + "/" + name + "/thumbs").mkdir();
		
		return buildCollectionContainer(file);
	}
	
	private CollectionContainer buildCollectionContainer( File file )
	{
		uploader = new ImageUploader( file.getAbsolutePath() ); 
		resizer = new ImageResizer( file.getAbsolutePath() );
		
		collection = new Collection( file.getAbsolutePath() + "/thumbs" );
		upload = new Upload("", uploader);
		
		CollectionContainer container = new CollectionContainer( file.getName(), collection, upload );
		
		upload.addSucceededListener(resizer);
		upload.addStartedListener(container);
		upload.addProgressListener(container);
		upload.addFinishedListener(container);
		
		return container;
	}
}
