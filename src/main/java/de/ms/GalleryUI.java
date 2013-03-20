package de.ms;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import de.ms.gallery.view.GalleryFactory;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
@Theme("testTheme")
public class GalleryUI extends UI
{
    @Override
    protected void init(VaadinRequest request) 
    {
    	final GalleryFactory factory = new GalleryFactory();
    	
    	setContent( factory.build() );
    }
}