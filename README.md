# TravelMemo

This is a travel journal app where you can record your travel notes, take pictues, map your POIs on a map and even share
trips with your friends. The trip can be updated/modified by people who travelled with you on a trip - they can add photos,
trip notes , voice notes etc and that will be visible to trip member.


[![Build Status](https://travis-ci.org/CodePathGrp10/TravelMemo.svg?branch=master)](https://travis-ci.org/CodePathGrp10/TravelMemo)

## User stories
 

### These are the **P1** stories :
 
 * [X] Sign-in with google account
 * [X] Create Trip 
    * [X] Enter title, description & Save it
    * [X] Save the trip to firebase database
 * [X] View trip details
    * [X] View trip name and description
    * [X] Show trip photos from Firebase storage
    * [X] Show Markers on map where the photo was taken
 * [X] Add a Photo
    * [X] Select a photo from the Gallery on the device
    * [X] Upload the selected photo to Firebase storage
    * [X] Open the camera on the device.
    * [X] Upload the photos to Firebase storage
    * [X] Put mark on the map based on location of device
 * [X] Sign-out
     
### These are the **P2** stories :
 
 * [ ] Able to add Voice Notes
 * [X] Edit trip
 * [X] Delete a trip
 * [X] View trips of your friends
 * [ ] Show POIs on the map
 * [X] View Photos of the trip fullscreen
     
### These are the **P3** stories :
 
 * [X] Authentication via Google/FB
 * [X] Login Page
 * [ ] Public or private Trip
 * [X] Share a public trip with a friend
 * [X] View all shared Trip
 * [ ] Profile/Settings
   * [ ] User profile from FB/Google or create a local profile
   * [ ] Settings screen for some sharing/UI related options.



## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/HkloSD6.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

Week2 GIF:

<img src='http://i.imgur.com/riRyv8b.gif' title='Week2 GIF'/>

GIF created with [LiceCap](http://www.cockos.com/licecap/).


 ## Open-source libraries used

- [ImagePicker](http://www.materialup.com/posts/imagepicker) - A simple library to pick images from the gallery and camera.
- [MaterialDrawer](https://github.com/mikepenz/MaterialDrawer) - The flexible, easy to use, all in one drawer library for your Android project.
- [SmoothProgressBar](https://github.com/castorflex/SmoothProgressBar) - A small Android library allowing you to have a smooth and customizable horizontal indeterminate ProgressBar.
- [Android-SimpleLocation](https://github.com/delight-im/Android-SimpleLocation) - Utility class for easy access to the device location on Android
- [Like Button](https://android-arsenal.com/details/1/3038) - Like Button is a library that allows you to create a button with animation effects similar to Twitter's heart when you like something.
- [BlurredView](https://material.uplabs.com/posts/blurredview) - Dynamic blur of Image Views for Android.
- [Fab-Speed-dial](https://github.com/yavski/fab-speed-dial) - New FAB speed dial button
- [CircleImageView](https://github.com/hdodenhof/CircleImageView) - Circular image view
- [ButterKnife](https://github.com/JakeWharton/butterknife) - View binding library
- [Glide](https://github.com/bumptech/glide) - Image Loading library

## License

    Copyright [2016] [TravelMemo team members - Akshat, Mike, Travis]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


    License for everything not in third_party and not otherwise marked:

    Copyright 2014 Google, Inc. All rights reserved.

    Redistribution and use in source and binary forms, with or without modification, are
    permitted provided that the following conditions are met:

       1. Redistributions of source code must retain the above copyright notice, this list of
             conditions and the following disclaimer.

       2. Redistributions in binary form must reproduce the above copyright notice, this list
             of conditions and the following disclaimer in the documentation and/or other materials
             provided with the distribution.

    THIS SOFTWARE IS PROVIDED BY GOOGLE, INC. ``AS IS'' AND ANY EXPRESS OR IMPLIED
    WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
    FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL GOOGLE, INC. OR
    CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
    CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
    SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
    ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
    NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
    ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

    The views and conclusions contained in the software and documentation are those of the
    authors and should not be interpreted as representing official policies, either expressed
    or implied, of Google, Inc.
    ---------------------------------------------------------------------------------------------
    License for third_party/disklrucache:

    Copyright 2012 Jake Wharton
    Copyright 2011 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    ---------------------------------------------------------------------------------------------
    License for third_party/gif_decoder:

    Copyright (c) 2013 Xcellent Creations, Inc.

    Permission is hereby granted, free of charge, to any person obtaining
    a copy of this software and associated documentation files (the
    "Software"), to deal in the Software without restriction, including
    without limitation the rights to use, copy, modify, merge, publish,
    distribute, sublicense, and/or sell copies of the Software, and to
    permit persons to whom the Software is furnished to do so, subject to
    the following conditions:

    The above copyright notice and this permission notice shall be
    included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
    LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
    OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
    WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
    ---------------------------------------------------------------------------------------------
    License for third_party/gif_encoder/AnimatedGifEncoder.java and
    third_party/gif_encoder/LZWEncoder.java:

    No copyright asserted on the source code of this class. May be used for any
    purpose, however, refer to the Unisys LZW patent for restrictions on use of
    the associated LZWEncoder class. Please forward any corrections to
    kweiner@fmsware.com.

    -----------------------------------------------------------------------------
    License for third_party/gif_encoder/NeuQuant.java

    Copyright (c) 1994 Anthony Dekker

    NEUQUANT Neural-Net quantization algorithm by Anthony Dekker, 1994. See
    "Kohonen neural networks for optimal colour quantization" in "Network:
    Computation in Neural Systems" Vol. 5 (1994) pp 351-367. for a discussion of
    the algorithm.

    Any party obtaining a copy of these files from the author, directly or
    indirectly, is granted, free of charge, a full and unrestricted irrevocable,
    world-wide, paid up, royalty-free, nonexclusive right and license to deal in
    this software and documentation files (the "Software"), including without
    limitation the rights to use, copy, modify, merge, publish, distribute,
    sublicense, and/or sell copies of the Software, and to permit persons who
    receive copies from any such party to do so, with the only requirement being
    that this copyright notice remain intact.

    Copyright 2013 Jake Wharton

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    Copyright 2014 - 2016 Henning Dodenhof

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    Copyright 2016 Nguyen Hoang Lam

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use
    this file except in compliance with the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    <!--
      ~ Copyright 2016 Yavor Ivanov
      ~
      ~ Licensed under the Apache License, Version 2.0 (the "License");
      ~ you may not use this file except in compliance with the License.
      ~ You may obtain a copy of the License at
      ~
      ~     http://www.apache.org/licenses/LICENSE-2.0
      ~
      ~ Unless required by applicable law or agreed to in writing, software
      ~ distributed under the License is distributed on an "AS IS" BASIS,
      ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      ~ See the License for the specific language governing permissions and
      ~ limitations under the License.
      -->

    Copyright 2016 Qiushui

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    Copyright 2016 Joel Dean

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


    Copyright (c) delight.im <info@delight.im>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    Copyright 2014 Antoine Merle

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    Copyright 2016 Mike Penz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.