<p align='center'><img src='https://user-images.githubusercontent.com/57448981/187096108-cf4ba238-9064-479e-ab06-b56bd6a8250b.png' height='200px' width='200px'/> </p>

<hr style='border-style:inset; border-width:0.5px'/>

# **Introduction 👀** 
Teachers face the serious issue of checking codes from students' papers. They had to write it down manually and then check. When they had to do it in a massive ratio, mistakes can happen, they have to check the code again if they had done something wrong or if the student itself wrote the wrong code. That's a mess. That's where PlayCode comes to the rescue. 
Now Teachers can grab their smartphones and click a picture of code from paper. It'll recognize the code, then they'll select the code language, and hit the Run button. The desired output screen will pop up. Isn't it easy ?? Yes, it is. 😎
# Feature 🐱‍🏍
- Scan code from camera and Run 
- Load code from gallery and Run
- Write code and Run
# Demo 🦾
https://user-images.githubusercontent.com/57448981/187533362-fcaa0ce9-c051-44e5-b56a-b9e06e7326c2.mp4

# Tech Stack 👨🏽‍💻
- Design 
  - Kotlin 
  - XML
- Text recognisition
  - [Firebase ML kit](https://firebase.google.com/docs/ml-kit)

## Problems 🥺
As you can see in demo, Firebase ML kit cannot detect handwritten text properly 😣. Here [Google Cloud Vision](https://cloud.google.com/vision/) works fine. But for that, I need a billing account that includes a credit card, and not me my friends or family have one. That's why I cannot continue this project 😓

# Developep Setup 
1. Fork this project 
2. Go to Android studio or [download from here](https://developer.android.com/studio)
3. Click on -> File -> New -> Project from version control
4. Paste to clone
``` 
git clone https://github.com/<Username>/PlayCode.git 
```
5. Let android studio download the code
6. If asked, click "sync gradle"
7. Create a virtual device following this link
8. Click on Run tab and then Run app

**Whooh done 🦄**

