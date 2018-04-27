This txt file is to describe the DIR / file organization of the delivery for the  FYP " xxxxx. txt"


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

written by
ZHU Zicong
Date: 2018-04-21

File name "3_Program_comply " 

This Dir contains all source codes 
