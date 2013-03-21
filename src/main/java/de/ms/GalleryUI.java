package de.ms;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import de.ms.gallery.factories.CollectionFactory;
import de.ms.gallery.factories.OverviewFactory;
import de.ms.gallery.view.CollectionContainer;
import de.ms.gallery.view.Overview.CreateCollectionEvent;
import de.ms.gallery.view.Overview.CreateCollectionListener;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class GalleryUI extends UI implements CreateCollectionListener
{
	public final String path = System.getProperty("user.home") + "/uploads";
	public final String OVERVIEW = "overview";
	
	public Navigator navigator;

	final File rootFolder = new File(path);

	OverviewFactory pFactory;
	CollectionFactory cFactory;
	
    @Override
    protected void init(VaadinRequest request) 
    {
    	getPage().setTitle("Dana & Mila");
    	
    	File[] rootFolderContent = rootFolder.listFiles();
    	
    	pFactory = new OverviewFactory( rootFolderContent );
    	cFactory = new CollectionFactory( rootFolderContent );
    	
    	navigator = new Navigator( this, this);
    	
    	navigator.addView( "", pFactory.buildOverview() );
    	
    	ArrayList<CollectionContainer> collectionViews = cFactory.buildCollections();
    	
    	Iterator<CollectionContainer> iterator = collectionViews.iterator();
    	
    	while( iterator.hasNext() )
    	{
    		CollectionContainer view = iterator.next();
    		navigator.addView( view.getName(), view);
    	}
    }

	@Override
	public void createCollection(CreateCollectionEvent event)
	{
		navigator.addView( event.getName(), cFactory.createCollection( event.getName() ) );
	}
}