package de.ms.gallery.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.VerticalLayout;

import de.ms.GalleryUI;

@SuppressWarnings("serial")
public class CollectionContainer extends VerticalLayout implements View, StartedListener, ProgressListener, FinishedListener
{
	Collection collection;
	Upload upload;
	ProgressIndicator progress;
	Button close = new Button("X");

	public CollectionContainer( String name, Collection collection, Upload upload )
	{
		this.name = name;
		this.collection = collection;
		this.upload = upload;
		
		progress = new ProgressIndicator();

		upload.setButtonCaption("Datei hochladen");
		upload.setImmediate(true);
		upload.addSucceededListener( collection );

		progress.setCaption( "Progress" );
		progress.setVisible( false );

		close.addClickListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				( (GalleryUI) UI.getCurrent() ).navigator.navigateTo( "" );
			}
		});

		addComponent( close );
		addComponent( collection );
		addComponent( upload );
		addComponent( progress );

		setMargin( true );
		setSpacing( true );
	}

	//////////////////////////
	// Properties
	//////////////////////////
	
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}


	///////////////////////////
	// interface methods
	///////////////////////////

	@Override
	public void enter(ViewChangeEvent event)
	{
		// TODO Auto-generated method stub

	}
	
	@Override
	public void uploadStarted(StartedEvent event)
	{
		progress.setValue(0F);
		progress.setVisible(true);
		progress.setPollingInterval(500);
	}

	@Override
	public void updateProgress(long readBytes, long contentLength)
	{
		progress.setValue( new Float( readBytes / (float) contentLength ) );
	}

	@Override
	public void uploadFinished(FinishedEvent event)
	{
		progress.setVisible(false);
	}
}
