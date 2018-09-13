# ucsb-cs56-scrapstosnacks

gives recipe suggestions based on ingredient input.

Also, gives recipes based the approximate time to prepare and whether it is breakfast, lunch, dinner or simple snacks.





SETTING UP DATABASE INSTRUCTIONS

To connect using the mongo shell:

  mongo ds020208.mlab.com:20208/scrapstosnacksuser -u <dbuser> -p <dbpassword>
	
To connect using a driver via the standard MongoDB URI:

  mongodb://<dbuser>:<dbpassword>@ds020208.mlab.com:20208/scrapstosnacksuser


Now, open up env.sh for editing. The first two lines say:

export MONGODB_USER=scrapstosnacksuser

export MONGODB_PASS=scrapstosnacks1

DO NOT CHANGE THESE TO THE USERNAME AND PASSWORD YOU USED TO LOGIN TO mlab.com!!! These are a different user and password, that you are going to create right now in the mlab.com window.

In your file, create a username (Literally using testuser is fine). For password, make up a good long random password, such as 8sfvlSFE13RGDG2. The longer and more random the better, because you are never going to have to remember or type in this password; You are going to enter it once in this file; then copy and paste it into MLab when you create the user/password, and then never have to type it again. Please DON'T literally use abcd1234 or 8sfvlSFE13RGDG2.

Type it in the env.sh file first. Then click the "Users" tab, and look over to the right side of the screen for the "Add database user" button. Click it, and enter the username and password that you just created (e.g. testuser and 8sfvlSFE13RGDG2. You'll want to copy/paste the password since you have to type it twice.)


To connect using the mongo shell:

  mongo ds020208.mlab.com:20208/scrapstosnacksuser -u <dbuser> -p <dbpassword>
	
info:
	
MONGODB_NAME=scrapstosnacksdb
	
MONGODB_HOST=ds020208.mlab.com

MONGODB_PORT=20208

Once you've made these edits, you need to type the following so that these environment variables take effect:
. env.sh
This sets up the environment variables that the Java code will read from.

While the previous steps are "one time only" steps, this final step must be done each time you log in to a term Unix terminal session; the environment variable are defined as part of the current process.

Once you've done these steps, you should be able to run and not see the error message about defining environment variables.





DATABASE FOR HEROKU INFO


heroku  config:set MONGODB_USER=${MONGODB_USER} --app scrapstosnacks	

heroku  config:set MONGODB_PASS=${MONGODB_PASS}	--app scrapstosnacks

heroku  config:set MONGODB_NAME=${MONGODB_NAME}	--app scrapstosnacks

heroku  config:set MONGODB_HOST=${MONGODB_HOST}	--app scrapstosnacks

heroku  config:set MONGODB_PORT=${MONGODB_PORT}	--app scrapstosnacks

