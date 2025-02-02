#!/bin/bash

# Email address for notifications
EMAIL="yangjunhust@outlook.com"
SENDER_EMAIL="juny@uchicago.edu"
HOST_NAME=$(hostname)

# Check if a previous command was successful
if [ $? -eq 0 ]; then
    SUBJECT="✅ Command Completed Successfully on $HOST_NAME"
    MESSAGE="The last command finished successfully on $HOST_NAME."
else
    SUBJECT="❌ Command Failed on $HOST_NAME"
    MESSAGE="The last command encountered an error on $HOST_NAME."
fi

# Send the email notification
echo "$MESSAGE" | mail -s "$SUBJECT" "$EMAIL" -r "$SENDER_EMAIL"
