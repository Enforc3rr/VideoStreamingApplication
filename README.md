# Mini-Youtube
### Purpose Of This Application 
These days trend of short videos are on rise youtube recently realsed "Shorts" . So , taking inspiration from Youtube Shorts I decided to make this application which will allow user to upload 120 secs video .
Unlike Tik-Tok This Application is going to be targeted more towards educational and travel content ( [FireShip.io](https://www.youtube.com/channel/UCsBjURrPoezykLs9EqgamOA) is the living example that educational videos don't need to be long to make it's point)

### Tech Stack
This Project uses uses Server Side Capabilities of Nodejs and Spring Boot.       
Reasons To Use ->    
1) Java for All Kinds of Upload and Conversion Of videos to Different Resolution Process as this process is CPU intensive.So,it's better to handle it in Java.
2) Nodejs for Playback , Authentication-Authorization as these are just some simple I/O operations which NodeJs is pretty good at.

### Backend Architecture / System's Design
![System's Design](Architecture1.JPG)

### End Points
| End Point      |   Port Number | HTTP Method   |           Description                   |  Required User registration |
| :---            | :----:  |    :----:     |          :----:                           | ---:   |
| /video/upload    | 8080 | POST          | To Upload Videos                        |  Yes |
| /video/fetch      | 8000 | GET           | To Fetch All Available Videos           | No   |
| /video/user/registration|8000 | POST | To register the user |  No |

### Different Models 
###### - User Registration Model ( TO Pass in the Body of the API)
username*  
password  
firstName  
lastName  
email*    
countryOfUser   
role   


### Things Needed To Be Implemented In Future 
- ~~Authentication-Authorization~~
- The One who has uploaded can delete the videos
- User Interface
- Handling Exceptions  
- Ability To Add Comments , Likes etc.

### Issues
1) Thumbnail Uploaded isn't getting converted to lower resolution.

### Things to be taken care of before starting the project on your local computer.
1) Setup the local MongoDB server with VideoStreamingApplcation Database.
2) Make Sure that you have downloaded the current version of [FFMPEG](https://www.ffmpeg.org/download.html) and make sure you have changed the path . 
3) Make sure that you have changed the Input and Output file destination of the video that will be converted into different resolutions.


##### If you are a front-end developer pls go through different controller classes to learn about different responses and requests.


