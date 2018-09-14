# ucsb-cs56-scrapstosnacks

Gives recipe suggestions based on ingredient input.
Contains both a database and an API that contain recipes.

Take a look at our website!
https://scrapstosnacks.herokuapp.com/



For our API we used https://food2fork.com 
and for our database we used MONGODB


We used the following tutorial to set up our database env vars keys, also displayed below:
https://github.com/ucsb-cs56-pconrad/sparkjava-rest-mlab-frontend#getting-it-to-run-setting-up-envsh



# Getting it to run: setting up env.sh

To run, the usual steps of `mvn compile exec:java` then visiting http://localhost:4567 are a good start.

But, you'll get this error message:
```
Error: Must define env variable MONGODB_USER
Error: Must define env variable MONGODB_PASS
Error: Must define env variable MONGODB_NAME
Error: Must define env variable MONGODB_HOST
Error: Must define env variable MONGODB_PORT
Phillips-Mac-mini:sparkjava-rest-mlab-frontend pconrad$ 
```

YOU HAVE TO CREATE AN `env.sh`  FILE IN ORDER FOR THIS TO RUN.

To fix this, you need to take the following steps. Note that the steps involving mlab.com are pretty self-explanatory if you go to their website, so I'm not including much detail. 

We also used this website to set up our database
MongoDBTutorial:https://github.com/ucsb-cs56-m18/sparkjava-mongodb-mlab-tutorial

1. Create a free account at mlab.com

2. Create a deployment on the free tier

3. Create a database. Call it whatever you like; perhaps mlab-blog-demo for example.

4. Create a user for that database

In that database, create two collections, initially empty

..*a collection called posts
..*a collection called counters
In the counters collection, create a document with exactly this content:
```
{
 "_id": "postId",
 "seq": 0
}
```


Take note of the user credentials your just made, your db name, and host
mongodb://<dbuser>:<dbpassword>@d<dbhost>/<dbname>
 
 YOUR DB USER AND DB PASS ARE NOT YOUR ACCOUNT USERNAME AND PASSWORD, THEY ARE THE USER THAT YOU CREATED WITHIN THE DATABASE
 
 
# Creating environment variables locally

Since we're going to be logging into a remote database, we have to hide our login credentials from the outside world. To do this, we're going to make a .env.sh file inside our project directory. It should have a structure similar to this.

export USER_=your user's username
export PASS_=your user's password
export DB_NAME_=your db name
export HOST_=your host name // should be something with mlab in it...

also include the API key in this file unders something like

export API_KEY=whatever the api key is

!!! IMPORTANT !!!

Make sure to run the following to add .env to your .gitignore! This way our secret credentials won't be pushed into our GitHub repos.
```
echo ".env.sh" >> .gitignore
```
Once you've made these edits, you need to type the following so that these environment variables take effect:

```
. env.sh
```
While the previous steps are "one time only" steps, this final step must be done each time you log in to a term Unix terminal session; the environment variable are defined as part of the current process.


#Creating config variables on heroku

create a heroku account and a heroku project
Go to your dashboard https://dashboard.heroku.com/apps/<project-name>/settings
Click on Settings
Click Reveal Config Vars inside the Config Vars menu
Input your key value pairs for your config variables in the same way as the environment variables
USER_        username
.
.
.
This will allow our live webapp to access our database on mLab.




Once you've done these steps, you should be able to type `mvn package heroku:deploy`

Take note: 

MongoDB.java is basically all the functions for interacting with database
View.java is for all the get and post functions.
ScrapsToSnacksMain.java is for acutally calling the functions in View.
API.java is for all the functions for interacting with API.


---



Take note that we have keys for MongoDB and for the FOOD2FORK API. 
Both are applied in different areas but were both put into the env.sh file



