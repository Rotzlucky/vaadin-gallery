package de.ms.gallery.factories;

import java.io.File;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

import de.ms.GalleryUI;
import de.ms.gallery.view.Overview;
import de.ms.gallery.view.Overview.CreateCollectionEvent;
import de.ms.gallery.view.Overview.CreateCollectionListener;

public class OverviewFactory implements CreateCollectionListener
{
	Overview overview;
	
	File[] content;
	
	public OverviewFactory( File[] content )
	{
		this.content = content;
	}
	
	public View buildOverview()
	{
		overview = new Overview();
		
		for( File file: content )
		{
			if( file.isDirectory() )
			{
				overview.addPreviewButton( new Button( file.getName() ) );
			}
		}
		
		overview.addCreateCollectionListener( this );
		overview.addCreateCollectionListener( (GalleryUI) UI.getCurrent() );
		
		return overview;
	}

	@Override
	public void createCollection(CreateCollectionEvent event)
	{
		overview.addPreviewButton( new Button( event.getName() ) );
	}
}
