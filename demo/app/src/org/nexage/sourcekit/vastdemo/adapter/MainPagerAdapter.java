//
//  MainPagerAdapter.java
//
//  Copyright (c) 2014 Nexage. All rights reserved.
//

package org.nexage.sourcekit.vastdemo.adapter;

import org.nexage.sourcekit.vastdemo.AboutFragment;
import org.nexage.sourcekit.vastdemo.VASTSamplesFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {

	public MainPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new VASTSamplesFragment();
		case 1:
			return new AboutFragment();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

}
