Nexage Integration SourceKit for VAST
=====================================

Nexage Integration SourceKit for VAST is an easy to use library which implements the IAB VAST 2.0 spec (http://www.iab.net/guidelines/508676/digitalvideo/vsuite/vast/vast_copy). It is written for Android and works in both phone & tablet apps.

**Features:**

- VAST 2 implementation
- Handles VAST & VAST Wrapper
- Optionally choose to validate with Xerces
- Integrates with just a few lines of code

**Requirements:**

- Android 2.3+
- Eclipse + ADT

Getting Started
===============

Step 1. Import the "VAST" project into Eclipse where you have your main App.

Step 2. Include VAST Library under Project Properties -> Android section.

Step 3. Add the following permissions to your app's Android manifest.

	<uses-permission android:name="android.permission.INTERNET"></uses-permission>
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>

Step 4. Add the following activity element to your app's Android manifest.

	<activity
            android:name="org.nexage.sourcekit.vast.activity.VASTActivity"
            android:screenOrientation="landscape" >
	</activity>
    
Step 5. Import required packages into your activity.

	import org.nexage.sourcekit.vast.VASTPlayer;
	
Step 6. Declare a VASTPlayer member variable in your activity (or fragment).

	private VASTPlayer vastPlayer;

Step 7. Initialize the VASTPlayer object with a listener.
	
	vastPlayer = new VASTPlayer(this, // Activity
		new VASTPlayer.VASTPlayerListener() {
			@Override
			public void vastReady() {
				Log.i(TAG, "VAST Document is ready and we can play it now");
			}

			@Override
			public void vastError(int error) {
				Log.e(TAG, "Unable to play VAST Document (error code: " + error + ")");
			}
		}
	);
	
Step 8. Load the VASTPlayer.

	private void loadPlayer() {
		vastPlayer.loadVideoWithData(vastXMLContent);
		// OR: vastPlayer.loadVideoWithUrl(urlOfVastDoc);
	}
	
Step 9. After you've received the vastReady callback, you can play the video.

	vastPlayer.play();

That's it! 


LICENSE
=======

Copyright (c) 2014, Nexage, Inc.<br/> 
All rights reserved.<br/>
Provided under BSD-3 license as follows:<br/>

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

1.  Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

2.  Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.

3.  Neither the name of Nexage nor the names of its
    contributors may be used to endorse or promote products derived from
    this software without specific prior written permission.
 
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
