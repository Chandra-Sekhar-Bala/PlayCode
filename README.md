<p align='center'><img src='https://user-images.githubusercontent.com/57448981/187096108-cf4ba238-9064-479e-ab06-b56bd6a8250b.png' height='200px' width='200px'/> </p>

<hr style='border-style:inset; border-width:0.5px'/>

# **Introduction 👀** 
Teachers face the serious issue of checking codes from students' papers. They had to write it down manually and then check. When they had to do it in a massive ratio, mistakes can happen, they have to check the code again if they had done something wrong or if the student itself wrote the wrong code. That's a mess. That's where PlayCode comes to the rescue. 
Now Teachers can grab their smartphones and click a picture of code from paper. It'll recognize the code, then they'll select the code language, and hit the Run button. The desired output screen will pop up. Isn't it easy ?? Yes, it is. 😎
# Feature 🐱‍🏍
- Scan code from the camera and Run 
- Load code from the gallery and Run
- Write code and Run


<!--screenshots-->
## ScreenShots
<p align = "center">
<img src="https://github.com/Chandra-Sekhar-Bala/PlayCode/assets/57448981/f81a90ac-9948-4932-ae33-b2e7b98c0ab0" width=500/>
<img src="https://github.com/Chandra-Sekhar-Bala/PlayCode/assets/57448981/2e436ca9-a4d2-4997-b319-be6a03bb2787" width=500/>
<img src="https://github.com/Chandra-Sekhar-Bala/PlayCode/assets/57448981/4650dbeb-83be-4293-928d-bbdae243ddf2" width=500/>

</p>


### Watch on youtube

<a href="https://youtu.be/2qfQ2m5tJXM">
  <img src="https://github-production-user-asset-6210df.s3.amazonaws.com/57448981/237409266-ff16d5e8-7c34-48a5-9162-b0f96589f29b.png" width="80" />
</a>


# Demo 🦾

https://user-images.githubusercontent.com/57448981/187533362-fcaa0ce9-c051-44e5-b56a-b9e06e7326c2.mp4

# Tech Stack 👨🏽‍💻
- Design 
  - Kotlin 
  - XML
- Text recognition
  - [Firebase ML kit](https://firebase.google.com/docs/ml-kit)

## Known Bugs 🐞
As you can see in the demo, Firebase ML kit cannot detect handwritten text properly 😣, I've created an issue on GitHub that could be found [here](https://github.com/firebase/firebase-android-sdk/issues/4120). In this situation [Google Cloud Vision](https://cloud.google.com/vision/) works fine. But for that, I need a billing account that includes a credit card, and not me my friends or family have one. That's why I cannot continue this project 😓

# How to Contribute?
1. Fork this project 
2. Go to Android studio or [download from here](https://developer.android.com/studio)
3. Click on -> File -> New -> Project from version control
4. Paste to clone
``` 
git clone https://github.com/<Username>/PlayCode.git 
```
5. Let android studio download the code
6. If asked, click "sync Gradle"
7. Create a virtual device following this [link](https://developer.android.com/studio/run/managing-avds)
8. Click on Run tab and then Run the app

**Whooh done 🦄**
