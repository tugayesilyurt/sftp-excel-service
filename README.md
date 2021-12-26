# Spring Boot creating excel and upload to sftp server example

## Run the System
We can easily run the whole with only a single command:

* `docker-compose up`

The services can be run on the background with command:

* `docker-compose up -d`


## Stop the System
Stopping all the running containers is also simple with a single command:

* `docker-compose down`


If you need to stop and remove all containers, networks, and all images used by any service in <em>docker-compose.yml</em> file, use the command:

* `docker-compose down --rmi all`


### EndPoints ###

| Service       | EndPoint                      | Method | Description                                      |
| ------------- | ----------------------------- | :-----:| ------------------------------------------------ |
| Excel         | /api/v1/excel/upload-sftp     | POST   | Creating excel and upload to sftp server         |


		<dependency>
			<groupId>com.jcraft</groupId>
			<artifactId>jsch</artifactId>
			<version>0.1.55</version>
		</dependency>
		<dependency>
			<groupId>org.apache.poi</groupId>
			<artifactId>poi</artifactId>
			<version>5.1.0</version>
		</dependency>
		<dependency>
			<groupId>org.apache.poi</groupId>
			<artifactId>poi-ooxml</artifactId>
			<version>5.1.0</version>
		</dependency>