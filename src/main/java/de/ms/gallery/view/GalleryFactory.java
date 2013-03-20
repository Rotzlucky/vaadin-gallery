package de.ms.gallery.view;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Upload;

import de.ms.gallery.support.ImageResizer;
import de.ms.gallery.support.ImageUploader;
import de.ms.gallery.view.CollectionPreview.CreateCollectionEvent;
import de.ms.gallery.view.CollectionPreview.CreateCollectionListener;

@SuppressWarnings("serial")
public class GalleryFactory implements CreateCollectionListener
{
	Map<String, AbstractComponent> contentMap = new HashMap<String, AbstractComponent>();

	MainView mainView;
	
	CollectionPreview preview;
	
	private Upload upload;
	private ProgressIndicator progress;
	private Collection collection;
	
	private CollectionContainer container;
	
	private ImageUploader uploader;
	private ImageResizer resizer;
	
	private final String path = System.getProperty("user.home") + "/uploads";
	final File rootFolder = new File(path);
	
	public CustomComponent build()
	{
		preview = new CollectionPreview( "preview" );
		preview.addCreateCollectionListener(this);
		
		contentMap.put( preview.name, preview );
		mainView = new MainView( contentMap, preview.name );
		preview.addOpenCollectionListener( mainView );
		
		File[] rootFolderContent = rootFolder.listFiles();
		
		for( File file: rootFolderContent )
		{
			if( file.isDirectory() )
			{
				buildCollectionContainer(file);
			}
		}
		
		return mainView;
	}

	@Override
	public void createCollection(CreateCollectionEvent event)
	{
		File file = new File(path + "/" + event.getName());
		file.mkdir();
		
		new File(path + "/" + event.getName() + "/img").mkdir();
		new File(path + "/" + event.getName() + "/thumbs").mkdir();
		
		contentMap.clear();
		contentMap.put( preview.name, preview );
		
		buildCollectionContainer(file);
	}
	
	private void buildCollectionContainer( File file )
	{
		Button btn = new Button(file.getName());
		preview.addPreviewButton(btn);
		
		uploader = new ImageUploader( file.getAbsolutePath() ); 
		resizer = new ImageResizer( file.getAbsolutePath() );
		
		collection = new Collection( file.getAbsolutePath() + "/thumbs" );
		upload = new Upload("", uploader);
		progress = new ProgressIndicator();
		
		container = new CollectionContainer( file.getName(), collection, upload, progress );
		container.addCloseCollectionListener( mainView );
		
		upload.addSucceededListener(resizer);
		upload.addStartedListener(container);
		upload.addProgressListener(container);
		upload.addFinishedListener(container);
		
		contentMap.put( container.name, container );
	}
}
