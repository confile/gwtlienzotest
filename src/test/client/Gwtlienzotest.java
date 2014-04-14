package test.client;

import java.util.concurrent.TimeUnit;

import org.michaelgorski.kinetic.KColor;
import org.michaelgorski.kinetic.KImage;
import org.michaelgorski.kinetic.KLayer;
import org.michaelgorski.kinetic.KRectangle;
import org.michaelgorski.kinetic.KStage;
import org.michaelgorski.kinetic.Kinetic;

import com.emitrom.lienzo.client.core.image.PictureLoadedHandler;
import com.emitrom.lienzo.client.core.shape.Layer;
import com.emitrom.lienzo.client.core.shape.Picture;
import com.emitrom.lienzo.client.core.shape.Rectangle;
import com.emitrom.lienzo.client.widget.LienzoPanel;
import com.google.common.base.Stopwatch;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class Gwtlienzotest implements EntryPoint {
	
	Stopwatch stopwatch;
	
	LienzoPanel lienzoPanel;
	Layer layer;
	
	KStage kStage;
	KLayer kLayer;
	
	
	public void onModuleLoad() {
		
		final FileUpload fileUpload = new FileUpload();
		FlowPanel canvasPanel = new FlowPanel();
		RootPanel.get().add(fileUpload);
		RootPanel.get().add(canvasPanel);
				
		// set stage
		setLienzoStage(canvasPanel, Window.getClientWidth(), Window.getClientHeight()- 50);
//		setKineticStage(canvasPanel, Window.getClientWidth(), Window.getClientHeight()- 50);
		
		final Image image = new Image();
		
		fileUpload.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				startTimer();
				
				
				getSelectedFile(image.getElement(), fileUpload.getElement(), new Callback<String, String>() {
					
					@Override
					public void onSuccess(String imageUrl) {
						
						setLienzoImage(imageUrl);
//						setKineticImage(imageUrl);
						
					}
					
					@Override
					public void onFailure(String reason) {}
				});
			}
		});
		
		
	}
		
	
	private void setLienzoStage(FlowPanel canvasPanel, int width, int height) {
		lienzoPanel = new LienzoPanel(width, height);
		canvasPanel.add(lienzoPanel);
		
		layer = new Layer();
		Rectangle stageBackground = new Rectangle(width, height);
		stageBackground.setFillColor("yellow");
		
		layer.add(stageBackground);
		lienzoPanel.add(layer);
		layer.draw();
	}
	
	
	private void setLienzoImage(String imageUrl) {
		Picture backgroundImage = new Picture(imageUrl, true);
		backgroundImage.onLoad(new PictureLoadedHandler() {
			
			@Override
			public void onPictureLoaded(Picture picture) {
				GWT.log("image loaded");
				
				layer.add(picture);
				layer.draw();
				stopTimer();
			}
		});
	}
	
	
	private void setKineticStage(FlowPanel canvasPanel, int width, int height) {
		kStage = Kinetic.createStage(canvasPanel.getElement(), width, height);
		kLayer = Kinetic.createLayer();
		
		KRectangle stageBackground = Kinetic.createRectangle();
		stageBackground.setWidth(width);
		stageBackground.setHeight(height);
		stageBackground.setFill(new KColor("lime"));
		kLayer.add(stageBackground);
		kStage.add(kLayer);
		kLayer.draw();
	}
	
	
	private void setKineticImage(String imageUrl) {
		Kinetic.createImage(imageUrl, new Callback<KImage, String>() {

			@Override
			public void onFailure(String reason) {}

			@Override
			public void onSuccess(KImage kimage) {
				kLayer.add(kimage);
				kLayer.draw();
				stopTimer();
			}
			
		});

	}
	
	
	
	
	public void startTimer() {
		GWT.log("Start timer");
		if (stopwatch != null) {
			stopwatch.reset();
		}
		else {
			stopwatch = new Stopwatch().start();
		}
	}
	
	public void stopTimer() {
		stopwatch.stop();
		long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		GWT.log("Stop timer: "+time+" ms");
	}
	
	
	public static native void getSelectedFile(Element image, Element inputFile, Callback<String, String> callback) /*-{
	
		var files = inputFile.files;
	
		if (!files || !files.length) 
			return
	
		var myFile = files[0];
		var _URL = $wnd.URL || $wnd.webkitURL;
	
		var callbackDone = $entry(function(file) {	
			callback.@com.google.gwt.core.client.Callback::onSuccess(Ljava/lang/Object;)(file);
		});
		
		image.onload = function() {
			callbackDone(image.src);
		};		
	
		image.src = _URL.createObjectURL(myFile);
	
	}-*/;	
	
	
	
	
	
}
