---
topic: "Databases"
desc: "Storage that lasts longer than a single session"
---

# Sessions are temporary, so we need more permanent storage

A webapp can store information in a [Session](webapps/webapps-intro-part-5/) only temporarily.

Once the web browser is closed, any information the user entered that was stored in the session is lost.

So, if we want a web app to remember some information entered by the user from one session to the next, 
we need to store it somewhere more permanently.

# Why not just write to a file

For a desktop app where there is likely to be only one user at a time, writing to the file system is often a
way that data is stored between sessions.

For web apps, though, there are at least three reasons that we tend to use databases rather than 
reading or writing regular files:

* *concurrency*: Web apps can have many simultaneous users.  
    * There are multiple instances of your application running all at the same time, not just one.    
    * If they all try to write to the file, or read from the file at the same time, the application can have bugs.
    * Database systems, such as MySQL, Postgres, or MongoDB provide a solution to this problem
    * While it is possible to solve this problem with regular files, by the time you do that, what you have done is "reinvented the database"
* *scalability*: This also relates to the "many simultaneous users" thing
    * Having lots of users trying to write to the same file can be buggy, but the problem can be mangaged by
        making them all "wait their turn".
    * But, if you do that, then your application can become very. very. very. slow.
    * This is a another problem that database systems already have a solution to.
* *security*:  This is probably the most important reason
    * If your web app can write to the file system, then a hacker may find a way to leverage that capability as a weakness.
    * If you can write user data to the file system, a hacker may find a way to write malware (viruses, bots) to the file
       system, and trick the system into executing it.  Now you have an infected, compromised system.
    * The same thing can be done with databases (see [Little Bobby Tables](https://xkcd.com/327/), however most database
       systems have techniques in place to "sanitize database inputs".   Those aren't foolproof, but at least they
       exist, and they are typically part of the standard procedure for writing to the database.
    * The security issue is so important that many webapp providers *don't even let you write to the file system at all*,
        or if they do, they only allow you to write temporary files (which are pretty useless for storing persistent information.)
        
# Types of Databases: SQL vs. NoSQL

There are two main types of databases in common use with web applications:

* SQL-based databases, which include MySQL, Postgres, sqllite, and Oracle
* NoSQL databases, which include Redis and MongoDB

# Securing your database

Before deploying any system that store persistent data on a public-facing server (e.g. a Heroku server), it is important to
*secure your database*.  This means:

* Requiring users to login with some username/password that has been verified
    * If you allow unauthenticated users to store stuff in your database, you *will* find it full of unspeakably horrible
        stuff very soon.    The bots and the 4chan trolls will find it, and you'll wish you had taken the time to secure it.
    * At best, it will become "full" of spam inserted by bots.  At worst, it will be full of content that may disgust and
        traumatize you and others.    
* Making sure that all database inputs are properly sanitized
    * Among other things, this means avoiding code injection attacks where an attacker puts malicious code written 
         in JavaScript, Python, or other languages
        into the data that you allow users to enter, data that gets stored in your database, and might eventually 
        accidentally end up being run.     
    * Each web system has specific ways to take input from the users that may be *tainted* (that's a technical term),
        and *sanitize* it (another technical term) to ensure that any code an attacker may have inserted 
        is rendered harmless.
    * We'll cover the techniques for doing this when we cover specific database systems.
    
# What are we using in SPIS?

We are currently looking at two possibilities:

* MongoDB, which is a NoSQL document-based system
* Redis, which is a NoSQL key-value store

More details on these will be posted here as they become available.

# References

* a comparison of NoSQL databases
    * [https://www.digitalocean.com/community/tutorials/a-comparison-of-nosql-database-management-systems-and-models](https://www.digitalocean.com/community/tutorials/a-comparison-of-nosql-database-management-systems-and-models)
