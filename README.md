# SlackAppender
SlackAppender is a Log4J Appender for routing log messages to Slack. 

# Requirements
- Log4J
- Java 8 or higher
- Slack Incoming Webhook integration enabled (https://api.slack.com/incoming-webhooks)

# Configuration
In order to use the SlackAppender, the SlackAppender needs to be added to the classpath of the Java application for which it is being used and Log4J should be configured to use the SlackAppender.

Appender classname: com.tod.utils.logging.SlackAppender

Available (appender specific) settings : 
- Url (mandatory): 
- UserName: 
- Channel: 
- Emoji: 

Example config through log4j properties
log4j.appender.slack=com.tod.utils.logging.SlackAppender
log4j.appender.slack.Threshold=ERROR
log4j.appender.slack.Url={the URL of the Incoming Webhook URL setup within Slack}
log4j.appender.slack.UserName={your username here}
log4j.appender.slack.Channel={the channel to post the messages in}
log4j.appender.slack.Emoji={the icon to use}

# Feature Requests & Bugs
Found a bug or would like to see a new feature implemented? Raise an issue in the [Issue Tracker](https://github.com/TheOrangeDots/SlackAppender/issues)

# Contributing
Eager to fix a bug or introduce a new feature? Clone the repository and issue a pull request

# License
SlackAppender is licensed under MIT License