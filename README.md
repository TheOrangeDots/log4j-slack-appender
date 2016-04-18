# SlackAppender
SlackAppender is a Log4J Appender for routing log messages to Slack. 

# Requirements
- Log4J 1.x (SlackAppender currently does NOT directly support Log4j 2, but should work in Log4j 2 through the [Log4j 1.x bridge](https://logging.apache.org/log4j/2.x/manual/migration.html))
- Java 8 or higher (Since it depends on [Nashorn](https://en.wikipedia.org/wiki/Nashorn_(JavaScript_engine)))
- A configured [Slack Incoming Webhook integration](https://api.slack.com/incoming-webhooks)

# Configuration
In order to use the SlackAppender, the SlackAppender needs to be added to the classpath of the Java application for which it is being used and Log4J should be configured to use the SlackAppender.

The Slack Appender can be dowloaded from the [releases section](https://github.com/TheOrangeDots/SlackAppender/releases). Only the dowload of SlackAppender.jar is required. The downloaded SlackAppender.jar needs to be put on the classpath of the Java application that is to use the Slack Appender. 

<pre>
#### ADDING SlackAppender.jar TO THE CLASSPATH FOR SERVOY USERS:
Servoy has a classpath where each library is specified specifically. Just adding SlackAppender.jar to the `application_server/lib` folder is not sufficient. A good option to add SlackAppender.jar to the classpath would be adding a subfolder called 'extra' to the `application_server/lib` folder and placing the SlackAppender.jar into this newly created folder. Once this is done, the classpath needs to be edited to include this new folder:

- When [running Servoy as a Service](https://wiki.servoy.com/display/public/DOCS/Running+the+Server+As+a+Service), open `/application_server/service/wrapper.conf` in a text editor and add the following entry 'wrapper.java.classpath.xx=lib\extra\SlackAppender.jar', replacing xx with an appropriate incremental number (based in the already existing wrapper.java.classpath.xx entries)
- When not using the Service Wrapper, the classpath is located in `application_server/servoy_server.bat/sh`: open this file in a text editor and add `lib/extra/*` to the classpath (making sure to use a semi-colon to properly separate the new entry form the existing entries)

If you'd like a cleaner way to include the Slack appender in your Servoy Application Server, please vote for: [Provide a way to include extra JARs in the Servoy app server, without having to modify Servoy's .bat\/.sh files](https://support.servoy.com/browse/SVY-9450) in the Servoy Support system
</pre>

Appender classname: com.tod.utils.logging.SlackAppender

Available (appender specific) settings: 
- Url (mandatory): the url provided by slack when setting up an Incoming Webhook in Slack 
- UserName: Overrides the userName configured in the Incoming Webhook in Slack
- Channel: Overrides the channel configured in the Incoming Webhook in Slack
- Emoji: Overrides the emoji configured in the Incoming Webhook in Slack

Example config through log4j properties:
```
log4j.appender.slack=com.tod.utils.logging.SlackAppender
log4j.appender.slack.Threshold=ERROR
log4j.appender.slack.Url={the URL of the Incoming Webhook URL setup within Slack}
log4j.appender.slack.UserName={your username here}
log4j.appender.slack.Channel={the channel to post the messages in}
log4j.appender.slack.Emoji={the icon to use}
```

# Feature Requests & Bugs
Found a bug or would like to see a new feature implemented? Raise an issue in the [Issue Tracker](https://github.com/TheOrangeDots/SlackAppender/issues)

# Contributing
Eager to fix a bug or introduce a new feature? Clone the repository and issue a pull request

# License
SlackAppender is licensed under MIT License
