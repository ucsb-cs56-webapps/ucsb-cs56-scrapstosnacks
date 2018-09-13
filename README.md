# ucsb-cs56-scrapstosnacks

Gives recipe suggestions based on ingredient input.
Contains both a database and an API that contain recipes.

Take a look at our website!
https://scrapstosnacks.herokuapp.com/




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
Keep your mlab.com window open; you'll need it. But now turn back to the command line where you cloned this repo. You'll see a file called env.sh.EXAMPLE. Copy it to env.sh

cp env.sh.EXAMPLE env.sh
Edit the env.sh file. The values in it are just example values. You'll need to change them as indicated in the next steps. For each step, you'll get some piece of information from the mlab.com window, so arrange your windows side by side where you can see them both.

Go to the mlab.com window and navigate to the page for your database. If you called it mlab-blog-demo, for example, that page will have the URL https://mlab.com/databases/mlab-blog-demo and it will information like this at the top (this is just an example)

```
To connect using the mongo shell:
  mongo ds143932.mlab.com:43932/cs56-m18-demo -u <dbuser> -p <dbpassword>
To connect using a driver via the standard MongoDB URI (what's this?):
  mongodb://<dbuser>:<dbpassword>@ds143932.mlab.com:43932/cs56-m18-demo
You should also see tabs for Collections, Users, Stats, Backups and Tools.
```

Now, open up env.sh for editing. The first two lines say:

```
export MONGODB_USER=testuser
export MONGODB_PASS=abcd1234
```

DO NOT CHANGE THESE TO THE USERNAME AND PASSWORD YOU USED TO LOGIN TO mlab.com!!! These are a different user and password, that you are going to create right now in the mlab.com window.

In your file, create a username (Literally using testuser is fine). For password, make up a good long random password, such as 8sfvlSFE13RGDG2. The longer and more random the better, because you are never going to have to remember or type in this password; You are going to enter it once in this file; then copy and paste it into MLab when you create the user/password, and then never have to type it again. Please DON'T literally use abcd1234 or 8sfvlSFE13RGDG2.

Type it in the env.sh file first. Then click the "Users" tab, and look over to the right side of the screen for the "Add database user" button. Click it, and enter the username and password that you just created (e.g. testuser and 8sfvlSFE13RGDG2. You'll want to copy/paste the password since you have to type it twice.)

Now, we'll move on to the other values in the env.sh file.

For these values, you are going to find these on the Mlab screen for your database:

For `MONGODB_NAME` change it from `cs56-m18-demo` to whatever the name of your database is (e.g. mlab-blog-demo)
For `MONGODB_HOST` and `MONGO_PORT` find the thing that says:

```
To connect using the mongo shell:
  mongo ds144023.mlab.com:47245/cs56-m18-demo -u <dbuser> -p <dbpassword>
```
In this example, `MONGODB_HOST` should be `ds144023.mlab.com` and `MONGODB_PORT` should be `47245`.


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


---




For reference, our mongodb data and variables are these:
```
To connect to database using the mongo shell:
%mongo ds020208.mlab.com:20208/scrapstosnacksdb -u <dbuser> -p <dbpassword>
To connect using a driver via the standard MongoDB URI (what's this?):
  mongodb://<dbuser>:<dbpassword>@ds020208.mlab.com:20208/scrapstosnacksdb
```


In the env.sh file:
```
#MongoDB KEYS
export MONGODB_USER=scrapstosnacksuser
export MONGODB_PASS=scrapstosnacks1
export MONGODB_NAME=scrapstosnacksdb
export MONGODB_HOST=ds020208.mlab.com
export MONGODB_PORT=20208


#FOOD2FORK KEY
export API_KEY=70f769ead6ccca4ee94f8078390f9f7d

#this is another key you can use if the first one runs out of API calls
export API_KEY1=83fab007b63aaf5344a8a877f54c2c5b
```


Take note that we have keys for MongoDB and for the FOOD2FORK API. 
Both are applied in different areas but were both put into the env.sh file
