This txt file is to describe the DIR / file organization of the delivery for the  FYP " xxxxx. txt"
This is a sample only for your reference.

written by
ZHU Zicong
Date: 2018-04-21

File name "_readme_1st_(content overview).txt " 

==== A =======================================================================================


In the TOP Dir of the delivery, there are 6 main directories as follows.

DIR 1. 1_FYP Document:
	Include Project report, Technical paper, User Manual, and Presentation powerpoint.


DIR 2. 2_Program_exe : executable file of project

	--- The exe runing  environment (e.g., Win8 ,,,,,,,) and how to set it up.
		-- Java Server
			- Windows10 (other versions of Windows may be compatible£©
			- Java ver.1.8+
		-- Android Application
			- Android Ver. 4.0+
		-- MySQL Database
			- MySQL ver. 8.0.11 (installation refer to https://dev.mysql.com/downloads/installer/)
			- Follow the dialogs of installer to install MySQL server
			- A video reference for installation: https://www.youtube.com/watch?v=fwQyZz6cNGU
		-- Face Identification
			- Python 3.6 (refer to https://www.python.org/downloads/)
			- Tensorflow (refer to https://www.tensorflow.org/install/install_windows)
	--- All the exe in client side
		-- fyp.apk
	--- All the exe in the Server  (if it is applciable)
		-- fyp_server_0.0.1-SNAPTSHOT.jar
	--- All files of Face Recoginition Part
		-- /FaceIdentification
			- i. 	/Codes
					1.	/Model: contains all models, graphs and variables. New models after training will be saved under this folder. No need to change
					2.	Eigenface_10.py: Run this file to train the model, folder path in this file need to be updated
					3.	restore.py: This file will be called by server. No need to change, folder path in this file need to be updated
			- ii.	/FaceDatabase
					1.	All the faces for training are stored here
					2.	A 10-people set and 40-people set are prepared
					3.	Replace the corresponding image folder to train other images
			- iii.	/TestImages
					1.	All received images from client will be saved here
					2.	Images in this folder will be identified by restore.py
	--- How to run
		-- Start the database
			- Command: mysql -u <username> -p, and enter password to login to database
			- Command: source /fyp.sql to import the database
			- start a new terminal, Command: net start mysql, to start the database
		-- Train the face identification model
			- Update all folder paths in /Codes/Eigenface_10.py, /Codes/restore.py and in
			  server resource file "Identification.java"
			- Command: python .\Codes\Eigenface_10.py, to train the model
			- Modify parameters in file Eigenface_10.py for better training results
		-- Run the server
			- Command: java - jar .\fyp_server-0.0.1-SNAPSHOT.jar server fyp_server.yml
		-- Install the apk on android device


DIR 3. 3_Code_n_Compile: Source codes of project
	--- All the code in client side
		-- In folder /Client
	--- All the code in the Server  (if it is applciable)
		-- In folder /Server_
	--- The code development environment and the SDK, and how to set it up.
		-- For client application
			- Use Android Studio to compile (ver 3.1.1+ is preferred)
			- Optional: copy all the files in folder /Client to a new workspace
			- In Android Studio, choose "Open an existing Android Studio project"
			- Plug in your android phone to compurter or craete a virtual device
			- In Run tab, choose "Run LoginActivity" to build the project and install it on specific devices
		-- For server program
			- Use Eclipse to compile (ver. Mars.1 Release 4.5.1+ is preferred)
			- Install Maven on Eclipse [1]
				- Open Eclipse
				- Go to Help -> Eclipse Marketplace
				- Search by Maven
				- Click "Install" button at "Maven Integration for Eclipse" section
				- Follow the instruction step by step
				- Go to Window -> Preferences, Maven can be observed at left panel if successfully installed
			- Import the server program into Eclipse [2]
				- Create a New Java Project in Eclipse
				- Goto File > Import > General > Archive File
				- Select the required Archive file (/Server_/FYP_Server.jar)
				- Select the project you created in step 1 and click OK
			- Build the project on Eclipse with Maven
				- In Project Explorer of Eclipse, right click the project
				- Choose Run As > Run Configurations
				- In Run Configurations, right click the Maven Build at left panel
				- Choose New to create a new configuration
				- In Main Tab, clarify the Base directory
				- Enter "clean install" in Goals
				- Click Run to build the project
			- You can directly unzip the FYP_Server.jar to get all source codes



--- List out all reference 
      - [1] https://stackoverflow.com/questions/8620127/maven-in-eclipse-step-by-step-installation
      - [2] https://stackoverflow.com/questions/15380065/import-project-jar-into-eclipse



==== B ===============================================================================================


Insruction / procedure guideline to read the documemntion and running order

Reading order
1.  Read this file first
2.  Refer to sperate readme files in each folder

Running order
1.	If you want to run the application, please refer to readme file in 2_Program_exe
2.	If you want to compile the project, please refer to readme file in 3_Code_n_compile
