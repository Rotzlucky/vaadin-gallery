package de.ms.gallery.view;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class CollectionPreview extends CustomComponent
{
	private final VerticalLayout layout = new VerticalLayout();
	
	public String name;
	
	public CollectionPreview( String name )
	{
		this.name = name;
		
		layout.setSpacing(true);
		layout.setMargin(true);
		
		final TextField collectionLabel = new TextField();
		layout.addComponent(collectionLabel);
		
		Button createCollection = new Button( "Neu" );
		createCollection.addClickListener( new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				if(collectionLabel.getValue().length() > 0)
				{
					fireEvent( new CreateCollectionEvent( UI.getCurrent(), collectionLabel.getValue()));
					collectionLabel.setValue("");
				}
			}
		});
		
		layout.addComponent(createCollection);
		
        setCompositionRoot( layout );
	}
	
	public void addPreviewButton( Button btn )
	{
		Button previewButton = btn;
		previewButton.addClickListener( new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				fireEvent( new CollectionPreview.OpenCollectionEvent( UI.getCurrent(), event.getButton().getCaption() ) );
			}
		});
		
		layout.addComponent(previewButton);
	}
	
	
	///////////////////////////////////
	// Preview Events
	///////////////////////////////////
	
	private static final Method CREATE_COLLECTION_METHOD;
	private static final Method OPEN_COLLECTION_METHOD;
	
	static 
	{
        try 
        {
        	CREATE_COLLECTION_METHOD = CreateCollectionListener.class.getDeclaredMethod("createCollection", new Class[] { CreateCollectionEvent.class });
        	OPEN_COLLECTION_METHOD = OpenCollectionListener.class.getDeclaredMethod("openCollection", new Class[] { OpenCollectionEvent.class });
        } 
        catch (final java.lang.NoSuchMethodException e) 
        {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error finding methods in CollectionPreview");
        }
    }
	
    /**
     * CollectionPreview.CreateCollectionEvent event is sent when the createCollection Button is clicked.
     */
	public static class CreateCollectionEvent extends Component.Event
	{
		private String name;
		
		public CreateCollectionEvent(Component source, String name)
		{
			super(source);
			this.name = name;
		}
		
		public String getName()
		{
			return name;
		}
	}
	
	/**
     * CollectionPreview.OpenCollectionEvent event is sent when an existing Collection is selected.
     */
	public static class OpenCollectionEvent extends Component.Event 
	{
		private String name;
		
		public OpenCollectionEvent(Component source, String name)
		{
			super(source);
			this.name = name;
		}
		
		public String getName()
		{
			return name;
		}
	}
	
	///////////////////////////////////
	// Preview Listener
	///////////////////////////////////
	
	/**
     * Receives the events when the createCollection Button is clicked.
     */
    public interface CreateCollectionListener extends Serializable 
    {
        public void createCollection(CreateCollectionEvent event);
    }
	
	public void addCreateCollectionListener(CreateCollectionListener listener) 
	{
        addListener(CreateCollectionEvent.class, listener, CREATE_COLLECTION_METHOD);
    }
	
	public void removeCreateCollectionListener(CreateCollectionListener listener) 
	{
        removeListener(CreateCollectionEvent.class, listener, CREATE_COLLECTION_METHOD);
    }
	
	
    /**
     * Receives the events when a Collection is selected.
     */
    public interface OpenCollectionListener extends Serializable 
    {
        public void openCollection(OpenCollectionEvent event);
    }
	
	public void addOpenCollectionListener(OpenCollectionListener listener) 
	{
        addListener(OpenCollectionEvent.class, listener, OPEN_COLLECTION_METHOD);
    }
	
	public void removeOpenCollectionListener(OpenCollectionListener listener) 
	{
        removeListener(OpenCollectionEvent.class, listener, OPEN_COLLECTION_METHOD);
    }
}