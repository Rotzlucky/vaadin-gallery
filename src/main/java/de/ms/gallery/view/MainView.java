package de.ms.gallery.view;

import java.util.Map;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

import de.ms.gallery.view.CollectionContainer.CloseCollectionEvent;
import de.ms.gallery.view.CollectionContainer.CloseCollectionListener;
import de.ms.gallery.view.CollectionPreview.OpenCollectionEvent;
import de.ms.gallery.view.CollectionPreview.OpenCollectionListener;

@SuppressWarnings("serial")
public class MainView extends CustomComponent implements OpenCollectionListener, CloseCollectionListener
{
	Map<String, AbstractComponent> contentMap;
	String firstView;
	
	VerticalLayout layout = new VerticalLayout();
	
	public MainView( Map<String, AbstractComponent> contentMap, String firstView )
	{
		this.contentMap = contentMap;
		this.firstView = firstView;
		
		layout.setMargin(true);
		
		layout.addComponent( contentMap.get( firstView ) );
		
		setCompositionRoot(layout);
	}
	
	public void openCollection( OpenCollectionEvent event )
	{
		layout.removeAllComponents();
		layout.addComponent( contentMap.get( event.getName() ) );
	}

	@Override
	public void closeCollection(CloseCollectionEvent event)
	{
		layout.removeAllComponents();
		layout.addComponent( contentMap.get( firstView ) );
	}
}
