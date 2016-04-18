# SlackAppender
SlackAppender is a Log4J Appender for routing log messages to Slack. 

# Requirements
- Log4J 1.x (SlackAppender currently does NOT directly support Log4j 2, but should work in Log4j 2 through the [Log4j 1.x bridge](https://logging.apache.org/log4j/2.x/manual/migration.html))
- Java 8 or higher (Since it depends on [Nashorn](https://en.wikipedia.org/wiki/Nashorn_(JavaScript_engine)))
- A configured [Slack Incoming Webhook integration](https://api.slack.com/incoming-webhooks)

# Configuration
In order to use the SlackAppender, the SlackAppender needs to be added to the classpath of the Java application for which it is being used and Log4J should be configured to use the SlackAppender.

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
