//
//  VASTSamplesFragment.java
//
//  Copyright (c) 2014 Nexage. All rights reserved.
//
package org.nexage.sourcekit.vastdemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.nexage.sourcekit.util.SourceKitLogger;
import org.nexage.sourcekit.vast.VASTPlayer;
import org.nexage.sourcekit.vastdemo.adapter.VASTFileListAdapter;
import org.nexage.sourcekit.vastdemo.adapter.VASTListItem;
import org.nexage.sourcekit.vastdemo.util.FileHelper;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class VASTSamplesFragment extends ListFragment {

	private final static String TAG = "VASTSamplesFragment";
	VASTPlayer newPlayer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SourceKitLogger.d(TAG, "onCreateView");

		VASTFileListAdapter adapter = new VASTFileListAdapter(
				inflater.getContext(), getTestFiles());
		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	
		super.onListItemClick(l, v, position, id);

		// Find the selected row
		VASTListItem selectedListItem = (VASTListItem) getListAdapter()
				.getItem(position);
		SourceKitLogger.d(TAG, "Selected List item: " + selectedListItem.getTitle());
		
		this.getListView().setClickable(false); // Disable multiple taps

		// Get file content
		String vastXMLContent = FileHelper.getFileContent(getActivity(),
				selectedListItem.getDescription());
		if (!TextUtils.isEmpty(vastXMLContent)) {
			// We can create VAST Player and pass the data
			newPlayer = new VASTPlayer(getActivity(),
					new VASTPlayer.VASTPlayerListener() {

						@Override
						public void vastReady() {
							SourceKitLogger.i(TAG,
									"VAST Document is ready and we can play it now");
							newPlayer.play();
						}

						@Override
						public void vastError(int error) {
						    String message = "Unable to play VAST Document: Error: " + error;
						    SourceKitLogger.e(TAG,  message);
							getListView().setClickable(true);
						}
					});

			newPlayer.loadVideoWithData(vastXMLContent);
		}

	}

	private ArrayList<VASTListItem> getTestFiles() {
		SourceKitLogger.d(TAG, "getTestFiles");
		
		String[] files = null;

		// Title map to display on the list
		Map<String, String> map = new HashMap<String, String>();
		map.put("WrapperSimple", "Wrapper");
		map.put("SimpleTracking", "Impression Tracking");
		map.put("vast_doubleclick_inline_comp", "Inline");
		map.put("vast_liverail_linear_comp", "Linear");
		map.put("vast_missing_mediafile", "Missing MediaFile");
		map.put("vast_wrapper_linear_1", "Wrapper Linear");

		ArrayList<VASTListItem> testFiles = new ArrayList<VASTListItem>();
		try {
			files = getResources().getAssets().list("");
		} catch (IOException e) {
			SourceKitLogger.e(TAG, e.getMessage(), e);
		}
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String file = files[i];
				String fileName = file.replace(".xml", "");

				// Ignore some files which were created by Android
				if (fileName.equals("images") || fileName.equals("sounds") || fileName.equals("webkit")) {
					SourceKitLogger.d(TAG, "We will ignore " + fileName);
				} else {
					SourceKitLogger.d(TAG, files[i]);
					String displayName = (map.get(fileName) == null ? fileName : map.get(fileName));
					testFiles.add(new VASTListItem(displayName, fileName + ".xml"));
				}
			}
		}
		return testFiles;
	}

}
