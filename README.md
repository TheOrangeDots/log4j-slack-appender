# SlackAppender
SlackAppender is a [Log4j](https://logging.apache.org/log4j) [Appender]() for routing log messages to [Slack](http://slack.com). 

# Requirements
- Log4j 1.x (SlackAppender currently does NOT directly support Log4j 2, but should work in Log4j 2 through the [Log4j 1.x bridge](https://logging.apache.org/log4j/2.x/manual/migration.html))
- Java 8 or higher (Since it depends on [Nashorn](https://en.wikipedia.org/wiki/Nashorn_(JavaScript_engine)))
- A configured [Slack Incoming Webhook integration](https://api.slack.com/incoming-webhooks)

# Configuration
In order to use the SlackAppender, SlackAppender.jar needs to be added to the classpath of the Java application for which it is being used and Log4j should be configured to use the SlackAppender.

#### Adding SlackAppender.jar to the classpath:
The SlackAppender.jar can be dowloaded from the [releases section](https://github.com/TheOrangeDots/SlackAppender/releases). Only the dowload of SlackAppender.jar is required. The downloaded SlackAppender.jar needs to be put on the classpath of the Java application that is to use the Slack Appender. 

>#### ADDING SlackAppender.jar TO THE CLASSPATH FOR [SERVOY](http://servoy.com) USERS:
>When deploying your Servoy solution using WAR deployment, the SlackAppender.jar can be added to `application_server/lib` in the Servoy Developer installation from where the WAR export is made.
>
>When using a regular Servoy Application Server for deploying your solution, Servoy has a classpath where each library is specified specifically. Just adding SlackAppender.jar to the `application_server/lib` folder is not sufficient. A good option to add SlackAppender.jar to the classpath would be adding a subfolder called 'extra' to the `application_server/lib` folder and placing the SlackAppender.jar into this newly created folder. Once this is done, the classpath needs to be edited to include this new folder:
>- When using the [Service Wrapper](https://wiki.servoy.com/display/public/DOCS/Running+the+Server+As+a+Service), open `/application_server/service/wrapper.conf` in a text editor and add `wrapper.java.classpath.xx=lib\extra\SlackAppender.jar`, replacing xx with an appropriate incremental number (based in the already existing wrapper.java.classpath.xx entries)
>- When not using the Service Wrapper, the classpath is located in `application_server/servoy_server.bat/sh`: open this file in a text editor and add `lib/extra/*` to the classpath (making sure to use a semi-colon to properly separate the new entry form the existing entries)

>If you'd like a cleaner way to include the Slack appender in your Servoy Application Server, please vote for: [Provide a way to include extra JARs in the Servoy app server, without having to modify Servoy's .bat/.sh files](https://support.servoy.com/browse/SVY-9450) in the Servoy Support system

#### Configuring Log4j to utilize the Slack Appender
After adding SlackAppender.jar to the classpath, Log4j needs to be configured to correctly connect to Slack and route logmessages to it. Appenders for Log4j are configured using their className and the className for the Slack Appender is `com.tod.utils.logging.SlackAppender`.

Besides generic appender configuration options, the Slack Appender comes with teh following additional configuration options:
- Url (mandatory): the url provided by slack when setting up an Incoming Webhook in Slack 
- UserName: Overrides the userName configured in the Incoming Webhook in Slack
- Channel: Overrides the channel configured in the Incoming Webhook in Slack
- Emoji: Overrides the emoji configured in the Incoming Webhook in Slack

Log4j has several ways it can be configured, one of them being through a .properties file. Below an example of such a configuration, which routes only ERROR level logmessages to Slack:
```
log4j.appender.slack=com.tod.utils.logging.SlackAppender
log4j.appender.slack.Threshold=ERROR
log4j.appender.slack.Url=https\://hooks.slack.com/services/xxxxxxxx/xxxxxxxxx/xxxxxxxxxxxxxxxxxxxxxxxxxxx
log4j.appender.slack.UserName=Servoy
log4j.appender.slack.Channel=UAT
```

# Feature Requests & Bugs
Found a bug or would like to see a new feature implemented? Raise an issue in the [Issue Tracker](https://github.com/TheOrangeDots/SlackAppender/issues)

# Contributing
Eager to fix a bug or introduce a new feature? Clone the repository and issue a pull request

# License
SlackAppender is licensed under MIT License
