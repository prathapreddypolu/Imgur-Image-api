# Imgur-Image-api

Imgur-Image-api is a spring boot RESTFull API . This API allows user to register and validate the basic information .Also, this API Interact with Imgur' api for uploading,viewing and deleting the images .


API Features:

1) User Registration : Register users with basic information .
2) Images Management : upload Images ,view images,delete Images and Get user Image account details after basic user authentication
3) Imgur API Integration : API interact with Imgur api with oAuth authentication.
4) User and Image details can publish in kafka topic .

API End points :

 Image end points : 
      POST :  /api/images/upload  - using endpoint we can upload Images in user account.
      GET :   /api/images/view/{username} - Based up on the user account we can view the all the Images.
      DELETE: /api/images/delete/{imageId} - Delete image based up on the Image ID.
      GET :   /api/images/user/{userAccountName} - Getting user account details,we check whether account status and other details .

 User Registration end points:
      GET :   /api/user/registration  - Save the user account details(H2 DB) . If user details already existing we are not adding .
      GET :   /api/user//username}  -  Get User account details by using the userName 

 Kafka :
      GET :   /api/user/publish - publishing the basic user details in Kafka topic
      GET :   /api/image/publish - publishing the Image details in Kafka topic 
    
Note : This API work based up on the Image app registration CLIENT_ID and CLIENT_SECRET . If application needs to run in local , we need to update the Imgur API CLIENT_ID and CLIENT_SECRET details in application configuration files .


