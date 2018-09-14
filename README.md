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

To fix this, you need to take the following steps. Note that the steps involving mlab.com are pretty self-explanatory if you go to their website, so I'm not including much detail. You'll figure it out.

1. Create a free account at mlab.com

2. Create a deployment on the free tier

3. Create a database. Call it whatever you like; perhaps mlab-blog-demo for example.

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
Keep your mlab.com window open; you'll need it. But now turn back to the command line where you cloned this repo.
MongoDBTutorial:https://github.com/ucsb-cs56-m18/sparkjava-mongodb-mlab-tutorial
Go to the MongoDBTutorial and follow the instruction there and create the .env file.

Edit the env.sh file. The values in it are just example values. You'll need to change them as indicated in the next steps. For each step, you'll get some piece of information from the mlab.com window, so arrange your windows side by side where you can see them both.

Go to the mlab.com window and navigate to the page for your database. If you called it mlab-blog-demo, for example, that page will have the URL https://mlab.com/databases/mlab-blog-demo and it will information like this at the top (this is just an example)

create the heroku account and set up the env on the config Vars as exactly as what you did in the env file.

Once you've made these edits, you need to type the following so that these environment variables take effect:

```
. env.sh
```
This sets up the environment variables that the Java code will read from.

While the previous steps 1-9 are "one time only" steps, this final step must be done each time you log in to a term Unix terminal session; the environment variable are defined as part of the current process.

Once you've done these steps, you should be able to run and not see the error message about defining environment variables.

```
Error: Must define env variable MONGODB_USER
etc..
```
So try doing `mvn compile exec:java` again, and visiting http://localhost:4567

MangoDB.java is basically all the functions for interacting with database
View.java is for all the get and post functions.
ScrapsToSnacksMain.java is for acutally calling the functions in View.
API.java is for all the functions for interacting with API.


---



Take note that we have keys for MongoDB and for the FOOD2FORK API. 
Both are applied in different areas but were both put into the env.sh file
