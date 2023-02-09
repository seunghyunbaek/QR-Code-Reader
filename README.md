# QR Code 란? 
QR 코드는 URL을 QR 코드로 만든 것이다. <br/> 

# Introduction
QR 코드를 스캔하고 해당 링크로 이동할 수 있는 애플리케이션 <br/>
구현 
1. QR 코드 스캔 
2. 스캔한 URL로 연결하기

# Demo
<figure>
   <img src="user-images.githubusercontent.com/39176041/217736895-b5cf6684-fce7-48c2-950b-b1ff36077683.jpg" width="200" height="400"/>
   <img src="user-images.githubusercontent.com/39176041/217737503-72101a05-f05f-4d2e-a987-0da794deaf7b.jpg" width="200" height="400"/>
 </figure>



# Library 
### ZXing Adnroid Embedded 라이브러리
디코딩을 위해 ZXing을 사용하는 안드로이드용 라이브러리  <br/>
이 라이브러리는 ZXing Android Barcode Scanner 애플리케이션을 기반으로 하지만 공식적인 ZXing 프로젝트와는 관련이 없다. <br/>
특징 <br/>
1. Intents를 통해 적은 코드로 사용할 수 있다
2. Activity 에 넣을 수 있다. 
3. 스캔은 가로 or 세로 모드가 가능하다
4. 카메라는 빠른 시작을 위해 백그라운드 스레드에서 관리된다
[Zxing Android Embedded github](https://github.com/journeyapps/zxing-android-embedded) <br/>

세로 모드를 사용하기 위한 방법 <br/>
1. CaptureActivity를 상속받는 Activity 클래스를 만들기 (layoout 파일은 필요하지 않다)
2. Manifest.xml 에 설정하기 
   1. 설정시 추가해야할 속성
   - android:screenOrientation="portrait"
   - android:theme="@style/zxing_CaptureTheme"
3. ScanOption 설정 시 captureActivity 속성에 1번에서 만든 클래스로 설정해준다. 
