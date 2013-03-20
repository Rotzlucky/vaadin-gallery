package de.ms.gallery.view;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class CollectionContainer extends VerticalLayout implements StartedListener, ProgressListener, FinishedListener
{
	String name;
	Collection collection;
	Upload upload;
	ProgressIndicator progress;
	Button close = new Button("X");
	
	public CollectionContainer( String name, Collection collection, Upload upload, ProgressIndicator progress )
	{
		this.name = name;
		this.collection = collection;
		this.upload = upload;
		this.progress = progress;
		
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
				fireEvent( new CollectionContainer.CloseCollectionEvent(UI.getCurrent()));
			}
		});
        
        addComponent(close);
        addComponent( collection );
        addComponent( upload );
        addComponent( progress );
        
        setMargin( true );
        setSpacing( true );
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
	
	///////////////////////////////////
	// Preview Events
	///////////////////////////////////
	
	private static final Method CLOSE_COLLECTION_METHOD;
	
	static 
	{
        try 
        {
        	CLOSE_COLLECTION_METHOD = CloseCollectionListener.class.getDeclaredMethod("closeCollection", new Class[] { CloseCollectionEvent.class });
        } 
        catch (final java.lang.NoSuchMethodException e) 
        {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error finding methods in CollectionContainer");
        }
    }
	
    /**
     * CollectionContainer.CloseCollectionEvent event is sent when the close Button is clicked.
     */
	public static class CloseCollectionEvent extends Component.Event
	{
		public CloseCollectionEvent(Component source)
		{
			super(source);
		}
	}
	
	///////////////////////////////////
	// Preview Listener
	///////////////////////////////////
	
	/**
     * Receives the events when the close Button is clicked.
     */
    public interface CloseCollectionListener extends Serializable 
    {
        public void closeCollection(CloseCollectionEvent event);
    }
	
	public void addCloseCollectionListener(CloseCollectionListener listener) 
	{
        addListener(CloseCollectionEvent.class, listener, CLOSE_COLLECTION_METHOD);
    }
	
	public void removeCloseCollectionListener(CloseCollectionListener listener) 
	{
        removeListener(CloseCollectionEvent.class, listener, CLOSE_COLLECTION_METHOD);
    }
}
