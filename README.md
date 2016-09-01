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
    * [ ] Show Markers on map where the photo was taken
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
 * [ ] Public or private Trip
 * [ ] Share a public trip with a friend
 * [ ] View all shared Trip
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