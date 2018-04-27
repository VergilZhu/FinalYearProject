This txt file is to describe the DIR / file organization of the delivery for the  FYP " xxxxx. txt"




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
--- User menu.



written by
ZHU Zicong
Date: 2018-04-21

File name "2_Program_exe " 

This Dir contains executable files of server and application 


