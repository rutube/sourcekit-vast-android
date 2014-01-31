//
//  VASTModelPostValidator.java
//
//  Copyright (c) 2014 Nexage. All rights reserved.
//

package org.nexage.sourcekit.vast.processor;

import java.util.List;

import org.nexage.sourcekit.util.SourceKitLogger;
import org.nexage.sourcekit.vast.model.VASTMediaFile;
import org.nexage.sourcekit.vast.model.VASTModel;

import android.text.TextUtils;

public class VASTModelPostValidator {
    
    private static final String TAG = "VASTModelPostValidator";

    // This method tries to make sure that there is at least 1 Media file to 
    // be used for VASTActivity. Also, if the boolean validateModel is true, it will
    // do additional validations which includes "at least 1 impression tracking url's is required'
    // If any of the above fails, it returns false. The false indicates that you can stop proceeding
    // further to display this on the MediaPlayer.
    
    public static boolean validate(VASTModel model, VASTMediaPicker mediaPicker, boolean validateModel) {
        SourceKitLogger.d(TAG, "validate");

        if (validateModel && !validateModel(model)) {
            SourceKitLogger.d(TAG, "Validator returns: not valid (invalid model)");
            return false;
        }

        boolean isValid = false;
        
        // Must have a MediaPicker to choose one of the MediaFile element from XML
        if (mediaPicker != null) {
            List<VASTMediaFile> mediaFiles = model.getMediaFiles();
            if (mediaFiles != null && mediaFiles.size() > 0) {
                VASTMediaFile mediaFile = mediaPicker.pickVideo(mediaFiles);
                if (mediaFile != null) {
                    String url = mediaFile.getValue();
                    if (!TextUtils.isEmpty(url)) {
                        isValid = true;
                        // Let's set this value inside VASTModel so that it can be accessed from VASTPlayer
                        model.setPickedMediaFileURL(url);
                        SourceKitLogger.d(TAG, "mediaPicker selected mediaFile with URL " + url);
                    }
                }
            }
        }
        else {
            SourceKitLogger.w(TAG, "mediaPicker: We don't have a compatible media file to play.");
        }
        
        SourceKitLogger.d(TAG, "Validator returns: " + (isValid?"valid":"not valid (no media file)"));
        return isValid;
    }
    
    // This method is the equivalent of a very simple schema validation.
    private static boolean validateModel(VASTModel model) {
        SourceKitLogger.d(TAG, "validateModel");
        
        // There should be at least one impression.
        List<String> impressions = model.getImpressions();
        if (impressions == null || impressions.size() == 0) {
            return false;
        }
        
        // There must be at least one VASTMediaFile object, and all the VASTMediaFile objects
        // must have valid values for delivery, height, type, and width.
        // As a sanity check, both the height and width should be greater than 0 and less than maxPixels.
        final int maxPixels = 5000;
        List<VASTMediaFile> mediaFiles = model.getMediaFiles();
        if (mediaFiles == null || mediaFiles.size() == 0) {
        	SourceKitLogger.d(TAG, "Validator error: mediaFile list invalid");
            return false;
        }
        for (VASTMediaFile mediaFile : mediaFiles) {
            // delivery
            String delivery = mediaFile.getDelivery();
            if (delivery == null ||
                    !(delivery.equalsIgnoreCase("progressive") ||  delivery.equalsIgnoreCase("streaming"))) {
            	   SourceKitLogger.d(TAG, "Validator error: mediaFile delivery type unknown");
                   return false;
               }
               
               // height
               if (null == mediaFile.getHeight()) {
               	  	SourceKitLogger.d(TAG, "Validator error: mediaFile height null");
                    return false;
               }
               int height = mediaFile.getHeight().intValue();
               if (!(0 < height && height < maxPixels)) {
              	  	SourceKitLogger.d(TAG, "Validator error: mediaFile height invalid: " + height);
              	  	return false;
               }
               
               // type
               if (TextUtils.isEmpty(mediaFile.getType())) {
              	 	SourceKitLogger.d(TAG, "Validator error: mediaFile type empty");
              	 	return false;
               }
               
               // width
               if (null == mediaFile.getWidth()) {
              	 	SourceKitLogger.d(TAG, "Validator error: mediaFile width null");
                    return false;
               }
               int width = mediaFile.getWidth().intValue();
               if (!(0 < width && width < maxPixels)) {
               		SourceKitLogger.d(TAG, "Validator error: mediaFile width invalid: " + width);
               		return false;
               }
           }
        
        return true;
    }

}
