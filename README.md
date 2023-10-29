# stc.assessment
stc assessment backend 
1- Build maven project using cmd : mvn install -DskipTests .

2- Run docker compose using cmd : docker-compose up --build .

3- Create Space API (run post api in postman ) : 
   http://localhost:8080/spaces

4- Create Folder Item API (run post api in postman ) : 
   http://localhost:8080/spaces/1/folders?folderName=backend&userEmail=admin@stc.com

5- Create File Item API (run post api in postman ) : 
   http://localhost:8080/folders/2/files?fileName=assessment.pdf&userName=admin@stc.com

6- to see file metaDate (run get request in postman ): 
   http://localhost:8080/files/3/fileMetadata?userEmail=admin@stc.com
   

7- to download file as binary :
   http://localhost:8080/files/1/download?userEmail=taghreed@gmail.com
