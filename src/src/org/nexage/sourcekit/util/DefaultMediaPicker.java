//
//  DefaultMediaPicker.java
//
//  Created by Harsha Herur on 12/4/13.
//  Copyright (c) 2014 Nexage. All rights reserved.
//

package org.nexage.sourcekit.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.nexage.sourcekit.vast.model.VASTMediaFile;
import org.nexage.sourcekit.vast.processor.VASTMediaPicker;

import android.content.Context;
import android.util.DisplayMetrics;

public class DefaultMediaPicker implements VASTMediaPicker {
	
	private static final String TAG = "DefaultMediaPicker";

	// These are the Android supported MIME types, see http://developer.android.com/guide/appendix/media-formats.html#core (as of API 18)
	String SUPPORTED_VIDEO_TYPE_REGEX = "video/.*(?i)(mp4|3gpp|mp2t|webm|matroska)";
	
	private int deviceWidth;
	private int deviceHeight;
	private int deviceArea;
	private Context context;

	public DefaultMediaPicker(Context context)
	{
		this.context = context;
		setDeviceWidthHeight();
	}
	
	public DefaultMediaPicker(int width, int height)
	{
		setDeviceWidthHeight(width, height);
	}

	private void setDeviceWidthHeight() {

		// get the device width and height of the device using the context
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		deviceWidth = metrics.widthPixels;
		deviceHeight = metrics.heightPixels;
		deviceArea = deviceWidth * deviceHeight;
	}
	
	public void setDeviceWidthHeight(int width, int height) {

		this.deviceWidth = width;
		this.deviceHeight = height;
		deviceArea = deviceWidth * deviceHeight;
		
	}

	private class AreaComparator implements Comparator<VASTMediaFile> {

		@Override
		public int compare(VASTMediaFile obj1, VASTMediaFile obj2) {
		   // get area of the video of the two MediaFiles
			int obj1Area = obj1.getWidth().intValue() * obj1.getHeight().intValue();
			int obj2Area = obj2.getWidth().intValue() * obj2.getHeight().intValue();
			
			// get the difference between the area of the MediaFile and the area of the screen
			int obj1Diff = Math.abs(obj1Area - deviceArea);
			int obj2Diff = Math.abs(obj2Area - deviceArea);
			 SourceKitLogger.v(TAG, "AreaComparator: obj1:" + obj1Diff +" obj2:" + obj2Diff);
				
			// choose the MediaFile which has the lower difference in area
			if(obj1Diff < obj2Diff) {
				return -1;
			} else if(obj1Diff > obj2Diff) {
				return 1;
			} else {
				return 0;
			}
		}
		
	}
	
	private boolean isMediaFileCompatible(VASTMediaFile media) {

		// check if the MediaFile is compatible with the device.
		// further checks can be added here
		return media.getType().matches(SUPPORTED_VIDEO_TYPE_REGEX);
	}

	private VASTMediaFile getBestMatch(List<VASTMediaFile> list) {
	     SourceKitLogger.d(TAG, "getBestMatch");
	     
		// Iterate through the sorted list and return the first compatible media.
		// If none of the media file is compatible, return null
		Iterator<VASTMediaFile> iterator = list.iterator();

		while (iterator.hasNext()) {
			VASTMediaFile media = iterator.next();
			if (isMediaFileCompatible(media)) {
				return media;
			}
		}
		return null;
	}

	@Override
	public VASTMediaFile pickVideo(List<VASTMediaFile> list) {
		
		// given a list of MediaFiles, select the most appropriate one.
		// Use any one of the Comparators defined above.
		
		Collections.sort(list, new AreaComparator());

		return getBestMatch(list);

	}
}
