package de.ms.gallery.view;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import de.ms.GalleryUI;

@SuppressWarnings("serial")
public class Overview extends CustomComponent implements View
{
	private final VerticalLayout layout = new VerticalLayout();
	
	public Overview()
	{
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

	@Override
	public void enter(ViewChangeEvent event)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void addPreviewButton( Button btn )
	{
		Button previewButton = btn;
		previewButton.addClickListener( new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				( (GalleryUI) UI.getCurrent() ).navigator.navigateTo(event.getButton().getCaption());
			}
		});
		
		layout.addComponent(previewButton);
	}
	
	
	///////////////////////////////////
	// Preview Events
	///////////////////////////////////
	
	private static final Method CREATE_COLLECTION_METHOD;
	
	static 
	{
        try 
        {
        	CREATE_COLLECTION_METHOD = CreateCollectionListener.class.getDeclaredMethod("createCollection", new Class[] { CreateCollectionEvent.class });
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
}